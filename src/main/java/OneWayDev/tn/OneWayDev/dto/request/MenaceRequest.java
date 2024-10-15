package OneWayDev.tn.OneWayDev.dto.request;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class MenaceRequest {
    @Valid

    @NotBlank(message = "nom  is required and cannot be blank.")
    @Size(min=3,max = 25,message = "nom length min is 3 and max is 25")
    private String nom;
    @NotNull(message = "actif Id is required and cannot be null.")
    private Long vulnerabiliteId;








}
