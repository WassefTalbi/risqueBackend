package OneWayDev.tn.OneWayDev.Controller;


import OneWayDev.tn.OneWayDev.Entity.Actif;
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
    public ResponseEntity<?> findAllVulnerabilite(){
        try {
            return new ResponseEntity<>(vulnerabiliteService.findAllVulnerabilite(), HttpStatus.OK);

        }
        catch (Exception e) {
            System.out.println("Caught Exception: " + e.getClass().getName());
            System.out.println("Exception Message: " + e.getMessage());
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/of-actif/{idActif}")
    public ResponseEntity<?> findVulnerabiliteByActif(@PathVariable Long idActif){
        try {
            return new ResponseEntity<>(vulnerabiliteService.findVulnerabiliteByActif(idActif), HttpStatus.OK);

        }
        catch (Exception e) {
            System.out.println("Caught Exception: " + e.getClass().getName());
            System.out.println("Exception Message: " + e.getMessage());
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addVulnerabilite( @Valid @ModelAttribute VulnerabiliteRequest vulnerabiliteRequest) {
        try {
            System.out.println("nom "+vulnerabiliteRequest.getNom());
            Actif updatedActif = vulnerabiliteService.addVulnerabilite(vulnerabiliteRequest);
            return new ResponseEntity<>(updatedActif, HttpStatus.CREATED);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.out.println("Caught Exception: " + e.getClass().getName());
            System.out.println("Exception Message: " + e.getMessage());
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add-menace")
    public ResponseEntity<?> addMenace( @ModelAttribute MenaceRequest menaceRequest) {
        try {
            Vulnerabilite updatedVulnerabilite = vulnerabiliteService.addMenace(menaceRequest);
            return new ResponseEntity<>(updatedVulnerabilite, HttpStatus.CREATED);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.out.println("Caught Exception: " + e.getClass().getName());
            System.out.println("Exception Message: " + e.getMessage());
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
