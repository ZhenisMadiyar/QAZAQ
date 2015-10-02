package com.madiyar.ikazak.kazak;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.madiyar.ikazak.kazak.ertegiler.ErtegiList;
import com.madiyar.ikazak.kazak.kuileri.Kui;
import com.madiyar.ikazak.kazak.makal.MakalCategoryActivity;
import com.madiyar.ikazak.kazak.nationalGames.NationalGames;
import com.madiyar.ikazak.kazak.traditions.TraditionCategory;
import com.madiyar.ikazak.kazak.writers.MainWriter;
import com.parse.ParseAnalytics;

import java.util.Locale;

public class MainMenu extends Activity {

//    ImageButton button_makal, button_tradition, button_games, button_writers, button_kui, button_ertegi;
    GridView gridView;
    AdapterGridView adapter;
    String[] menu = {"Makal", "Salt", "Oiyndar", "Akyndar", "Kuiler", "Ertegi"};

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String locale;
//    String app_locale;
    Locale myLocale;
    int kaz[] = {R.drawable.makal_menu,R.drawable.salt_menu,R.drawable.game_menu,R.drawable.writer_menu,R.drawable.kui_menu,R.drawable.ertegi_menu};
    int rus[] = {R.drawable.makal_rus,R.drawable.salt_dastur_rus,R.drawable.oiyn_rus,R.drawable.akyndar_rus,R.drawable.kui_rus,R.drawable.ertegi_rus};
    int eng[] = {R.drawable.makal_eng,R.drawable.salt_dastur_eng,R.drawable.oiyn_eng,R.drawable.akyndar_eng,R.drawable.kui_eng,R.drawable.ertegi_eng};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);



        ParseAnalytics.trackAppOpened(getIntent());
//        app_locale =  getResources().getConfiguration().locale.toString();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        locale = sharedPreferences.getString("last_locale", "kk_KZ");
        setLocale(locale);

//        Log.i("language:", locale);
        if (locale.equals("ru_RU")) {
            adapter = new AdapterGridView(this, menu, rus);
        } else if (locale.equals("kk_KZ")) {
            adapter = new AdapterGridView(this, menu, kaz);
        } else {
            adapter = new AdapterGridView(this, menu, eng);
        }

        gridView = (GridView) findViewById(R.id.gridView_main);

        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                if (position == 0) {
                    intent = new Intent(getApplicationContext(), MakalCategoryActivity.class);
                }else if (position == 1) {
                    intent = new Intent(getApplicationContext(), TraditionCategory.class);
                }else if (position == 2) {
                    intent = new Intent(getApplicationContext(), NationalGames.class);
                }else if (position == 3) {
                    intent = new Intent(getApplicationContext(), MainWriter.class);
                }else if (position == 4) {
                    intent = new Intent(getApplicationContext(), Kui.class);
                }else if (position == 5) {
                    intent = new Intent(getApplicationContext(), ErtegiList.class);
                }
                startActivity(intent);
            }
        });
    }

    public void setLocale(String lang) {

        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
//        Intent refresh = new Intent(this, MainMenu.class);
//        startActivity(refresh);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting:
                startActivity(new Intent(getApplicationContext(), Settings.class));
                break;
        }

        return (super.onOptionsItemSelected(item));
    }




    private class AdapterGridView extends BaseAdapter{
        Activity activity;
        String[] menuName;
        int[] imag;
        public AdapterGridView(Activity mainMenu, String[] menu, int[] imag) {
            this.activity = mainMenu;
            this.menuName = menu;
            this.imag = imag;
        }

        @Override
        public int getCount() {
            return menuName.length;
        }

        @Override
        public Object getItem(int position) {
            return menuName[position];
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
                        R.layout.row_main_menu_item, null);
                holder = new ViewHolder();
//                holder.text = (TextView) view.findViewById(R.id.textViewTitle);
                holder.imageView = (ImageView) view.findViewById(R.id.imageViewMM);
                view.setTag(holder);
            }else{
                holder = (ViewHolder) view.getTag();
            }
            holder.imageView.setBackgroundResource(imag[position]);
//            holder.text.setText(menuName[position]);
            return view;
        }
        private class ViewHolder {
//            TextView text;
            ImageView imageView;
        }
    }
}
