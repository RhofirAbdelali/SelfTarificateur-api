package miage.abdelali.app.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import miage.abdelali.app.dto.DevisRequestDto;
import miage.abdelali.app.dto.DevisResponseDto;
import miage.abdelali.app.dto.SubscriptionCreateDto;
import miage.abdelali.app.entities.Subscription;
import miage.abdelali.app.services.EmailService;
import miage.abdelali.app.services.PdfService;
import miage.abdelali.app.services.SubscriptionService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api")
public class SubscriptionController {

  private final SubscriptionService service;
  private final EmailService emailService;
  private final PdfService pdfService;
  private final ObjectMapper mapper;

  public SubscriptionController(
      SubscriptionService service,
      EmailService emailService,
      PdfService pdfService,
      ObjectMapper mapper
  ) {
    this.service = service;
    this.emailService = emailService;
    this.pdfService = pdfService;
    this.mapper = mapper;
  }

  @PostMapping("/subscriptions")
  public ResponseEntity<Subscription> create(@RequestBody SubscriptionCreateDto dto) {
    Subscription saved = service.create(dto);
    return ResponseEntity.created(URI.create("/api/admin/subscriptions/" + saved.getId())).body(saved);
  }

  @GetMapping("/admin/subscriptions")
  public List<Subscription> list() {
    return service.list();
  }

  @GetMapping("/admin/subscriptions/{id}")
  public ResponseEntity<Subscription> getOne(@PathVariable Long id) {
    Subscription s = service.find(id);
    return s == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(s);
  }

  @PostMapping("/admin/subscriptions/{id}/resend-email")
  public ResponseEntity<?> resend(@PathVariable Long id) {
    Subscription s = service.find(id);
    if (s == null) return ResponseEntity.notFound().build();

    try {
      DevisRequestDto req = hasText(s.getReqJson()) ? mapper.readValue(s.getReqJson(), DevisRequestDto.class) : null;
      DevisResponseDto res = hasText(s.getResJson()) ? mapper.readValue(s.getResJson(), DevisResponseDto.class) : null;

      byte[] pdf = null;
      String pdfName = null;
      if (req != null && res != null) {
        String nomAffiche = ((s.getPrenom() == null ? "" : s.getPrenom() + " ")
                           + (s.getNom() == null ? "" : s.getNom())).trim();
        if (nomAffiche.isBlank()) nomAffiche = "Client";
        pdf = pdfService.buildDevisPdf(nomAffiche, req, res);
        pdfName = "devis_" + (s.getProductCode() == null ? "produit" : s.getProductCode())
                + "_" + (s.getVersionCode() == null ? "version" : s.getVersionCode()) + ".pdf";
      }

      emailService.sendSubscriptionConfirmation(s, pdf, pdfName);
      return ResponseEntity.noContent().build();
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body("Erreur envoi email: " + e.getMessage());
    }
  }

  private static boolean hasText(String v) {
    return v != null && !v.isBlank();
  }
  
  @GetMapping("/admin/subscriptions/{id}/pdf")
  public ResponseEntity<byte[]> downloadPdf(@PathVariable Long id) {
    Subscription s = service.find(id);
    if (s == null) return ResponseEntity.notFound().build();

    try {
      DevisRequestDto req = (s.getReqJson()!=null && !s.getReqJson().isBlank())
          ? mapper.readValue(s.getReqJson(), DevisRequestDto.class) : null;
      DevisResponseDto res = (s.getResJson()!=null && !s.getResJson().isBlank())
          ? mapper.readValue(s.getResJson(), DevisResponseDto.class) : null;

      if (req == null || res == null) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(("PDF indisponible : req/res manquants pour la souscription " + id).getBytes());
      }

      String nomAffiche = ((s.getPrenom()==null?"":s.getPrenom()+" ")
                         + (s.getNom()==null?"":s.getNom())).trim();
      if (nomAffiche.isBlank()) nomAffiche = "Client";

      byte[] pdf = pdfService.buildDevisPdf(nomAffiche, req, res);
      String filename = "devis_" + (s.getProductCode()==null?"produit":s.getProductCode())
                      + "_" + (s.getVersionCode()==null?"version":s.getVersionCode())
                      + "_id" + s.getId() + ".pdf";

      HttpHeaders h = new HttpHeaders();
      h.setContentType(MediaType.APPLICATION_PDF);
      h.setContentDisposition(ContentDisposition.attachment().filename(filename).build());
      return new ResponseEntity<>(pdf, h, HttpStatus.OK);
    } catch (Exception e) {
      return ResponseEntity.internalServerError()
          .body(("Erreur génération PDF : " + e.getMessage()).getBytes());
    }
  }
}
