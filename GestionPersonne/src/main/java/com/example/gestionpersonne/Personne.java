package com.example.gestionpersonne;

import java.io.Serial;
import java.io.Serializable;

public class Personne implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String nom;
    private int age;
    private String mail;

    public Personne(String nom, int age, String mail) {
        this.nom = nom;
        this.age = age;
        this.mail = mail;
    }

    @Override
    public String toString() {
        return "Nom: " + this.nom + ", Age: " + this.age + ", Mail: " + this.mail;
    }
}
