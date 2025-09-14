package miage.abdelali.app.services;

import java.util.List;
import miage.abdelali.app.dto.TariffVersionCreateDto;
import miage.abdelali.app.entities.TariffVersion;

public interface TariffVersionService {
	TariffVersion save(TariffVersion tariffVersion);

	List<TariffVersion> findAll();

	TariffVersion findById(Long id);

	void delete(Long id);

	TariffVersion update(Long id, TariffVersionCreateDto dto);
	
    TariffVersion activateExclusive(Long id);
    
    TariffVersion findActive();

}
