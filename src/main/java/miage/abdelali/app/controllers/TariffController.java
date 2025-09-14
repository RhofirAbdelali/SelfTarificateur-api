package miage.abdelali.app.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ContentDisposition;
import java.time.format.DateTimeFormatter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import miage.abdelali.app.dto.DevisPdfRequest;
import miage.abdelali.app.dto.DevisRequestDto;
import miage.abdelali.app.dto.DevisResponseDto;
import miage.abdelali.app.dto.EstimationRequestDto;
import miage.abdelali.app.entities.enums.Fumeur;
import miage.abdelali.app.services.PdfService;
import miage.abdelali.app.services.TariffService;

@RestController
@RequestMapping("/api/tariffs")
@Validated
public class TariffController {

	private final TariffService tariffService;
	private final PdfService pdfService;

	public TariffController(TariffService tariffService, PdfService pdfService) {
		this.tariffService = tariffService;
		this.pdfService = pdfService;
	}

	@PostMapping("/devis")
	public ResponseEntity<?> devis(@RequestBody @Valid DevisRequestDto req) {
		try {
			DevisResponseDto out = tariffService.devis(req);
			return ResponseEntity.ok(out);
		} catch (Exception e) {
			return badRequest("Requête invalide : " + e.getMessage());
		}
	}

	@PostMapping("/estimation")
	public ResponseEntity<?> estimation(@RequestBody @Valid EstimationRequestDto req) {
		try {
			DevisRequestDto full = mapWizardToDevis(req);
			DevisResponseDto out = tariffService.devis(full);
			return ResponseEntity.ok(out);
		} catch (Exception e) {
			return badRequest("Requête invalide : " + e.getMessage());
		}
	}

	private DevisRequestDto mapWizardToDevis(EstimationRequestDto req) {
		DevisRequestDto d = new DevisRequestDto();
		d.setProductCode("PREV-DEC-IND");
		d.setVersionCode("2025.07");

		d.setBirthDate(req.getBirthDate());
		d.setCapital(nz(req.getCapitalDeces()));
		d.setSmoker(req.getFumeur() == Fumeur.OUI ? "OUI" : "NON");

		d.setCouverture(req.getCouverture());
		d.setNiveauProtection(req.getNiveauProtection());
		d.setIndemniteJournaliere(req.getIndemniteJournaliere());
		d.setFranchiseJours(req.getFranchiseJours());
		d.setAssureActuel(req.getAssureActuel());
		d.setBeneficiaire(req.getBeneficiaire());
		d.setStatutPro(req.getStatutPro());

		return d;
	}

	private static BigDecimal nz(BigDecimal v) {
		return v == null ? BigDecimal.ZERO : v;
	}

	private static ResponseEntity<Map<String, Object>> badRequest(String msg) {
		Map<String, Object> body = new HashMap<>();
		body.put("error", "bad_request");
		body.put("message", msg);
		return ResponseEntity.badRequest().body(body);
	}

	@PostMapping(value = "/devis/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> devisPdf(@RequestParam(name = "nom", required = false) String nom,
			@RequestBody @Valid DevisRequestDto req) {
		var res = tariffService.devis(req);

		byte[] pdf = pdfService.buildDevisPdf(nom, req, res);

		String filename = "devis-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmm")) + ".pdf";

		var headers = new HttpHeaders();
		headers.setContentDisposition(ContentDisposition.attachment().filename(filename).build());
		headers.setContentType(MediaType.APPLICATION_PDF);

		return ResponseEntity.ok().headers(headers).body(pdf);
	}

}
