package miage.abdelali.app.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import miage.abdelali.app.entities.TariffVersion;

public interface TariffVersionRepository extends JpaRepository<TariffVersion, Long> {
	Optional<TariffVersion> findByVersionCode(String versionCode);
	TariffVersion findFirstByActiveTrueOrderByEffectiveFromDesc();
    TariffVersion findFirstByActiveTrueOrderByIdDesc();
    TariffVersion findFirstByOrderByEffectiveFromDesc();
}