package com.example.wasp.pokedex.ui.pokemons;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.wasp.pokedex.R;
import com.example.wasp.pokedex.provider.model.Move;
import com.example.wasp.pokedex.provider.model.Pokemon;
import com.example.wasp.pokedex.util.MoveComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 */
public class PokemonMovesFragment extends Fragment {

    private final String KEY_POKEMON = "pokemon";

    /**
     * UI components
     */
    @InjectView(R.id.pokeMovesList)
    ListView moves;

    /**
     * Attributes
     */
    private Pokemon pokemon;
    public PokemonMovesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pokemon_moves, container, false);
        ButterKnife.inject(this,view);

        Bundle bundle = getArguments();

        Pokemon pokemon = (Pokemon) bundle.getSerializable(KEY_POKEMON);

        ArrayAdapter<Move> adapter = new ArrayAdapter<>(view.getContext(),
                android.R.layout.simple_list_item_1);

        moves.setAdapter(adapter);
        adapter.addAll(getMovesByLevel(pokemon.getMoves()));

        return view;
    }

    private List<Move> getMovesByLevel(List<Move> moves){
        List<Move> result = new ArrayList<>();

        for (Move move : moves) {
            if(move.getLevel() != null){
                result.add(move);
            }
        }

        Collections.sort(result, new MoveComparator());

        return result;
    }


}
