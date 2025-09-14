package miage.abdelali.app.controllers;

import miage.abdelali.app.dto.ProductCreateDto;
import miage.abdelali.app.entities.Product;
import miage.abdelali.app.services.ProductService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	private final ProductService service;

	public ProductController(ProductService service) {
		this.service = service;
	}

	// Public
	@GetMapping
	public List<Product> list() {
		return service.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Product> getOne(@PathVariable Long id) {
		Product p = service.findById(id);
		return p != null ? ResponseEntity.ok(p) : ResponseEntity.notFound().build();
	}

	// Admin  
	@PostMapping
	public ResponseEntity<?> create(@RequestBody ProductCreateDto dto) {
		try {
			if (dto == null || isBlank(dto.getCode()) || isBlank(dto.getLabel())) {
				return badRequest("code et label sont obligatoires");
			}
			if (service.existsByCode(dto.getCode().trim())) {
				return badRequest("code déjà utilisé");
			}
			Product saved = service.create(dto.getCode().trim(), dto.getLabel().trim());
			return ResponseEntity.created(URI.create("/api/products/" + saved.getId())).body(saved);
		} catch (Exception e) {
			return badRequest("Requête invalide : " + e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ProductCreateDto dto) {
		try {
			Product updated = service.update(id, dto);
			if (updated == null)
				return ResponseEntity.notFound().build();
			return ResponseEntity.ok(updated);
		} catch (Exception e) {
			return badRequest("Requête invalide : " + e.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		Product p = service.findById(id);
		if (p == null)
			return ResponseEntity.notFound().build();
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	private static boolean isBlank(String s) {
		return s == null || s.isBlank();
	}

	private static ResponseEntity<Map<String, Object>> badRequest(String msg) {
		Map<String, Object> body = new HashMap<>();
		body.put("error", "bad_request");
		body.put("message", msg);
		return ResponseEntity.badRequest().body(body);
	}
}