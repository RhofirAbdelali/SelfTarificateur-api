package miage.abdelali.app.Services.Impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;

import org.springframework.stereotype.Service;

import miage.abdelali.app.Dto.DevisRequestDto;
import miage.abdelali.app.Dto.DevisResponseDto;
import miage.abdelali.app.Services.TariffService;

@Service
public class TariffServiceImpl implements TariffService {

	@Override
	public DevisResponseDto Devis(DevisRequestDto req) {
		int age = Period.between(LocalDate.parse(req.getBirthDate()), LocalDate.now()).getYears();

		// barème
		BigDecimal baseRate = bd(0.0025);
		BigDecimal ageFactor = (age < 30) ? bd(1.00)
				: (age < 40) ? bd(1.20) : (age < 50) ? bd(1.60) : (age < 60) ? bd(2.20) : bd(3.50);
		BigDecimal smokerFactor = "OUI".equalsIgnoreCase(req.getSmoker()) ? bd(1.30) : bd(1.00);
		BigDecimal capital = req.getCapital();

		BigDecimal primeNette = capital.multiply(baseRate).multiply(ageFactor).multiply(smokerFactor);
		BigDecimal frais = bd(2.00);
		BigDecimal taxeRate = bd(0.09);
		BigDecimal taxe = primeNette.multiply(taxeRate);
		BigDecimal primeTTC = primeNette.add(frais).add(taxe);

		DevisResponseDto res = new DevisResponseDto();
		res.setPrimeNette(scale2(primeNette));
		res.setFrais(scale2(frais));
		res.setTaxe(scale2(taxe));
		res.setPrimeTTC(scale2(primeTTC));
		res.setVersionTarif(req.getVersionCode());
		return res;
	}

	private static BigDecimal bd(double v) {
		return BigDecimal.valueOf(v);
	}

	private static BigDecimal scale2(BigDecimal v) {
		return v.setScale(2, RoundingMode.HALF_UP);
	}
}