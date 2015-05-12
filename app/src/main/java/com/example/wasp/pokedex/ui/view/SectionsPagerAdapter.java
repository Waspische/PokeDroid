package com.example.wasp.pokedex.ui.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.wasp.pokedex.R;
import com.example.wasp.pokedex.provider.model.Pokemon;
import com.example.wasp.pokedex.ui.pokemons.PokemonMovesFragment;
import com.example.wasp.pokedex.ui.pokemons.PokemonDescriptionFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Wasp on 12/05/2015.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private final String KEY_POKEMON = "pokemon";

    private List<Fragment> dataSource;
    private Context context;
    private Pokemon pokemon;

    public SectionsPagerAdapter(FragmentManager fm, Context context, Pokemon pokemon) {
        super(fm);
        initFragment(pokemon);
        this.context = context;
        this.pokemon = pokemon;
    }

    private void initFragment(Pokemon pokemon){
        dataSource = new ArrayList<Fragment>();

        // passage du pokémon aux fragments
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_POKEMON, pokemon);
        PokemonDescriptionFragment pdf = new PokemonDescriptionFragment();
        pdf.setArguments(bundle);
        PokemonMovesFragment paf = new PokemonMovesFragment();
        paf.setArguments(bundle);

        dataSource.add(pdf);
        dataSource.add(paf);
    }

    @Override
    public Fragment getItem(int i) {
        return dataSource.get(i);
    }

    @Override
    public int getCount() {
        return dataSource.size();
    }

    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
                return context.getString(R.string.title_section1).toUpperCase(l);
            case 1:
                return context.getString(R.string.title_section2).toUpperCase(l);
        }
        return null;
    }
}

