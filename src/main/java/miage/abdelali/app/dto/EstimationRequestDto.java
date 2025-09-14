package miage.abdelali.app.dto;

import java.math.BigDecimal;
import jakarta.validation.constraints.*;
import miage.abdelali.app.entities.enums.*;

public class EstimationRequestDto {

	@NotNull(message = "beneficiaire obligatoire")
	private BeneficiaireType beneficiaire;

	@NotNull(message = "assure actuel obligatoire")
	private Boolean assureActuel;

	@NotBlank(message = "birthDate obligatoire")
	private String birthDate;

	@NotNull(message = "statut pro obligatoire")
	private StatutPro statutPro;

	@NotNull(message = "fumeur obligatoire")
	private Fumeur fumeur;

	@NotNull(message = "capital décès obligatoire")
	@Positive(message = "capital > 0")
	private BigDecimal capitalDeces;

	@NotNull(message = "couverture obligatoire")
	private Couverture couverture;

	@Min(value = 0, message = "IJ >= 0")
	private Integer indemniteJournaliere;

	@Min(value = 0, message = "franchise >= 0")
	private Integer franchiseJours;

	@NotNull(message = "budget obligatoire")
	@Min(10)
	@Max(100)
	private Integer budgetMensuel;

	@NotNull(message = "niveau protection obligatoire")
	private NiveauProtection niveauProtection;

	public BeneficiaireType getBeneficiaire() {
		return beneficiaire;
	}

	public void setBeneficiaire(BeneficiaireType v) {
		this.beneficiaire = v;
	}

	public Boolean getAssureActuel() {
		return assureActuel;
	}

	public void setAssureActuel(Boolean v) {
		this.assureActuel = v;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String v) {
		this.birthDate = v;
	}

	public StatutPro getStatutPro() {
		return statutPro;
	}

	public void setStatutPro(StatutPro v) {
		this.statutPro = v;
	}

	public Fumeur getFumeur() {
		return fumeur;
	}

	public void setFumeur(Fumeur v) {
		this.fumeur = v;
	}

	public BigDecimal getCapitalDeces() {
		return capitalDeces;
	}

	public void setCapitalDeces(BigDecimal v) {
		this.capitalDeces = v;
	}

	public Couverture getCouverture() {
		return couverture;
	}

	public void setCouverture(Couverture v) {
		this.couverture = v;
	}

	public Integer getIndemniteJournaliere() {
		return indemniteJournaliere;
	}

	public void setIndemniteJournaliere(Integer v) {
		this.indemniteJournaliere = v;
	}

	public Integer getFranchiseJours() {
		return franchiseJours;
	}

	public void setFranchiseJours(Integer v) {
		this.franchiseJours = v;
	}

	public Integer getBudgetMensuel() {
		return budgetMensuel;
	}

	public void setBudgetMensuel(Integer v) {
		this.budgetMensuel = v;
	}

	public NiveauProtection getNiveauProtection() {
		return niveauProtection;
	}

	public void setNiveauProtection(NiveauProtection v) {
		this.niveauProtection = v;
	}
}
