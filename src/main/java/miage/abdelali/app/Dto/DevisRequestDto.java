package miage.abdelali.app.Dto;

import java.math.BigDecimal;
import jakarta.validation.constraints.*;

public class DevisRequestDto {
	@NotBlank
	private String productCode;
	@NotBlank
	private String versionCode;
	@NotBlank
	private String birthDate;
	@NotNull
	@Positive
	private BigDecimal capital;
	@NotBlank
	private String smoker;
	@Size(max = 30)
	private String professionCategory;

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
