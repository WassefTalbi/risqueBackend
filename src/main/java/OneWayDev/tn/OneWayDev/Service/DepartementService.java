package OneWayDev.tn.OneWayDev.Service;

import OneWayDev.tn.OneWayDev.Entity.Departement;

import OneWayDev.tn.OneWayDev.Entity.Entreprise;
import OneWayDev.tn.OneWayDev.Repository.DepartementRepository;

import OneWayDev.tn.OneWayDev.dto.request.DepartementRequest;
import OneWayDev.tn.OneWayDev.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartementService {
    private final DepartementRepository departementRepository;
    private final EntrepriseService entrepriseService;
    private final FileService fileService;

    public Page<Departement> findDepartmentsByEntreprise(int offset, int pageSize, String field,Long idEntreprise){
        Page<Departement> departements = departementRepository.findAllByEntrepriseId( idEntreprise,PageRequest.of(offset, pageSize, Sort.by(field)));
        return departements;
    }

    public Departement findDEpartementById(Long idDepartement){
        Optional<Departement> departement= departementRepository.findById(idDepartement);
        if (!departement.isPresent()){
            throw new NotFoundException("no Departement found");
        }

        return departement.get();
    }

    public Departement addDepartement(DepartementRequest departementRequest,Long idEntreprise){
        try{
        Entreprise entreprise= entrepriseService.findEntrepriseById(idEntreprise);
            Departement departement=new Departement();
            departement.setEntreprise(entreprise);
            departement.setNom(departementRequest.getNom());
            departement.setPriorite(departementRequest.getPriorite());
            departement.setValeurEconomique(departementRequest.getValeurEconomique());
            String logo= fileService.uploadFile(departementRequest.getLogo());
            departement.setLogo(logo);
            return departementRepository.save(departement);
        }
        catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }


    }
    /*
 public Entreprise editEntreprise(Long idDepartement, EntrepriseRequest entrepriseRequest){
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
    public void deleteDepartement(Long idDepartement){
        Departement entreprise= departementRepository.findById(idDepartement).orElseThrow(()->new NotFoundException(" no Departement found"));
         departementRepository.deleteById(idDepartement);

    }


}
