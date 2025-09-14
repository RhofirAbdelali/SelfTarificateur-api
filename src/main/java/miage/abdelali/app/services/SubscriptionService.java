package miage.abdelali.app.services;

import java.util.List;
import miage.abdelali.app.dto.SubscriptionCreateDto;
import miage.abdelali.app.entities.Subscription;

public interface SubscriptionService {
  Subscription create(SubscriptionCreateDto dto);
  List<Subscription> list();
  Subscription find(Long id);
}
