package com.madiyar.ikazak.kazak.nationalGames;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.madiyar.ikazak.kazak.R;
import com.madiyar.ikazak.kazak.database.MakalCategory;

import java.util.List;

/**
 * Created by Madiyar on 31.01.2015.
 */
public class NatGameAdapter extends BaseAdapter {
    Activity activity;
    List<MakalCategory> game;

    public NatGameAdapter(Activity activity, List<MakalCategory> name_n_g) {
        this.activity = activity;
        this.game = name_n_g;
    }

    @Override
    public int getCount() {
        return game.size();
    }

    @Override
    public Object getItem(int i) {
        return game.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder = null;
        if (view == null) {
            view = View.inflate(activity,
                    R.layout.row_national_game, null);
            holder = new ViewHolder();
            holder.text = (TextView) view.findViewById(R.id.textViewNG);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.text.setText(game.get(position).getContent());
        return view;
    }

    private static class ViewHolder {
        TextView text;
    }
}