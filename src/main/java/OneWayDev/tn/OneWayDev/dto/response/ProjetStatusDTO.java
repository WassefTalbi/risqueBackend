package OneWayDev.tn.OneWayDev.dto.response;

import OneWayDev.tn.OneWayDev.Entity.Etat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProjetStatusDTO {
    private Etat status;
    private Long count;
}
