package fr.diginamic.hello.service;

import fr.diginamic.hello.dao.DepartementDao;
import fr.diginamic.hello.dao.VilleDao;
import fr.diginamic.hello.dto.VilleDto;
import fr.diginamic.hello.entities.Departement;
import fr.diginamic.hello.entities.Ville;
import fr.diginamic.hello.exceptions.VilleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
    * Service pour gérer les opérations sur les villes.
    * Ce service utilise le DAO VilleDao pour interagir avec la base de données.
 */
@Service
public class VilleService {

    /**
        * DAO pour accéder aux données des villes.
        * L'annotation @Autowired permet à Spring d'injecter automatiquement une instance de VilleDao.
     */
    @Autowired
    private VilleDao villeDao;

    @Autowired
    private DepartementDao departementDao;

    /**
        * Extrait toutes les villes de la base de données.
        * @return Liste de toutes les villes
     */
    public List<Ville> extractVilles() {
        return villeDao.extraireVilles();
    }

    /**
        * Extrait une ville spécifique de la base de données.
        * @param idVille Identifiant de la ville à extraire
        * @return Ville correspondante ou null si non trouvée
     */
    public Ville extractVille(int idVille) {
        return villeDao.extraireVilleParId(idVille);
    }

    /**
        * Extrait les villes dont le nom contient un suffixe spécifique.
        * @param suffixe Suffixe à rechercher dans le nom des villes
        * @return Liste des villes correspondantes
     */
    public List<Ville> extractVilles(String suffixe) {
        return villeDao.extraireVillesParNom(suffixe);
    }

    /**
        * Extrait les villes dont la population est supérieure à un minimum spécifié.
        * @param min Population minimale
        * @return Liste des villes correspondantes
     */
    public List<Ville> extractVilles(int min) {
        return villeDao.extraireVillesParPopulationMin(min);
    }

    /**
        * Extrait les villes dont la population est comprise entre un minimum et un maximum spécifiés.
        * @param min Population minimale
        * @param max Population maximale
        * @return Liste des villes correspondantes
     */
    public List<Ville> extractVilles(int min, int max) {
        return villeDao.extraireVillesParPopulationEntre(min, max);
    }

    public List<Ville> extractPlusGrandesVillesDepartement(int idDepartement, int n) {
        List<Ville> villes = villeDao.extractPlusGrandesVillesDepartement(idDepartement);
        return villes.stream().limit(n).toList();
    }

    public List<Ville> extractVillesByPopulationEtDepartement(int min, int max, int idDepartement) {
        return villeDao.extractVillesByPopulationEtDepartement(min, max, idDepartement);
    }

    public Ville construireVilleDepuisDto(VilleDto dto) throws VilleException {
        Ville ville = new Ville();
        ville.setId(dto.getId());
        ville.setNom(dto.getNom());
        ville.setPopulation(dto.getPopulation());

        Departement departement = null;

        if (dto.getIdDepartement() != null) {
            departement = departementDao.extraireDepartement(dto.getIdDepartement());
        }

        if (departement == null && dto.getCodeDepartement() != null && !dto.getCodeDepartement().isBlank()) {
            departement = departementDao.extraireDepartementByCode(dto.getCodeDepartement());
        }

        if (departement == null && dto.getCodeDepartement() != null && !dto.getCodeDepartement().isBlank()) {
            departement = new Departement();
            departement.setCode(dto.getCodeDepartement());
            departement.setNom("Département " + dto.getCodeDepartement());
            departementDao.insererDepartement(departement);
        }
        return ville;
    }

    /**
        * Insère une nouvelle ville dans la base de données.
        * @param ville Ville à insérer
        * @return Liste de toutes les villes après l'insertion
        * @throws VilleException si le nom de la ville est vide ou si la population est négative
     */
    public List<Ville> insertVille(Ville ville) throws VilleException {
        if (ville.getNom() == null || ville.getNom().isEmpty()) {
            throw new VilleException("Le nom de la ville ne peut pas être vide");
        }
        if (ville.getPopulation() < 0) {
            throw new VilleException("La population de la ville doit être supérieure à 0");
        }

        villeDao.insererVille(ville);
        return villeDao.extraireVilles();
    }

    /**
        * Modifie une ville existante dans la base de données.
        * @param idVille Identifiant de la ville à modifier
        * @param villeModifiee Ville avec les nouvelles valeurs
        * @return Liste de toutes les villes après la modification
        * @throws VilleException si la ville n'est pas trouvée ou si les nouvelles valeurs sont invalides
     */
    public List<Ville> modifierVille(int idVille, Ville villeModifiee) throws VilleException {
        Ville ville = villeDao.extraireVilleParId(idVille);

        if (ville == null) {
            throw new VilleException("Ville introuvable");
        }

        ville.setNom(villeModifiee.getNom());
        ville.setPopulation(villeModifiee.getPopulation());
        ville.setDepartement(villeModifiee.getDepartement());
        villeDao.modifierVille(ville);

        return villeDao.extraireVilles();
    }

    /**
        * Supprime une ville de la base de données.
        * @param idVille Identifiant de la ville à supprimer
        * @return Liste de toutes les villes après la suppression
        * @throws VilleException si la ville n'est pas trouvée
     */
    public List<Ville> supprimerVille(int idVille) throws VilleException {
        Ville ville = villeDao.extraireVilleParId(idVille);

        if (ville == null) {
            throw new VilleException("Ville non trouvée");
        }

        villeDao.supprimerVille(idVille);

        return villeDao.extraireVilles();
    }

}
