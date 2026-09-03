package fr.diginamic.hello.controleurs;

import fr.diginamic.hello.exceptions.VilleException;
import fr.diginamic.hello.entities.Ville;
import fr.diginamic.hello.service.VilleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/*
    * Contrôleur REST pour gérer les opérations sur les villes.
    * Ce contrôleur expose des endpoints pour interagir avec les villes via HTTP.
    * L'annotation @RequestMapping("/villes") définit le chemin de base pour tous les endpoints de ce contrôleur.
 */
@RestController
@RequestMapping("/villes")
public class VilleController {

    /*
        * Service pour gérer les opérations sur les villes.
        * L'annotation @Autowired permet à Spring d'injecter automatiquement une instance de VilleService.
     */
    @Autowired
    private VilleService villeService;

    /*
        * Endpoint pour récupérer toutes les villes.
        * @return Liste de toutes les villes
     */
    @GetMapping
    public List<Ville> getVilles() {
        return villeService.extractVilles();
    }

    /*
        * Endpoint pour récupérer une ville par son identifiant.
        * @param id Identifiant de la ville à récupérer
        * @return Ville correspondante ou null si non trouvée
     */
    @GetMapping("/{id}")
    public ResponseEntity<Ville> getVille(@PathVariable int id) {

        Ville ville = villeService.extractVille(id);

        if (ville == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ville);
    }

    /*
        * Endpoint pour rechercher des villes dont le nom commence par un suffixe donné.
        * @param suffixe Suffixe à rechercher dans le nom des villes
        * @return Liste des villes correspondantes
     */
    @GetMapping("/recherche/nom")
    public List<Ville> rechercherparDebutNom(@RequestParam String suffixe) throws VilleException {

        List<Ville> resultat = villeService.extractVilles(suffixe);

        if (resultat.isEmpty()) {
            throw new VilleException("Aucune ville dont le nom commence par " + suffixe + " n'a été trouvée");
        }
        return resultat;
    }

    /*
        * Endpoint pour rechercher des villes dont la population est supérieure à un minimum spécifié.
        * @param min Population minimale
        * @return Liste des villes correspondantes
     */
    @GetMapping("/recherche/population-min")
    public List<Ville> rechercherParPopulationMin(@RequestParam int min) throws VilleException {

        List<Ville> resultat = villeService.extractVilles(min);

        if (resultat.isEmpty()) {
            throw new VilleException("Aucune ville n'a une population supérieure à " + min);
        }
        return resultat;
    }

    /*
        * Endpoint pour rechercher des villes dont la population est comprise entre un minimum et un maximum spécifiés.
        * @param min Population minimale
        * @param max Population maximale
        * @return Liste des villes correspondantes
     */
    @GetMapping("/recherche/population-entre")
    public List<Ville> rechercherPopulationentre(@RequestParam int min, @RequestParam int max) throws VilleException {

        List<Ville> resultat = villeService.extractVilles(min, max);

        if (resultat.isEmpty()) {
            throw new VilleException("Aucune ville n'a une population comprise entre " + min + " et " + max);
        }
        return resultat;
    }

    /*
        * Endpoint pour créer une nouvelle ville.
        * @param ville Ville à créer
        * @return Liste de toutes les villes après l'insertion
        * @throws VilleException si le nom de la ville est vide ou si la population est inférieure à 10
     */
    @PostMapping
    public List<Ville> creerVille(@Valid @RequestBody Ville ville) throws VilleException {

        verifierVille(ville);
        return villeService.insertVille(ville);
    }

    /*
        * Endpoint pour modifier une ville existante.
        * @param id Identifiant de la ville à modifier
        * @param villeModifiee Ville modifiée
        * @return Liste de toutes les villes après la modification
        * @throws VilleException si le nom de la ville est vide ou si la population est inférieure à 10
     */
    @PutMapping("/{id}")
    public List<Ville> modifierVille(@PathVariable int id, @Valid @RequestBody Ville villeModifiee) throws VilleException {

        verifierVille(villeModifiee);
        return villeService.modifierVille(id, villeModifiee);

    }

    /*
        * Endpoint pour supprimer une ville existante.
        * @param id Identifiant de la ville à supprimer
        * @return Liste de toutes les villes après la suppression
        * @throws VilleException si la ville n'est pas trouvée
     */
    @DeleteMapping("/{id}")
    public List<Ville> supprimerVille(@PathVariable int id) throws VilleException {

        return villeService.supprimerVille(id);
    }

    /*
        * Méthode privée pour vérifier la validité d'une ville.
        * @param ville Ville à vérifier
        * @throws VilleException si le nom de la ville est vide ou si la population est inférieure à 10
     */
    private void verifierVille(Ville ville) throws VilleException {
        if (ville.getNom() == null || ville.getNom().trim().length() < 2) {
            throw new VilleException("Le nom de la ville doit contenir au moins 2 caractères");
        }

        if (ville.getPopulation() == 0 || ville.getPopulation() < 10) {
            throw new VilleException("La ville doit avoir au moins 10 habitants");
        }
    }

}


