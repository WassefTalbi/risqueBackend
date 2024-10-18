package OneWayDev.tn.OneWayDev.Service;

import OneWayDev.tn.OneWayDev.Entity.*;
import OneWayDev.tn.OneWayDev.Repository.ActifRepository;
import OneWayDev.tn.OneWayDev.Repository.MenaceRepository;
import OneWayDev.tn.OneWayDev.Repository.VulnerabiliteRepository;
import OneWayDev.tn.OneWayDev.dto.request.MenaceRequest;
import OneWayDev.tn.OneWayDev.dto.request.VulnerabiliteRequest;
import OneWayDev.tn.OneWayDev.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class VulnerabiliteService {
    private final ActifRepository actifRepository;
    private final VulnerabiliteRepository vulnerabiliteRepository;
    private final MenaceRepository menaceRepository;
    public List<Actif> findAllVulnerabilite(){
        List<Actif> actifs = actifRepository.findAll();
        return actifs;
    }
    public List<Vulnerabilite> findVulnerabiliteByActif( Long idActif){
        List<Vulnerabilite> vulnerabilites = vulnerabiliteRepository.findByActifsId( idActif);
        return vulnerabilites;
    }

    public Actif addVulnerabilite(VulnerabiliteRequest vulnerabiliteRequest) {

        Actif actif = actifRepository.findById(vulnerabiliteRequest.getActifId())
                .orElseThrow(() -> new NotFoundException("Actif not found with id: " + vulnerabiliteRequest.getActifId()));
        Vulnerabilite vulnerabilite=new Vulnerabilite();
        vulnerabilite.setNom(vulnerabiliteRequest.getNom());

       actif.addVulnerabilite(vulnerabilite);
        return actifRepository.save(actif);
    }


    public Vulnerabilite addMenace(MenaceRequest menaceRequest) {

        Vulnerabilite vulnerabilite = vulnerabiliteRepository.findById(menaceRequest.getVulnerabiliteId())
                .orElseThrow(() -> new NotFoundException("Actif not found with id: " + menaceRequest.getVulnerabiliteId()));
        Menace menace=new Menace();
        menace.setNom(menaceRequest.getNom());
        vulnerabilite.addMenace(menace);

        return vulnerabiliteRepository.save(vulnerabilite);
    }

    public void removeMenaceFromVulnerabilite(Long vulnerabiliteId, Long menaceId) {
        Vulnerabilite vulnerabilite = vulnerabiliteRepository.findById(vulnerabiliteId)
                .orElseThrow(()->new NotFoundException("Vulnerabilite not found with id: " + vulnerabiliteId));



      Menace menace=   menaceRepository.findById(menaceId)
              .orElseThrow(()->new NotFoundException("Menace not found with id: " + menaceId));

        if (vulnerabilite.getMenaces().contains(menace)) {
            vulnerabilite.getMenaces().remove(menace);
            menace.getVulnerabilites().remove(vulnerabilite);

            vulnerabiliteRepository.save(vulnerabilite);
            menaceRepository.save(menace);
        } else {
            throw new IllegalArgumentException("Menace is not associated with the Vulnerabilite");
        }
    }


    public void removeVulnerabiliteFromActif(Long actifId, Long vulnerabiliteId) {
        Actif actif = actifRepository.findById(actifId)
                .orElseThrow(() -> new NotFoundException("No actif found with ID: " + actifId));
        Vulnerabilite vulnerabilite = vulnerabiliteRepository.findById(vulnerabiliteId)
                .orElseThrow(() -> new NotFoundException("No actif found with ID: " + vulnerabiliteId));
        if (!actif.getVulnerabilites().contains(vulnerabilite)) {
            throw new IllegalStateException("vulnerabilite with ID: " + vulnerabiliteId + " is not part of the actif with ID: " + actifId);
        }
        actif.getVulnerabilites().remove(vulnerabilite);
        vulnerabilite.getActifs().remove(actif);
        vulnerabiliteRepository.save(vulnerabilite);
        actifRepository.save(actif);
    }
}
