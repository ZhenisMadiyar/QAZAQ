package com.madiyar.ikazak.kazak.makal;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.madiyar.ikazak.kazak.R;

import java.util.ArrayList;

/**
 * Created by madiyar on 11/18/14.
 */
public class AdapterTypfaceList extends BaseAdapter {
    ArrayList<Typeface> typefaces;
    ArrayList<String> nameFonts;
    Activity activity;

    public AdapterTypfaceList(Activity activity, ArrayList<Typeface> typfaceList, ArrayList<String> nameFonts) {
        this.activity = activity;
        this.typefaces = typfaceList;
        this.nameFonts = nameFonts;
    }

    @Override
    public int getCount() {
        return typefaces.size();
    }

    @Override
    public Object getItem(int position) {
        return typefaces.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder = null;
        if (view == null){
            view = View.inflate(activity,
                    R.layout.row_item_fonts, null);
            holder = new ViewHolder();

            holder.text = (TextView) view.findViewById(R.id.textView);
            view.setTag(holder);
        }
        else{
            holder = (ViewHolder) view.getTag();
        }
        holder.text.setText(nameFonts.get(position));
        holder.text.setTypeface(typefaces.get(position));
        return view;
    }
    private static class ViewHolder {
        TextView text;
    }
}
