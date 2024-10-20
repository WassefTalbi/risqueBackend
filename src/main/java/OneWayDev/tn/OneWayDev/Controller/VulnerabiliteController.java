package OneWayDev.tn.OneWayDev.Controller;


import OneWayDev.tn.OneWayDev.Entity.Actif;
import OneWayDev.tn.OneWayDev.Entity.Menace;
import OneWayDev.tn.OneWayDev.Entity.Vulnerabilite;
import OneWayDev.tn.OneWayDev.Service.VulnerabiliteService;

import OneWayDev.tn.OneWayDev.dto.request.MenaceRequest;
import OneWayDev.tn.OneWayDev.dto.request.VulnerabiliteRequest;
import OneWayDev.tn.OneWayDev.exception.NotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vulnerabilite")
@CrossOrigin("*")
public class VulnerabiliteController {
    private final VulnerabiliteService vulnerabiliteService;
    @GetMapping("/all")
    public ResponseEntity<?> findAllMenace(){
        try {
            return new ResponseEntity<>(vulnerabiliteService.findAllMenace(), HttpStatus.OK);

        }
        catch (Exception e) {
            System.out.println("Caught Exception: " + e.getClass().getName());
            System.out.println("Exception Message: " + e.getMessage());
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/of-actif/{idActif}")
    public ResponseEntity<?> findMenacesByActif(@PathVariable Long idActif){
        try {
            return new ResponseEntity<>(vulnerabiliteService.findMenaceByActif(idActif), HttpStatus.OK);

        }
        catch (Exception e) {
            System.out.println("Caught Exception: " + e.getClass().getName());
            System.out.println("Exception Message: " + e.getMessage());
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addMenace( @Valid @ModelAttribute MenaceRequest menaceRequest) {
        System.out.println("id"+menaceRequest.getActifId());
        System.out.println("nom"+menaceRequest.getNom());
        try {
            System.out.println("nom "+menaceRequest.getNom());
            Actif updatedActif = vulnerabiliteService.addMenace(menaceRequest);
            return new ResponseEntity<>(updatedActif, HttpStatus.CREATED);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.out.println("Caught Exception: " + e.getClass().getName());
            System.out.println("Exception Message: " + e.getMessage());
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add-vulnerabilite")
    public ResponseEntity<?> addVulnerabilite( @Valid @ModelAttribute VulnerabiliteRequest vulnerabiliteRequest) {
        try {
            Menace updatedVulnerabilite = vulnerabiliteService.addVulnerabilite(vulnerabiliteRequest);
            return new ResponseEntity<>(updatedVulnerabilite, HttpStatus.CREATED);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.out.println("Caught Exception: " + e.getClass().getName());
            System.out.println("Exception Message: " + e.getMessage());
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/remove-vulnerabilite/{menaceId}/{vulnerabiliteId}")
    public ResponseEntity<Void> removeMenaceFromVulnerabilite(@PathVariable Long menaceId, @PathVariable Long vulnerabiliteId) {
        vulnerabiliteService.removeVulnerabiliteFromMenace(menaceId, vulnerabiliteId);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/remove-menace/{actifId}/{menaceId}")
    public ResponseEntity<Void> removeMenaceFromProject(@PathVariable Long actifId, @PathVariable Long menaceId) {
        vulnerabiliteService.removeMenaceFromActif(actifId, menaceId);
        return ResponseEntity.noContent().build();
    }

}
