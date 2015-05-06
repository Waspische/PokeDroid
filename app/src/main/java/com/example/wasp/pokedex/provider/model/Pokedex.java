package com.example.wasp.pokedex.provider.model;

import java.util.List;

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
public class Pokedex extends ModelClass {

    List<Pokemon> pokemon;

    public List<Pokemon> getPokemon() {
        return pokemon;
    }

    public void setPokemons(List<Pokemon> pokemon) {
        this.pokemon = pokemon;
    }
}
