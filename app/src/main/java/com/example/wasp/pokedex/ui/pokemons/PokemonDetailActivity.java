package com.example.wasp.pokedex.ui.pokemons;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wasp.pokedex.R;
import com.example.wasp.pokedex.dao.PersistancePokemonsDataSource;
import com.example.wasp.pokedex.model.PersistancePokemon;
import com.example.wasp.pokedex.provider.RestClient;
import com.example.wasp.pokedex.provider.Service.PokeService;
import com.example.wasp.pokedex.provider.model.Pokemon;
import com.example.wasp.pokedex.provider.model.Sprite;
import com.example.wasp.pokedex.provider.model.Type;
import com.example.wasp.pokedex.provider.task.AsyncResponse;
import com.example.wasp.pokedex.provider.task.GetPokemonTask;
import com.example.wasp.pokedex.ui.view.SectionsPagerAdapter;
import com.example.wasp.pokedex.ui.view.SlidingTabLayout;
import com.squareup.picasso.Picasso;

import java.sql.SQLException;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PokemonDetailActivity extends ActionBarActivity implements AsyncResponse {

    private final String KEY_POKEMON = "pokemon";
    private final String KEY_PERSISTANCE_POKEMON = "persistancePokemon";

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

    @InjectView(R.id.pokeball)
    ImageView pokeball;

    @InjectView(R.id.sliding_tabs)
    SlidingTabLayout slidingTabLayout;
    @InjectView(R.id.viewPager)
    ViewPager viewPager;

    @InjectView(R.id.types)
    LinearLayout typesLayout;

    @InjectView(R.id.capture_pokemon_button)
    Button captureButton;

    /**
     * AsyncTask
     */
    public GetPokemonTask getPokemonTask;

    /**
     * Attributes
     */
    private Pokemon pokemon;

    private FragmentPagerAdapter adapter;

    // liaison à la base de données
    private PersistancePokemonsDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_detail);
        ButterKnife.inject(this);

        // récupération du pokémon
        Pokemon selectedPokemon = (Pokemon) getIntent().getSerializableExtra(KEY_POKEMON);
        if (selectedPokemon != null) {
            // on vient de la page principale
        } else {
            // on vient dxe la page des pokémons capturés
            PersistancePokemon pp = (PersistancePokemon) getIntent().getSerializableExtra(KEY_PERSISTANCE_POKEMON);
            selectedPokemon = new Pokemon();
            selectedPokemon.setName(pp.getName());
            selectedPokemon.setNational_id(pp.getNational_id());
            selectedPokemon.setResource_uri("/api/v1/pokemon/"+pp.getNational_id()+"/");
        }

        // récupération de toutes les infos liées à un pokémon
        getPokemonTask = new GetPokemonTask(this);
        getPokemonTask.delegate = this;
        getPokemonTask.execute(selectedPokemon.getResource_uri());

        if (toolbar != null) {
            toolbar.setTitle(R.string.loading);
            this.setSupportActionBar(toolbar);
            this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // initialise le lien avec la base de données
        dataSource = new PersistancePokemonsDataSource(this);
    }

    @OnClick(R.id.capture_pokemon_button)
    public void onCaptureClick(){
        try {
            dataSource.open();
            PersistancePokemon result = dataSource.getPokemonByNationalId(pokemon.getNational_id());
            // le pokemon n'existe pas dans la base
            if(result == null){
                dataSource.createPokemon(pokemon.getName(), pokemon.getNational_id());

                Toast.makeText(PokemonDetailActivity.this,
                        "Well done ! Pokemon captured !", Toast.LENGTH_LONG).show();
            } else {
                dataSource.deletePokemonByNayionalId(pokemon.getNational_id());
                Toast.makeText(PokemonDetailActivity.this,
                        "He will live peacefully without you ...", Toast.LENGTH_LONG).show();
            }

            dataSource.close();
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(PokemonDetailActivity.this,
                    "An error occured", Toast.LENGTH_SHORT).show();
        }
        updateCapturePokemon(pokemon);
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

        // création des types
        List<Type> types = pokemon.getTypes();
        for (Type type : types) {
            Button typeButton = new Button(this);
            String typeName = type.getName();
            typeButton.setText(typeName.substring(0,1).toUpperCase() + typeName.substring(1));
            typeButton.setTextColor(Color.parseColor("#FFFFFF"));
            typeButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 65, getResources().getDisplayMetrics());
            int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
            int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 1, getResources().getDisplayMetrics());
            layoutParams.setMargins(margin,margin,margin,margin);
            typeButton.setLayoutParams(layoutParams);
            // set style
            typeButton.setBackgroundResource(R.drawable.typed_button);
            GradientDrawable shapeDrawable = (GradientDrawable)typeButton.getBackground();
            switch (typeName){
                case "fire":
                    shapeDrawable.setColor(getResources().getColor(R.color.fire));
                    break;
                case "rock":
                    shapeDrawable.setColor(getResources().getColor(R.color.rock));
                    break;
                case "steel":
                    shapeDrawable.setColor(getResources().getColor(R.color.steel));
                    break;
                case "ghost":
                    shapeDrawable.setColor(getResources().getColor(R.color.ghost));
                    break;
                case "fighting":
                    shapeDrawable.setColor(getResources().getColor(R.color.fighting));
                    break;
                case "normal":
                    shapeDrawable.setColor(getResources().getColor(R.color.normal));
                    break;
                case "poison":
                    shapeDrawable.setColor(getResources().getColor(R.color.poison));
                    break;
                case "flying":
                    shapeDrawable.setColor(getResources().getColor(R.color.flying));
                    break;
                case "psychic":
                    shapeDrawable.setColor(getResources().getColor(R.color.psychic));
                    break;
                case "bug":
                    shapeDrawable.setColor(getResources().getColor(R.color.bug));
                    break;
                case "fairy":
                    shapeDrawable.setColor(getResources().getColor(R.color.fairy));
                    break;
                case "ice":
                    shapeDrawable.setColor(getResources().getColor(R.color.ice));
                    break;
                case "dark":
                    shapeDrawable.setColor(getResources().getColor(R.color.dark));
                    break;
                case "grass":
                    shapeDrawable.setColor(getResources().getColor(R.color.grass));
                    break;
                case "electric":
                    shapeDrawable.setColor(getResources().getColor(R.color.electric));
                    break;
                case "ground":
                    shapeDrawable.setColor(getResources().getColor(R.color.ground));
                    break;
                case "water":
                    shapeDrawable.setColor(getResources().getColor(R.color.water));
                    break;
                case "dragon":
                    shapeDrawable.setColor(getResources().getColor(R.color.dragon));
                    break;
            }
            typesLayout.addView(typeButton);
        }

        // création de la capture
        updateCapturePokemon(pokemon);
    }

    public void updateCapturePokemon(Pokemon pokemon){
        try {
            dataSource.open();
            PersistancePokemon result = dataSource.getPokemonByNationalId(pokemon.getNational_id());
            // le pokemon n'existe pas dans la base
            if(result == null){
                pokeball.setVisibility(View.GONE);
                captureButton.setText("Throw a pokeball !");
            } else {
                pokeball.setVisibility(View.VISIBLE);
                Picasso.with(PokemonDetailActivity.this).load(R.mipmap.ic_emptyball).into(pokeball);
                captureButton.setText("Set him free !");
            }
            captureButton.setVisibility(View.VISIBLE);

            dataSource.close();
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(PokemonDetailActivity.this,
                    "An error occured", Toast.LENGTH_SHORT).show();
        }
    }

    public Pokemon getSelectedPokemon(){
        return this.pokemon;
    }

}
