package OneWayDev.tn.OneWayDev.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity

public class Vulnerabilite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    @ManyToMany(mappedBy = "vulnerabilites",cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE})
    private List<Menace>menaces;
    @ManyToMany()
    @JsonIgnore
    private List<Actif>actifs=new ArrayList<>();
    public void addMenace(Menace menace){
        this.getMenaces().add(menace);
        menace.getVulnerabilites().add(this);
    }
    public void removeMenace(Menace menace) {
        if (this.getMenaces().contains(menace)) {
            this.getMenaces().remove(menace);
            menace.getVulnerabilites().remove(this);
        }
    }

}
