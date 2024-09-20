package OneWayDev.tn.OneWayDev.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Departement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private Integer priorite;
    private Integer valeurEconomique;
    private String logo;
    @ManyToOne
    @JsonIgnore
    private Entreprise entreprise;
    @OneToMany(mappedBy = "departement",cascade =CascadeType.REMOVE)
    @JsonIgnore
    private List<Projet> projets;
}
