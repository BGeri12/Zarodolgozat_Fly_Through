package com.example.zarodolgozat_fly_through;

public class Users {

    private String felhasznaloNev,jelszo,email;

    public Users(){

    }

    public Users(String felhasznaloNev, String jelszo, String email) {
        this.felhasznaloNev = felhasznaloNev;
        this.jelszo = jelszo;
        this.email = email;
    }

    public String getFelhasznaloNev() {
        return felhasznaloNev;
    }

    public void setFelhasznaloNev(String felhasznaloNev) {
        this.felhasznaloNev = felhasznaloNev;
    }

    public String getJelszo() {
        return jelszo;
    }

    public void setJelszo(String jelszo) {
        this.jelszo = jelszo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
