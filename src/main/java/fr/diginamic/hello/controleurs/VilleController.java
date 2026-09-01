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
    private int prochainId = 1;

    @PostConstruct
    public void initData() {
        villes.add(new Ville(prochainId++, "Montpellier", 307101));
        villes.add(new Ville(prochainId++, "Marseille", 350751));
        villes.add(new Ville(prochainId++, "Milan", 254365));
    }

    @GetMapping
    public List<Ville> getVilles() {
        return villes;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ville> getVille(@PathVariable int id) {
        for (Ville ville : villes) {
            if (ville.getId() == id) {
                return ResponseEntity.ok(ville);
            }
        }
        return ResponseEntity.notFound().build();
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

        ville.setId(prochainId++);
        villes.add(ville);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Ville insérée avec succès");

    }

    @PutMapping("/{id}")
    public ResponseEntity<String> modifierVille(@PathVariable int id, @RequestBody Ville villeModifiee) {
        for (Ville ville : villes) {
            if (ville.getId() == id) {
                ville.setNom(villeModifiee.getNom());
                ville.setPopulation(villeModifiee.getPopulation());
                return ResponseEntity.ok("Ville modifiée avec succès");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ville non trouvée");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> supprimerVille(@PathVariable int id) {

        for (int i = 0; i < villes.size(); i++) {
            if (villes.get(i).getId() == id) {
                villes.remove(i);
                return ResponseEntity.ok("Ville supprimée avec succès");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ville non trouvée");

    }
}

