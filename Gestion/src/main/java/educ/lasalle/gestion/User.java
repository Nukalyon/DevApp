package educ.lasalle.gestion;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Classe User permettant de stocker des informations sur un utilisateur et de les modifier
 */
public class User {
    private final IntegerProperty id = new SimpleIntegerProperty(this, "id");
    private String nom;
    private String email;

    /** Constructeur de la classe User
     * @param id        Integer, unique associé à cet utilisateur
     * @param nom       String, Nom de l'utilisateur
     * @param email     String, Email de l'utilisateur
     */
    public User(int id, String nom, String email) {
        this.id.set(id);
        this.nom = nom;
        this.email = email;
    }

    /**
     * Fonction permettant de récupérer l'id de l'utilisateur
     * @return l'id de l'utilisateur
     */
    public int getId() {
        return this.id.getValue();
    }

    /**
     * Fonction permettant de récupérer le nom de l'utilisateur
     * @return le nom de l'utilisateur
     */
    public String getNom() {
        return this.nom;
    }

    /**
     * Fonction permettant de récupérer le mail de l'utilisateur
     * @return l'email de l'utilisateur
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Fonction permettant de changer le nom de l'utilisateur
     * @param nom String, le nom que l'on va assigner à cet usager
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Fonction permettant de changer le mail de l'utilisateur
     * @param email String, le mail que l'on va assigner à cet usager
     */
    public void setEmail(String email) {
        this.email = email;
    }
}