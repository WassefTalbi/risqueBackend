package OneWayDev.tn.OneWayDev.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor

public class EntrepriseRequest {
    @Valid

    @NotBlank(message = "nom  is required and cannot be blank.")
    @Size(min=3,max = 25,message = "nom length min is 3 and max is 25")
    private String nom;
    @Email(message = "inavalid mail format")
    @NotBlank(message = "email is required and cannot be blank.")
    @Column(unique = true)
    private String email;
    @NotBlank(message = "adresse is required and cannot be blank.")
    @Size(min=3,max = 25,message = "adresse length min is 3 and max is 200      ")
    private String adresse;
    @NotBlank(message = "domaine is required and cannot be blank.")
    @Size(min=3,max = 25,message = "domaine length min is 3 and max is 25")
    private String domaine;

    private MultipartFile logo;



}
