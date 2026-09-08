package fr.diginamic.hello.service;

import fr.diginamic.hello.dto.DepartementApiDto;
import fr.diginamic.hello.entities.Departement;
import fr.diginamic.hello.exceptions.DepartementException;
import fr.diginamic.hello.repositories.DepartementRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class DepartementService {

    @Autowired
    private DepartementRepository departementRepository;

    @Value("${application.init}")
    private boolean initData;

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

    @PostConstruct
    public void initData() {
        if (!initData) {
            return;
        }

        RestTemplate restTemplate = new RestTemplate();

        DepartementApiDto[] dto = restTemplate.getForObject(
                "https://geo.api.gouv.fr/departements",
                DepartementApiDto[].class
        );

        for (int i = 0; i < dto.length; i++) {
            Departement departementDB = departementRepository.findByCode(dto[i].getCode());

            if (departementDB == null) {
                departementDB = new Departement();
                departementDB.setNom(dto[i].getNom());
                departementDB.setCode(dto[i].getCode());
            } else {
                departementDB.setNom(dto[i].getNom());
            }

            departementRepository.save(departementDB);
        }

    }
}
