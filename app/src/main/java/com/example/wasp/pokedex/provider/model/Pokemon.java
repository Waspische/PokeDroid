package com.example.wasp.pokedex.provider.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Wasp on 23/04/2015.
 *
 * Objet pour mapper les données reçues de l'API
 * ____________________________________________________________________________
 * |                                                                          |
 * | les noms des attributs doivent être identiques à ceux des données reçues |
 * |                                                                          |
 * |__________________________________________________________________________|
 */
public class Pokemon extends ModelClass implements Serializable{

    private Double height;
    private Double weight;

    private List<Sprite> sprites;

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public List<Sprite> getSprites() {
        return sprites;
    }

    public void setSprites(List<Sprite> sprites) {
        this.sprites = sprites;
    }

    @Override
    public String toString() {
        return this.getName();
    }

}
