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
    private Integer national_id;

    private List<Sprite> sprites;

    private List<Move> moves;

    private List<Description> descriptions;
    private Description selectedDescription;

    private List<Type> types;

    // convert to meters
    public Double getHeight() {
        return height / 10 ;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    // convert to kilograms
    public Double getWeight() {
        return weight / 10;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getNational_id() {
        return national_id;
    }

    public void setNational_id(Integer national_id) {
        this.national_id = national_id;
    }

    public List<Sprite> getSprites() {
        return sprites;
    }

    public void setSprites(List<Sprite> sprites) {
        this.sprites = sprites;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public List<Description> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(List<Description> descriptions) {
        this.descriptions = descriptions;
    }

    public Description getSelectedDescription() {
        return selectedDescription;
    }

    public void setSelectedDescription(Description selectedDescription) {
        this.selectedDescription = selectedDescription;
    }

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }

    @Override
    public String toString() {
        return "#" + String.format("%03d", this.getNational_id()) + " " + this.getName();
    }

}
