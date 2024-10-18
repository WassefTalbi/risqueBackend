package OneWayDev.tn.OneWayDev.Controller;

import OneWayDev.tn.OneWayDev.Service.ActifService;
import OneWayDev.tn.OneWayDev.Service.DepartementService;
import OneWayDev.tn.OneWayDev.dto.request.ActifRequest;
import OneWayDev.tn.OneWayDev.dto.request.AssetRequest;
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
@RequestMapping("/actif")
@CrossOrigin("*")
public class ActifController {
private final ActifService actifService;
    @GetMapping("/all")
    public ResponseEntity<?> findAllActifs(){
        try {
            return new ResponseEntity<>(actifService.findAllActifs(), HttpStatus.OK);

        }
        catch (Exception e) {
            System.out.println("Caught Exception: " + e.getClass().getName());
            System.out.println("Exception Message: " + e.getMessage());
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/allByCategorie/{idCategorie}")
    public ResponseEntity<?> findActifsByCategorie(@PathVariable Long idCategorie){
        try {
            return new ResponseEntity<>(actifService.findActifsByCategorie(idCategorie), HttpStatus.OK);

        }
        catch (Exception e) {
            System.out.println("Caught Exception: " + e.getClass().getName());
            System.out.println("Exception Message: " + e.getMessage());
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/findById/{idActif}")
    public ResponseEntity<?>findActifById(@PathVariable Long idActif){
        try {
            return new ResponseEntity<>(actifService.findActifById(idActif), HttpStatus.OK);
        }catch (NotFoundException execption){
            return new ResponseEntity<>(execption.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/add")
    public ResponseEntity<?> addActif(@Valid @ModelAttribute ActifRequest actifRequest){
        try {
            return new ResponseEntity<>(actifService.addActif(actifRequest), HttpStatus.CREATED);
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

        @PutMapping ("/edit/{idActif}")
        public ResponseEntity<?> editActif(@PathVariable Long idActif,@ModelAttribute @Valid AssetRequest assetRequest){
            assetRequest.setIsNew(false);
            try {
                return new ResponseEntity<>(actifService.editActif(idActif,assetRequest), HttpStatus.CREATED);
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

    @DeleteMapping("/delete/{idActif}")
    public ResponseEntity<Map<String, String>> removeActif(@PathVariable Long idActif) {
        Map<String, String> response = new HashMap<>();
        try {
            actifService.deleteActif(idActif);
            response.put("message", "Actif dropped successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (NotFoundException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }



}
