package OneWayDev.tn.OneWayDev.Repository;
import OneWayDev.tn.OneWayDev.Entity.Actif;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ActifRepository extends JpaRepository<Actif, Long> {
  List<Actif> findByCategorieId(Long idCategorie);


}