package OneWayDev.tn.OneWayDev.Controller;

import OneWayDev.tn.OneWayDev.Service.EntrepriseService;
import OneWayDev.tn.OneWayDev.dto.request.EntrepriseRequest;
import OneWayDev.tn.OneWayDev.exception.EmailExistsExecption;
import OneWayDev.tn.OneWayDev.exception.NotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/entreprise")
@CrossOrigin("*")
public class EntrepriseController {
private final EntrepriseService entrepriseService;
    @GetMapping("/all/{offset}/{pageSige}/{field}")
    public ResponseEntity<?> findEntrepriseswithPaginationAndSorting(@PathVariable int offset,
                                                                     @PathVariable int pageSige,
                                                                     @PathVariable String field){
        try {
            return new ResponseEntity<>(entrepriseService.findEntrepriseswithPaginationAndSorting(offset, pageSige, field), HttpStatus.OK);

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
    @GetMapping("/findById/{idEntreprise}")
    public ResponseEntity<?>findEntrepriseById(@PathVariable Long idEntreprise){
        try {
            return new ResponseEntity<>(entrepriseService.findEntrepriseById(idEntreprise), HttpStatus.OK);
        }catch (NotFoundException execption){
            return new ResponseEntity<>(execption.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/add")
    public ResponseEntity<?> addEntreprisee(@ModelAttribute @Valid EntrepriseRequest entrepriseRequest){
        try {
            return new ResponseEntity<>(entrepriseService.addEntrprise(entrepriseRequest), HttpStatus.CREATED);
        }
        catch (EmailExistsExecption e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);//409
        }
        catch (Exception e) {
            System.out.println(e.getClass().getName());
            System.out.println(e.getMessage());
            return new ResponseEntity<>("An unexpected error occurred try again", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping ("/edit/{idEntreprise}")
    public ResponseEntity<?> editEntreprise(@PathVariable Long idEntreprise,@ModelAttribute @Valid EntrepriseRequest entrepriseRequest){
        try {
            return new ResponseEntity<>(entrepriseService.editEntreprise(idEntreprise,entrepriseRequest), HttpStatus.CREATED);
        }
        catch (NotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            System.out.println(e.getClass().getName());
            System.out.println(e.getMessage());
            return new ResponseEntity<>("An unexpected error occurred try again", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/delete/{idEntreprise}")
    public ResponseEntity<Map<String, String>> removeEntreprise(@PathVariable Long idEntreprise) {
        Map<String, String> response = new HashMap<>();
        try {
            entrepriseService.deleteEntreprise(idEntreprise);
            response.put("message", "Entreprise dropped successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (NotFoundException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }
}
