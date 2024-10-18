package OneWayDev.tn.OneWayDev.Controller;

import OneWayDev.tn.OneWayDev.Service.DepartementService;
import OneWayDev.tn.OneWayDev.Service.EntrepriseService;
import OneWayDev.tn.OneWayDev.dto.request.DepartementRequest;
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
@RequestMapping("/departement")
@CrossOrigin("*")
public class DepartementController {
private final DepartementService departementService;
    @GetMapping("/all/{offset}/{pageSige}/{field}/{idEntreprise}")
    public ResponseEntity<?> findDepartementByEntreprise(@PathVariable int offset,
                                                                     @PathVariable int pageSige,
                                                                     @PathVariable String field,
                                                                     @PathVariable Long idEntreprise){
        try {
            return new ResponseEntity<>(departementService.findDepartmentsByEntreprise(offset, pageSige, field,idEntreprise), HttpStatus.OK);

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
    @GetMapping("/findById/{idDepartement}")
    public ResponseEntity<?>findDepartementById(@PathVariable Long idDepartement){
        try {
            return new ResponseEntity<>(departementService.findDepartementById(idDepartement), HttpStatus.OK);
        }catch (NotFoundException execption){
            return new ResponseEntity<>(execption.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/add/{idEntreprise}")
    public ResponseEntity<?> addDepartement(@ModelAttribute @Valid DepartementRequest departementRequest,@PathVariable Long idEntreprise){
        try {
            return new ResponseEntity<>(departementService.addDepartement(departementRequest,idEntreprise), HttpStatus.CREATED);
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
        public ResponseEntity<?> editDepartement(@PathVariable Long idDepartement,@ModelAttribute @Valid DepartementRequest departementRequest){
            try {
                return new ResponseEntity<>(departementService.editDepartement(idDepartement,departementRequest), HttpStatus.OK);
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

    @DeleteMapping("/delete/{idDepartement}")
    public ResponseEntity<Map<String, String>> removeDepartement(@PathVariable Long idDepartement) {
        Map<String, String> response = new HashMap<>();
        try {
            departementService.deleteDepartement(idDepartement);
            response.put("message", "Departement dropped successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (NotFoundException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }
}
