package com.example.wasp.pokedex.provider.model;

import java.io.Serializable;

/**
 * Created by Wasp on 13/05/2015.
 */
public class Description extends ModelClass implements Serializable {

    private String description;

    public Description(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
