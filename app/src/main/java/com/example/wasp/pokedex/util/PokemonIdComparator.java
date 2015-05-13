package com.example.wasp.pokedex.util;

import com.example.wasp.pokedex.provider.model.Pokemon;

import java.util.Comparator;

/**
 * Created by Wasp on 12/05/2015.
 */
public class PokemonIdComparator implements Comparator<Pokemon> {

    @Override
    public int compare(Pokemon p1, Pokemon p2) {
        return p1.getNational_id() - p2.getNational_id();

    }
}
