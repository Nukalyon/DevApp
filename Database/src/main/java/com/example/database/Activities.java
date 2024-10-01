package com.example.database;

public class Activities {
    private String nom;
    private double prix;

    public Activities(String nom, double prix) {
        this.nom = nom;
        this.prix = prix;
    }

    public String getNom() {
        return nom;
    }

    public double getPrix() {
        return prix;
    }

    @Override
    public String toString() {
        return this.nom + " (" + this.prix + " $)";
    }
}
