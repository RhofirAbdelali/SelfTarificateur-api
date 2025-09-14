package miage.abdelali.app.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import miage.abdelali.app.entities.UserAccount;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
	Optional<UserAccount> findByEmailIgnoreCase(String email);

	boolean existsByEmailIgnoreCase(String email);
}
