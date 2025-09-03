package miage.abdelali.app.Entities;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "tariff_version")
public class TariffVersion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 30)
	private String versionCode;

	private LocalDate effectiveFrom;
	private LocalDate effectiveTo;

	// getters/setters
	public Long getId() {
		return id;
	}

	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	public LocalDate getEffectiveFrom() {
		return effectiveFrom;
	}

	public void setEffectiveFrom(LocalDate effectiveFrom) {
		this.effectiveFrom = effectiveFrom;
	}

	public LocalDate getEffectiveTo() {
		return effectiveTo;
	}

	public void setEffectiveTo(LocalDate effectiveTo) {
		this.effectiveTo = effectiveTo;
	}
}