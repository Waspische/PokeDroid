package com.example.wasp.pokedex.model;

/**
 * Created by Wasp on 23/04/2015.
 */
public class PersistancePokemon {

    private long id;
    private String name;

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

    @Override
    public String toString() {
        return "PersistancePokemon{" +
                "name='" + name + '\'' +
                '}';
    }
}
