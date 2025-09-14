package miage.abdelali.app.services;

import miage.abdelali.app.dto.DevisRequestDto;
import miage.abdelali.app.dto.DevisResponseDto;

public interface TariffService {
	DevisResponseDto devis(DevisRequestDto req);

	default DevisResponseDto quote(DevisRequestDto req) {
		return devis(req);
	}
}
