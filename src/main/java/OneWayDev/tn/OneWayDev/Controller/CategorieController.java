package OneWayDev.tn.OneWayDev.Controller;

import OneWayDev.tn.OneWayDev.Entity.Categorie;
import OneWayDev.tn.OneWayDev.Service.CategorieService;
import OneWayDev.tn.OneWayDev.Service.DepartementService;
import OneWayDev.tn.OneWayDev.dto.request.CategorieRequest;
import OneWayDev.tn.OneWayDev.dto.request.DepartementRequest;
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
@RequestMapping("/categorie")
@CrossOrigin("*")
public class CategorieController {
private final CategorieService categorieService;
    @GetMapping("/all")
    public ResponseEntity<?> findAll(){
        try {
            return new ResponseEntity<>(categorieService.findAll(), HttpStatus.OK);

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
    @GetMapping("/findById/{idCategorie}")
    public ResponseEntity<?>findDepartementById(@PathVariable Long idCategorie){
        try {
            return new ResponseEntity<>(categorieService.findCategorieById(idCategorie), HttpStatus.OK);
        }catch (NotFoundException execption){
            return new ResponseEntity<>(execption.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/add")
    public ResponseEntity<?> addCategorie(@Valid @ModelAttribute CategorieRequest categorieRequest){
        try {
            return new ResponseEntity<>(categorieService.addCategorie(categorieRequest), HttpStatus.CREATED);
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
    /*
        @PutMapping ("/edit/{idEntreprise}")
        public ResponseEntity<?> editCategorie(@PathVariable Long idEntreprise,@ModelAttribute @Valid EntrepriseRequest entrepriseRequest){
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
    */
    @DeleteMapping("/delete/{iCategorie}")
    public ResponseEntity<Map<String, String>> removeCategorie(@PathVariable Long iCategorie) {
        Map<String, String> response = new HashMap<>();
        try {
            categorieService.deleteCategorie(iCategorie);
            response.put("message", "Categorie dropped successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (NotFoundException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }
}
