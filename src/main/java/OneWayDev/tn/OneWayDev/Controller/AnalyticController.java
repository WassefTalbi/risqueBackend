package OneWayDev.tn.OneWayDev.Controller;


import OneWayDev.tn.OneWayDev.Service.AnalyticService;
import OneWayDev.tn.OneWayDev.dto.response.ProjetStatusDTO;
import OneWayDev.tn.OneWayDev.dto.response.RisqueImpactDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/analytic")
@CrossOrigin("*")
public class AnalyticController {
private final AnalyticService analyticService;

    @GetMapping("/counts")
    public ResponseEntity<Map<String, Long>> getAnalyticsCounts() {
        Map<String, Long> counts = new HashMap<>();
        counts.put("totalUsers", analyticService.getTotalUsers());
        counts.put("totalProjects", analyticService.getTotalProjects());
        counts.put("totalActifs", analyticService.getTotalActifs());
        return ResponseEntity.ok(counts);
    }
    @GetMapping("/risque-impacts")
    public ResponseEntity<List<RisqueImpactDTO>> getRisqueImpacts() {
        List<RisqueImpactDTO> impacts = analyticService.getRisqueImpacts();
        return ResponseEntity.ok(impacts);
    }
    @GetMapping("/projets-status")
    public ResponseEntity<List<ProjetStatusDTO>> getProjetStatusCounts() {
        List<ProjetStatusDTO> statusCounts = analyticService.getProjetStatusCounts();
        return ResponseEntity.ok(statusCounts);
    }

}
