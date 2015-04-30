package com.example.wasp.pokedex.ui.drawer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.wasp.pokedex.R;
import com.example.wasp.pokedex.model.DrawerItem;
import com.example.wasp.pokedex.ui.drawer.DrawerItemAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;

/**
 * Created by Wasp on 23/04/2015.
 */

public class DrawerFragment extends Fragment {

    @InjectView(R.id.drawer_list)
    ListView listView;
    private List<DrawerItem> drawerItemList = new ArrayList<>();
    private DrawerItemAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drawer, container, false);
        ButterKnife.inject(this, view);

        buildDrawerList();

        adapter = new DrawerItemAdapter(getActivity(), drawerItemList);
        listView.setAdapter(adapter);

//        View header = inflater.inflate(R.layout.headerview    , null, false);
//        listView.addHeaderView(header, null, false);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        return view;
    }

    @OnItemClick(R.id.drawer_list)
    public void submit(View view, int position) {
//        Intent i = new Intent(getActivity(),PersonDetailActivity.class);
//        Bundle bundle = new Bundle();
//        i.putExtra("person",drawerItemList.get(position));
//        startActivity(i);
    }

    private void buildDrawerList() {
        drawerItemList.add(new DrawerItem("Pokémon", R.mipmap.tata));
        drawerItemList.add(new DrawerItem("PokéItem", R.mipmap.tata) );
        drawerItemList.add(new DrawerItem("CS & CT", R.mipmap.tata) );
    }

}
