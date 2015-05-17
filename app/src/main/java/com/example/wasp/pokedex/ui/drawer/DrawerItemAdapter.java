package com.example.wasp.pokedex.ui.drawer;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.wasp.pokedex.ui.drawer.DrawerItem;

import java.util.List;

/**
 * Created by Wasp on 23/04/2015.
 */
public class DrawerItemAdapter extends BaseAdapter {

    private Context context;
    private List<DrawerItem> dataSource;
    private DrawerItemViewHolder viewHolder;
    private View cellView;
    private DrawerItem item;

    public DrawerItemAdapter(Context context, List<DrawerItem> dataSource) {
        this.context = context;
        this.dataSource = dataSource;
    }

    @Override
    public int getCount() {
        return dataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return dataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        cellView = convertView;
        item = dataSource.get(position);
        if (cellView == null) {
            viewHolder = new DrawerItemViewHolder(context, cellView);
            cellView = viewHolder.getView();
            cellView.setTag(viewHolder);
        }
        viewHolder = (DrawerItemViewHolder) cellView.getTag();
        viewHolder.updateView(item);
        return cellView;
    }
}
