package com.madiyar.ikazak.kazak.writers;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.madiyar.ikazak.kazak.R;
import com.madiyar.ikazak.kazak.database.DataBaseSource;
import com.madiyar.ikazak.kazak.database.DatabaseHelper;
import com.madiyar.ikazak.kazak.database.Makal;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by madiyar on 11/21/14.
 */
public class Lyrics_activity extends Fragment {
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    ListView listView;
    List<Makal> list;
    DataBaseSource dataBaseSource;
    List<Makal>listOfmakal;
    List<Makal>listOfmakal_next;
    private String[] allColumns_writer_lyric_list = { DatabaseHelper.COLUMN_ID_WLL,DatabaseHelper.COLUMN_TAG_WLL,DatabaseHelper.COLUMN_CONTENT_WLL,DatabaseHelper.COLUMN_LYRIC_WLL};
    public static final String TABLE_WRITER_LYRICS_LIST= "WriterLyricsList";
    String tag;
    AdapterLyricList adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.writer_lyrics, container, false);

        setHasOptionsMenu(true);
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        listOfmakal = new ArrayList<Makal>();
        listOfmakal_next = new ArrayList<Makal>();
        listView = (ListView) view.findViewById(R.id.listViewLyrics);
        list = new ArrayList<Makal>();

        tag = prefs.getString("tag", "");
        Log.i("aaaaaaa", tag);

        dataBaseSource = new DataBaseSource(getActivity());
        dataBaseSource.open();
        listOfmakal = dataBaseSource.getWriter(TABLE_WRITER_LYRICS_LIST, allColumns_writer_lyric_list, tag);
        dataBaseSource.close();
        listOfmakal_next.addAll(listOfmakal);

        adapter = new AdapterLyricList(getActivity(), listOfmakal_next);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (listOfmakal_next.get(position).toString().equals("Извините, данный раздел находится в разработке")) {
                    Log.i("info_lyric", "EMPTY");
                    listView.setClickable(false);
                } else if(listOfmakal_next.get(position).toString().equals("Sorry, this section is under construction")) {
                    listView.setClickable(false);
                } else {
                    listView.setClickable(true);
                    Intent intent = new Intent(getActivity(), LyricView.class);
                    intent.putExtra("lyric", listOfmakal_next.get(position).getPoint());
                    startActivity(intent);
                }
            }
        });
        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_makal, menu);
        SearchManager searchManager =
                (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        if (null != searchView) {
            searchView.setSearchableInfo(
                    searchManager.getSearchableInfo(getActivity().getComponentName()));
        }
        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
//                Toast.makeText(getActivity(), "TEXT:" + s, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                SearchAdapter(s);
                return false;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void SearchAdapter(String str) {
        if (str == null) {
            listOfmakal_next.clear();
            adapter.notifyDataSetChanged();
        } else {
            listOfmakal_next.clear();
            for (int i = 0; i < listOfmakal.size(); i++) {
                if (listOfmakal.get(i).getContent().toLowerCase().contains(str.toString().toLowerCase())) {
                    listOfmakal_next.add(listOfmakal.get(i));
                }
                adapter.notifyDataSetChanged();
            }
        }
    }
}
