package miage.abdelali.app.Entities;

import jakarta.persistence.*;

@Entity
public class Quote {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
}