package OneWayDev.tn.OneWayDev.Service;

import OneWayDev.tn.OneWayDev.Entity.Actif;
import OneWayDev.tn.OneWayDev.Entity.Etat;
import OneWayDev.tn.OneWayDev.Entity.Projet;
import OneWayDev.tn.OneWayDev.Repository.ActifRepository;
import OneWayDev.tn.OneWayDev.Repository.ProjetRepository;
import OneWayDev.tn.OneWayDev.Repository.UserRepository;
import OneWayDev.tn.OneWayDev.dto.response.ProjetStatusDTO;
import OneWayDev.tn.OneWayDev.dto.response.RisqueImpactDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyticService {
    private final ActifRepository actifRepository;
    private final ProjetRepository projetRepository;
    private final UserRepository userRepository;
    public long getTotalUsers() {
        return userRepository.count();
    }

    public long getTotalProjects() {
        return projetRepository.count();
    }

    public long getTotalActifs() {
        return actifRepository.count();
    }

    public List<RisqueImpactDTO> getRisqueImpacts() {
        List<Actif> actifs = actifRepository.findAll();

        return actifs.stream()
                .filter(actif -> actif.getRisque() != null)
                .map(actif -> new RisqueImpactDTO(
                        actif.getNom(),
                        actif.getRisque().getProbabilite() * actif.getRisque().getValeurBaseImpact()
                ))
                .collect(Collectors.toList());
    }

    public List<ProjetStatusDTO> getProjetStatusCounts() {
        List<Projet> projets = projetRepository.findAll();
        Map<Etat, Long> statusCounts = projets.stream()
                .collect(Collectors.groupingBy(Projet::getEtat, Collectors.counting()));
        return statusCounts.entrySet().stream()
                .map(entry -> new ProjetStatusDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

}
