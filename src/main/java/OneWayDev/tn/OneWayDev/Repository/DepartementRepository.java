package OneWayDev.tn.OneWayDev.Repository;

import OneWayDev.tn.OneWayDev.Entity.Departement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DepartementRepository extends JpaRepository<Departement, Long> {
    Page<Departement> findAllByEntrepriseId(Long idEntreprise, Pageable pageable);


}