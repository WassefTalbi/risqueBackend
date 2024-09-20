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
public class Vulnerabilite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    @ManyToMany(mappedBy = "vulnerabilites",cascade = CascadeType.REMOVE)
    private List<Menace>menaces;
    @ManyToMany
    @JsonIgnore
    private List<Actif>actifs;

}
