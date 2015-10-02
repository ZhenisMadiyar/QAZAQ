package com.madiyar.ikazak.kazak.writers;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.madiyar.ikazak.kazak.R;
import com.madiyar.ikazak.kazak.database.Makal;

import java.util.List;

/**
 * Created by madiyar on 11/25/14.
 */
public class AdapterLyricList extends BaseAdapter {
    Activity activity;
    List<Makal> list;
    public AdapterLyricList(Activity activity, List<Makal> listOfmakal_next) {
        this.activity = activity;
        this.list = listOfmakal_next;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
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
                    R.layout.row_item_makal_category, null);
            holder = new ViewHolder();
            holder.text = (TextView) view.findViewById(R.id.textViewMakalCategory);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        holder.text.setText(list.get(position).getContent());
        return view;
    }
    private static class ViewHolder {
        TextView text;
    }
}
