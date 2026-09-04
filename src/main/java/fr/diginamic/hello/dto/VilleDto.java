package fr.diginamic.hello.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class VilleDto {

    /**
     * Identifiant unique de la ville.
     */
    private Integer id;

    /**
     * Nom de la ville.
     */
    @NotBlank(message = "Le nom de la ville ne peut pas être vide")
    @Size(min = 2, message = "Le nom de la ville doit contenir au moins 2 caractères")
    private String nom;

    /**
     * Population de la ville.
     */
    @Min(value = 10, message = "La population doit être supérieure ou égale à 10")
    private Integer population;

    /**
     * Identifiant du département auquel appartient la ville.
     */
    private Integer idDepartement;

    /**
     * Code du département auquel appartient la ville.
     */
    private String codeDepartement;

    /**
     * Construit un DTO de ville vide.
     */
    public VilleDto() {
    }

    /**
     * Construit un DTO de ville avec ses principales informations.
     * @param id identifiant de la ville
     * @param nom nom de la ville
     * @param population population de la ville
     * @param idDepartement identifiant du département
     * @param codeDepartement code du département
     */
    public VilleDto(Integer id, String nom, Integer population, Integer idDepartement, String codeDepartement) {
        this.id = id;
        this.nom = nom;
        this.population = population;
        this.idDepartement = idDepartement;
        this.codeDepartement = codeDepartement;
    }

    /**
     * Vérifie que le département est renseigné soit par son identifiant, soit par son code.
     * @return true si l'un des deux est renseigné, false sinon
     */
    @AssertTrue(message = "Le code du département ou l'identifiant du département doit être renseigné")
    public boolean isDepartementRenseigne() {
        return idDepartement != null || (codeDepartement != null && !codeDepartement.isBlank());
    }

    /**
     * Retourne l'identifiant de la ville.
     * @return identifiant de la ville
     */
    public Integer getId() {
        return id;
    }

    /**
     * Modifie l'identifiant de la ville.
     * @param id identifiant de la ville
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Retourne le nom de la ville.
     * @return nom de la ville
     */
    public String getNom() {
        return nom;
    }

    /**
     * Modifie le nom de la ville.
     * @param nom nom de la ville
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Retourne la population de la ville.
     * @return population de la ville
     */
    public Integer getPopulation() {
        return population;
    }

    /**
     * Modifie la population de la ville.
     * @param population population de la ville
     */
    public void setPopulation(Integer population) {
        this.population = population;
    }

    /**
     * Retourne l'identifiant du département auquel appartient la ville.
     * @return identifiant du département
     */
    public Integer getIdDepartement() {
        return idDepartement;
    }

    /**
     * Modifie l'identifiant du département auquel appartient la ville.
     * @param idDepartement identifiant du département
     */
    public void setIdDepartement(Integer idDepartement) {
        this.idDepartement = idDepartement;
    }

    /**
     * Retourne le code du département auquel appartient la ville.
     * @return code du département
     */
    public String getCodeDepartement() {
        return codeDepartement;
    }

    /**
     * Modifie le code du département auquel appartient la ville.
     * @param codeDepartement code du département
     */
    public void setCodeDepartement(String codeDepartement) {
        this.codeDepartement = codeDepartement;
    }
}