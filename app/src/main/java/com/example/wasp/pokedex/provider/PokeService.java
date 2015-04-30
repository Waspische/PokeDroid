package com.example.wasp.pokedex.provider;

import com.example.wasp.pokedex.model.Pokemon;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Wasp on 23/04/2015.
 */
public interface PokeService {

        @GET("/pokemon/{pokemonId}/")
        void getPokemon(@Path("pokemonId") String user, Callback<Pokemon> cb);

}
