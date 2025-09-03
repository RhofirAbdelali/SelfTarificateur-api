package miage.abdelali.app.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import miage.abdelali.app.Entities.Quote;

public interface QuoteRepository extends JpaRepository<Quote, Long> {}