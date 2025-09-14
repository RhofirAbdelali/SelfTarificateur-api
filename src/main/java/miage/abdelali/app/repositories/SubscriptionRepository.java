package miage.abdelali.app.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import miage.abdelali.app.entities.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
  List<Subscription> findAllByOrderByCreatedAtDesc();
}
