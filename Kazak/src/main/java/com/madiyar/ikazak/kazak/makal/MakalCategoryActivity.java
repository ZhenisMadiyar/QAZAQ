package com.madiyar.ikazak.kazak.makal;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.madiyar.ikazak.kazak.R;
import com.madiyar.ikazak.kazak.database.DataBaseSource;
import com.madiyar.ikazak.kazak.database.DatabaseHelper;
import com.madiyar.ikazak.kazak.database.MakalCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by madiyar on 11/14/14.
 */
public class MakalCategoryActivity extends FragmentActivity {
    ListView listView;
    String makal_category[];
    AdapterMakalCategory adapter;
    ArrayList<String> arrayList;

    DataBaseSource dataSource;
    List<MakalCategory> listOfCat;
    List<MakalCategory> listOfCat_next;

    ArrayList<Integer> sound = new ArrayList<Integer>();
    private String[] allColumns_makal_category = { DatabaseHelper.COLUMN_ID_C,DatabaseHelper.COLUMN_TAG_C,DatabaseHelper.COLUMN_CONTENT_C};
//    public static final String TABLE_CAT = "MakalCategory";
    MediaPlayer mp;

    int kaz[] = {R.raw._kz_adam, R.raw._kz_akyldykyk, R.raw._kz_ar_namys, R.raw._kz_bilim,
            R.raw._kz_dostyk, R.raw._kz_til, R.raw._kz_enbek, R.raw._kz_otbasy, R.raw._kz_otan, R.raw._kz_otirik};
    int ru[] = {R.raw.ru_chelovek, R.raw.ru_um, R.raw.ru_sovest, R.raw.ru_znanie,
            R.raw.ru_druzhba, R.raw.ru_iyazyk, R.raw.ru_trud, R.raw.ru_semya, R.raw.ru_rodina, R.raw.ru_pravda};

    MP3PlayerService mp3Service;
    private ServiceConnection mp3PlayerServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName arg0, IBinder binder) {
            mp3Service = ((MP3PlayerService.LocalBinder) binder).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makal);
        listView = (ListView) findViewById(R.id.listView_makal);
        arrayList = new ArrayList<String>();

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setTitle(R.string.makal_act);

        listOfCat = new ArrayList<MakalCategory>();
        listOfCat_next = new ArrayList<MakalCategory>();
        dataSource = new DataBaseSource(getApplicationContext());
        dataSource.open();
        listOfCat = dataSource.getAllCategory(DatabaseHelper.TABLE_CAT, allColumns_makal_category);
        dataSource.close();
        listOfCat_next.addAll(listOfCat);

        final String locale =  getResources().getConfiguration().locale.toString();
//        Toast.makeText(getApplicationContext(), locale, Toast.LENGTH_SHORT).show();
        if (locale.equals("ru_ru")) {
            for (int s : ru) {
                sound.add(s);
            }
        }else {
            for (int s : kaz) {
                sound.add(s);
            }
        }

        adapter = new AdapterMakalCategory(this, listOfCat_next);
        listView.setAdapter(adapter);

        this.startService(new Intent(this, MP3PlayerService.class));
        //bind to our service by first creating a new connectionIntent
        Intent connectionIntent = new Intent(this, MP3PlayerService.class);
        this.bindService(connectionIntent, mp3PlayerServiceConnection,
                Context.BIND_AUTO_CREATE);
//        makal_category = getResources().getStringArray(R.array.Category_names);
//        for (String s:makal_category){
//            arrayList.add(s);
//        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                mp3Service.playSong(getApplicationContext(), sound.get(position));
//                mp3Service.release(getApplicationContext());
                if (locale.equals("en_gb")) {

                } else {
                    mp = MediaPlayer.create(getApplicationContext(), sound.get(position));
                    mp.start();
                }
                Intent intent = new Intent(getApplicationContext(), MakalListActivity.class);
                intent.putExtra("title_labe", listOfCat_next.get(position).getContent());
                intent.putExtra("tag", listOfCat_next.get(position).getTag());
                intent.putExtra("pos", position);
                startActivity(intent);
//                while (mp.isPlaying()) {

//                }
//                mp.release();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_makal, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        if (null != searchView) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        }
        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //      Toast.makeText(getActivity(), "TEXT:" + s, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                SearchAdapter(s);
                return false;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);
        return true;
    }
    private void SearchAdapter(String str) {
        if (str == null) {
            listOfCat_next.clear();
            adapter.notifyDataSetChanged();
        } else {
            listOfCat_next.clear();
            for (int i = 0; i < listOfCat.size(); i++) {
                if (listOfCat.get(i).getContent().toLowerCase().contains(str.toString().toLowerCase())) {
                    listOfCat_next.add(listOfCat.get(i));
                }
                adapter.notifyDataSetChanged();
            }
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
