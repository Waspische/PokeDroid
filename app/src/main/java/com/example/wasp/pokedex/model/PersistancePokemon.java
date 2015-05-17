package com.example.wasp.pokedex.model;

import java.io.Serializable;

/**
 * Created by Wasp on 23/04/2015.
 */
public class PersistancePokemon implements Serializable{

    private long id;
    private String name;
    private int national_id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {

        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNational_id() {
        return national_id;
    }

    public void setNational_id(int national_id) {
        this.national_id = national_id;
    }

    @Override
    public String toString() {
        return "#" + String.format("%03d", this.getNational_id()) + " " + this.getName();
    }
}
