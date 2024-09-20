package OneWayDev.tn.OneWayDev.Service;

import OneWayDev.tn.OneWayDev.Entity.Actif;

import OneWayDev.tn.OneWayDev.Entity.Risque;

import OneWayDev.tn.OneWayDev.Repository.ActifRepository;

import OneWayDev.tn.OneWayDev.Repository.RisqueRepository;

import OneWayDev.tn.OneWayDev.dto.request.RisqueRequest;
import OneWayDev.tn.OneWayDev.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RisqueService {
    private final RisqueRepository risqueRepository;
    private final ActifRepository actifRepository;

    public List<Risque> findAllRisques(){
        List<Risque> risques = risqueRepository.findAll();
        return risques;
    }


    public Risque findRisqueById(Long idRisque){
        Optional<Risque> risque= risqueRepository.findById(idRisque);
        if (!risque.isPresent()){
            throw new NotFoundException("no Risque found");
        }

        return risque.get();
    }


    public Risque addRisque(RisqueRequest risqueRequest){
        Risque risque=new Risque();
        risque.setNom(risqueRequest.getRisqueNom());
        risque.setPriorite(risqueRequest.getRisquePriorite());
        risque.setValeurFinanciere(risqueRequest.getRisqueValeurFinanciere());
        risque.setProbabilite(risqueRequest.getProbabilite());
        risque.setValeurBaseImpact(risqueRequest.getValeurBaseImpact());

        return risque;



    }
/*
 public Entreprise editActif(Long idActif, EntrepriseRequest entrepriseRequest){
     try{
         Entreprise entreprise= entrepriseRepository.findById(idDepartement).orElseThrow(()->new NotFoundException("departement not found"));
         entreprise.setNom(entrepriseRequest.getNom());
         entreprise.setDomaine(entrepriseRequest.getDomaine());
         entreprise.setAdresse(entrepriseRequest.getAdresse());
         String logo= fileService.uploadFile(entrepriseRequest.getLogo());
         entreprise.setLogo(logo);
         return entrepriseRepository.save(entreprise);
     }
     catch (Exception e){
         throw new RuntimeException(e.getMessage());
     }

 }
*/
    public void deleteRisque(Long idRisque){
        Risque risque= risqueRepository.findById(idRisque).orElseThrow(()->new NotFoundException(" no risque found"));
        risqueRepository.deleteById(idRisque);

    }


}
