package com.example.wasp.pokedex.ui.pokemons;

import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wasp.pokedex.R;
import com.example.wasp.pokedex.provider.RestClient;
import com.example.wasp.pokedex.provider.Service.PokeService;
import com.example.wasp.pokedex.provider.model.Pokemon;
import com.example.wasp.pokedex.provider.model.Sprite;
import com.example.wasp.pokedex.provider.task.AsyncResponse;
import com.example.wasp.pokedex.provider.task.GetPokemonTask;
import com.example.wasp.pokedex.ui.view.SectionsPagerAdapter;
import com.example.wasp.pokedex.ui.view.SlidingTabLayout;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PokemonDetailActivity extends ActionBarActivity implements AsyncResponse {

    private final String KEY_POKEMON = "pokemon";

    private RestClient restClient = new RestClient();

    /**
     * UI components
     */
    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.pokeName)
    TextView pokeName;

    @InjectView(R.id.pokePicture)
    ImageView pokePicture;

    @InjectView(R.id.sliding_tabs)
    SlidingTabLayout slidingTabLayout;
    @InjectView(R.id.viewPager)
    ViewPager viewPager;

    /**
     * AsyncTask
     */
    public GetPokemonTask getPokemonTask;

    /**
     * Attributes
     */
    private Pokemon pokemon;

    private FragmentPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_detail);
        ButterKnife.inject(this);

        // récupération du pokémon
        Pokemon selectedPokemon = (Pokemon) getIntent().getSerializableExtra(KEY_POKEMON);

        // récupération de toutes les infos liées à un pokémon
        getPokemonTask = new GetPokemonTask(this);
        getPokemonTask.delegate = this;
        getPokemonTask.execute(selectedPokemon.getResource_uri());

        if (toolbar != null) {
            this.setSupportActionBar(toolbar);
            this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void processFinish(List<Pokemon> output) {
        // nothing
    }

    // fonction appelée à la fin de la tâche asynchrone
    public void processFinish(Pokemon output) {
        this.pokemon = output;
        toolbar.setTitle(this.getString(R.string.title_activity_pokemon_detail, pokemon.getName()));

        adapter = new SectionsPagerAdapter(getSupportFragmentManager(), this, pokemon);
        viewPager.setAdapter(adapter);
        slidingTabLayout.setViewPager(viewPager);

        updateDetail(pokemon);
    }

    // mise à jour des informations d'un pokémon
    private void updateDetail(Pokemon pokemon){
        pokeName.setText(pokemon.getName());

        // récupération de l'image
        // TODO mettre une image vide avant
        PokeService pokeService = restClient.getPokeService();
        List<Sprite> sprites = pokemon.getSprites();
        if(sprites != null && !sprites.isEmpty()){
            pokeService.getSprite(sprites.get(0).getResource_uri(), new Callback<Sprite>() {
                @Override
                public void success(Sprite sprite, Response response) {
                    // success!

                    String pokeUri = sprite.getImage();
                    Picasso.with(PokemonDetailActivity.this).load(RestClient.BASE_URL + pokeUri).into(pokePicture);
                }

                @Override
                public void failure(RetrofitError error) {
                    // something went wrong
                }
            });
        } else {
            // mettre une image par défaut
        }
    }

    public Pokemon getSelectedPokemon(){
        return this.pokemon;
    }

}
