package com.example.wasp.pokedex.provider.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.wasp.pokedex.provider.RestClient;
import com.example.wasp.pokedex.provider.Service.PokeService;
import com.example.wasp.pokedex.provider.model.Pokemon;

/**
 * Created by Wasp on 04/05/2015.
 *
 */
public class GetPokemonTask extends AsyncTask<String, Integer, Pokemon> {

    public AsyncResponse delegate=null;

    private ProgressDialog progressDialog;
    private Context context;

    private RestClient restClient = new RestClient();

    public GetPokemonTask(Context context){
        this.context = context;
    }


    @Override
    protected Pokemon doInBackground(String... params) {

        Pokemon result;
        String url = params[0];

        // get pokemon service for api
        PokeService pokeService = restClient.getPokeService();

        result = pokeService.getPokemon(url);

        return result;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Getting Pokemon ...");
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(Pokemon pokemon){
        if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }
        delegate.processFinish(pokemon);
    }
}
