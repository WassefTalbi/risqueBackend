package OneWayDev.tn.OneWayDev.Service;

import OneWayDev.tn.OneWayDev.Entity.*;

import OneWayDev.tn.OneWayDev.Repository.ActifRepository;
import OneWayDev.tn.OneWayDev.Repository.DepartementRepository;
import OneWayDev.tn.OneWayDev.Repository.ProjetRepository;
import OneWayDev.tn.OneWayDev.Repository.UserRepository;
import OneWayDev.tn.OneWayDev.dto.request.ProjetRequest;
import OneWayDev.tn.OneWayDev.dto.response.RiskMatrixCell;
import OneWayDev.tn.OneWayDev.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjetService {
    private final ProjetRepository projetRepository;
    private  final DepartementRepository departementRepository;
    private final UserRepository userRepository;
    private final ActifRepository actifRepository;

    public Page<Projet> findProjetssByDepartement(int offset, int pageSize, String field, Long idDepartement){
        Page<Projet> projets = projetRepository.findAllByDepartementId( idDepartement,PageRequest.of(offset, pageSize, Sort.by(field)));
        return projets;
    }

    public Projet findProjetById(Long idProjet){
        Optional<Projet> projet= projetRepository.findById(idProjet);
        if (!projet.isPresent()){
            throw new NotFoundException("no projet found");
        }

        return projet.get();
    }
    public Projet addProjet(ProjetRequest projetRequest){

      Departement departement=  departementRepository.findById(projetRequest.getDepartementId()).orElseThrow(()->new NotFoundException(" no departement found"));
       User chefProjet= userRepository.findById(projetRequest.getChefProjetId()).orElseThrow(()->new NotFoundException(" no chef projet found"));

        Projet projet=new Projet();
        projet.setChefProjet(chefProjet);
        projet.setDepartement(departement);
        projet.setNom(projetRequest.getNom());
        projet.setAvancement(projetRequest.getAvancement());
        projet.setDescription(projetRequest.getDescription());
        projet.setEtat(projetRequest.getEtat());
        projet.setDateDebut(projetRequest.getDateDebut());
        projet.setDateFin(projetRequest.getDateFin());
        return projetRepository.save(projet);



    }
    public Projet editProjet(Long idProjet, ProjetRequest projetRequest){
        Projet projet= projetRepository.findById(idProjet).orElseThrow(()->new NotFoundException(" no projet found"));
        Departement departement=  departementRepository.findById(projetRequest.getDepartementId()).orElseThrow(()->new NotFoundException(" no departement found"));
        User chefProjet= userRepository.findById(projetRequest.getChefProjetId()).orElseThrow(()->new NotFoundException(" no chef projet found"));
        projet.setChefProjet(chefProjet);
        projet.setDepartement(departement);
        projet.setNom(projetRequest.getNom());
        projet.setAvancement(projetRequest.getAvancement());
        projet.setDescription(projetRequest.getDescription());
        projet.setEtat(projetRequest.getEtat());
        projet.setDateDebut(projetRequest.getDateDebut());
        projet.setDateFin(projetRequest.getDateFin());
        return projetRepository.save(projet);



    }

    public void deleteProjet(Long idProjet){
        Projet projet= projetRepository.findById(idProjet).orElseThrow(()->new NotFoundException(" no projet found"));
         projetRepository.deleteById(idProjet);
    }

    public List<Actif> getActifsNotInProject(Long projectId) {
        Projet projet = projetRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("No project found with ID: " + projectId));
        List<Actif> actifsInProject = projet.getActifs();
        List<Actif> allActifs = actifRepository.findAll();
        List<Actif> actifsNotInProject = allActifs.stream()
                .filter(actif -> !actifsInProject.contains(actif))
                .collect(Collectors.toList());
        return actifsNotInProject;
    }
    public List<Actif> getActifsInProject(Long projectId) {
        Projet projet = projetRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("No project found with ID: " + projectId));
        List<Actif> actifsInProject = projet.getActifs();
        return actifsInProject;
    }
    public void assignActifsToProject(List<Long> actifsId, Long projectId) {

        Projet projet = projetRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("No projet found with ID: " + projectId));

        List<Actif> actifs = actifsId.stream()
                .map(id -> actifRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException("No Actif found with ID: " + id)))
                .collect(Collectors.toList());

        actifs.forEach(actif -> {
            projet.addActif(actif);
        });

        projetRepository.save(projet);
    }
    public void removeActifFromProject(Long actifId, Long projectId) {
        Projet projet = projetRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("No project found with ID: " + projectId));
        Actif actif = actifRepository.findById(actifId)
                .orElseThrow(() -> new NotFoundException("No actif found with ID: " + actifId));
        if (!projet.getActifs().contains(actif)) {
            throw new IllegalStateException("Actif with ID: " + actifId + " is not part of the project with ID: " + projectId);
        }
        projet.getActifs().remove(actif);
        actif.getProjets().remove(projet);
        projetRepository.save(projet);
        actifRepository.save(actif);
    }

    public double calculateProjetRisque(Long projetId) {
        Projet projet = projetRepository.findById(projetId)
                .orElseThrow(() -> new NotFoundException("Projet not found"));
        List<Actif> actifs = projet.getActifs();
        double totalRisque = 0;
        for (Actif actif : actifs) {
            Risque risque = actif.getRisque();

            if (risque != null) {
                Integer probabilite = risque.getProbabilite(); // Potential
                Integer impact = risque.getValeurBaseImpact(); // Impact

                if (probabilite != null && impact != null) {

                    double risqueActif = probabilite * impact;
                    totalRisque += risqueActif;
                }
            }
        }
        return totalRisque;
    }

    public String determineRiskTreatment(double totalRisk) {
        if (totalRisk > 100) {
            return "Avoid";
        } else if (totalRisk > 50) {
            return "Reduce";
        } else {
            return "Accept";
        }
    }


    public List<RiskMatrixCell> getRiskMatrix(Long projectId) {
        Projet projet = projetRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Projet not found"));
        List<Actif> actifs = projet.getActifs();
        List<RiskMatrixCell> matrixCells = new ArrayList<>();
        for (Actif actif : actifs) {
            Risque risque = actif.getRisque();

            if (risque != null) {
                int likelihood = Math.min(6, Math.max(1, risque.getProbabilite()));
                int impact = Math.min(6, Math.max(1, risque.getValeurBaseImpact()));

                RiskMatrixCell cell = matrixCells.stream()
                        .filter(c -> c.getLikelihood() == likelihood && c.getImpact() == impact)
                        .findFirst()
                        .orElse(null);

                if (cell == null) {
                    cell = new RiskMatrixCell(likelihood, impact, 0);
                    matrixCells.add(cell);
                }
                cell.setCount(cell.getCount() + 1);
            }
        }
        return matrixCells;
    }

}
