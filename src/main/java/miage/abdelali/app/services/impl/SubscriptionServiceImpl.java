package miage.abdelali.app.services.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import miage.abdelali.app.dto.DevisRequestDto;
import miage.abdelali.app.dto.DevisResponseDto;
import miage.abdelali.app.dto.SubscriptionCreateDto;
import miage.abdelali.app.entities.Subscription;
import miage.abdelali.app.repositories.SubscriptionRepository;
import miage.abdelali.app.services.EmailService;
import miage.abdelali.app.services.PdfService;
import miage.abdelali.app.services.SubscriptionService;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

  private final SubscriptionRepository repo;
  private final EmailService emailService;
  private final PdfService pdfService;
  private final ObjectMapper mapper;

  public SubscriptionServiceImpl(
      SubscriptionRepository repo,
      EmailService emailService,
      PdfService pdfService,
      ObjectMapper mapper
  ) {
    this.repo = repo;
    this.emailService = emailService;
    this.pdfService = pdfService;
    this.mapper = mapper;
  }

  @Override
  public Subscription create(SubscriptionCreateDto dto) {
    Subscription s = new Subscription();
    s.setProductCode(trim(dto.getProductCode()));
    s.setVersionCode(trim(dto.getVersionCode()));

    s.setCivilite(trim(dto.getCivilite()));
    s.setNom(trim(dto.getNom()));
    s.setPrenom(trim(dto.getPrenom()));
    s.setBirthDate(parseDate(dto.getBirthDate()));
    s.setEmail(trim(dto.getEmail()));
    s.setTel(trim(dto.getTel()));
    s.setCp(trim(dto.getCp()));
    s.setVille(trim(dto.getVille()));

    s.setDateEffet(parseDate(dto.getDateEffet()));
    s.setPeriodicite(trim(dto.getPeriodicite()));
    s.setIban(trim(dto.getIban()));

    s.setPrimeNette(dto.getPrimeNette());
    s.setFrais(dto.getFrais());
    s.setTaxe(dto.getTaxe());
    s.setPrimeTTC(dto.getPrimeTTC());
    s.setCurrency(trim(dto.getCurrency()));

    s.setReqJson(dto.getReqJson());
    s.setResJson(dto.getResJson());

    if (StringUtils.hasText(dto.getStatus())) s.setStatus(dto.getStatus().trim());
    s.setSignature(trim(dto.getSignature()));

    Subscription saved = repo.save(s);
 
    try {
      DevisRequestDto req = null;
      DevisResponseDto res = null;
      if (StringUtils.hasText(saved.getReqJson())) {
        req = mapper.readValue(saved.getReqJson(), DevisRequestDto.class);
      }
      if (StringUtils.hasText(saved.getResJson())) {
        res = mapper.readValue(saved.getResJson(), DevisResponseDto.class);
      }
      if (req != null && res != null) {
        String nomAffiche = (saved.getPrenom() == null ? "" : saved.getPrenom()+" ")
                          + (saved.getNom() == null ? "" : saved.getNom());
        nomAffiche = nomAffiche.trim().isEmpty() ? "Client" : nomAffiche.trim();

        byte[] pdf = pdfService.buildDevisPdf(nomAffiche, req, res);
        String pdfName = "devis_" + (saved.getProductCode()==null?"produit":saved.getProductCode())
                       + "_" + (saved.getVersionCode()==null?"version":saved.getVersionCode()) + ".pdf";
        emailService.sendSubscriptionConfirmation(saved, pdf, pdfName);
      } else { 
        emailService.sendSubscriptionConfirmation(saved, null, null);
      }
    } catch (Exception ignore) { 
    }

    return saved;
  }

  @Override
  public List<Subscription> list() {
    return repo.findAllByOrderByCreatedAtDesc();
  }

  @Override
  public Subscription find(Long id) {
    return repo.findById(id).orElse(null);
  }

  private static String trim(String v) { return v == null ? null : v.trim(); }
  private static LocalDate parseDate(String v) {
    if (!StringUtils.hasText(v)) return null;
    return LocalDate.parse(v);
  }
}
