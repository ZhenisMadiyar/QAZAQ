package com.madiyar.ikazak.kazak.traditions;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.madiyar.ikazak.kazak.R;
import com.madiyar.ikazak.kazak.database.Makal;

import java.util.List;

/**
 * Created by Madiyar on 02.02.2015.
 */
public class TraditionAdapter extends BaseAdapter{
    List<Makal> arrayList;
    Activity activity;
    public TraditionAdapter(Activity activity, List<Makal> arrayList) {
        this.arrayList = arrayList;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1 = view;
        ViewHolder viewHolder = null;
        if (view == null) {
            view = View.inflate(activity, R.layout.row_tradition_list,null);
            viewHolder = new ViewHolder();
            viewHolder.traditionName = (TextView) view.findViewById(R.id.textViewTradition);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.traditionName.setText(arrayList.get(i).getContent());
        return view;
    }

    private class ViewHolder {
        TextView traditionName;
    }
}
