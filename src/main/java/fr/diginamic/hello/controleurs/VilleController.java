package fr.diginamic.hello.controleurs;

import fr.diginamic.hello.exceptions.VilleException;
import fr.diginamic.hello.entities.Ville;
import jakarta.annotation.PostConstruct;
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
    public ResponseEntity<String> creerVille(@RequestBody Ville ville) throws VilleException {

        for (Ville villeExistante : villes) {
            if (villeExistante.getNom().equalsIgnoreCase(ville.getNom())) {
                throw new VilleException("Ville existente");
            }
        }

        ville.setId(prochainId++);
        villes.add(ville);

        return ResponseEntity.ok("Ville créée avec succès");

    }

    @PutMapping("/{id}")
    public ResponseEntity<String> modifierVille(@PathVariable int id, @RequestBody Ville villeModifiee) throws VilleException {
        for (Ville ville : villes) {
            if (ville.getId() == id) {
                ville.setNom(villeModifiee.getNom());
                ville.setPopulation(villeModifiee.getPopulation());
                return ResponseEntity.ok("Ville modifiée avec succès");
            }
        }
        throw new VilleException("Ville non trouvée");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> supprimerVille(@PathVariable int id) throws VilleException {

        for (int i = 0; i < villes.size(); i++) {
            if (villes.get(i).getId() == id) {
                villes.remove(i);
                return ResponseEntity.ok("Ville supprimée avec succès");
            }
        }
        throw new VilleException("Ville non trouvée");
    }

    @GetMapping("/recherche/nom")
    public List<Ville> rechercherparDebutNom(@RequestParam String nom) throws VilleException {
        List<Ville> resultat = new ArrayList<>();

        for (Ville ville : villes) {
            if (ville.getNom().toLowerCase().startsWith(nom.toLowerCase())) {
                resultat.add(ville);
            }
        }

        if (resultat.isEmpty()) {
            throw new VilleException("Aucune ville dont le nom commence par " + nom + " n'a été trouvée");
        }
        return resultat;
    }

    @GetMapping("/recherche/population-min")
    public List<Ville> rechercherParPopulationMin(@RequestParam int min) throws VilleException {

        List<Ville> resultat = new ArrayList<>();

        for (Ville ville : villes) {
            if (ville.getPopulation() >= min) {
                resultat.add(ville);
            }
        }

        if (resultat.isEmpty()) {
            throw new VilleException("Aucune ville n'a une population supérieure à " + min);
        }
        return resultat;
    }

    @GetMapping("/recherche/population-entre")
    public List<Ville> rechercherPopulationentre(@RequestParam int min, @RequestParam int max) throws VilleException {

        List<Ville> resultat = new ArrayList<>();

        for (Ville ville : villes) {
            if (ville.getPopulation() >= min && ville.getPopulation() <max) {
                resultat.add(ville);
            }
        }

        if (resultat.isEmpty()) {
            throw new VilleException("Aucune ville n'a une population comprise entre " + min + " et " + max);
        }
        return resultat;
    }

    private void verifierVille(Ville ville) throws VilleException {
        if (ville.getNom() == null || ville.getNom().trim().length() < 2) {
            throw new VilleException("Le nom de la ville doit contenir au moins 2 caractères");
        }

        if (ville.getPopulation() == null || ville.getPopulation() < 10) {
            throw new VilleException("La ville doit avoir au moins 10 habitants");
        }
    }

}


