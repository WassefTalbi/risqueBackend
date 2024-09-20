package OneWayDev.tn.OneWayDev.Service;

import OneWayDev.tn.OneWayDev.Entity.Entreprise;
import OneWayDev.tn.OneWayDev.Repository.EntrepriseRepository;
import OneWayDev.tn.OneWayDev.dto.request.EntrepriseRequest;
import OneWayDev.tn.OneWayDev.exception.EmailExistsExecption;
import OneWayDev.tn.OneWayDev.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EntrepriseService {
    private final EntrepriseRepository entrepriseRepository;
    private final FileService fileService;

    public Page<Entreprise> findEntrepriseswithPaginationAndSorting(int offset, int pageSize, String field){
        Page<Entreprise> entreprises = entrepriseRepository.findAll( PageRequest.of(offset, pageSize, Sort.by(field)));
        return entreprises;
    }

    public Entreprise findEntrepriseById(Long idEntreprise){
        Optional<Entreprise> entreprise= entrepriseRepository.findById(idEntreprise);
        if (!entreprise.isPresent()){
            throw new NotFoundException("no entreprise found");
        }

        return entreprise.get();
    }
    public Entreprise addEntrprise(EntrepriseRequest entrepriseRequest){
        try{
            if(entrepriseRepository.findByEmail(entrepriseRequest.getEmail()).isPresent()){
                throw new EmailExistsExecption("Email already exists");
            }
            Entreprise entreprise=new Entreprise();
            entreprise.setNom(entrepriseRequest.getNom());
            entreprise.setAdresse(entrepriseRequest.getAdresse());
            entreprise.setDomaine(entrepriseRequest.getDomaine());
            entreprise.setEmail(entrepriseRequest.getEmail());
            String logo= fileService.uploadFile(entrepriseRequest.getLogo());
            entreprise.setLogo(logo);
            return entrepriseRepository.save(entreprise);
        }
        catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }


    }
    public Entreprise editEntreprise(Long idEntreprise, EntrepriseRequest entrepriseRequest){
        try{
            Entreprise entreprise= entrepriseRepository.findById(idEntreprise).orElseThrow(()->new NotFoundException("entreprise not found"));
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

    public void deleteEntreprise(Long idEntreprise){
        Entreprise entreprise= entrepriseRepository.findById(idEntreprise).orElseThrow(()->new NotFoundException(" no entreprisefound"));
         entrepriseRepository.deleteById(idEntreprise);

    }


}
