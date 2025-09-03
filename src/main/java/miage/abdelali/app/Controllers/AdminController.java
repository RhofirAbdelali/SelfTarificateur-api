package miage.abdelali.app.Controllers;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import miage.abdelali.app.Dto.ProductCreateDto;
import miage.abdelali.app.Dto.TariffVersionCreateDto;
import miage.abdelali.app.Entities.Product;
import miage.abdelali.app.Entities.TariffVersion;
import miage.abdelali.app.Repositories.ProductRepository;
import miage.abdelali.app.Repositories.TariffVersionRepository;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

	private final ProductRepository productRepo;
	private final TariffVersionRepository tariffRepo;

	public AdminController(ProductRepository productRepo, TariffVersionRepository tariffRepo) {
		this.productRepo = productRepo;
		this.tariffRepo = tariffRepo;
	}

	@PostMapping("/products")
	public ResponseEntity<?> createProduct(@RequestBody @Valid ProductCreateDto dto) {
		if (productRepo.existsByCode(dto.getCode())) {
			return ResponseEntity.badRequest().body(Map.of("error", "code_already_exists"));
		}
		Product p = new Product();
		p.setCode(dto.getCode());
		p.setLabel(dto.getLabel());
		p.setActive(true);
		Product saved = productRepo.save(p);
		return ResponseEntity.ok(Map.of("id", saved.getId(), "code", saved.getCode(), "label", saved.getLabel()));
	}

	@PostMapping("/tariffs")
	public ResponseEntity<?> createTariff(@RequestBody @Valid TariffVersionCreateDto dto) {
		TariffVersion t = new TariffVersion();
		t.setVersionCode(dto.getVersionCode());
		if (dto.getEffectiveFrom() != null && !dto.getEffectiveFrom().isBlank()) {
			t.setEffectiveFrom(LocalDate.parse(dto.getEffectiveFrom()));
		}
		if (dto.getEffectiveTo() != null && !dto.getEffectiveTo().isBlank()) {
			t.setEffectiveTo(LocalDate.parse(dto.getEffectiveTo()));
		}
		TariffVersion saved = tariffRepo.save(t);

		Map<String, Object> body = new java.util.HashMap<>();
		body.put("id", saved.getId());
		body.put("versionCode", saved.getVersionCode());
		if (saved.getEffectiveFrom() != null)
			body.put("effectiveFrom", saved.getEffectiveFrom());
		if (saved.getEffectiveTo() != null)
			body.put("effectiveTo", saved.getEffectiveTo());

		return ResponseEntity.ok(body);
	}

}