package fr.diginamic.hello.service;

import fr.diginamic.hello.entities.Ville;
import fr.diginamic.hello.repositories.VilleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VilleService {

    @Autowired
    private VilleRepository villeRepository;

    public Page<Ville> extractVilles(int page, int size) {
        return villeRepository.findAll(PageRequest.of(page, size));
    }

    public List<Ville> rechercherParNom(String prefixe) {
        return villeRepository.findByNomStartingWithIgnoreCase(prefixe);
    }

    public List<Ville> rechercherParPopulationMin(int min) {
        return villeRepository.findByPopulationGreaterThanOrderByPopulationDesc(min);
    }

    public List<Ville> rechercherParPopulationEntre(int min, int max) {
        return villeRepository.findByPopulationBetweenOrderByPopulationDesc(min, max);
    }

    public List<Ville> rechercherParDepartementEtPopulationMin(int idDepartement, int min) {
        return villeRepository.findByDepartementIdAndPopulationGreaterThanOrderByPopulationDesc(idDepartement, min);
    }

    public List<Ville> rechercherParDepartementEtPopulationEntre(int idDepartement, int min, int max) {
        return villeRepository.findByDepartementIdAndPopulationBetweenOrderByPopulationDesc(idDepartement, min, max);
    }

    public List<Ville> rechercherPlusGrandesVillesDepartement(int idDepartement, int n) {
        return villeRepository.findByDepartementIdOrderByPopulationDesc(idDepartement, PageRequest.of(0, n));
    }

    public List<Ville> rechercherParCodeDepartement(String code) {
        return villeRepository.findByDepartementCodeOrderByPopulationDesc(code);
    }

    public Ville creerVille(Ville ville) {
        return villeRepository.save(ville);
    }

    public Ville modifierVille(int id, Ville villeModifiee) {
        Ville villeExistante = villeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ville introuvable avec l'id : " + id));

        villeExistante.setNom(villeModifiee.getNom());
        villeExistante.setPopulation(villeModifiee.getPopulation());
        villeExistante.setDepartement(villeModifiee.getDepartement());

        return villeRepository.save(villeExistante);
    }

    public void supprimerVille(int id) {
        Ville villeExistante = villeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ville introuvable avec l'id : " + id));

        villeRepository.delete(villeExistante);
    }
}