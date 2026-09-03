package fr.diginamic.hello.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/*
 * Classe représentant une ville
 */
@Entity
public class Ville {

    /*
     * Identifiant unique de la ville
     * La valeur est générée automatiquement par la base de données.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /*
     * Nom de la ville
     */
    @NotBlank(message = "Le nom de la ville ne peut pas être vide")
    @Size(min = 2, message = "Le nom de la ville doit contenir au moins 2 caractères")
    private String nom;

    /*
     * Population de la ville
     */
    @Min(value = 1, message = "La population doit être supérieure ou égale à 1")
    private Integer population;

    /*
     * Département auquel appartient la ville
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departement_id")
    private Departement departement;

    /*
     * Constructeur sans argument
     */
    public Ville() {
    }

    /*
        * Constructeur avec arguments
     */
    public Ville(Integer id, String nom, Integer population) {
        this.id = id;
        this.nom = nom;
        this.population = population;

    }

    /*
        * @return id Identifiant unique de la ville
     */
    public Integer getId() {
        return id;
    }

    /*
     * @param id Identifiant unique de la ville
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /*
     * @return nom Nom de la ville
     */
    public String getNom() {
        return nom;
    }

    /*
     * @param nom Nom de la ville
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /*
     * @return population Population de la ville
     */
    public Integer getPopulation() {
        return population;
    }

    /*
     * @param population Population de la ville
     */
    public void setPopulation(Integer population) {
        this.population = population;
    }

    /*
     * @return departement Département auquel appartient la ville
     */
    public Departement getDepartement() {
        return departement;
    }
/*
        * @param departement Département auquel appartient la ville
 */
    public void setDepartement(Departement departement) {
        this.departement = departement;
    }
}

