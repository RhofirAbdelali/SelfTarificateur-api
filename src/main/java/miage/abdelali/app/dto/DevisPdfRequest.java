package miage.abdelali.app.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public class DevisPdfRequest {
	@NotBlank(message = "nom obligatoire")
	private String nom;

	@NotBlank(message = "prenom obligatoire")
	private String prenom;

	@Email
	@NotBlank(message = "email obligatoire")
	private String email;

	@NotNull(message = "devis obligatoire")
	@Valid
	private DevisRequestDto devis;

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public DevisRequestDto getDevis() {
		return devis;
	}

	public void setDevis(DevisRequestDto devis) {
		this.devis = devis;
	}
}
