package miage.abdelali.app.config;

import java.io.IOException;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	private final JwtService jwt;
	private final UserDetailsService uds;

	public JwtAuthFilter(JwtService jwt, @Lazy UserDetailsService uds) {
		this.jwt = jwt;
		this.uds = uds;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws ServletException, IOException {

		final String auth = req.getHeader(HttpHeaders.AUTHORIZATION);
		if (auth == null || !auth.startsWith("Bearer ")) {
			chain.doFilter(req, res);
			return;
		}

		final String token = auth.substring(7);

		String principal;
		try {
			principal = jwt.extractUsername(token);
		} catch (Exception e) {
			chain.doFilter(req, res);
			return;
		}

		if (principal != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails user = uds.loadUserByUsername(principal);
			if (jwt.isValid(token, user)) {
				var authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}

		chain.doFilter(req, res);
	}
}
