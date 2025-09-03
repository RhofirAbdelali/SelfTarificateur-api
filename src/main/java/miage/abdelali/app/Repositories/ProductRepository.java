package miage.abdelali.app.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import miage.abdelali.app.Entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	boolean existsByCode(String code);
}