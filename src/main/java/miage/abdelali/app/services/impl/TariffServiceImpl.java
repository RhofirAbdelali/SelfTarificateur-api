package miage.abdelali.app.services.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;

import org.springframework.stereotype.Service;

import miage.abdelali.app.dto.DevisRequestDto;
import miage.abdelali.app.dto.DevisResponseDto;
import miage.abdelali.app.entities.enums.BeneficiaireType;
import miage.abdelali.app.entities.enums.Couverture;
import miage.abdelali.app.entities.enums.NiveauProtection;
import miage.abdelali.app.entities.enums.StatutPro;
import miage.abdelali.app.services.TariffService;

@Service
public class TariffServiceImpl implements TariffService {

	private static final BigDecimal TAUX_BASE = bd(0.0025);
	private static final BigDecimal FRAIS_FIXES = bd(2.00);
	private static final BigDecimal TAUX_TAXE = bd(0.09);
	private static final BigDecimal SURCOUT_IJ_PAR_JOUR = bd(0.20);

	@Override
	public DevisResponseDto devis(DevisRequestDto req) {
		if (req.getBirthDate() == null || req.getBirthDate().isBlank())
			throw new IllegalArgumentException("birthDate manquante");
		if (req.getCapital() == null || req.getCapital().compareTo(BigDecimal.ZERO) <= 0)
			throw new IllegalArgumentException("capital doit être > 0");
		if (req.getSmoker() == null || req.getSmoker().isBlank())
			throw new IllegalArgumentException("smoker (OUI/NON) manquant");

		final int age = computeAge(req.getBirthDate());
		final BigDecimal capital = req.getCapital();

		BigDecimal primeNette = capital.multiply(TAUX_BASE).multiply(facteurAge(age))
				.multiply(facteurFumeur(req.getSmoker()));

		final BigDecimal facteurWizard = facteurCouverture(req.getCouverture())
				.multiply(facteurNiveau(req.getNiveauProtection())).multiply(facteurBeneficiaire(req.getBeneficiaire()))
				.multiply(facteurStatutPro(req.getStatutPro())).multiply(facteurAssureActuel(req.getAssureActuel()));

		primeNette = primeNette.multiply(facteurWizard);

		BigDecimal surcoutOptions = bd(0);
		if (req.getCouverture() == Couverture.TOUT_RISQUE) {
			surcoutOptions = surcoutOptions.add(surcoutIJ(req.getIndemniteJournaliere()));
			primeNette = primeNette.multiply(facteurFranchise(req.getFranchiseJours()));
		}

		final BigDecimal taxe = primeNette.multiply(TAUX_TAXE);
		final BigDecimal primeTTC = primeNette.add(FRAIS_FIXES).add(taxe).add(surcoutOptions);

		DevisResponseDto out = new DevisResponseDto();
		out.setPrimeNette(s2(primeNette));
		out.setFrais(s2(FRAIS_FIXES));
		out.setTaxe(s2(taxe));
		out.setPrimeTTC(s2(primeTTC));
		out.setCurrency("EUR");
		out.setVersionTarif(req.getVersionCode());
		return out;
	}

	private static BigDecimal facteurAge(int age) {
		if (age < 30)
			return bd(1.00);
		if (age < 40)
			return bd(1.20);
		if (age < 50)
			return bd(1.60);
		if (age < 60)
			return bd(2.20);
		return bd(3.50);
	}

	private static BigDecimal facteurFumeur(String smoker) {
		return "OUI".equalsIgnoreCase(smoker) ? bd(1.30) : bd(1.00);
	}

	private static BigDecimal facteurCouverture(Couverture c) {
		if (c == null)
			return bd(1.00);
		return switch (c) {
		case DECES_SEUL -> bd(1.00);
		case TOUT_RISQUE -> bd(1.35);
		};
	}

	private static BigDecimal facteurNiveau(NiveauProtection n) {
		if (n == null)
			return bd(1.00);
		return switch (n) {
		case FAIBLE -> bd(0.90);
		case MOYEN -> bd(1.00);
		case ELEVE -> bd(1.15);
		};
	}

	private static BigDecimal facteurBeneficiaire(BeneficiaireType b) {
		if (b == null)
			return bd(1.00);
		return switch (b) {
		case ADULTE -> bd(1.00);
		case COUPLE -> bd(1.10);
		case ADULTE_ENFANTS -> bd(1.15);
		case COUPLE_ENFANTS -> bd(1.20);
		};
	}

	private static BigDecimal facteurStatutPro(StatutPro s) {
		if (s == null)
			return bd(1.00);
		return switch (s) {
		case SANS_EMPLOI -> bd(1.05);
		default -> bd(1.00);
		};
	}

	private static BigDecimal facteurAssureActuel(Boolean assure) {
		return Boolean.TRUE.equals(assure) ? bd(0.95) : bd(1.00);
	}

	private static BigDecimal surcoutIJ(Integer ij) {
		int v = (ij == null) ? 0 : ij;
		return bd(v).multiply(SURCOUT_IJ_PAR_JOUR);
	}

	private static BigDecimal facteurFranchise(Integer franchiseJours) {
		if (franchiseJours == null)
			return bd(1.00);
		return switch (franchiseJours) {
		case 30 -> bd(1.10);
		case 60 -> bd(1.00);
		case 90 -> bd(0.90);
		default -> bd(1.00);
		};
	}

	private static int computeAge(String birthDateIso) {
		return Period.between(LocalDate.parse(birthDateIso), LocalDate.now()).getYears();
	}

	private static BigDecimal bd(double v) {
		return BigDecimal.valueOf(v);
	}

	private static BigDecimal s2(BigDecimal v) {
		return v.setScale(2, RoundingMode.HALF_UP);
	}
}
