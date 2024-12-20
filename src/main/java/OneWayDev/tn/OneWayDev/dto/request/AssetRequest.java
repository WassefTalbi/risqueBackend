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

public class AssetRequest {
    @Valid

    @NotBlank(message = "nom  is required and cannot be blank.")

    private String nom;
    private Boolean isNew;
    @NotBlank(message = "description is required and cannot be blank.")

    private String description;
    @NotNull(message = "valeur Financiere is required and cannot be null.")
    private Integer valeurFinanciere;
    @NotNull(message = "valeur de Donnees is required and cannot be null.")
    private Integer valeurDonnees;
    @NotNull(message = "priorite is required and cannot be null.")
    private Integer priorite;

    private MultipartFile logo;
    @NotNull(message = "categorie Id is required and cannot be null.")
    private Long categorieId;






}
