package miage.abdelali.app.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import miage.abdelali.app.Dto.DevisRequestDto;
import miage.abdelali.app.Dto.DevisResponseDto;
import miage.abdelali.app.Services.TariffService;

@RestController
@RequestMapping("/api/tariffs")
@Validated
public class TariffController {

  private final TariffService tariffService;

  public TariffController(TariffService tariffService) {
    this.tariffService = tariffService;
  }

  @PostMapping("/devis")
	public ResponseEntity<DevisResponseDto> Devis(@RequestBody @Valid DevisRequestDto req) {
    return ResponseEntity.ok(tariffService.Devis(req));
  }
}
