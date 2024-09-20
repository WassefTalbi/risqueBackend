package OneWayDev.tn.OneWayDev.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor

public class CategorieRequest {
    @Valid

    @NotBlank(message = "nom  is required and cannot be blank.")
    @Size(min=3,max = 25,message = "nom length min is 3 and max is 25")
    private String nom;

    @NotNull(message = "categorie logo is required.")
    private MultipartFile logo;



}
