package fr.diginamic.hello.controleurs;

import fr.diginamic.hello.exceptions.VilleException;
import fr.diginamic.hello.entities.Ville;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Retourne la liste de toutes les villes")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Liste des villes au format JSON",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Ville.class)))})
    })

    @GetMapping
    public List<Ville> getVilles() {
        return villes;
    }

    @Operation(summary = "Retourne une ville à partir de son identifiant")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ville trouvée",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Ville.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ville non trouvée",
                    content = @Content()
            )
    })

    @GetMapping("/{id}")
    public ResponseEntity<Ville> getVille(@Parameter(description = "Identifiant de la ville à récupérer",
            example = "1", required = true)
                                          @PathVariable int id) {
        for (Ville ville : villes) {
            if (ville.getId() == id) {
                return ResponseEntity.ok(ville);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Crée une nouvelle ville")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ville insérée avec succès",
                    content = @Content(mediaType = "text/plain")
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Erreur de validation ou ville déjà existante",
                    content = @Content(mediaType = "text/plain")
            )
    })

    @PostMapping
    public ResponseEntity<String> creerVille(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Ville à créer",
            required = true,
            content = @Content(schema = @Schema(implementation = Ville.class))
    )
                                             @RequestBody Ville ville) throws VilleException {

        verifierVille(ville);

        for (Ville villeExistante : villes) {
            if (villeExistante.getNom().equalsIgnoreCase(ville.getNom())) {
                throw new VilleException("La ville est déja existente");
            }
        }

        ville.setId(prochainId++);
        villes.add(ville);

        return ResponseEntity.ok("Ville créée avec succès");

    }

    @Operation(summary = "Modifie une ville existante")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ville modifiée avec succès",
                    content = @Content(mediaType = "text/plain")
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Erreur de validation ou ville introuvable",
                    content = @Content(mediaType = "text/plain")
            )
    })

    @PutMapping("/{id}")
    public ResponseEntity<String> modifierVille(@Parameter
         (description = "Identifiant de la ville à modifier", example = "2", required = true)
          @PathVariable int id,

          @io.swagger.v3.oas.annotations.parameters.RequestBody(
                  description = "Nouvelles données de la ville",
                  required = true,
                  content = @Content(schema = @Schema(implementation = Ville.class)))

          @RequestBody Ville villeModifiee) throws VilleException {

        for (Ville ville : villes) {
            if (ville.getId() == id) {
                ville.setNom(villeModifiee.getNom());
                ville.setPopulation(villeModifiee.getPopulation());
                return ResponseEntity.ok("Ville modifiée avec succès");
            }
        }
        throw new VilleException("Ville non trouvée");
    }

    @Operation(summary = "Supprime une ville à partir de son identifiant")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ville supprimée avec succès",
                    content = @Content(mediaType = "text/plain")
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Ville introuvable",
                    content = @Content(mediaType = "text/plain")
            )
    })

    @DeleteMapping("/{id}")
    public ResponseEntity<String> supprimerVille( @Parameter(description = "Identifiant de la ville à supprimer",
            example = "3", required = true)

            @PathVariable int id) throws VilleException {

        for (int i = 0; i < villes.size(); i++) {
            if (villes.get(i).getId() == id) {
                villes.remove(i);
                return ResponseEntity.ok("Ville supprimée avec succès");
            }
        }
        throw new VilleException("Ville non trouvée");
    }

    @Operation(summary = "Recherche les villes dont le nom commence par une chaîne donnée")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Liste des villes trouvées",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Ville.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Aucune ville trouvée",
                    content = @Content(mediaType = "text/plain")
            )
    })

    @GetMapping("/recherche/nom")
    public List<Ville> rechercherparDebutNom(@Parameter(description = "Début du nom à rechercher",
            example = "Mo", required = true)

            @RequestParam String nom) throws VilleException {

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
            if (ville.getPopulation() >= min && ville.getPopulation() < max) {
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


