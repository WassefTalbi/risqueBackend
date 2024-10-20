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
        return  risqueRepository.findById(idRisque).orElseThrow(()->new NotFoundException("no risque found") );
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

 public Risque editRisque(Long idRisque, RisqueRequest risqueRequest){
     try{
         Risque risque= risqueRepository.findById(idRisque).orElseThrow(()->new NotFoundException("risque not found"));
         risque.setNom(risqueRequest.getRisqueNom());
         risque.setPriorite(risqueRequest.getRisquePriorite());
         risque.setValeurFinanciere(risqueRequest.getRisqueValeurFinanciere());
         risque.setProbabilite(risqueRequest.getProbabilite());
         risque.setValeurBaseImpact(risqueRequest.getValeurBaseImpact());
         return risqueRepository.save(risque);
     }
     catch (Exception e){
         throw new RuntimeException(e.getMessage());
     }

 }

    public void deleteRisque(Long idRisque){
        Risque risque= risqueRepository.findById(idRisque).orElseThrow(()->new NotFoundException(" no risque found"));
        risqueRepository.deleteById(idRisque);

    }


}
