package fr.diginamic.hello.controleurs;

import fr.diginamic.hello.dto.VilleDto;
import fr.diginamic.hello.entities.Ville;
import fr.diginamic.hello.dto.VilleMapper;
import fr.diginamic.hello.service.VilleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/villes")
public class VilleController {

    @Autowired
    private VilleService villeService;

    @GetMapping
    public Page<Ville> getVilles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return villeService.extractVilles(page, size);
    }

    @GetMapping("/recherche/nom")
    public List<VilleDto> rechercherParNom(@RequestParam String prefixe) {
        return villeService.rechercherParNom(prefixe)
                .stream()
                .map(VilleMapper::toDto)
                .toList();
    }

    @GetMapping("/recherche/population-min")
    public List<VilleDto> rechercherParPopulationMin(@RequestParam int min) {
        return villeService.rechercherParPopulationMin(min)
                .stream()
                .map(VilleMapper::toDto)
                .toList();
    }

    @GetMapping("/recherche/population-entre")
    public List<VilleDto> rechercherParPopulationEntre(@RequestParam int min, @RequestParam int max) {
        return villeService.rechercherParPopulationEntre(min, max)
                .stream()
                .map(VilleMapper::toDto)
                .toList();
    }

    @GetMapping("/departement/{idDepartement}/population-min")
    public List<VilleDto> rechercherParDepartementEtPopulationMin(
            @PathVariable int idDepartement,
            @RequestParam int min) {
        return villeService.rechercherParDepartementEtPopulationMin(idDepartement, min)
                .stream()
                .map(VilleMapper::toDto)
                .toList();
    }

    @GetMapping("/departement/{idDepartement}/population-entre")
    public List<VilleDto> rechercherParDepartementEtPopulationEntre(
            @PathVariable int idDepartement,
            @RequestParam int min,
            @RequestParam int max) {
        return villeService.rechercherParDepartementEtPopulationEntre(idDepartement, min, max)
                .stream()
                .map(VilleMapper::toDto)
                .toList();
    }

    @GetMapping("/departement/{idDepartement}/plus-grandes")
    public List<VilleDto> rechercherPlusGrandesVillesDepartement(
            @PathVariable int idDepartement,
            @RequestParam int n) {
        return villeService.rechercherPlusGrandesVillesDepartement(idDepartement, n)
                .stream()
                .map(VilleMapper::toDto)
                .toList();
    }
}