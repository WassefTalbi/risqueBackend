package OneWayDev.tn.OneWayDev.Repository;

import OneWayDev.tn.OneWayDev.Entity.Menace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenaceRepository extends JpaRepository<Menace, Long> {
    List<Menace> findByActifsId(Long idActif);


}