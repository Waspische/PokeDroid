package com.example.wasp.pokedex.ui.pokemons;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wasp.pokedex.R;
import com.example.wasp.pokedex.provider.RestClient;
import com.example.wasp.pokedex.provider.Service.PokeService;
import com.example.wasp.pokedex.provider.model.Pokemon;
import com.example.wasp.pokedex.provider.model.Sprite;
import com.example.wasp.pokedex.provider.task.AsyncResponse;
import com.example.wasp.pokedex.provider.task.GetPokedexTask;
import com.example.wasp.pokedex.provider.task.GetPokemonTask;
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
    @InjectView(R.id.pokeName)
    TextView pokeName;
    @InjectView(R.id.pokeWeight)
    TextView pokeWeight;
    @InjectView(R.id.pokeHeight)
    TextView pokeHeight;

    @InjectView(R.id.pokePicture)
    ImageView pokePicture;

    /**
     * AsyncTask
     */
    public GetPokemonTask getPokemonTask;

    /**
     * Attributes
     */
    private Pokemon pokemon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_detail);
        ButterKnife.inject(this);

        // récupération du pokémon
        Pokemon selectedPokemon = (Pokemon) getIntent().getSerializableExtra(KEY_POKEMON);

        // récupération de toutes les infos liés à un pokémon
        getPokemonTask = new GetPokemonTask(this);
        getPokemonTask.delegate = this;
        getPokemonTask.execute(selectedPokemon.getResource_uri());

    }

    public void processFinish(List<Pokemon> output) {
        // nothing
    }

    // fonction appelée à la fin de la tâche asynchrone
    public void processFinish(Pokemon output) {
        this.pokemon = output;
        updateDetail(pokemon);
    }

    // mise à jour des informations d'un pokémon
    private void updateDetail(Pokemon pokemon){
        pokeName.setText(pokemon.getName());
        pokeHeight.setText(pokemon.getHeight().toString());
        pokeWeight.setText(pokemon.getWeight().toString());

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

}
