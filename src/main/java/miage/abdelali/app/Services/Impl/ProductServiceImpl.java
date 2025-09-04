package miage.abdelali.app.Services.Impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import miage.abdelali.app.Entities.Product;
import miage.abdelali.app.Repositories.ProductRepository;
import miage.abdelali.app.Services.ProductService;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

	private final ProductRepository repo;

	public ProductServiceImpl(ProductRepository repo) {
		this.repo = repo;
	}

	@Override
	public boolean existsByCode(String code) {
		return repo.existsByCode(code);
	}

	@Override
	public Product create(String code, String label) {
		if (repo.existsByCode(code)) {
			throw new IllegalArgumentException("le code produit existe déja: " + code);
		}
		Product p = new Product();
		p.setCode(code);
		p.setLabel(label);
		p.setActive(true);
		return repo.save(p);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Product> findAll() {
		return repo.findAll();
	}

	@Override
	public Product save(Product product) {
		return repo.save(product);
	}

	@Override
	public void delete(Long id) {
		repo.deleteById(id);
	}
}