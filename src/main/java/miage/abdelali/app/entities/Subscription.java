package miage.abdelali.app.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "subscriptions")
public class Subscription {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String productCode;
  private String versionCode;

  private String civilite;
  private String nom;
  private String prenom;
  private LocalDate birthDate;
  private String email;
  private String tel;
  private String cp;
  private String ville;

  private LocalDate dateEffet;
  private String periodicite;
  private String iban;

  private BigDecimal primeNette;
  private BigDecimal frais;
  private BigDecimal taxe;
  private BigDecimal primeTTC;
  private String currency;

  @Column(columnDefinition = "TEXT")
  private String reqJson;

  @Column(columnDefinition = "TEXT")
  private String resJson;

  @Column(length = 32)
  private String status;

  @Column(columnDefinition = "TEXT")
  private String signature;

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  @PrePersist
  public void prePersist() {
    LocalDateTime now = LocalDateTime.now();
    createdAt = now;
    updatedAt = now;
    if (status == null || status.isBlank()) status = "SIGNED";
  }

  @PreUpdate
  public void preUpdate() {
    updatedAt = LocalDateTime.now();
  }

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public String getProductCode() { return productCode; }
  public void setProductCode(String productCode) { this.productCode = productCode; }

  public String getVersionCode() { return versionCode; }
  public void setVersionCode(String versionCode) { this.versionCode = versionCode; }

  public String getCivilite() { return civilite; }
  public void setCivilite(String civilite) { this.civilite = civilite; }

  public String getNom() { return nom; }
  public void setNom(String nom) { this.nom = nom; }

  public String getPrenom() { return prenom; }
  public void setPrenom(String prenom) { this.prenom = prenom; }

  public LocalDate getBirthDate() { return birthDate; }
  public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }

  public String getTel() { return tel; }
  public void setTel(String tel) { this.tel = tel; }

  public String getCp() { return cp; }
  public void setCp(String cp) { this.cp = cp; }

  public String getVille() { return ville; }
  public void setVille(String ville) { this.ville = ville; }

  public LocalDate getDateEffet() { return dateEffet; }
  public void setDateEffet(LocalDate dateEffet) { this.dateEffet = dateEffet; }

  public String getPeriodicite() { return periodicite; }
  public void setPeriodicite(String periodicite) { this.periodicite = periodicite; }

  public String getIban() { return iban; }
  public void setIban(String iban) { this.iban = iban; }

  public BigDecimal getPrimeNette() { return primeNette; }
  public void setPrimeNette(BigDecimal primeNette) { this.primeNette = primeNette; }

  public BigDecimal getFrais() { return frais; }
  public void setFrais(BigDecimal frais) { this.frais = frais; }

  public BigDecimal getTaxe() { return taxe; }
  public void setTaxe(BigDecimal taxe) { this.taxe = taxe; }

  public BigDecimal getPrimeTTC() { return primeTTC; }
  public void setPrimeTTC(BigDecimal primeTTC) { this.primeTTC = primeTTC; }

  public String getCurrency() { return currency; }
  public void setCurrency(String currency) { this.currency = currency; }

  public String getReqJson() { return reqJson; }
  public void setReqJson(String reqJson) { this.reqJson = reqJson; }

  public String getResJson() { return resJson; }
  public void setResJson(String resJson) { this.resJson = resJson; }

  public String getStatus() { return status; }
  public void setStatus(String status) { this.status = status; }

  public String getSignature() { return signature; }
  public void setSignature(String signature) { this.signature = signature; }

  public LocalDateTime getCreatedAt() { return createdAt; }
  public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

  public LocalDateTime getUpdatedAt() { return updatedAt; }
  public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
