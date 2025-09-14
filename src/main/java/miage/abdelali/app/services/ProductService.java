package miage.abdelali.app.services;

import java.util.List;
import miage.abdelali.app.dto.ProductCreateDto;
import miage.abdelali.app.entities.Product;

public interface ProductService {
	boolean existsByCode(String code);

	Product create(String code, String label);

	List<Product> findAll();

	Product save(Product product);

	void delete(Long id);

	Product findById(Long id);

	Product update(Long id, ProductCreateDto dto);
}
