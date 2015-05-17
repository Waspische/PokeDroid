package com.example.wasp.pokedex.ui.pokemons;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.wasp.pokedex.PokeActivity;
import com.example.wasp.pokedex.R;
import com.example.wasp.pokedex.dao.PersistancePokemonsDataSource;
import com.example.wasp.pokedex.provider.model.Pokemon;
import com.example.wasp.pokedex.provider.task.AsyncResponse;
import com.example.wasp.pokedex.provider.task.GetPokedexTask;
import com.example.wasp.pokedex.provider.RestClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;

/**
 * Created by Wasp on 23/04/2015.
 */
public class PokemonsFragment extends Fragment implements AsyncResponse{

    // liaison à la base de données
    private PersistancePokemonsDataSource dataSource;

    private final String KEY_POKEMON = "pokemon";

    /**
     * UI components
     */
    @InjectView(R.id.listPokemons)
    ListView pokemons;

    ProgressDialog progressDialog;

    /**
     * UI related attributes
     */
    private ArrayAdapter<Pokemon> adapter;

    /**
     * Attributes
     */
    private List<Pokemon> pokemonList = new ArrayList<>();

    /**
     * AsyncTask
     */
    public GetPokedexTask getPokedexTask;

    public PokemonsFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.pokemons_fragment, container, false);
        ButterKnife.inject(this, view);

        // change le titre
        getActivity().setTitle(R.string.pokedex);

        // changer l'adapter si on veut mettre plus que le nom
        adapter = new ArrayAdapter<>(view.getContext(),
                android.R.layout.simple_list_item_1);

        pokemons.setAdapter(adapter);

        // appel de la tâche asynchrone pour récupérer les pokémons
        getPokedexTask = new GetPokedexTask(adapter, getActivity());
        getPokedexTask.delegate = this;
        getPokedexTask.execute();

        return view;
    }

    @OnItemClick(R.id.listPokemons)
    public void onPokemonClicked(int position){
        Intent i = new Intent(getActivity(),PokemonDetailActivity.class);
        Pokemon selectedPokemon = pokemonList.get(position);
        i.putExtra(KEY_POKEMON, selectedPokemon);
        startActivity(i);
    }

    // récupération de la liste de pokémons à la fin de la tâche asynchrone
    public void processFinish(List<Pokemon> output) {
        pokemonList = output;
    }

    public void processFinish(Pokemon output) {
        // nothing
    }

}
