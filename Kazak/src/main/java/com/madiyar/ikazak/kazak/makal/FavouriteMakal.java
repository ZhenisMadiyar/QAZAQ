package com.madiyar.ikazak.kazak.makal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.madiyar.ikazak.kazak.R;
import com.madiyar.ikazak.kazak.database.DataBaseSource;
import com.madiyar.ikazak.kazak.database.Makal;

import java.util.ArrayList;
import java.util.List;

import yuku.ambilwarna.AmbilWarnaDialogFragment;
import yuku.ambilwarna.OnAmbilWarnaListener;

/**
 * Created by madiyar on 12/22/14.
 */
public class FavouriteMakal extends FragmentActivity  implements OnAmbilWarnaListener {
    ListView listView;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    List<Makal> listOfmakal;
    List<Makal> listOfSize;
    DataBaseSource dbsrc;
//    String tag;
    AdapterMakalList adapter_of_fav;

    ArrayList<Typeface> typfaceList;

    int textSize;
    int textFace;
    int pos;
    FloatingActionButton fab;
    private static int mColor = -16777216;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.makal_favourite);

        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setTitle(R.string.favourite);
        Intent intent= getIntent();
        pos = intent.getIntExtra("pos", 0);
//        Bundle bundle = intent.getExtras();
//        tag = (String) bundle.get("tag");

        typfaceList = new ArrayList<Typeface>();

        typfaceList.add(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/DroidSans.ttf"));
        typfaceList.add(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/OpenSans-Regular.ttf"));
        typfaceList.add(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/TIMES.TTF"));
        typfaceList.add(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/ARIAL.TTF"));
        prefs = PreferenceManager.getDefaultSharedPreferences(this);


        listOfmakal = new ArrayList<Makal>();
        listOfSize = new ArrayList<Makal>();

        textSize = prefs.getInt("textsize", 20);
        textFace = prefs.getInt("textface", 0);



        fab = new FloatingActionButton.Builder(this)
                .withDrawable(getResources().getDrawable(R.drawable.palette_72))
                .withButtonColor(Color.WHITE)
                .withGravity(Gravity.BOTTOM | Gravity.LEFT)
                .withMargins(0, 0, 16, 16)
                .create();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showColorPicker();
            }
        });

        if (savedInstanceState != null) {
            AmbilWarnaDialogFragment fragment = (AmbilWarnaDialogFragment) getSupportFragmentManager().findFragmentByTag("color_picker_dialog");
            if (fragment != null) {
                fragment.setOnAmbilWarnaListener(this);
            }
        }

        listView = (ListView) findViewById(R.id.listview_f);
        dbsrc = new DataBaseSource(this);
        dbsrc.open();
        listOfSize = dbsrc.getAllMakal();
        Log.i("SIZE", listOfSize.size()+"");
        for(int i = 0; i < listOfSize.size(); i++){
            if(prefs.getBoolean(listOfSize.get(i).getPoint(), false)){
                listOfmakal.add(dbsrc.getMakalByPoint(listOfSize.get(i).getPoint()));
            }
        }
        dbsrc.close();

        adapter_of_fav = new AdapterMakalList(this, listOfmakal, textSize,typfaceList.get(textFace), pos, mColor);
        listView.setAdapter(adapter_of_fav);
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

    @Override
    public void onCancel(AmbilWarnaDialogFragment dialogFragment) {

    }

    @Override
    public void onOk(AmbilWarnaDialogFragment dialogFragment, int color) {
        FavouriteMakal.this.mColor = color;
        adapter_of_fav = new AdapterMakalList(this, listOfmakal, textSize,typfaceList.get(textFace), pos, mColor);
        listView.setAdapter(adapter_of_fav);
    }
    private void showColorPicker() {
        int thisColor = mColor == 0 ? Color.BLUE : mColor;

        // create new instance of AmbilWarnaDialogFragment and set OnAmbilWarnaListener listener to it
        // show dialog fragment with some tag value
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        AmbilWarnaDialogFragment fragment = AmbilWarnaDialogFragment.newInstance(thisColor, android.R.style.Theme_Dialog);
        fragment.setOnAmbilWarnaListener(this);

        fragment.show(ft, "color_picker_dialog");
    }
}
