package miage.abdelali.app.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import miage.abdelali.app.Entities.Insured;

public interface InsuredRepository extends JpaRepository<Insured, Long> {}