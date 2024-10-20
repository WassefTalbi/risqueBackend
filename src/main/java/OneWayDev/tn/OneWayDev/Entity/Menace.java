package OneWayDev.tn.OneWayDev.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Menace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    @ManyToMany(mappedBy = "menaces",cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE})

    private List<Vulnerabilite>vulnerabilites=new ArrayList<>();
    @ManyToMany()
    @JsonIgnore
    private List<Actif>actifs=new ArrayList<>();

    public void addVulnerabilite(Vulnerabilite vulnerabilite){
        this.getVulnerabilites().add(vulnerabilite);
        vulnerabilite.getMenaces().add(this);
    }
    public void removeVulnerabilite(Vulnerabilite vulnerabilite) {
        if (this.getVulnerabilites().contains(vulnerabilite)) {
            this.getVulnerabilites().remove(vulnerabilite);
            vulnerabilite.getMenaces().remove(this);
        }
    }
}