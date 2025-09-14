package miage.abdelali.app.config;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import miage.abdelali.app.entities.Role;

@Service
public class JwtService {

	@Value("${app.jwt.secret}")
	private String secret;

	@Value("${app.jwt.expiration-ms:3600000}")
	private long expirationMs;

	private SecretKey signingKey() {
		byte[] keyBytes = (secret == null ? "" : secret).getBytes(StandardCharsets.UTF_8);
		if (keyBytes.length < 32) {
			byte[] padded = new byte[32];
			for (int i = 0; i < padded.length; i++) {
				padded[i] = keyBytes[i % keyBytes.length];
			}
			keyBytes = padded;
		}
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String generateToken(String subject, Set<Role> roles) {
		Instant now = Instant.now();
		Instant exp = now.plusMillis(expirationMs);

		Collection<String> roleNames = (roles == null ? Set.<String>of()
				: roles.stream().map(Role::getName).collect(Collectors.toSet()));

		return Jwts.builder().subject(subject).claim("roles", roleNames).issuedAt(Date.from(now))
				.expiration(Date.from(exp)).signWith(signingKey()).compact();
	}

	public String extractUsername(String token) {
		Claims claims = Jwts.parser().verifyWith(signingKey()).build().parseSignedClaims(token).getPayload();
		return claims.getSubject();
	}

	public boolean isValid(String token, UserDetails userDetails) {
		try {
			Claims claims = Jwts.parser().verifyWith(signingKey()).build().parseSignedClaims(token).getPayload();

			String sub = claims.getSubject();
			Date exp = claims.getExpiration();

			return sub != null && exp != null && exp.after(new Date())
					&& sub.equalsIgnoreCase(userDetails.getUsername());
		} catch (Exception e) {
			return false;
		}
	}

	public long getExpirationMs() {
		return expirationMs;
	}
}
