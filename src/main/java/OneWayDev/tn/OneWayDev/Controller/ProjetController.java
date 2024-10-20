package OneWayDev.tn.OneWayDev.Controller;

import OneWayDev.tn.OneWayDev.Entity.Actif;
import OneWayDev.tn.OneWayDev.Entity.User;
import OneWayDev.tn.OneWayDev.Service.DepartementService;
import OneWayDev.tn.OneWayDev.Service.ProjetService;
import OneWayDev.tn.OneWayDev.dto.request.DepartementRequest;
import OneWayDev.tn.OneWayDev.dto.request.ProjetRequest;
import OneWayDev.tn.OneWayDev.dto.request.ProjetRequestChef;
import OneWayDev.tn.OneWayDev.dto.response.RiskMatrixCell;
import OneWayDev.tn.OneWayDev.dto.response.RiskSummary;
import OneWayDev.tn.OneWayDev.exception.EmailExistsExecption;
import OneWayDev.tn.OneWayDev.exception.NotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/projet")
@CrossOrigin("*")
public class ProjetController {
private final ProjetService projetService;
    @GetMapping("/all/{offset}/{pageSige}/{field}/{idDepartement}")
    public ResponseEntity<?> findProjetssByDepartement(@PathVariable int offset,
                                                                     @PathVariable int pageSige,
                                                                     @PathVariable String field,
                                                                     @PathVariable Long idDepartement){
        try {
            return new ResponseEntity<>(projetService.findProjetssByDepartement(offset, pageSige, field,idDepartement), HttpStatus.OK);

        }catch (IllegalArgumentException e) {

            return new ResponseEntity<>("Page size must not be less than one", HttpStatus.BAD_REQUEST);
        }catch (PropertyReferenceException e) {
            return new ResponseEntity<>("No property found ", HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            System.out.println("Caught Exception: " + e.getClass().getName());
            System.out.println("Exception Message: " + e.getMessage());
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/findById/{idProjet}")
    public ResponseEntity<?>findProjetById(@PathVariable Long idProjet){
        try {
            return new ResponseEntity<>(projetService.findProjetById(idProjet), HttpStatus.OK);
        }catch (NotFoundException execption){
            return new ResponseEntity<>(execption.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/add")
    public ResponseEntity<?> addProjet( @Valid @RequestBody ProjetRequest projetRequest){
        try {
            return new ResponseEntity<>(projetService.addProjet(projetRequest), HttpStatus.CREATED);
        }

        catch (Exception e) {
            System.out.println(e.getClass().getName());
            System.out.println(e.getMessage());
            return new ResponseEntity<>("An unexpected error occurred try again", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @PostMapping("/chef-add")
    public ResponseEntity<?> addProjetChef(@Valid @RequestBody ProjetRequestChef projetRequestChef, Principal principal){
        try {
            String chef=principal.getName();
            return new ResponseEntity<>(projetService.addProjetChef(projetRequestChef,chef), HttpStatus.CREATED);
        }

        catch (Exception e) {
            System.out.println(e.getClass().getName());
            System.out.println(e.getMessage());
            return new ResponseEntity<>("An unexpected error occurred try again", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

        @PutMapping ("/edit/{idProjet}")
        public ResponseEntity<?> editProjet(@PathVariable Long idProjet,@RequestBody @Valid ProjetRequest projetRequest){
            try {
                return new ResponseEntity<>(projetService.editProjet(idProjet,projetRequest), HttpStatus.OK);
            }
            catch (NotFoundException e){
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
            }
            catch (Exception e) {
                return new ResponseEntity<>("An unexpected error occurred try again", HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }

    @DeleteMapping("/delete/{idProjet}")
    public ResponseEntity<Map<String, String>> deleteProjet(@PathVariable Long idProjet) {
        Map<String, String> response = new HashMap<>();
        try {
            projetService.deleteProjet(idProjet);
            response.put("message", "projet dropped successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (NotFoundException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }

    @PostMapping("/assignActifs/{projectId}")
    public ResponseEntity<Map<String, String>> assignActifsToProject(@PathVariable Long projectId,@RequestBody List<Long> actifsId) {
        Map<String, String> response = new HashMap<>();
        try {
            projetService.assignActifsToProject(actifsId,projectId);
            response.put("message", "actifs assigned to project successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (NotFoundException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }


    @GetMapping("/{projectId}/actifs-not-in-project")
    public ResponseEntity<List<Actif>> getActifsNotInProject(@PathVariable Long projectId) {
        List<Actif> actifsNotInProject = projetService.getActifsNotInProject(projectId);
        return ResponseEntity.ok(actifsNotInProject);
    }
    @GetMapping("/{projectId}/actifs-in-project")
    public ResponseEntity<List<Actif>> getActifsInProject(@PathVariable Long projectId) {
        List<Actif> actifsInProject = projetService.getActifsInProject(projectId);
        return ResponseEntity.ok(actifsInProject);
    }

    @DeleteMapping("/actif/{projectId}/remove-actif/{actifId}")
    public ResponseEntity<Void> removeActifFromProject(@PathVariable Long projectId, @PathVariable Long actifId) {
        projetService.removeActifFromProject(actifId, projectId);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/risque/{projetId}")
    public ResponseEntity<RiskSummary> getProjetRisque(@PathVariable Long projetId) {
        double risqueProjet = projetService.calculateProjetRisque(projetId);
        double totalRisk = projetService.calculateProjetRisque(projetId);
        String treatment = projetService.determineRiskTreatment(totalRisk);
        RiskSummary riskSummary= new RiskSummary(totalRisk, treatment);
        return ResponseEntity.ok(riskSummary);
    }
    @GetMapping("/risque-matrix/{projectId}")
    public List<RiskMatrixCell> getRiskMatrix(@PathVariable Long projectId) {
        return projetService.getRiskMatrix(projectId);
    }

}
