package OneWayDev.tn.OneWayDev.dto.request;


import OneWayDev.tn.OneWayDev.Entity.Etat;
import OneWayDev.tn.OneWayDev.advice.NotPast;
import jakarta.validation.Valid;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor

public class ProjetRequest {
    @Valid

    @NotBlank(message = "nom  is required and cannot be blank.")
    @Size(min=3,max = 25,message = "nom length min is 3 and max is 25")
    private String nom;
    @NotBlank(message = "description is required and cannot be blank.")
    @Size(min=3,max = 200,message = "description length min is 3 and max is 200      ")
    private String description;
    @NotNull(message = "avancement is required and cannot be null.")
    private Integer avancement;
    @NotNull(message = "chef Projet Id is required and cannot be null.")
    private Long chefProjetId;
    @NotNull(message = "departement Id is required and cannot be null.")
    private Long departementId;
    //@NotBlank(message = "etat is required and cannot be blank.")
    private Etat etat;
    @NotPast(message = "dateDebut cannot be in the past.")
    private LocalDate dateDebut;

    @NotPast(message = "dateFin cannot be in the past.")
    private LocalDate dateFin;




}
