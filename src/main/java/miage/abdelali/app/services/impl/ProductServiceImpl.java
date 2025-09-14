package miage.abdelali.app.services.impl;

import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import miage.abdelali.app.dto.ProductCreateDto;
import miage.abdelali.app.entities.Product;
import miage.abdelali.app.repositories.ProductRepository;
import miage.abdelali.app.services.ProductService;

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
		Product p = new Product();
		p.setCode(code);
		p.setLabel(label);
		return repo.save(p);
	}

	@Override
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

	@Override
	public Product findById(Long id) {
		return repo.findById(id).orElse(null);
	}

	@Override
	public Product update(Long id, ProductCreateDto dto) {
		Product existing = repo.findById(id).orElse(null);
		if (existing == null)
			return null;

		if (dto.getCode() != null && !dto.getCode().isBlank()) {
			String newCode = dto.getCode().trim();
			repo.findByCode(newCode).ifPresent(other -> {
				if (!Objects.equals(other.getId(), existing.getId())) {
					throw new IllegalArgumentException("code déjà utilisé");
				}
			});
			existing.setCode(newCode);
		}
		if (dto.getLabel() != null && !dto.getLabel().isBlank()) {
			existing.setLabel(dto.getLabel().trim());
		}
		return repo.save(existing);
	}
}
