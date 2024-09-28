package OneWayDev.tn.OneWayDev.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Actif {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String description;
    private Integer priorite;
    private Integer valeurFinanciere;
    private Integer valeurDonnees;
    private String logo;
    @ManyToOne

    private Categorie categorie;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE,CascadeType.REMOVE})
    @JsonIgnore
    private List<Projet> projets = new ArrayList<>();
    @OneToOne(mappedBy = "actif",cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private Risque risque;
    @ManyToMany(mappedBy = "actifs",cascade = CascadeType.REMOVE)
    private List<Vulnerabilite>vulnerabilites;





    public void addProjet(Projet projet) {
        this.projets.add(projet);
        projet.getActifs().add(this);
    }

    public void removeProjet(Projet projet) {
        this.projets.remove(projet);
        projet.getActifs().remove(this);
    }


}
