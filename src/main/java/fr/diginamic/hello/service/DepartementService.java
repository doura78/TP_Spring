package fr.diginamic.hello.service;

import fr.diginamic.hello.entities.Departement;
import fr.diginamic.hello.exceptions.DepartementException;
import fr.diginamic.hello.repositories.DepartementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartementService {

    @Autowired
    private DepartementRepository departementRepository;

    public List<Departement> extractDepartements() {
        return departementRepository.findAll();
    }

    public Departement extractDepartement(int idDepartement) {
        return departementRepository.findById(idDepartement).orElse(null);
    }

    public Departement extractDepartementParNom(String nomDepartement) {
        return departementRepository.findByNomStartingWithIgnoreCase(nomDepartement)
                .stream()
                .findFirst()
                .orElse(null);
    }

    public Departement extractDepartementParCode(String codeDepartement) {
        return departementRepository.findByCode(codeDepartement);
    }

    public List<Departement> insertDepartement(Departement departement) {
        departementRepository.save(departement);
        return departementRepository.findAll();
    }

    public List<Departement> modifierDepartement(Departement departement) {
        departementRepository.save(departement);
        return departementRepository.findAll();
    }

    public List<Departement> supprimerDepartement(int idDepartement) throws DepartementException {
        Departement departement = departementRepository.findById(idDepartement).orElse(null);

        if (departement == null) {
            throw new DepartementException("Département introuvable");
        }

        if (departement.getVilles() != null && !departement.getVilles().isEmpty()) {
            throw new DepartementException("Impossible de supprimer ce département car il contient encore des villes");
        }

        departementRepository.deleteById(idDepartement);
        return departementRepository.findAll();
    }
}