package com.madiyar.ikazak.kazak.nationalGames;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.madiyar.ikazak.kazak.R;
import com.madiyar.ikazak.kazak.database.DataBaseSource;
import com.madiyar.ikazak.kazak.database.DatabaseHelper;
import com.madiyar.ikazak.kazak.database.MakalCategory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by madiyar on 11/20/14.
 */
public class NationalGames extends Activity {
    ListView listView;
    NatGameAdapter adapter;

    private String[] allColumns_game = {DatabaseHelper.COLUMN_ID_GAME,
            DatabaseHelper.COLUMN_TAG_GAME, DatabaseHelper.COLUMN_NAME_GAME, DatabaseHelper.COLUMN_CONTENT_GAME};
    DataBaseSource dataSource;
    List<MakalCategory> listOfCat;
    List<MakalCategory> listOfCat_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.national_games);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setTitle(R.string.national_games);


        listOfCat = new ArrayList<MakalCategory>();
        listOfCat_next = new ArrayList<MakalCategory>();
        dataSource = new DataBaseSource(getApplicationContext());
        dataSource.open();
        listOfCat = dataSource.getAllGames(DatabaseHelper.TABLE_GAME, allColumns_game);
        dataSource.close();
        listOfCat_next.addAll(listOfCat);

        Collections.sort(listOfCat_next, MakalCategory.makalCategoryComparator);
        listView = (ListView) findViewById(R.id.listViewNG);
        adapter = new NatGameAdapter(this, listOfCat_next);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), GameView.class);
                intent.putExtra("content", listOfCat_next.get(i).getBiography());
                intent.putExtra("title", listOfCat_next.get(i).getContent());
                startActivity(intent);
            }
        });
//        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
//            int mLastFirstVisibleItem = 0;
//
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {   }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                if (view.getId() == listView.getId()) {
//                    final int currentFirstVisibleItem = listView.getFirstVisiblePosition();
//
//                    if (currentFirstVisibleItem > mLastFirstVisibleItem) {
//                        // getSherlockActivity().getSupportActionBar().hide();
//                        getActionBar().hide();
//                    } else if (currentFirstVisibleItem < mLastFirstVisibleItem) {
//                        // getSherlockActivity().getSupportActionBar().show();
//                        getActionBar().show();
//                    }
//
//                    mLastFirstVisibleItem = currentFirstVisibleItem;
//                }
//            }
//        });
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
