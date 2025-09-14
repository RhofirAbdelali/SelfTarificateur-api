package miage.abdelali.app.entities;

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

	@Column(name = "active", nullable = false)
	private boolean active;

	private LocalDate effectiveFrom;
	private LocalDate effectiveTo;

	public Long getId() {
		return id;
	}

	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
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