package OneWayDev.tn.OneWayDev.Repository;

import OneWayDev.tn.OneWayDev.Entity.Projet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjetRepository extends JpaRepository<Projet, Long> {
  Page<Projet> findAllByDepartementId(Long idDepartemenet, Pageable pageable);
}