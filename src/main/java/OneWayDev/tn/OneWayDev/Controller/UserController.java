package OneWayDev.tn.OneWayDev.Controller;

import OneWayDev.tn.OneWayDev.Entity.User;
import OneWayDev.tn.OneWayDev.Repository.UserRepository;
import OneWayDev.tn.OneWayDev.Service.AuthService;
import OneWayDev.tn.OneWayDev.Service.UserService;
import OneWayDev.tn.OneWayDev.exception.NotFoundException;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("user")
@CrossOrigin("*")
@Validated
public class UserController {

    private UserService userService;
    private UserRepository userRepository;

    @GetMapping("/email/{email}")
    public User getUserByEmail(@PathVariable(value = "email") String email){
        return userService.getUserByEmail(email);
    }

    @GetMapping("/all")
    public List<User> getAllUsers(){
        return userService.findAll();
    }
    @GetMapping("/assignToChef")
    public List<User> getAllUsersAssignToChef(){
        return userService.findUsersToAssignChef();
    }

    @PutMapping("/block/{id}")
    public User blockUser(@PathVariable(value = "id") Long idUser){
        return userService.blockedUser(idUser, false);
    }

    @PutMapping("/unblock/{id}")
    public User unblockUser(@PathVariable(value = "id") Long idUser){
        return userService.blockedUser(idUser, true);
    }

    @PostMapping("/upload-profile-picture")
    public ResponseEntity<String> uploadProfilePicture(@RequestParam("file") MultipartFile file, @RequestParam("email") String email) {
      try{
          userService.uploadProfilePicture(file,email);
          return new ResponseEntity<>("Profile picture updated successfully", HttpStatus.OK);
      } catch (Exception e) {

          return new ResponseEntity<>("An unexpected error occurred",HttpStatus.INTERNAL_SERVER_ERROR);
      }

    }


    @GetMapping("/image/{imageName}")
    public ResponseEntity<org.springframework.core.io.Resource> getImage(@PathVariable String imageName) {
        // Assuming images are stored in src/main/resources/uploads directory
        Path imagePath = Paths.get("src/main/resources/upload").resolve(imageName);
        try {
            org.springframework.core.io.Resource resource = new UrlResource(imagePath.toUri());
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok().body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/delete/{idUser}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable Long idUser) {
        Map<String, String> response = new HashMap<>();
        try {
            userService.deleteUser(idUser);
            response.put("message", "User dropped successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (NotFoundException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }

}
