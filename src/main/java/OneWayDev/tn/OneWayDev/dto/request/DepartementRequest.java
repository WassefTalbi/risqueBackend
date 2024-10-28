package OneWayDev.tn.OneWayDev.dto.request;


import jakarta.validation.Valid;

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

public class DepartementRequest {
    @Valid

    @NotBlank(message = "nom  is required and cannot be blank.")

    private String nom;
    @NotNull(message = "valeur Economique is required and cannot be null.")
    private Integer valeurEconomique;
    @NotNull(message = "priorite is required and cannot be null.")
    private Integer priorite;

    private MultipartFile logo;




}
