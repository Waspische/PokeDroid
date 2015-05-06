package com.example.wasp.pokedex.provider.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;

import com.example.wasp.pokedex.provider.Service.PokeService;
import com.example.wasp.pokedex.provider.RestClient;
import com.example.wasp.pokedex.provider.model.Pokemon;

import java.util.List;

/**
 * Created by Wasp on 30/04/2015.
 *
 */
public class GetPokedexTask extends AsyncTask<Integer, Integer, List<Pokemon>> {

    public AsyncResponse delegate=null;

    private ArrayAdapter<Pokemon> adapter;
    private ProgressDialog progressDialog;
    private Context context;

    private RestClient restClient = new RestClient();

    public GetPokedexTask(ArrayAdapter adapter, Context context){
        this.adapter = adapter;
        this.context = context;
    }


    @Override
    protected List<Pokemon> doInBackground(Integer... params) {

        List<Pokemon> result = null;

        // get pokemon service for api
        PokeService pokeService = restClient.getPokeService();

//        for (int i = 1; i <= nbPokemons; i++) {
//            result.add(pokeService.getPokemon(i));
//
//            // aller chercher les images des pokémons
//
//            publishProgress(i,nbPokemons);
//        }

        result = pokeService.getPokedex().getPokemon();

        // TODO order list by id

        return result;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Getting Pokedex ...");
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(List<Pokemon> pokemons){
        adapter.addAll(pokemons);
        if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }
        delegate.processFinish(pokemons);
    }

//    @Override
//    protected void onProgressUpdate(Integer... values){
//        progressDialog.setMessage("Getting Pokemon ... " + values[0] + "/" + values[1]);
//
//    }
}
