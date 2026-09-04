package fr.diginamic.hello.service;

import fr.diginamic.hello.dao.DepartementDao;
import fr.diginamic.hello.entities.Departement;
import fr.diginamic.hello.exceptions.DepartementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartementService {

    @Autowired
    private DepartementDao departementDao;

    public List<Departement> extractDepartements() {
        return departementDao.extraireDepartements();
    }

    public Departement extractDepartement(int idDepartement) {
        return departementDao.extraireDepartement(idDepartement);
    }

    public Departement extractDepartementParNom(String nomDepartement) {
        return departementDao.extraireDepartementParNom(nomDepartement)
                .stream()
                .findFirst()
                .orElse(null);
    }

    public Departement extractDepartementParCode(String codeDepartement) {
        return departementDao.extraireDepartementByCode(codeDepartement);
    }

    public List<Departement> insertDepartement(Departement departement) {
        departementDao.insererDepartement(departement);
        return departementDao.extraireDepartements();
    }

    public List<Departement> modifierDepartement(Departement departement) {
        departementDao.mettreAJourDepartement(departement);
        return departementDao.extraireDepartements();
    }

    public List<Departement> supprimerDepartement(int idDepartement) throws DepartementException {
        Departement departement = departementDao.extraireDepartement(idDepartement);

        if (departement == null) {
            throw new DepartementException("Département introuvable");
        }

        if (departement.getVilles() != null && !departement.getVilles().isEmpty()) {
            throw new DepartementException("Impossible de supprimer ce département car il contient encore des villes");
        }

        departementDao.supprimerDepartement(idDepartement);
        return departementDao.extraireDepartements();
    }
}
