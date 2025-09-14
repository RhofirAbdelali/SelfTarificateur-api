package miage.abdelali.app.services.impl;

import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import miage.abdelali.app.repositories.UserAccountRepository;

@Service
public class DbUserDetailsService implements UserDetailsService {

	private final UserAccountRepository repo;

	public DbUserDetailsService(UserAccountRepository repo) {
		this.repo = repo;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		var user = repo.findByEmailIgnoreCase(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

		var authorities = user.getRoles().stream().map(r -> new SimpleGrantedAuthority("ROLE_" + r.getName()))
				.collect(Collectors.toList());

		return org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
				.password(user.getPassword()).authorities(authorities).disabled(!user.isEnabled()).build();
	}
}
