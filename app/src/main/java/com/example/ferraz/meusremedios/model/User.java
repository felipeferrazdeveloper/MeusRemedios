package com.example.ferraz.meusremedios.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ferraz on 02/10/17.
 */

public class User {

    private String username;
    private String email;
    private List<Receita> remedios;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Receita> getRemedios() {
        return remedios;
    }

    public void setRemedios(List<Receita> remedios) {
        this.remedios = remedios;
    }

    public User() {
        this.remedios = new ArrayList<Receita>();
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

}
