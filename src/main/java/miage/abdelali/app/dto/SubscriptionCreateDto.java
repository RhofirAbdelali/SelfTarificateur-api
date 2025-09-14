package miage.abdelali.app.dto;

import java.math.BigDecimal;

public class SubscriptionCreateDto {
  private String productCode;
  private String versionCode;

  private String civilite;
  private String nom;
  private String prenom;
  private String birthDate;
  private String email;
  private String tel;
  private String cp;
  private String ville;

  private String dateEffet;
  private String periodicite;
  private String iban;

  private BigDecimal primeNette;
  private BigDecimal frais;
  private BigDecimal taxe;
  private BigDecimal primeTTC;
  private String currency;

  private String reqJson;
  private String resJson;

  private String status;
  private String signature;

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
  public String getBirthDate() { return birthDate; }
  public void setBirthDate(String birthDate) { this.birthDate = birthDate; }
  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }
  public String getTel() { return tel; }
  public void setTel(String tel) { this.tel = tel; }
  public String getCp() { return cp; }
  public void setCp(String cp) { this.cp = cp; }
  public String getVille() { return ville; }
  public void setVille(String ville) { this.ville = ville; }

  public String getDateEffet() { return dateEffet; }
  public void setDateEffet(String dateEffet) { this.dateEffet = dateEffet; }
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
}
