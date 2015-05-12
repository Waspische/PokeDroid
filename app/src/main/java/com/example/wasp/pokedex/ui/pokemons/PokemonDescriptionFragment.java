package com.example.wasp.pokedex.ui.pokemons;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wasp.pokedex.R;
import com.example.wasp.pokedex.provider.model.Pokemon;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 */
public class PokemonDescriptionFragment extends Fragment {

    private final String KEY_POKEMON = "pokemon";

    /**
     * UI components
     */
    @InjectView(R.id.pokeWeight)
    TextView pokeWeight;
    @InjectView(R.id.pokeHeight)
    TextView pokeHeight;

    /**
     * Attributes
     */
    private Pokemon pokemon;

    public PokemonDescriptionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pokemon_description, container, false);
        ButterKnife.inject(this,view);

        Bundle bundle = getArguments();

        Pokemon pokemon = (Pokemon) bundle.getSerializable(KEY_POKEMON);

        pokeHeight.setText(getActivity().getString(R.string.height, pokemon.getHeight().toString()));
        pokeWeight.setText(getActivity().getString(R.string.weight, pokemon.getWeight().toString()));

        return view;
    }


}
