package miage.abdelali.app.Services;

import miage.abdelali.app.Dto.DevisRequestDto;
import miage.abdelali.app.Dto.DevisResponseDto;

public interface TariffService {
	DevisResponseDto Devis(DevisRequestDto req);
}