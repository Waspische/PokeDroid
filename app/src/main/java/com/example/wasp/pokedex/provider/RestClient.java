package com.example.wasp.pokedex.provider;

import com.example.wasp.pokedex.provider.Service.PokeService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by Wasp on 30/04/2015.
 *
 * Client servant à créer et récupérer le service REST
 */
public class RestClient {

    public static final String BASE_URL = "http://pokeapi.co";
    private PokeService pokeService;

    public RestClient()
    {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BASE_URL)
                .setConverter(new GsonConverter(gson))
                .build();

        pokeService = restAdapter.create(PokeService.class);
    }

    public PokeService getPokeService()
    {
        return pokeService;
    }

}
