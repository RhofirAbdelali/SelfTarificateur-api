package miage.abdelali.app.Entities;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "insured")
public class Insured {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 120)
	private String firstName;

	@Column(nullable = false, length = 120)
	private String lastName;

	@Column(nullable = false)
	private LocalDate birthDate;

	@Column(length = 30)
	private String professionCategory;

	@Column(length = 10)
	private String smoker;

	// getters/setters
	public Long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public String getProfessionCategory() {
		return professionCategory;
	}

	public void setProfessionCategory(String professionCategory) {
		this.professionCategory = professionCategory;
	}

	public String getSmoker() {
		return smoker;
	}

	public void setSmoker(String smoker) {
		this.smoker = smoker;
	}
}