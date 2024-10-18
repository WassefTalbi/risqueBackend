package OneWayDev.tn.OneWayDev.Service;

import OneWayDev.tn.OneWayDev.Entity.Actif;
import OneWayDev.tn.OneWayDev.Entity.Categorie;
import OneWayDev.tn.OneWayDev.Entity.Projet;
import OneWayDev.tn.OneWayDev.Entity.Risque;
import OneWayDev.tn.OneWayDev.Repository.ActifRepository;
import OneWayDev.tn.OneWayDev.Repository.CategorieRepository;
import OneWayDev.tn.OneWayDev.dto.request.ActifRequest;
import OneWayDev.tn.OneWayDev.dto.request.AssetRequest;
import OneWayDev.tn.OneWayDev.dto.request.RisqueRequest;
import OneWayDev.tn.OneWayDev.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ActifService {
    private final ActifRepository actifRepository;
    private final CategorieRepository categorieRepository;
    private final FileService fileService;
    private final RisqueService risqueService;
    public List<Actif> findAllActifs(){
        List<Actif> actifs = actifRepository.findAll();
        return actifs;
    }
    public List<Actif> findActifsByCategorie( Long idCategorie){
        List<Actif> actifs = actifRepository.findByCategorieId( idCategorie);
        return actifs;
    }

    public Actif findActifById(Long idActif){
        Optional<Actif> actif= actifRepository.findById(idActif);
        if (!actif.isPresent()){
            throw new NotFoundException("no Actif found");
        }

        return actif.get();
    }



    public Actif addActif(ActifRequest actifRequest){
        Categorie categorie= categorieRepository.findById(actifRequest.getCategorieId()).orElseThrow(()->new NotFoundException(" no categorie found"));
        Actif actif=new Actif();
        actif.setNom(actifRequest.getNom());
        actif.setDescription(actifRequest.getDescription());
        actif.setCategorie(categorie);
        actif.setPriorite(actifRequest.getPriorite());
        actif.setValeurFinanciere(actifRequest.getValeurFinanciere());
        actif.setValeurDonnees(actifRequest.getValeurDonnees());
        String logo= fileService.uploadFile(actifRequest.getLogo());
        actif.setLogo(logo);
        Risque risque = new Risque();
        risque.setNom(actifRequest.getRisqueNom());
        risque.setValeurFinanciere(actifRequest.getRisqueValeurFinanciere());
        risque.setProbabilite(actifRequest.getProbabilite());
        risque.setPriorite(actifRequest.getRisquePriorite());
        risque.setValeurBaseImpact(actifRequest.getValeurBaseImpact());
        risque.setActif(actif);
        actif.setRisque(risque);

        return actifRepository.save(actif);



    }

 public Actif editActif(Long idActif, AssetRequest assetRequest){
     try{
         Actif actif= actifRepository.findById(idActif).orElseThrow(()->new NotFoundException("actif not found"));
         actif.setNom(assetRequest.getNom());
         actif.setDescription(assetRequest.getDescription());
         actif.setPriorite(assetRequest.getPriorite());
         actif.setValeurFinanciere(assetRequest.getValeurFinanciere());
         actif.setValeurDonnees(assetRequest.getValeurDonnees());
         String logo= fileService.uploadFile(assetRequest.getLogo());
         actif.setLogo(logo);
         return actifRepository.save(actif);
     }
     catch (Exception e){
         throw new RuntimeException(e.getMessage());
     }

 }

    public void deleteActif(Long idActif) {
        Actif actif = actifRepository.findById(idActif).orElseThrow(() -> new NotFoundException("No actif found"));
        for (Projet projet : new ArrayList<>(actif.getProjets())) {
            actif.removeProjet(projet);
        }
        actifRepository.deleteById(idActif);
    }


}
