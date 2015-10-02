package com.madiyar.ikazak.kazak.ertegiler;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.madiyar.ikazak.kazak.R;

/**
 * Created by madiyar on 12/4/14.
 */
public class AdapterErtegiList extends BaseAdapter {
    Activity activity;
    BookItem[] arrayList;
    public AdapterErtegiList(Activity activity, BookItem[] ertegiler) {
        this.activity = activity;
        arrayList = ertegiler;
    }

    @Override
    public int getCount() {
        return arrayList.length;
    }

    @Override
    public Object getItem(int position) {
        return arrayList[position];
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
                    R.layout.row_ertegiler_list, null);
            holder = new ViewHolder();
            holder.text = (TextView) view.findViewById(R.id.textViewErtegiName);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        holder.text.setText(arrayList[position].getTitle());
        return view;
    }
    private static class ViewHolder {
        TextView text;
//        ImageView imageView;
    }
}
