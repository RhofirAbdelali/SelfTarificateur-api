package miage.abdelali.app.controllers;

import miage.abdelali.app.dto.TariffVersionCreateDto;
import miage.abdelali.app.entities.TariffVersion;
import miage.abdelali.app.services.TariffVersionService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/admin/tariff-versions")
public class TariffVersionController {

    private final TariffVersionService service;
    public TariffVersionController(TariffVersionService service) { this.service = service; }

    @GetMapping
    public List<TariffVersion> list() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TariffVersion> getOne(@PathVariable Long id) {
        TariffVersion t = service.findById(id);
        return t != null ? ResponseEntity.ok(t) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody TariffVersionCreateDto dto) {
        try {
            if (dto == null || isBlank(dto.getVersionCode())) return badRequest("versionCode est obligatoire");
            TariffVersion t = new TariffVersion();
            t.setVersionCode(dto.getVersionCode().trim());
            if (!isBlank(dto.getEffectiveFrom())) t.setEffectiveFrom(LocalDate.parse(dto.getEffectiveFrom()));
            if (!isBlank(dto.getEffectiveTo()))   t.setEffectiveTo(LocalDate.parse(dto.getEffectiveTo()));
            t.setActive(false);
            TariffVersion saved = service.save(t);
            return ResponseEntity.created(URI.create("/api/admin/tariff-versions/" + saved.getId())).body(saved);
        } catch (Exception e) {
            return badRequest("Requête invalide : " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody TariffVersionCreateDto dto) {
        try {
            TariffVersion updated = service.update(id, dto);
            if (updated == null) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return badRequest("Requête invalide : " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        TariffVersion t = service.findById(id);
        if (t == null) return ResponseEntity.notFound().build();
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<?> activate(@PathVariable Long id) {
        TariffVersion activated = service.activateExclusive(id);
        if (activated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(activated);
    }

    private static boolean isBlank(String s) { return s == null || s.isBlank(); }

    private static ResponseEntity<Map<String, Object>> badRequest(String msg) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "bad_request");
        body.put("message", msg);
        return ResponseEntity.badRequest().body(body);
    }
    
}
