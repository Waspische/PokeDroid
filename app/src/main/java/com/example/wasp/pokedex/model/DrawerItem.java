package com.example.wasp.pokedex.model;

import java.io.Serializable;

/**
 * Created by Wasp on 23/04/2015.
 */
public class DrawerItem  implements Serializable{

    private String label;
    private int image;

    public DrawerItem(String label, int image) {
        this.label = label;
        this.image = image;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
