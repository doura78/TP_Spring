package fr.diginamic.hello.dao;

import fr.diginamic.hello.entities.Departement;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DAO pour gérer les opérations sur les départements.
 * Ce DAO utilise l'EntityManager pour interagir avec la base de données.
 */
@Repository
public class DepartementDao {

    /**
     * EntityManager pour gérer les entités JPA.
     * L'annotation @PersistenceContext permet à Spring d'injecter automatiquement une instance d'EntityManager.
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * Extrait tous les départements de la base de données.
     *
     * @return
     */
    public List<Departement> extraireDepartements() {
        return em.createQuery("SELECT d FROM Departement d", Departement.class).getResultList();
    }

    /**
     * Extrait un département par son identifiant.
     *
     * @param idDepartement Identifiant du département à extraire
     * @return Département correspondant ou null si non trouvé
     */
    public Departement extraireDepartement(int idDepartement) {
        return em.find(Departement.class, idDepartement);
    }

    /**
     * Extrait les départements dont le nom correspond à un nom donné.
     *
     * @param nomDepartement
     * @return
     */
    public List<Departement> extraireDepartementParNom(String nomDepartement) {
        return em.createQuery("SELECT d FROM Departement d WHERE d.nom = :nom", Departement.class)
                .setParameter("nom", nomDepartement)
                .getResultList();
    }

    /**
     * Extrait un département par son code.
     *
     * @param code
     * @return
     */
    public Departement extraireDepartementByCode(String code) {
        List<Departement> resultats = em
                .createQuery("select d from Departement d where d.code = :code", Departement.class)
                .setParameter("code", code)
                .getResultList();

        return resultats.isEmpty() ? null : resultats.get(0);
    }

    /**
     * Insère un nouveau département dans la base de données.
     *
     * @param departement
     */
    @Transactional
    public void insererDepartement(Departement departement) {
        em.persist(departement);
    }

    /**
     * Met à jour un département existant dans la base de données.
     *
     * @param departement
     */
    @Transactional
    public void mettreAJourDepartement(Departement departement) {
        em.merge(departement);
    }

    /**
     * Supprime un département de la base de données.
     *
     * @param idDepartement
     */
    @Transactional
    public void supprimerDepartement(int idDepartement) {
        Departement departement = em.find(Departement.class, idDepartement);
        if (departement != null) {
            em.remove(departement);
        }
    }
}
