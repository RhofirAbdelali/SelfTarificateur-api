package miage.abdelali.app.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import miage.abdelali.app.entities.TariffVersion;
import miage.abdelali.app.services.TariffVersionService;

@RestController
@RequestMapping("/api/tariff-versions")
public class TariffVersionPublicController {

  private final TariffVersionService service;

  public TariffVersionPublicController(TariffVersionService service) {
    this.service = service;
  }

  @GetMapping("/active")
  public ResponseEntity<TariffVersion> active() {
    TariffVersion v = service.findActive();
    return v == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(v);
  }
}
