package com.example.wasp.pokedex.provider;

import com.example.wasp.pokedex.provider.model.Pokemon;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Wasp on 23/04/2015.
 */
public interface PokeService {

        @GET("/api/v1/pokemon/{pokemonId}/")
        void getPokemon(@Path("pokemonId") int id, Callback<Pokemon> cb);

        @GET("{url}")
        void get(@Path("url") String url, Callback<Object> object);

}
