package com.example.wasp.pokedex.util;

import com.example.wasp.pokedex.model.PersistancePokemon;

import java.util.Comparator;

/**
 * Created by Wasp on 17/05/2015.
 */
public class PersistancePokemonIdComparator implements Comparator<PersistancePokemon> {
    @Override
    public int compare(PersistancePokemon p1, PersistancePokemon p2) {
        return p1.getNational_id() - p2.getNational_id();
    }
}
