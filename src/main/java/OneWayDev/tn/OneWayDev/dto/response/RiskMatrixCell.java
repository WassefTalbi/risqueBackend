package OneWayDev.tn.OneWayDev.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RiskMatrixCell {
    private int likelihood;
    private int impact;
    private int count;





}
