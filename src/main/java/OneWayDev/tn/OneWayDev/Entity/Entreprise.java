package OneWayDev.tn.OneWayDev.Entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Entreprise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String adresse;
    private String domaine;
    private String email;
    private String logo;
    @OneToMany(mappedBy = "entreprise",cascade =CascadeType.REMOVE)
    private List<Departement>departements;



}
