package miage.abdelali.app.Controllers;

import miage.abdelali.app.Entities.TariffVersion;
import miage.abdelali.app.Services.TariffVersionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tariffs")
public class TariffVersionController {

	private final TariffVersionService tariffService;

	public TariffVersionController(TariffVersionService tariffService) {
		this.tariffService = tariffService;
	}

	@GetMapping
	public List<TariffVersion> getAll() {
		return tariffService.findAll();
	}

	@PostMapping
	public TariffVersion create(@RequestBody TariffVersion tariff) {
		return tariffService.save(tariff);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		tariffService.delete(id);
	}
}