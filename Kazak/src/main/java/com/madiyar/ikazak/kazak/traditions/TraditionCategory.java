package com.madiyar.ikazak.kazak.traditions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.madiyar.ikazak.kazak.R;
import com.madiyar.ikazak.kazak.database.DataBaseSource;
import com.madiyar.ikazak.kazak.database.DatabaseHelper;
import com.madiyar.ikazak.kazak.database.MakalCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhenis Madiyar on 4/5/2015.
 */
public class TraditionCategory extends Activity {

    ListView listView;
    CategoryTraditionAdapter adapter;
    ArrayList<TraditionList> arrayList;
    private String[] allColumns_tradition = {DatabaseHelper.COLUMN_ID_TRADITION_CAT,
            DatabaseHelper.COLUMN_TAG_TRADITION_CAT, DatabaseHelper.COLUMN_TRADITION_CAT_CONTENT};
    DataBaseSource dataSource;
    List<MakalCategory> listOfCat;
    List<MakalCategory> listOfCat_next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tradition_main);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setTitle(R.string.national_traditions);

        listOfCat = new ArrayList<MakalCategory>();
        listOfCat_next = new ArrayList<MakalCategory>();
        dataSource = new DataBaseSource(getApplicationContext());
        dataSource.open();
        listOfCat = dataSource.getAllCategory(DatabaseHelper.TABLE_TRADITION_CAT, allColumns_tradition);
        dataSource.close();
        listOfCat_next.addAll(listOfCat);

        listView = (ListView) findViewById(R.id.listView2);
        adapter = new CategoryTraditionAdapter(this, listOfCat_next);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(getApplicationContext(), Traditions.class);
                intent.putExtra("tag", listOfCat_next.get(position).getTag());
                intent.putExtra("content", listOfCat_next.get(position).getContent());
                startActivity(intent);
            }
        });
    }

    public class CategoryTraditionAdapter extends BaseAdapter {
        List<MakalCategory> arrayList;
        Activity activity;
        public CategoryTraditionAdapter(Activity activity, List<MakalCategory> arrayList) {
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
                view = View.inflate(activity, R.layout.row_tradition,null);
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return (true);
        }

        return (super.onOptionsItemSelected(item));
    }
}
