package fr.diginamic.hello.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

/*
 * Classe représentant un département
 * * Cette classe est une entité JPA persistée dans la base de données.
 * Un département peut être associé à plusieurs villes.
 *  L'annotation {@link JsonIgnore} placée sur la liste des villes permet
 * d'éviter une boucle infinie lors de la conversion de l'objet en JSON.
 */
@Entity
public class Departement {

    /*
     * Identifiant unique du département
     * La valeur est générée automatiquement par la base de données.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /*
     * Code du département
     */
    @Min(value = 1, message = "Le code du département doit être supérieure ou égale à 1")
    private String code;

    /*
     * Nom du département
     */
    @NotNull
    @Size(min = 2, message = "Le nom du département doit contenir au moins 2 caractères")
    private String nom;

    /*
     * Liste des villes appartenant au département
     */
    @JsonIgnore
    @OneToMany(mappedBy = "departement")
    private List<Ville> villes;

    /*
     * Constructeur sans argument
     */
    public Departement() {

    }

    /*
     * Constructeur avec arguments
     * @param id Identifiant unique du département
     * @param code Code du département
     * @param nom Nom du département
     */
    public Departement(Integer id, String code, String nom) {
        this.id = id;
        this.code = code;
        this.nom = nom;
    }

    /*
     * @return id Identifiant unique du département
     */
    public Integer getId() {
        return id;
    }

    /*
     * @param id Identifiant unique du département
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /*
     * @return code Code du département
     */
    public String getCode() {
        return code;
    }

    /*
     * @param code Code du département
     */
    public void setCode(String code) {
        this.code = code;
    }

    /*
     * @return nom Nom du département
     */
    public String getNom() {
        return nom;
    }

    /*
     * @param nom Nom du département
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /*
     * @return villes Liste des villes appartenant au département
     */
    public List<Ville> getVilles() {
        return villes;
    }

    /*
     * @param villes Liste des villes appartenant au département
     */
    public void setVilles(List<Ville> villes) {
        this.villes = villes;
    }
}
