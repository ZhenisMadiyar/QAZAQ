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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.madiyar.ikazak.kazak.R;
import com.madiyar.ikazak.kazak.database.DataBaseSource;
import com.madiyar.ikazak.kazak.database.DatabaseHelper;
import com.madiyar.ikazak.kazak.database.Makal;
import com.navdrawer.SimpleSideDrawer;

import java.util.ArrayList;
import java.util.List;

import yuku.ambilwarna.AmbilWarnaDialogFragment;
import yuku.ambilwarna.OnAmbilWarnaListener;

/**
 * Created by madiyar on 11/16/14.
 */
public class MakalListActivity extends FragmentActivity implements OnAmbilWarnaListener {
    AdapterMakalList adapter;
    ListView listView;
    SimpleSideDrawer simpleSideDrawer;
//    String []makal = {"asdasda", "asdasda\nasdasdasd\naasdwdqwdsadas\nasdasdasdasd", "asdasda", "asdasda", "asdasda", "asdasda", "asdasda"};
//    ArrayList<String> makalList;

    ImageButton makeBigger, makeSmaller;
    int textSize = 20;
    int size = 20;
    int textFace;
    ListView listViewFonts;
    AdapterTypfaceList adapterTypfaceList;
    ArrayList<Typeface> typfaceList;
    ArrayList<String> nameFonts;
    RelativeLayout textSizeLayout;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;
//    Typeface face[] = new Typeface[3];

    DataBaseSource dataBaseSource;
    List<Makal> listOfmakal;
    List<Makal> listOfmakal_next;
    String tag = null;
    String text;
    int positionM;
    int []sounds;
    private String[] allColumns_makal_list = { DatabaseHelper.COLUMN_ID,DatabaseHelper.COLUMN_TAG,DatabaseHelper.COLUMN_POINT,DatabaseHelper.COLUMN_CONTENT};
    public static final String TABLE_MAKAL = "MakalList";

    FloatingActionButton fab;
    private static int mColor = -16777216;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makal_list);

        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        simpleSideDrawer = new SimpleSideDrawer(this);
        simpleSideDrawer.setRightBehindContentView(R.layout.right_menu_makal);

        makeBigger = (ImageButton) findViewById(R.id.imageButtonBig);
        makeSmaller = (ImageButton) findViewById(R.id.imageButtonSmall);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
//        face[1] = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/DroidSans.ttf");
//        face[2] = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/DroidSans.ttf");
//        face[3] = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/DroidSans.ttf");

        textSize = prefs.getInt("textsize", 20);
        textFace = prefs.getInt("textface", 0);

        typfaceList = new ArrayList<Typeface>();
        nameFonts = new ArrayList<String>();

        nameFonts.add("DroidSans");
        nameFonts.add("OpenSans-Regular");
        nameFonts.add("Times");
        nameFonts.add("Arial");
        nameFonts.add("Taurus");

        textSizeLayout = (RelativeLayout) findViewById(R.id.textSizeLay);
        typfaceList.add(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/DroidSans.ttf"));
        typfaceList.add(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/OpenSans-Regular.ttf"));
        typfaceList.add(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/TIMES.TTF"));
        typfaceList.add(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/ARIAL.TTF"));
        typfaceList.add(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/taurus.ttf"));
        listViewFonts = (ListView) findViewById(R.id.listViewFonts);
        listViewFonts.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        adapterTypfaceList = new AdapterTypfaceList(this, typfaceList, nameFonts);
        View header = getLayoutInflater().inflate(R.layout.header_fonts, null);
        listViewFonts.addHeaderView(header);
        listViewFonts.setAdapter(adapterTypfaceList);
        listViewFonts.setItemChecked(textFace, true);
        listViewFonts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setTypeface(listViewFonts.getCheckedItemPosition());
                simpleSideDrawer.closeRightSide();
            }
        });

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

        listOfmakal = new ArrayList<Makal>();
        listOfmakal_next = new ArrayList<Makal>();

        Intent in= getIntent();
        Bundle bundle = in.getExtras();
//        String s = in.getStringExtra("tag");
        if (bundle!=null) {
            text =(String) bundle.get("title_labe");
            positionM = (Integer) bundle.get("pos");
            getActionBar().setTitle(text);
            tag = (String) bundle.get("tag");
            Log.i("INFO", "NOT NULL");
            Log.i("INFO", text);
        } else {
            Log.i("INFO", "NULL");
        }



        dataBaseSource = new DataBaseSource(this);
        dataBaseSource.open();
        listOfmakal = dataBaseSource.getMakalListByTag(TABLE_MAKAL, allColumns_makal_list, tag);
        dataBaseSource.close();
        listOfmakal_next.addAll(listOfmakal);

        listView = (ListView) findViewById(R.id.listViewMakalListS);
//        makalList = new ArrayList<String>();
//        for (String s:makal){
//            makalList.add(s);
//        }
        adapter = new AdapterMakalList(this, listOfmakal_next, textSize, typfaceList.get(textFace), positionM, mColor);
        listView.setAdapter(adapter);

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
        makeBigger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (size < 40){
                    size = size + 2;
//                    int s = size;
                    setSize(size);
                }
            }
        });

        makeSmaller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (size > 10) {
                    size = size - 2;
                    setSize(size);
                }
            }
        });
    }

    private void setTypeface(int textFace) {

        adapter = new AdapterMakalList(this, listOfmakal_next, size, typfaceList.get(textFace-1), positionM, mColor);
        listView.setAdapter(adapter);
        editor = prefs.edit();
        editor.putInt("textface", textFace);
        editor.commit();
    }

    private void setSize(int size) {
        adapter = new AdapterMakalList(this, listOfmakal_next, size, typfaceList.get(textFace), positionM, mColor);
        listView.setAdapter(adapter);
//        textSize = size;
//        adapter.notifyDataSetChanged();
        editor = prefs.edit();
        editor.putInt("textsize", size);
        editor.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_makal_list, menu);
        return true;
    }

        @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.favourite:
                Intent intent = new Intent(getApplicationContext(), FavouriteMakal.class);
                intent.putExtra("pos", positionM);
//                intent.putExtra("posMakal", listView.getSelectedItemPosition());
//                Log.i("posCat=", positionM+"");
//                Log.i("posMakal=", listView.getSelectedItemPosition()+"");
                startActivity(intent);
                break;
            case R.id.other:
                simpleSideDrawer.toggleRightDrawer();
                break;
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
        MakalListActivity.this.mColor = color;
        adapter = new AdapterMakalList(this, listOfmakal_next, size, typfaceList.get(textFace), positionM, color);
        listView.setAdapter(adapter);
        Log.i("COLOR", color+"");
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
