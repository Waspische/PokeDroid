package com.example.wasp.pokedex.provider.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Wasp on 23/04/2015.
 *
 * Classe de base contenant les attributs communs à chaque objet renvoyé par l'API
 */
public class ModelClass implements Serializable{

    private String name;

    private String resource_uri;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResource_uri() {
        return resource_uri;
    }

    public void setResource_uri(String resource_uri) {
        this.resource_uri = resource_uri;
    }
}
