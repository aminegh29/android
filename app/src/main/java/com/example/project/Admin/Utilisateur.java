package com.example.project.Admin;

public class Utilisateur {
    private String id;
    private String nom;
    private String prenom;
    private String email;
    private String phone;
    private boolean isSecurite;
    private boolean isProfessor;
    private String password;

    // Constructeur par défaut requis pour Firebase
    public Utilisateur() {
    }

    public Utilisateur(String id, String nom, String prenom, String email, String phone, boolean isSecurite, boolean isProfessor) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.phone = phone;
        this.isSecurite = isSecurite;
        this.isProfessor = isProfessor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isSecurite() {
        return isSecurite;
    }

    public void setSecurite(boolean securite) {
        isSecurite = securite;
    }

    public boolean isProfessor() {
        return isProfessor;
    }

    public void setProfessor(boolean professor) {
        isProfessor = professor;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
