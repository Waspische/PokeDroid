package com.example.wasp.pokedex.provider.model;

import java.io.Serializable;

/**
 * Created by Wasp on 06/05/2015.
 *
 * Objet pour mapper les données reçues de l'API
 * ____________________________________________________________________________
 * |                                                                          |
 * | les noms des attributs doivent être identiques à ceux des données reçues |
 * |                                                                          |
 * |__________________________________________________________________________|
 */
public class Sprite extends ModelClass implements Serializable{

   private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
