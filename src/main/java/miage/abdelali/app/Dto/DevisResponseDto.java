package miage.abdelali.app.Dto;

import java.math.BigDecimal;

public class DevisResponseDto {
	private BigDecimal primeTTC;
	private String currency = "EUR";
	private BigDecimal primeNette;
	private BigDecimal frais;
	private BigDecimal taxe;
	private String versionTarif;

	public BigDecimal getPrimeTTC() {
		return primeTTC;
	}

	public void setPrimeTTC(BigDecimal v) {
		this.primeTTC = v;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String v) {
		this.currency = v;
	}

	public BigDecimal getPrimeNette() {
		return primeNette;
	}

	public void setPrimeNette(BigDecimal v) {
		this.primeNette = v;
	}

	public BigDecimal getFrais() {
		return frais;
	}

	public void setFrais(BigDecimal v) {
		this.frais = v;
	}

	public BigDecimal getTaxe() {
		return taxe;
	}

	public void setTaxe(BigDecimal v) {
		this.taxe = v;
	}

	public String getVersionTarif() {
		return versionTarif;
	}

	public void setVersionTarif(String v) {
		this.versionTarif = v;
	}
}
