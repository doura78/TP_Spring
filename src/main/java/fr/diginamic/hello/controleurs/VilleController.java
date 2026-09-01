package fr.diginamic.hello.controleurs;

import fr.diginamic.hello.ville.Ville;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/villes")
public class VilleController {

    private List<Ville> villes = new ArrayList<>();

    @PostConstruct
    public void initData() {
        villes.add(new Ville("Montpellier", 307101));
        villes.add(new Ville("Marseille", 350751));
        villes.add(new Ville("Milan", 254365));
    }

    @GetMapping
    public List<Ville> getVilles() {
        return villes;
    }


    @PostMapping
    public ResponseEntity<String> creerVille(@RequestBody Ville ville) {

        for (Ville villeExistante : villes) {
            if (villeExistante.getNom().equalsIgnoreCase(ville.getNom())) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("La ville existe déjà");
            }
        }

        villes.add(ville);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Ville insérée avec succès");

    }
}

