package OneWayDev.tn.OneWayDev.Service;

import OneWayDev.tn.OneWayDev.Entity.*;
import OneWayDev.tn.OneWayDev.Repository.ActifRepository;
import OneWayDev.tn.OneWayDev.Repository.CategorieRepository;
import OneWayDev.tn.OneWayDev.Repository.VulnerabiliteRepository;
import OneWayDev.tn.OneWayDev.dto.request.ActifRequest;
import OneWayDev.tn.OneWayDev.dto.request.MenaceRequest;
import OneWayDev.tn.OneWayDev.dto.request.VulnerabiliteRequest;
import OneWayDev.tn.OneWayDev.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VulnerabiliteService {
    private final ActifRepository actifRepository;
    private final VulnerabiliteRepository vulnerabiliteRepository;
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
        System.out.println("testing non into the method service "+vulnerabiliteRequest.getNom());
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







}
