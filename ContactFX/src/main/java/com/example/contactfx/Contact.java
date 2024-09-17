package com.example.contactfx;

import javafx.beans.property.StringProperty;

import java.util.Objects;

public class Contact
{
    private String nom;
    private String prenom;
    private String telephone;
    private String mail;

    public Contact(String nom, String prenom, String telephone, String mail) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.mail = mail;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getMail() {
        return mail;
    }

    public void setInfos(String n, String p, String t, String m)
    {
        this.nom = n;
        this.prenom = p;
        this.telephone = t;
        this.mail = m;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Contact)) {
            return false;
        }
        Contact other = (Contact) obj;
        return Objects.equals(this.nom, other.nom) &&
                Objects.equals(this.prenom, other.prenom) &&
                Objects.equals(this.telephone, other.telephone) &&
                Objects.equals(this.mail, other.mail);
    }
    @Override
    public int hashCode() {
        return Objects.hash(this.nom, this.telephone, this.mail);
    }
}
