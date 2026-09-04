package fr.diginamic.hello.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

/**
 * Représente un département persisté en base de données.
 * Un département peut être associé à plusieurs villes.</p>
 * L'annotation {@link JsonIgnore} sur la liste des villes permet
 * d'éviter une boucle infinie lors de la sérialisation JSON.
 */
@Entity
public class Departement {

    /**
     * Identifiant unique du département.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Code du département.
     */
    private String code;

    /**
     * Nom du département.
     */
    private String nom;

    /**
     * Liste des villes appartenant au département.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "departement")
    private List<Ville> villes;

    /**
     * Construit un département vide.
     */
    public Departement() {
    }

    /**
     * Construit un département avec ses principales informations
     * @param id identifiant du département
     * @param code code du département
     * @param nom nom du département
     */
    public Departement(Integer id, String code, String nom) {
        this.id = id;
        this.code = code;
        this.nom = nom;
    }

    /**
     * Retourne l'identifiant du département.
     *
     * @return identifiant du département
     */
    public Integer getId() {
        return id;
    }

    /**
     * Modifie l'identifiant du département.
     *
     * @param id identifiant du département
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Retourne le code du département.
     *
     * @return code du département
     */
    public String getCode() {
        return code;
    }

    /**
     * Modifie le code du département.
     *
     * @param code code du département
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Retourne le nom du département.
     *
     * @return nom du département
     */
    public String getNom() {
        return nom;
    }

    /**
     * Modifie le nom du département.
     *
     * @param nom nom du département
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Retourne les villes du département.
     *
     * @return liste des villes
     */
    public List<Ville> getVilles() {
        return villes;
    }

    /**
     * Modifie la liste des villes du département.
     *
     * @param villes liste des villes
     */
    public void setVilles(List<Ville> villes) {
        this.villes = villes;
    }
}