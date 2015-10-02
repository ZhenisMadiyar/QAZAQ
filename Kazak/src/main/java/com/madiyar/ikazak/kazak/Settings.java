package com.madiyar.ikazak.kazak;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by madiyar on 11/18/14.
 */
public class Settings extends Activity {
//    Spinner spinnerctrl;
//    Locale myLocale;
    String[] lang_eng = {"Language", "About program", "Rate app"};
    String[] lang_kz = {"Тілді ауыстыру", "Бағдарлама жайлы", "Бағалау"};
    String[] lang_ru = {"Язык", "О программе", "Оценить"};
    int img[] = {R.drawable.language_icon, R.drawable.about, R.drawable.rate};
    ListView listView;
    private Context contextForDialog = null;
    SettingAdapter set_adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        String locale =  getResources().getConfiguration().locale.toString();
        contextForDialog = this;

        if (locale.equals("ru_ru")){
            set_adapter = new SettingAdapter(this, lang_ru, img);
        }else if(locale.equals("en_gb")){
            set_adapter = new SettingAdapter(this, lang_eng, img);
        }else {
            set_adapter = new SettingAdapter(this, lang_kz, img);
        }
        getActionBar().setTitle(R.string.setting);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(set_adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    startActivity(new Intent(getApplicationContext(), Language.class));
                }else if(position == 1) {
                    // custom dialog
                    final Dialog dialog = new Dialog(contextForDialog);
                    dialog.setContentView(R.layout.about_program);
                    dialog.setTitle(R.string.about);

                    // set the custom dialog components - text, image and button
                    TextView text = (TextView) dialog.findViewById(R.id.text_version);
                    text.setText("3.0");

                    Button dialogButton = (Button) dialog.findViewById(R.id.button_ok);
                    // if button is clicked, close the custom dialog
                    dialogButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }else if (position == 2) {
                    Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName());
                    Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                    try {
                        startActivity(goToMarket);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(getApplicationContext(), R.string.market_error, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
//        spinnerctrl = (Spinner) findViewById(R.id.spinner);
//        spinnerctrl.setPrompt("Select Language");
//        spinnerctrl.setAdapter(adapter);
//
//        spinnerctrl.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//            public void onItemSelected(AdapterView<?> parent, View view,
//                                       int pos, long id) {
//
//                if (pos == 1) {
//
//                    Toast.makeText(parent.getContext(),
//                            "You have selected English", Toast.LENGTH_SHORT)
//                            .show();
//                    setLocale("en");
//                } else if (pos == 2) {
//
//                    Toast.makeText(parent.getContext(),
//                            "You have selected Russian", Toast.LENGTH_SHORT)
//                            .show();
//                    setLocale("ru");
//                } else if (pos == 3) {
//
//                    Toast.makeText(parent.getContext(),
//                            "You have selected Kazakh", Toast.LENGTH_SHORT)
//                            .show();
//                    setLocale("kz");
//                }
//
//            }
//
//            public void onNothingSelected(AdapterView<?> arg0) {
//                // TODO Auto-generated method stub
//            }
//
//        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_settings, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent social;
        Uri uri;
        switch (item.getItemId()) {
            case R.id.vk:
                uri = Uri.parse("https://vk.com/zh_madiyar");
                social = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(social);
                break;
            case R.id.fb:
                uri = Uri.parse("https://www.facebook.com/jenis.madiyar");
                social = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(social);
                break;
            case R.id.instagram:
                uri = Uri.parse("https://instagram.com/zhenismadiyar");
                social = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(social);
                break;
            case android.R.id.home:
                startActivity(new Intent(getApplicationContext(), MainMenu.class));
                return (true);
        }

        return (super.onOptionsItemSelected(item));
    }

    private class SettingAdapter extends BaseAdapter {
        Activity context;
        String name[];
        int img[];
        public SettingAdapter(Activity activity, String[] lang, int[] img) {
            this.context = activity;
            this.name = lang;
            this.img = img;
        }

        @Override
        public int getCount() {
            return name.length;
        }

        @Override
        public Object getItem(int i) {
            return name[i];
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = convertView;
            ViewHolder holder = null;
            if (view == null){
                view = View.inflate(context,
                        R.layout.row_item_settings, null);
                holder = new ViewHolder();
                holder.text = (TextView) view.findViewById(R.id.textViewSet);
                holder.imageView = (ImageView) view.findViewById(R.id.imageViewSet);
                view.setTag(holder);
            }else{
                holder = (ViewHolder) view.getTag();
            }

            holder.text.setText(name[position]);
            holder.imageView.setBackgroundResource(img[position]);
            return view;
        }
        private class ViewHolder {
            TextView text;
            ImageView imageView;
        }
    }

//    public void setLocale(String lang) {
//
//        myLocale = new Locale(lang);
//        Resources res = getResources();
//        DisplayMetrics dm = res.getDisplayMetrics();
//        Configuration conf = res.getConfiguration();
//        conf.locale = myLocale;
//        res.updateConfiguration(conf, dm);
//        Intent refresh = new Intent(this, Settings.class);
//        startActivity(refresh);
//    }
}
