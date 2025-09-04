package miage.abdelali.app.Services;

import java.util.List;
import miage.abdelali.app.Entities.Product;

public interface ProductService {
	boolean existsByCode(String code);

	Product create(String code, String label);

	List<Product> findAll();

	Product save(Product product);

	void delete(Long id);
}