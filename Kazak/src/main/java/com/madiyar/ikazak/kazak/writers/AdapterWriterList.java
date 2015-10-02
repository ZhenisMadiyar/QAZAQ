package com.madiyar.ikazak.kazak.writers;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.madiyar.ikazak.kazak.R;
import com.madiyar.ikazak.kazak.database.MakalCategory;

import java.util.List;

/**
 * Created by madiyar on 11/24/14.
 */
public class AdapterWriterList extends BaseAdapter {
    Activity activity;
    List<MakalCategory> category;
    public AdapterWriterList(Activity activity, List<MakalCategory> listOfCat_next) {
        this.activity = activity;
        this.category = listOfCat_next;
    }

    @Override
    public int getCount() {
        return category.size();
    }

    @Override
    public Object getItem(int position) {
        return category.get(position);
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
                    R.layout.row_item_writers_list, null);
            holder = new ViewHolder();
            holder.text = (TextView) view.findViewById(R.id.textViewMakalCategory);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        holder.text.setText(category.get(position).getContent());
        return view;
    }
    private static class ViewHolder {
        TextView text;
    }
}
