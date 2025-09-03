package miage.abdelali.app.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import miage.abdelali.app.Entities.TariffVersion;

public interface TariffVersionRepository extends JpaRepository<TariffVersion, Long> {}