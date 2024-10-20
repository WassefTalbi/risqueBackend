package OneWayDev.tn.OneWayDev.Repository;

import OneWayDev.tn.OneWayDev.Entity.Actif;
import OneWayDev.tn.OneWayDev.Entity.Vulnerabilite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VulnerabiliteRepository extends JpaRepository<Vulnerabilite, Long> {



}