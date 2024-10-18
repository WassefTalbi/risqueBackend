package OneWayDev.tn.OneWayDev.Controller;

import OneWayDev.tn.OneWayDev.Service.ActifService;
import OneWayDev.tn.OneWayDev.Service.RisqueService;
import OneWayDev.tn.OneWayDev.dto.request.ActifRequest;
import OneWayDev.tn.OneWayDev.dto.request.RisqueRequest;
import OneWayDev.tn.OneWayDev.exception.EmailExistsExecption;
import OneWayDev.tn.OneWayDev.exception.NotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/risque")
@CrossOrigin("*")
public class RisqueController {

private final RisqueService risqueService;
    @GetMapping("/all")
    public ResponseEntity<?> findAllRisque(){
        try {
            return new ResponseEntity<>(risqueService.findAllRisques(), HttpStatus.OK);

        }
        catch (Exception e) {
            System.out.println("Caught Exception: " + e.getClass().getName());
            System.out.println("Exception Message: " + e.getMessage());
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findById/{idRisque}")
    public ResponseEntity<?>findRisqueById(@PathVariable Long idRisque){
        try {
            return new ResponseEntity<>(risqueService.findRisqueById(idRisque), HttpStatus.OK);
        }catch (NotFoundException execption){
            return new ResponseEntity<>(execption.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/add")
    public ResponseEntity<?> addRisque(@Valid @ModelAttribute RisqueRequest risqueRequest){
        try {
            return new ResponseEntity<>(risqueService.addRisque(risqueRequest), HttpStatus.CREATED);
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

        @PutMapping ("/edit/{idRisque}")
        public ResponseEntity<?> editActif(@PathVariable Long idRisque,@ModelAttribute @Valid RisqueRequest risqueRequest){
            try {
                return new ResponseEntity<>(risqueService.editRisque(idRisque,risqueRequest), HttpStatus.CREATED);
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

    @DeleteMapping("/delete/{idRisque}")
    public ResponseEntity<Map<String, String>> removeActif(@PathVariable Long idRisque) {
        Map<String, String> response = new HashMap<>();
        try {
            risqueService.deleteRisque(idRisque);
            response.put("message", "actif dropped successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (NotFoundException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }
}
