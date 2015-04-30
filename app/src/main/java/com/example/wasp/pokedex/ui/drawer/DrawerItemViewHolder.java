package com.example.wasp.pokedex.ui.drawer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wasp.pokedex.R;
import com.example.wasp.pokedex.model.DrawerItem;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Wasp on 23/04/2015.
 */
public class DrawerItemViewHolder {

    private Context context;
    private View view;
    @InjectView(R.id.drawer_image)
    ImageView imageView;
    @InjectView(R.id.drawer_text)
    TextView textView;

    public DrawerItemViewHolder(Context context, View view) {
        this.context = context;
        createView();
    }

    private void createView() {
        view = LayoutInflater.from(context).inflate(R.layout.drawer_item, null);
        ButterKnife.inject(this, view);
    }

    public void updateView(DrawerItem item) {
        imageView.setImageResource(item.getImage());
        textView.setText(item.getLabel());
    }

    public View getView() {
        return view;
    }
}
