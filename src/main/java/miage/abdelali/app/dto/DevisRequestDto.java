package miage.abdelali.app.dto;

import java.math.BigDecimal;
import jakarta.validation.constraints.*;
import miage.abdelali.app.entities.enums.*;

public class DevisRequestDto {
	@NotBlank(message = "productCode obligatoire")
	private String productCode;

	@NotBlank(message = "versionCode obligatoire")
	private String versionCode;

	@NotBlank(message = "birthDate obligatoire")
	private String birthDate;

	@NotNull(message = "capital obligatoire")
	@Positive(message = "capital doit être > 0")
	private BigDecimal capital;

	@NotBlank(message = "smoker obligatoire (OUI/NON)")
	private String smoker;

	@Size(max = 30, message = "professionCategory max 30")
	private String professionCategory;

	@NotNull(message = "couverture obligatoire")
	private Couverture couverture;

	@NotNull(message = "niveau protection obligatoire")
	private NiveauProtection niveauProtection;

	private Integer indemniteJournaliere;
	private Integer franchiseJours;
	private Boolean assureActuel;

	public Couverture getCouverture() {
		return couverture;
	}

	public void setCouverture(Couverture couverture) {
		this.couverture = couverture;
	}

	public NiveauProtection getNiveauProtection() {
		return niveauProtection;
	}

	public void setNiveauProtection(NiveauProtection niveauProtection) {
		this.niveauProtection = niveauProtection;
	}

	public Integer getIndemniteJournaliere() {
		return indemniteJournaliere;
	}

	public void setIndemniteJournaliere(Integer indemniteJournaliere) {
		this.indemniteJournaliere = indemniteJournaliere;
	}

	public Integer getFranchiseJours() {
		return franchiseJours;
	}

	public void setFranchiseJours(Integer franchiseJours) {
		this.franchiseJours = franchiseJours;
	}

	public Boolean getAssureActuel() {
		return assureActuel;
	}

	public void setAssureActuel(Boolean assureActuel) {
		this.assureActuel = assureActuel;
	}

	public BeneficiaireType getBeneficiaire() {
		return beneficiaire;
	}

	public void setBeneficiaire(BeneficiaireType beneficiaire) {
		this.beneficiaire = beneficiaire;
	}

	public StatutPro getStatutPro() {
		return statutPro;
	}

	public void setStatutPro(StatutPro statutPro) {
		this.statutPro = statutPro;
	}

	private BeneficiaireType beneficiaire;
	private StatutPro statutPro;

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String v) {
		this.productCode = v;
	}

	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String v) {
		this.versionCode = v;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String v) {
		this.birthDate = v;
	}

	public BigDecimal getCapital() {
		return capital;
	}

	public void setCapital(BigDecimal v) {
		this.capital = v;
	}

	public String getSmoker() {
		return smoker;
	}

	public void setSmoker(String v) {
		this.smoker = v;
	}

	public String getProfessionCategory() {
		return professionCategory;
	}

	public void setProfessionCategory(String v) {
		this.professionCategory = v;
	}
}
