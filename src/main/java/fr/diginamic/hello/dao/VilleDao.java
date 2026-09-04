package fr.diginamic.hello.dao;

import fr.diginamic.hello.entities.Ville;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DAO pour gérer les opérations sur les villes.
 * Ce DAO utilise l'EntityManager pour interagir avec la base de données.
 */
@Repository
public class VilleDao {

    /**
     * EntityManager pour gérer les entités JPA.
     * L'annotation @PersistenceContext permet à Spring d'injecter automatiquement une instance d'EntityManager.
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * Extrait toutes les villes de la base de données.
     *
     * @return Liste de toutes les villes
     */
    public List<Ville> extraireVilles() {
        TypedQuery<Ville> query = em.createQuery("SELECT v FROM Ville v", Ville.class);
        return query.getResultList();
    }

    /**
     * Extrait une ville par son identifiant.
     * @param idVille Identifiant de la ville à extraire
     * @return Ville correspondante ou null si non trouvée
     */
    public Ville extraireVilleParId(int idVille) {
        return em.find(Ville.class, idVille);
    }

    /**
     * Extrait les villes dont le nom commence par un suffixe donné.
     * @param suffixe Suffixe à rechercher
     * @return Liste des villes correspondantes
     */
    public List<Ville> extraireVillesParNom(String suffixe) {
        TypedQuery<Ville> query = em.createQuery("SELECT v FROM Ville v WHERE lower(v.nom) LIKE lower(:suffixe)", Ville.class);
        return query
                .setParameter("suffixe", suffixe + "%")
                .getResultList();
    }

    /**
     * Extrait les villes dont la population est supérieure à un seuil donné.
     * @param min Seuil de population minimum
     * @return Liste des villes correspondantes
     */
    public List<Ville> extraireVillesParPopulationMin(int min) {
        TypedQuery<Ville> query = em.createQuery("SELECT v FROM Ville v WHERE v.population > :min", Ville.class);
        return query
                .setParameter("min", min)
                .getResultList();
    }

    /**
     * Extrait les villes dont la population est comprise entre deux seuils donnés.
     * @param min Seuil de population minimum
     * @param max Seuil de population maximum
     * @return Liste des villes correspondantes
     */
    public List<Ville> extraireVillesParPopulationEntre(int min, int max) {
        TypedQuery<Ville> query = em.createQuery("SELECT v FROM Ville v WHERE v.population BETWEEN :min and :max", Ville.class);
        return query
                .setParameter("min", min)
                .setParameter("max", max)
                .getResultList();
    }

    public List<Ville> extractVillesByDepartement(int idDepartement) {
        return em.createQuery(
                        "select v from Ville v where v.departement.id = :idDepartement",
                        Ville.class)
                .setParameter("idDepartement", idDepartement)
                .getResultList();
    }

    public List<Ville> extractPlusGrandesVillesDepartement(int idDepartement) {
        return em.createQuery(
                        "select v from Ville v where v.departement.id = :idDepartement order by v.population desc",
                        Ville.class)
                .setParameter("idDepartement", idDepartement)
                .getResultList();
    }

    public List<Ville> extractVillesByPopulationEtDepartement(int min, int max, int idDepartement) {
        return em.createQuery(
                        "select v from Ville v where v.population between :min and :max and v.departement.id = :idDepartement",
                        Ville.class)
                .setParameter("min", min)
                .setParameter("max", max)
                .setParameter("idDepartement", idDepartement)
                .getResultList();
    }

    /**
     * Insère une nouvelle ville dans la base de données.
     * @param ville Ville à insérer
     */
    @Transactional
    public void insererVille(Ville ville) {
        em.persist(ville);
    }

    /**
     * Modifie une ville existante dans la base de données.
     *
     * @param villeModifiee Ville contenant les nouvelles informations
     */
    @Transactional
    public void modifierVille(Ville villeModifiee) {
        em.merge(villeModifiee);
    }

    /**
     * Supprime une ville de la base de données.
     * @param idVille Identifiant de la ville à supprimer
     */
    @Transactional
    public void supprimerVille(int idVille) {
        Ville ville = em.find(Ville.class, idVille);
        if (ville != null) {
            em.remove(ville);
        }
    }
}
