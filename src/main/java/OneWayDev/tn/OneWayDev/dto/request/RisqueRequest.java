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

public class RisqueRequest {
    @Valid

    @NotBlank(message = "nom  is required and cannot be blank.")

    private String risqueNom;
    @NotNull(message = "valeur Financiere is required and cannot be null.")
    private Integer risqueValeurFinanciere;
    @NotNull(message = " probabilite is required and cannot be null.")
    private Integer probabilite;
    @NotNull(message = "priorite is required and cannot be null.")
    private Integer risquePriorite;
    @NotNull(message = " valeur Base Impact is required and cannot be null.")
    private Integer valeurBaseImpact;







}
