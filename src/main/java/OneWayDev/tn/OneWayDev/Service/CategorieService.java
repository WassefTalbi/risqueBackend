package OneWayDev.tn.OneWayDev.Service;

import OneWayDev.tn.OneWayDev.Entity.Categorie;
import OneWayDev.tn.OneWayDev.Entity.Departement;
import OneWayDev.tn.OneWayDev.Entity.Projet;
import OneWayDev.tn.OneWayDev.Entity.User;
import OneWayDev.tn.OneWayDev.Repository.CategorieRepository;
import OneWayDev.tn.OneWayDev.Repository.DepartementRepository;
import OneWayDev.tn.OneWayDev.Repository.ProjetRepository;
import OneWayDev.tn.OneWayDev.Repository.UserRepository;
import OneWayDev.tn.OneWayDev.dto.request.CategorieRequest;
import OneWayDev.tn.OneWayDev.dto.request.ProjetRequest;
import OneWayDev.tn.OneWayDev.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategorieService {
    private final CategorieRepository categorieRepository;
    private final FileService fileService;

    public List<Categorie> findAll(){
        List<Categorie> categories = categorieRepository.findAll();
        return categories;
    }

    public Categorie findCategorieById(Long idCategorie){
        Optional<Categorie> categorie= categorieRepository.findById(idCategorie);
        if (!categorie.isPresent()){
            throw new NotFoundException("no categorie found");
        }

        return categorie.get();
    }
    public Categorie addCategorie(CategorieRequest categorieRequest){
        Categorie categorie=new Categorie();
        categorie.setNom(categorieRequest.getNom());
        String logo= fileService.uploadFile(categorieRequest.getLogo());
        categorie.setLogo(logo);
        return categorieRepository.save(categorie);



    }
    public Categorie editCategorie(Long idCategorie, String nom){
        Categorie categorie= categorieRepository.findById(idCategorie).orElseThrow(()->new NotFoundException(" no Categorie found"));
        categorie.setNom(nom);
        return categorieRepository.save(categorie);



    }

    public void deleteCategorie(Long idCategorie){
        Categorie projet= categorieRepository.findById(idCategorie).orElseThrow(()->new NotFoundException(" no categorie found"));
         categorieRepository.deleteById(idCategorie);

    }


}
