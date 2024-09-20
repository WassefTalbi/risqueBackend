package OneWayDev.tn.OneWayDev.Entity;


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
public class Projet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String description;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private Integer avancement;
    private Etat etat;
    @ManyToOne
    private Departement departement;
    @ManyToOne
    private User chefProjet;
    @ManyToMany(mappedBy = "projets", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Actif> actifs = new ArrayList<>();


    public void addActif(Actif actif) {
        this.actifs.add(actif);
        actif.getProjets().add(this);
    }

    public void removeActif(Actif actif) {
        this.actifs.remove(actif);
        actif.getProjets().remove(this);
    }









}
