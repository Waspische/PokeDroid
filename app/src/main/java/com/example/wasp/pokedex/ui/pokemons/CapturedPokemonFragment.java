package com.example.wasp.pokedex.ui.pokemons;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.wasp.pokedex.R;
import com.example.wasp.pokedex.dao.PersistancePokemonsDataSource;
import com.example.wasp.pokedex.model.PersistancePokemon;
import com.example.wasp.pokedex.provider.model.Pokemon;
import com.example.wasp.pokedex.util.PersistancePokemonIdComparator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class CapturedPokemonFragment extends Fragment {

    private final String KEY_PERSISTANCE_POKEMON = "persistancePokemon";

    /**
     * UI Components
     */
    @InjectView(R.id.listCapturedPokemons)
    ListView capturedPokemons;

    /**
     * UI related attributes
     */
    private ArrayAdapter<PersistancePokemon> adapter;

    /**
     * Attributes
     */

    private List<PersistancePokemon> capturedPokemonsList = new ArrayList<>();

    // liaison à la base de données
    private PersistancePokemonsDataSource dataSource;

    public CapturedPokemonFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_captured_pokemon, container, false);
        ButterKnife.inject(this,view);

        // change le titre
        getActivity().setTitle(R.string.my_pokemons);

        // changer l'adapter si on veut mettre plus que le nom
        adapter = new ArrayAdapter<>(view.getContext(),
                android.R.layout.simple_list_item_1);

        capturedPokemons.setAdapter(adapter);

        dataSource = new PersistancePokemonsDataSource(view.getContext());
//        try {
//
//            dataSource.open();
//
////            dataSource.deleteAllPokemons();
//
//            capturedPokemonsList = dataSource.getAllPokemons();
//
//            Collections.sort(capturedPokemonsList, new PersistancePokemonIdComparator());
//
//            adapter.addAll(capturedPokemonsList);
//
//            // reset pokemon list
//    //        for (PersistancePokemon value : values) {
//    //            dataSource.deletePokemon(value.getId());
//    //        }
//            dataSource.close();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        return view;
    }

    @Override
     public void onStart()
    {  // After a pause OR at startup
        super.onResume();
        adapter.clear();
        try {

            dataSource.open();

//            dataSource.deleteAllPokemons();

            capturedPokemonsList = dataSource.getAllPokemons();

            Collections.sort(capturedPokemonsList, new PersistancePokemonIdComparator());

            adapter.addAll(capturedPokemonsList);

            // reset pokemon list
            //        for (PersistancePokemon value : values) {
            //            dataSource.deletePokemon(value.getId());
            //        }
            dataSource.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @OnItemClick(R.id.listCapturedPokemons)
    public void onPokemonClicked(int position){
        Intent i = new Intent(getActivity(),PokemonDetailActivity.class);
        PersistancePokemon selectedPokemon = capturedPokemonsList.get(position);
        i.putExtra(KEY_PERSISTANCE_POKEMON, selectedPokemon);
        startActivity(i);
    }

}
