package com.example.wasp.pokedex.provider;

import android.util.Log;

import com.example.wasp.pokedex.provider.model.Pokemon;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Wasp on 30/04/2015.
 */
public class PokeServiceImpl {

    private RestClient restClient = new RestClient();

    List<Pokemon> pokemons;

    public List<Pokemon> getAllPokemons(){

        // TODO vérifier pourquoi pokemons est vide !!!!!s

        pokemons = new ArrayList<Pokemon>();

        // get pokemon service for api
        PokeService pokeService = restClient.getPokeService();

        // get all (151) pokemons from api and insert them in base
//        for (int i = 0; i < 152; i++) {
        for (int i = 1; i < 10; i++) {

            pokeService.getPokemon(i, new Callback<Pokemon>() {
                @Override
                public void success(Pokemon pokemon, Response response) {
                    // success!
                    Log.i("App", pokemon.getName());
                    Log.i("App", pokemon.getResource_uri());
                    // you get the point...
                    pokemons.add(pokemon);
                }

                @Override
                public void failure(RetrofitError error) {
                    // something went wrong
                }
            });
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return pokemons;
    }

}
