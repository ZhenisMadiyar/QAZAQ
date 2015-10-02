package com.madiyar.ikazak.kazak.ertegiler;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.madiyar.ikazak.kazak.R;

import java.util.ArrayList;

/**
 * Created by madiyar on 11/20/14.
 */
public class Ertegi extends ListActivity {

    private VerticalAdapter verListAdapter;

    //    DataBaseSource dataSource;
//    List<MakalCategory> listOfCat;
//    List<MakalCategory> listOfCat_next;
//
//    GridView gridView;
//    AdapterErtegiList adapter;
//    private String[] allColumns_ertegi = {DatabaseHelper.COLUMN_TAG_ERTEGI,DatabaseHelper.COLUMN_CONTENT_ERTEGI,DatabaseHelper.COLUMN_POINT_ERTEGI};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ertegi);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setTitle(R.string.ertegiler);
        String locale =  getResources().getConfiguration().locale.toString();
        Library lb = new Library();
        if (locale.equals("ru_ru")) {
            for (BookItem item : BookItem.ALL_BOOKS_RU) {
                lb.addBookItem(item);
            }
        }else if (locale.equals("en_GB")) {
            for (BookItem item : BookItem.ALL_BOOKS_ENG) {
                lb.addBookItem(item);
            }
        } else {
            for (BookItem item : BookItem.ALL_BOOKS) {
                lb.addBookItem(item);
            }
        }
        ArrayList<ArrayList<BookItem>> groupList = new ArrayList<ArrayList<BookItem>>();
        groupList = lb.groupbyArrayBookItem(Library.AUTHOR);

        verListAdapter = new VerticalAdapter(this, R.layout.ertegi_row, groupList);
        setListAdapter(verListAdapter);

        verListAdapter.notifyDataSetChanged();

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(Ertegi.this, "CLICKED" + i, Toast.LENGTH_SHORT).show();
            }
        });
//        listOfCat = new ArrayList<MakalCategory>();
//        listOfCat_next = new ArrayList<MakalCategory>();
//        dataSource = new DataBaseSource(getApplicationContext());
//        dataSource.open();
//        listOfCat = dataSource.getAllCategory(DatabaseHelper.TABLE_ERTEGI, allColumns_ertegi);
//        dataSource.close();
//        listOfCat_next.addAll(listOfCat);
//
//        gridView = (GridView) findViewById(R.id.gridView);
//        adapter = new AdapterErtegiList(this, listOfCat_next);
//        gridView.setAdapter(adapter);
//
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(getApplicationContext(), ErtegiView.class);
//                intent.putExtra("tag", listOfCat_next.get(position).getTag());
//                intent.putExtra("pos", position);
//                startActivity(intent);
//            }
//        });
    }

    private class VerticalAdapter extends ArrayAdapter<ArrayList<BookItem>> {

        private int resource;

        public VerticalAdapter(Context _context, int _ResourceId,
                               ArrayList<ArrayList<BookItem>> _items) {
            super(_context, _ResourceId, _items);
            this.resource = _ResourceId;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View rowView;

            if (convertView == null) {
                rowView = LayoutInflater.from(getContext()).inflate(resource,
                        null);
            } else {
                rowView = convertView;
            }

            GridView gridView = (GridView) rowView.findViewById(R.id.subListview);
            GridAdapter horListAdapter = new GridAdapter(getContext(), R.layout.erteg_item, getItem(position));
            gridView.setAdapter(horListAdapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getApplicationContext(), ErtegiView.class);
                    intent.putExtra("content", getItem(position).get(i).getContent());
                    startActivity(intent);
//                    Toast.makeText(getApplicationContext(), getItem(position).get(i).getTitle(), Toast.LENGTH_SHORT).show();
                }
            });
            return rowView;
        }
    }

    /*
     * This class add some items to Horizontal ListView this ListView include
     * several bookItem.
     */
    private class GridAdapter extends ArrayAdapter<BookItem> {

        private int resource;

        public GridAdapter(Context _context, int _textViewResourceId, ArrayList<BookItem> _items) {
            super(_context, _textViewResourceId, _items);
            this.resource = _textViewResourceId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View retval = LayoutInflater.from(getContext()).inflate(this.resource, null);

            TextView topText = (TextView) retval.findViewById(R.id.title);
//			TextView bottomText = (TextView) retval.findViewById(R.id.author);

            topText.setText(getItem(position).getTitle());
//			bottomText.setText(getItem(position).getTitle());

            return retval;
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
