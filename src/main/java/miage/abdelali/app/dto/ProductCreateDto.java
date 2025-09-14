package miage.abdelali.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ProductCreateDto {
	@NotBlank
	@Size(max = 50)
	private String code;
	@NotBlank
	@Size(max = 120)
	private String label;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}