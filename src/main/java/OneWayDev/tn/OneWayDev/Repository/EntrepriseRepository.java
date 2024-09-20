package OneWayDev.tn.OneWayDev.Repository;
import OneWayDev.tn.OneWayDev.Entity.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EntrepriseRepository extends JpaRepository<Entreprise, Long> {
  Optional<Entreprise> findByEmail(String email);
}