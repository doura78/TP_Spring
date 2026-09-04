package fr.diginamic.hello.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * Représente une ville persistée en base de données.
 *
 * <p>Une ville possède un nom, une population et un département associé.</p>
 */
@Entity
public class Ville {

    /**
     * Identifiant unique de la ville.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Nom de la ville.
     */
    private String nom;

    /**
     * Population de la ville.
     */
    private Integer population;

    /**
     * Département auquel appartient la ville.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departement_id")
    private Departement departement;

    /**
     * Construit une ville vide.
     */
    public Ville() {
    }

    /**
     * Construit une ville avec ses principales informations.
     *
     * @param id identifiant de la ville
     * @param nom nom de la ville
     * @param population population de la ville
     */
    public Ville(Integer id, String nom, Integer population) {
        this.id = id;
        this.nom = nom;
        this.population = population;
    }

    /**
     * Retourne l'identifiant de la ville.
     *
     * @return identifiant de la ville
     */
    public Integer getId() {
        return id;
    }

    /**
     * Modifie l'identifiant de la ville.
     *
     * @param id identifiant de la ville
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Retourne le nom de la ville.
     *
     * @return nom de la ville
     */
    public String getNom() {
        return nom;
    }

    /**
     * Modifie le nom de la ville.
     *
     * @param nom nom de la ville
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Retourne la population de la ville.
     *
     * @return population de la ville
     */
    public Integer getPopulation() {
        return population;
    }

    /**
     * Modifie la population de la ville.
     *
     * @param population population de la ville
     */
    public void setPopulation(Integer population) {
        this.population = population;
    }

    /**
     * Retourne le département associé à la ville.
     *
     * @return département associé
     */
    public Departement getDepartement() {
        return departement;
    }

    /**
     * Modifie le département associé à la ville.
     *
     * @param departement département associé
     */
    public void setDepartement(Departement departement) {
        this.departement = departement;
    }
}