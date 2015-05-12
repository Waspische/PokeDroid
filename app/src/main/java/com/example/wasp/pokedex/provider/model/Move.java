package com.example.wasp.pokedex.provider.model;

import java.io.Serializable;

/**
 * Created by Wasp on 12/05/2015.
 */
public class Move extends ModelClass implements Serializable{

    private String learn_type;

    private Integer level;

    public String getLearn_type() {
        return learn_type;
    }

    public void setLearn_type(String learn_type) {
        this.learn_type = learn_type;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "Level " + level + " - " + getName();
    }
}
