package com.example.wasp.pokedex;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.wasp.pokedex.ui.drawer.DrawerItem;
import com.example.wasp.pokedex.ui.drawer.DrawerItemAdapter;
import com.example.wasp.pokedex.ui.pokemons.CapturedPokemonFragment;
import com.example.wasp.pokedex.ui.pokemons.PokemonsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;


public class PokeActivity extends ActionBarActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    private ActionBarDrawerToggle drawerToggle;


    @InjectView(R.id.drawer_list)
    ListView drawerList;

    private List<DrawerItem> drawerItemList = new ArrayList<>();
    private DrawerItemAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poke);
        ButterKnife.inject(this);

        setSupportActionBar(toolbar);
        drawerToggle= new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name,  R.string.app_name);
        drawerLayout.setDrawerListener(drawerToggle);

        buildDrawerList();

        adapter = new DrawerItemAdapter(this, drawerItemList);
        drawerList.setAdapter(adapter);

        if (savedInstanceState == null) {
            selectItem(0);
        }
        // TODO ajouter un contrôle pour la présence d'internet

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_poke, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(Gravity.START|Gravity.LEFT)){
            drawerLayout.closeDrawers();
            return;
        }
        super.onBackPressed();
    }


    @OnItemClick(R.id.drawer_list)
    public void selectItem(int position) {
        // Handle Navigation Options
        // update the main content by replacing fragments
        Fragment fragment = new PokemonsFragment();;

        Intent intent;
        switch (position) {
            case 0:
                fragment = new PokemonsFragment();
                break;
            case 1:
                fragment = new CapturedPokemonFragment();
                break;

        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.commit();

        drawerLayout.closeDrawer(drawerList);

    }

    private void buildDrawerList() {
        drawerItemList.add(new DrawerItem("Pokedex", R.mipmap.ic_pokemons));
        drawerItemList.add(new DrawerItem("My pokemons", R.mipmap.ic_emptyball) );
    }
}
