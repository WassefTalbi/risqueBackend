package OneWayDev.tn.OneWayDev.Service;

import OneWayDev.tn.OneWayDev.Entity.Entreprise;
import OneWayDev.tn.OneWayDev.Entity.Role;
import OneWayDev.tn.OneWayDev.Entity.User;
import OneWayDev.tn.OneWayDev.Enum.RoleType;
import OneWayDev.tn.OneWayDev.Repository.UserRepository;
import OneWayDev.tn.OneWayDev.dto.request.ProfileRequest;
import OneWayDev.tn.OneWayDev.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;


    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email " + email));
    }
    public List<User>findAll( ){
        Role role=new Role();
        role.setRoleType(RoleType.USER);
        List<User> users = userRepository.findByRolesRoleType(RoleType.USER);
        return users;
    }

   public List<User>findUsersToAssignChef( ){
        List<User> users = userRepository.findByRolesRoleType(RoleType.USER);
        return users;
    }

    public User findUserById(Long idUser){
       Optional<User>  user= userRepository.findById(idUser);
       if (!user.isPresent()){
           throw new NotFoundException("no user found");
       }
       return user.get();
    }



    public User enabledUser(Long idUser, Boolean enable){
        Optional<User>  findUser= userRepository.findById(idUser);
        if (!findUser.isPresent()){
            throw new NotFoundException("no user found");
        }
        User user= findUser.get();
        user.setEnabled(enable);
        return userRepository.save(user);
    }
    public User blockedUser(Long idUser, Boolean blocked){
        Optional<User>  findUser= userRepository.findById(idUser);
        if (!findUser.isPresent()){
            throw new NotFoundException("no user found");
        }

        User user= findUser.get();
        user.setNonLocked(blocked);
        return userRepository.save(user);
    }

    public int enableAppUser(String email) {
        return userRepository.enableAppUser(email);
    }


    public List<User> getAllUsers() {
        return userRepository.findByRolesRoleType(RoleType.USER);
    }
  public String  uploadProfilePicture( MultipartFile file,String email){
      String filePath = uploadFile(file);
      User user = getUserByEmail(email);
      user.setPhotoProfile(filePath);
      userRepository.save(user);
        return "Profile picture updated successfully";
  }

   public String uploadFile(MultipartFile file) {
    if (file.isEmpty()) {
        throw new IllegalArgumentException("Photo profile is required, should select a photo");
    }
    String filename = StringUtils.cleanPath(file.getOriginalFilename());
    try {
        if (filename.contains("..")) {
            throw new IllegalArgumentException("Cannot upload file with relative path outside current directory");
        }
        Path uploadDir = Paths.get("src/main/resources/uploads");
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        Path filePath = uploadDir.resolve(filename);
        if (Files.exists(filePath)) {
            // If file already exists, return the filename to save it in user's profile
            return filename;
        }

        Files.copy(file.getInputStream(), filePath);
        return filename;

    } catch (Exception e) {
        throw new RuntimeException("Failed to store file " + filename, e);
    }
}
    public void deleteUser(Long idUser){
        User user= userRepository.findById(idUser).orElseThrow(()->new NotFoundException(" no user found"));
        userRepository.deleteById(idUser);

    }
}
