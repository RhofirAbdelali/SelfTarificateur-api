package miage.abdelali.app.Controllers;

import miage.abdelali.app.Entities.Product;
import miage.abdelali.app.Services.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	private final ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping
	public List<Product> getAll() {
		return productService.findAll();
	}

	@PostMapping
	public Product create(@RequestBody Product product) {
		return productService.save(product);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		productService.delete(id);
	}
}