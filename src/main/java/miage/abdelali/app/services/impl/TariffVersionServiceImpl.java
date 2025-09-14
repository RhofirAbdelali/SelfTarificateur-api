package miage.abdelali.app.services.impl;

import java.time.LocalDate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import miage.abdelali.app.dto.TariffVersionCreateDto;
import miage.abdelali.app.entities.TariffVersion;
import miage.abdelali.app.repositories.TariffVersionRepository;
import miage.abdelali.app.services.TariffVersionService;

import java.util.List;

@Service
@Transactional
public class TariffVersionServiceImpl implements TariffVersionService {

	private final TariffVersionRepository repo;

	public TariffVersionServiceImpl(TariffVersionRepository repo) {
		this.repo = repo;
	}

	@Override
	public TariffVersion save(TariffVersion tv) {
		return repo.save(tv);
	}

	@Override
	public List<TariffVersion> findAll() {
		return repo.findAll();
	}

	@Override
	public TariffVersion findById(Long id) {
		return repo.findById(id).orElse(null);
	}

	@Override
	public void delete(Long id) {
		repo.deleteById(id);
	}

	@Override
	public TariffVersion update(Long id, TariffVersionCreateDto dto) {
		TariffVersion existing = repo.findById(id).orElse(null);
		if (existing == null)
			return null;

		if (dto.getVersionCode() != null && !dto.getVersionCode().isBlank()) {
			existing.setVersionCode(dto.getVersionCode().trim());
		}
		if (dto.getEffectiveFrom() != null && !dto.getEffectiveFrom().isBlank()) {
			existing.setEffectiveFrom(LocalDate.parse(dto.getEffectiveFrom()));
		} else {
			existing.setEffectiveFrom(null);
		}
		if (dto.getEffectiveTo() != null && !dto.getEffectiveTo().isBlank()) {
			existing.setEffectiveTo(LocalDate.parse(dto.getEffectiveTo()));
		} else {
			existing.setEffectiveTo(null);
		}
		return repo.save(existing);
	}

	@Override
	public TariffVersion activateExclusive(Long id) {
	    miage.abdelali.app.entities.TariffVersion target = repo.findById(id).orElse(null);
	    if (target == null) return null;
	    java.util.List<miage.abdelali.app.entities.TariffVersion> all = repo.findAll();
	    for (miage.abdelali.app.entities.TariffVersion v : all) {
	        v.setActive(v.getId().equals(id));
	    }
	    repo.saveAll(all);
	    return target;
	}
	
	@Override
	public TariffVersion findActive() {
	  TariffVersion v = repo.findFirstByActiveTrueOrderByEffectiveFromDesc();
	  if (v == null) v = repo.findFirstByActiveTrueOrderByIdDesc();
	  if (v == null) v = repo.findFirstByOrderByEffectiveFromDesc();
	  return v;
	}

}
