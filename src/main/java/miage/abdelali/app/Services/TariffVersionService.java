package miage.abdelali.app.Services;

import miage.abdelali.app.Entities.TariffVersion;
import java.util.List;

public interface TariffVersionService {
	TariffVersion save(TariffVersion tariffVersion);

	List<TariffVersion> findAll();

	TariffVersion findById(Long id);

	void delete(Long id);
}