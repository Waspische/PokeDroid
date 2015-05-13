package com.example.wasp.pokedex.provider.Service;

import com.example.wasp.pokedex.provider.model.Description;
import com.example.wasp.pokedex.provider.model.Pokedex;
import com.example.wasp.pokedex.provider.model.Pokemon;
import com.example.wasp.pokedex.provider.model.Sprite;

import retrofit.Callback;
import retrofit.http.EncodedPath;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Wasp on 23/04/2015.
 *
 * Service pour effectuer les différrents appels à l'API en utilisant Retrofit
 */
public interface PokeService {

    @GET("/api/v1/pokedex/1/")
    Pokedex getPokedex();

    @GET("/api/v1/pokemon/{pokemonId}/")
    void getPokemon(@Path("pokemonId") int id, Callback<Pokemon> cb);


    @GET("/api/v1/pokemon/{pokemonId}/")
    Pokemon getPokemon(@Path("pokemonId") int id);

    @GET("/{url}")
    Pokemon getPokemon(@EncodedPath("url") String url);

    @GET("/{url}")
    Description getDescription(@EncodedPath("url") String url);

    @GET("/{url}")
    void getSprite(@EncodedPath ("url") String url, Callback<Sprite> cb);

}
