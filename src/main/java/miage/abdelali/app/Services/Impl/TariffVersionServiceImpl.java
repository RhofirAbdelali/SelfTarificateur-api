package miage.abdelali.app.Services.Impl;

import miage.abdelali.app.Entities.TariffVersion;
import miage.abdelali.app.Repositories.TariffVersionRepository;
import miage.abdelali.app.Services.TariffVersionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TariffVersionServiceImpl implements TariffVersionService {

	private final TariffVersionRepository repository;

	public TariffVersionServiceImpl(TariffVersionRepository repository) {
		this.repository = repository;
	}

	@Override
	public TariffVersion save(TariffVersion tariffVersion) {
		return repository.save(tariffVersion);
	}

	@Override
	public List<TariffVersion> findAll() {
		return repository.findAll();
	}

	@Override
	public TariffVersion findById(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}
}