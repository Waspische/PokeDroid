package com.example.wasp.pokedex.ui.pokemons;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.wasp.pokedex.R;
import com.example.wasp.pokedex.dao.PersistancePokemonsDataSource;
import com.example.wasp.pokedex.model.PersistancePokemon;
import com.example.wasp.pokedex.provider.PokeServiceImpl;
import com.example.wasp.pokedex.provider.model.Pokemon;
import com.example.wasp.pokedex.provider.PokeService;
import com.example.wasp.pokedex.provider.RestClient;

import java.sql.SQLException;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Wasp on 23/04/2015.
 */
public class PokemonsFragment extends Fragment {

    private PersistancePokemonsDataSource dataSource;

    private RestClient restClient = new RestClient();

    @InjectView(R.id.listPokemons)
    ListView pokemons;

    @InjectView(R.id.add_button)
    Button add;

    @InjectView(R.id.text)
    TextView text;

    public PokemonsFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.pokemons_fragment, container, false);
        ButterKnife.inject(this, view);

        dataSource = new PersistancePokemonsDataSource(view.getContext());
        try {

            ArrayAdapter<PersistancePokemon> adapter = new ArrayAdapter<PersistancePokemon>(view.getContext(),
                    android.R.layout.simple_list_item_1);
            pokemons.setAdapter(adapter);

            dataSource.open();

            dataSource.deleteAllPokemons();

            PokeService pokeService = restClient.getPokeService();

            // get all (151) pokemons from api
//        for (int i = 0; i < 152; i++) {
            for (int i = 1; i < 10; i++) {

                pokeService.getPokemon(i, new Callback<Pokemon>() {
                    @Override
                    public void success(Pokemon pokemon, Response response) {
                        // success!
                        Log.i("App", pokemon.getName());
                        Log.i("App", pokemon.getResource_uri());
                        // you get the point...

                        ArrayAdapter<PersistancePokemon> adapter = (ArrayAdapter<PersistancePokemon>) pokemons.getAdapter();
                        adapter.add(dataSource.createPokemon(pokemon.getName()));
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

            List<PersistancePokemon> values = dataSource.getAllPokemons();

            // reset pokemon list
    //        for (PersistancePokemon value : values) {
    //            dataSource.deletePokemon(value.getId());
    //        }


        } catch (SQLException e) {
            e.printStackTrace();
        }


//        text.setText(pokemon.getName());


        return view;
    }

    @OnClick(R.id.add_button)
    public void add(){
        PersistancePokemon pokemon = null;
        ArrayAdapter<PersistancePokemon> adapter = (ArrayAdapter<PersistancePokemon>) pokemons.getAdapter();
        pokemon = dataSource.createPokemon("Pikachu");
        adapter.add(pokemon);
    }
}
