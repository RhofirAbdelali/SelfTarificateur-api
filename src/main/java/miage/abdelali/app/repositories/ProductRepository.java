package miage.abdelali.app.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import miage.abdelali.app.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	Optional<Product> findByCode(String code);

	boolean existsByCode(String code);
}