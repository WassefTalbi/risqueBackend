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
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor

public class ActifRequest {
    @Valid

    @NotBlank(message = "nom  is required and cannot be blank.")

    private String nom;
    @NotBlank(message = "description is required and cannot be blank.")

    private String description;
    @NotNull(message = "valeur Financiere is required and cannot be null.")
    private Integer valeurFinanciere;
    @NotNull(message = "valeur de Donnees is required and cannot be null.")
    private Integer valeurDonnees;
    @NotNull(message = "priorite is required and cannot be null.")
    private Integer priorite;
    @NotNull(message = "actif logo is required.")
    private MultipartFile logo;
    @NotNull(message = "categorie Id is required and cannot be null.")
    private Long categorieId;
    @NotBlank(message = "nom  is required and cannot be blank.")

    private String risqueNom;
    @NotNull(message = " probabilite is required and cannot be null.")
    private Integer probabilite;
    @NotNull(message = " valeur Base Impact is required and cannot be null.")
    private Integer valeurBaseImpact;





}
