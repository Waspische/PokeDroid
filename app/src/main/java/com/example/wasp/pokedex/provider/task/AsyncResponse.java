package com.example.wasp.pokedex.provider.task;

import com.example.wasp.pokedex.provider.model.Pokemon;

import java.util.List;

/**
 * Created by Wasp on 06/05/2015.
 *
 * Interface permettant de récupérer les résultats des tâches asynchrones dans les fragments ou activités
 */
public interface AsyncResponse {

    void processFinish(List<Pokemon> output);
    void processFinish(Pokemon output);

}
