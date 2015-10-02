package com.madiyar.ikazak.kazak.makal;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.madiyar.ikazak.kazak.R;
import com.madiyar.ikazak.kazak.database.MakalCategory;

import java.util.List;

/**
 * Created by madiyar on 11/16/14.
 */
public class AdapterMakalCategory extends BaseAdapter{
    List<MakalCategory> categoryList;
    Activity context;
    public AdapterMakalCategory(Activity context, List<MakalCategory> data) {
        this.context = context;
        this.categoryList = data;
    }

    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return categoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder = null;
        if (view == null){
            view = View.inflate(context,
                    R.layout.row_item_makal_category, null);
            holder = new ViewHolder();
            holder.text = (TextView) view.findViewById(R.id.textViewMakalCategory);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        holder.text.setText(categoryList.get(position).getContent());
        return view;
    }
    private static class ViewHolder {
        TextView text;
    }
}
