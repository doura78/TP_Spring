package fr.diginamic.hello.entities;

public class Ville {

    private Integer id;
    private String nom;
    private Integer population;

    public Ville() {
    }

    public Ville(Integer id, String nom, Integer population) {
        this.id = id;
        this.nom = nom;
        this.population = population;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }
}

