package miage.abdelali.app.controllers;

import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import miage.abdelali.app.config.JwtService;
import miage.abdelali.app.dto.*;
import miage.abdelali.app.entities.UserAccount;
import miage.abdelali.app.repositories.UserAccountRepository;

@RestController
@RequestMapping("/api/auth")
@Validated
public class AuthController {

	private static final Logger log = LoggerFactory.getLogger(AuthController.class);

	private final AuthenticationManager authManager;
	private final UserAccountRepository userRepo;
	private final PasswordEncoder encoder;
	private final JwtService jwt;

	public AuthController(AuthenticationManager authManager, UserAccountRepository userRepo, PasswordEncoder encoder,
			JwtService jwt) {
		this.authManager = authManager;
		this.userRepo = userRepo;
		this.encoder = encoder;
		this.jwt = jwt;
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody @Valid AuthLoginRequest req) {
		try {
			Authentication auth = authManager
					.authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));

			var user = userRepo.findByEmailIgnoreCase(req.getEmail()).orElseThrow();
			String token = jwt.generateToken(user.getEmail(), user.getRoles());

			var resp = new AuthLoginResponse();
			resp.setToken(token);
			resp.setTokenType("Bearer");
			resp.setEmail(user.getEmail());
			resp.setRoles(user.getRoles().stream().map(r -> "ROLE_" + r.getName()).toList());
			resp.setExpiresAt(Instant.now().plusMillis(jwt.getExpirationMs()));
			return ResponseEntity.ok(resp);

		} catch (BadCredentialsException e) {
			return ResponseEntity.status(401)
					.body(java.util.Map.of("error", "unauthorized", "message", "Identifiants invalides"));
		} catch (Exception e) {
			log.error("Login error", e);
			return ResponseEntity.status(500).body(java.util.Map.of("error", "internal_error", "message",
					"Une erreur est survenue. Réessayez plus tard."));
		}
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest req) {
		if (userRepo.existsByEmailIgnoreCase(req.getEmail())) {
			return ResponseEntity.badRequest()
					.body(java.util.Map.of("error", "bad_request", "message", "E-mail déjà utilisé"));
		}
		var u = new UserAccount();
		u.setEmail(req.getEmail());
		u.setPassword(encoder.encode(req.getPassword()));
		u.setEnabled(true);
		userRepo.save(u);

		return ResponseEntity.ok(java.util.Map.of("message", "Compte créé"));
	}
}
