package fr.diginamic.hello.controleurs;

import fr.diginamic.hello.entities.Departement;
import fr.diginamic.hello.exceptions.DepartementException;
import fr.diginamic.hello.service.DepartementService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departements")
public class DepartementController {

    @Autowired
    private DepartementService departementService;

    @Secured({"ROLE_USER","ROLE_ADMIN"})    @GetMapping
    public List<Departement> getDepartements() {
        return departementService.extractDepartements();
    }

    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @GetMapping("/{id}")
    public ResponseEntity<Departement> getDepartement(@PathVariable int id) {

        Departement departement = departementService.extractDepartement(id);

        if (departement == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(departement);
    }

    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @GetMapping("/recherche/nom")
    public List<Departement> rechercherparDebutNom(@RequestParam String nomDepartement) throws DepartementException {

        List<Departement> resultat = departementService.extractDepartements();

        if (resultat.isEmpty()) {
            throw new DepartementException("Aucun département dont le nom commence par " + nomDepartement + " n'a été trouvé");
        }
        return resultat;
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping
    public List<Departement> creerDepartement(@Valid @RequestBody Departement departement) throws DepartementException {

        verifierDepartement(departement);
        return departementService.insertDepartement(departement);
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/{id}")
    public List<Departement> modifierDepartement(@PathVariable int id, @Valid @RequestBody Departement departement)
            throws DepartementException {

        verifierDepartement(departement);
        return departementService.modifierDepartement( departement);
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    public List<Departement> supprimerDepartement(@PathVariable int id)
            throws DepartementException {
        return departementService.supprimerDepartement(id);
    }

    private void verifierDepartement(Departement departement) throws DepartementException {
        if (departement.getNom() == null || departement.getNom().trim().length() < 2) {
            throw new DepartementException("Le nom du département doit contenir au moins 2 caractères");
        }
    }
}
