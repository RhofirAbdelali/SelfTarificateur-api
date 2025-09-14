package miage.abdelali.app.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<?> handleBadCredentials(BadCredentialsException ex) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(Map.of("error", "unauthorized", "message", "Identifiants invalides"));
	}

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<?> handleAuth(AuthenticationException ex) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(Map.of("error", "unauthorized", "message", "Accès non autorisé"));
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<?> handleDenied(AccessDeniedException ex) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN)
				.body(Map.of("error", "forbidden", "message", "Accès refusé"));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
		return ResponseEntity.badRequest().body(Map.of("error", "bad_request", "message", "Requête invalide"));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleAll(Exception ex) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(Map.of("error", "internal_error", "message", "Une erreur est survenue. Réessayez plus tard."));
	}

}
