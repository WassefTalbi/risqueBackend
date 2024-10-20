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
    public List<Menace> findAllMenace(){
        List<Menace> menaces = menaceRepository.findAll();
        return menaces;
    }
    public List<Menace> findMenaceByActif( Long idActif){
        List<Menace> menaces = menaceRepository.findByActifsId( idActif);
        return menaces;
    }

    public Actif addMenace(MenaceRequest menaceRequest) {

        Actif actif = actifRepository.findById(menaceRequest.getActifId())
                .orElseThrow(() -> new NotFoundException("Actif not found with id: " + menaceRequest.getActifId()));
        Menace menace=new Menace();
        menace.setNom(menaceRequest.getNom());

       actif.addMenace(menace);
        return actifRepository.save(actif);
    }


    public Menace addVulnerabilite(VulnerabiliteRequest vulnerabiliteRequest) {

        Menace menace = menaceRepository.findById(vulnerabiliteRequest.getMenaceId())
                .orElseThrow(() -> new NotFoundException("Actif not found with id: " + vulnerabiliteRequest.getMenaceId()));
        Vulnerabilite vulnerabilite=new Vulnerabilite();
        vulnerabilite.setNom(vulnerabiliteRequest.getNom());
        menace.addVulnerabilite(vulnerabilite);

        return menaceRepository.save(menace);
    }

    public void removeVulnerabiliteFromMenace(Long menaceId, Long vulnerabiliteId) {
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


    public void removeMenaceFromActif(Long actifId, Long menaceId) {
        Actif actif = actifRepository.findById(actifId)
                .orElseThrow(() -> new NotFoundException("No actif found with ID: " + actifId));
        Menace menace = menaceRepository.findById(menaceId)
                .orElseThrow(() -> new NotFoundException("No menace found with ID: " + menaceId));
        if (!actif.getMenaces().contains(menace)) {
            throw new IllegalStateException("menace with ID: " + menaceId + " is not part of the actif with ID: " + actifId);
        }
        actif.getMenaces().remove(menace);
        menace.getActifs().remove(actif);
        menaceRepository.save(menace);
        actifRepository.save(actif);
    }
}
