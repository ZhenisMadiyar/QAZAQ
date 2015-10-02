package com.madiyar.ikazak.kazak.kuileri;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.madiyar.ikazak.kazak.R;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by madiyar on 11/20/14.
 */


public class Kui extends Activity implements MediaPlayer.OnCompletionListener {
    ListView listView;
    RelativeLayout relativeLayout;
    SeekBar seekBar;
    private boolean intialStage = true;
    //    String[] name = {"Адай", "Ақ желкен", "Ақсақ киік", "Алтын күй", "Аман бол шешем", "Аңшылық", "Балбырауын",
//            "Кедей зары", "Кішкентай", "Көңіл толқыны", "Қызыл қайың", "Түрмеден қашқан"};
//    String[] author = {"Құрманғазы", "Құрманғазы", "Құрманғазы", "Құрманғазы", "Құрманғазы", "Дәулеткерей", "Құрманғазы", "Құрманғазы",
//            "Құрманғазы", "Құрманғазы", "Құрманғазы", "Құрманғазы",};
//    String kui[] = {"https://6753ac611dbbaa42c91cce821cb4a97ffc5a3153.googledrive.com/host/0B5SiEwm5ZOqfflRKdXFCTUtYdXlYcmVTZXZCVFd5Z3F0M3RQRDlweVpLNjlTWnE2VGFXOE0/adai.mp3",
//            "https://6753ac611dbbaa42c91cce821cb4a97ffc5a3153.googledrive.com/host/0B5SiEwm5ZOqfflRKdXFCTUtYdXlYcmVTZXZCVFd5Z3F0M3RQRDlweVpLNjlTWnE2VGFXOE0/ak%20zhelken.mp3",
//            "https://6753ac611dbbaa42c91cce821cb4a97ffc5a3153.googledrive.com/host/0B5SiEwm5ZOqfflRKdXFCTUtYdXlYcmVTZXZCVFd5Z3F0M3RQRDlweVpLNjlTWnE2VGFXOE0/aksak%20kiik.mp3",
//            "https://6753ac611dbbaa42c91cce821cb4a97ffc5a3153.googledrive.com/host/0B5SiEwm5ZOqfflRKdXFCTUtYdXlYcmVTZXZCVFd5Z3F0M3RQRDlweVpLNjlTWnE2VGFXOE0/altyn%20kui.mp3",
//            "https://6753ac611dbbaa42c91cce821cb4a97ffc5a3153.googledrive.com/host/0B5SiEwm5ZOqfflRKdXFCTUtYdXlYcmVTZXZCVFd5Z3F0M3RQRDlweVpLNjlTWnE2VGFXOE0/aman_bol_sheshem.mp3",
//            "https://6753ac611dbbaa42c91cce821cb4a97ffc5a3153.googledrive.com/host/0B5SiEwm5ZOqfflRKdXFCTUtYdXlYcmVTZXZCVFd5Z3F0M3RQRDlweVpLNjlTWnE2VGFXOE0/anshylyk.mp3",
//            "https://6753ac611dbbaa42c91cce821cb4a97ffc5a3153.googledrive.com/host/0B5SiEwm5ZOqfflRKdXFCTUtYdXlYcmVTZXZCVFd5Z3F0M3RQRDlweVpLNjlTWnE2VGFXOE0/balbyrauyn.mp3",
//            "https://6753ac611dbbaa42c91cce821cb4a97ffc5a3153.googledrive.com/host/0B5SiEwm5ZOqfflRKdXFCTUtYdXlYcmVTZXZCVFd5Z3F0M3RQRDlweVpLNjlTWnE2VGFXOE0/kedei%20zary.mp3",
//            "https://6753ac611dbbaa42c91cce821cb4a97ffc5a3153.googledrive.com/host/0B5SiEwm5ZOqfflRKdXFCTUtYdXlYcmVTZXZCVFd5Z3F0M3RQRDlweVpLNjlTWnE2VGFXOE0/kishkentai%20kurmangazy.mp3",
//            "https://6753ac611dbbaa42c91cce821cb4a97ffc5a3153.googledrive.com/host/0B5SiEwm5ZOqfflRKdXFCTUtYdXlYcmVTZXZCVFd5Z3F0M3RQRDlweVpLNjlTWnE2VGFXOE0/konil_tolkyny.mp3",
//            "https://6753ac611dbbaa42c91cce821cb4a97ffc5a3153.googledrive.com/host/0B5SiEwm5ZOqfflRKdXFCTUtYdXlYcmVTZXZCVFd5Z3F0M3RQRDlweVpLNjlTWnE2VGFXOE0/kyzyl%20kaiyn.mp3",
//            "https://6753ac611dbbaa42c91cce821cb4a97ffc5a3153.googledrive.com/host/0B5SiEwm5ZOqfflRKdXFCTUtYdXlYcmVTZXZCVFd5Z3F0M3RQRDlweVpLNjlTWnE2VGFXOE0/turmeden%20kashkan.mp3"};
    ArrayList<Info> arrayList;
    TextView textView_author, textView_kui_name;
    MediaPlayer mp;

    private static String URL = "http://qazaq.96.lt/api/video%20upload%20and%20playback/get_kui.php";
    ArrayList<String> url_array = new ArrayList<String>();
    ArrayList<String> name_array = new ArrayList<String>();
    HashMap<String, Object> hm;

    private Handler mHandler = new Handler();
    private Utilities utils;
    boolean press = true;
    boolean press_adapter = true;
    Button btn_play, btn_share;
    int pos;
    Typeface type;
    ProgressDialog pDialog;
    Gson gson;
    AdapterKui adapter;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kui);

        type = Typeface.createFromAsset(getAssets(), "fonts/taurus.ttf");

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setTitle(R.string.kuiler);

        hm = new HashMap<String, Object>();

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btn_play = (Button) findViewById(R.id.button_play);
        btn_share = (Button) findViewById(R.id.button_share);

        arrayList = new ArrayList<Info>();
        listView = (ListView) findViewById(R.id.list_view);
        relativeLayout = (RelativeLayout) findViewById(R.id.olen_info);

        mp = new MediaPlayer();
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);

        if (isOnline()) {
//            new GetKuiList().execute();
            gson = new Gson();
            ParseCloud.callFunctionInBackground("get_kui", hm, new FunctionCallback<Object>() {
                @Override
                public void done(Object o, ParseException e) {
                    if (e == null) {
                        String response = gson.toJson(o);
                        Log.i("ResponseKui", response);
                        JSONArray jsonArray = null;
                        try {
                            jsonArray = new JSONArray(response);
                            Log.i("SIZE ARRAY JSON", jsonArray.length() + "");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                JSONObject estimatedData = jsonObject.getJSONObject("estimatedData");
                                JSONObject jsonImage = estimatedData.getJSONObject("thumb_image");
                                JSONObject mp3 = estimatedData.getJSONObject("mp3");
                                String name = estimatedData.getString("name");
                                String author = estimatedData.getString("author");
                                String imageUrl = jsonImage.getString("url");
                                String mp3Url = mp3.getString("url");
                                Log.i("Kuiler", name + "," + author);
                                arrayList.add(new Info(name, mp3Url, author, imageUrl));
                            }
                            Collections.sort(arrayList, Info.kuiComparator);
                            adapter = new AdapterKui(Kui.this, arrayList);
                            listView.setAdapter(adapter);
                            progressBar.setVisibility(View.GONE);
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    } else {

                    }
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), R.string.internet_connection, Toast.LENGTH_SHORT).show();
        }
//        seekBar = (SeekBar) findViewById(R.id.seek_bar);
//        seekBar.setOnSeekBarChangeListener(this);
        utils = new Utilities();

//        for (int i = 0; i < name.length; i++) {
//            arrayList.add(new Info(name[i], kui[i], author[i]));
//        }

        //adapter = new AdapterKui(this, name);
        //listView.setAdapter(adapter);
        relativeLayout.setVisibility(View.GONE);


        textView_author = (TextView) findViewById(R.id.text_view_ispolnitel);
        textView_kui_name = (TextView) findViewById(R.id.text_view_name);

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                mp = MediaPlayer.create(getApplicationContext(), arrayList.get(position).getAudio());
////                mp.start();
//                pos = position;
//                mp.reset();
//                if (isOnline()) {
//                    new Player().execute(arrayList.get(position).getAudio());
//                    relativeLayout.setVisibility(View.VISIBLE);
//                    textView_kui_name.setText(arrayList.get(position).getName());
//                    textView_author.setText(arrayList.get(position).getAuthor());
//                } else {
//                    Toast.makeText(getApplicationContext(), R.string.internet_connection, Toast.LENGTH_SHORT).show();
//                }
//                btn_play.setSelected(true);
////                seekBar.setProgress(0);
////                seekBar.setMax(100);
////                updateProgressBar();
//
//            }
//        });
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (press) {
                    mp.pause();
                    btn_play.setSelected(false);
                    press = false;
                } else {
                    mp.start();
                    btn_play.setSelected(true);
                    press = true;
                }

            }
        });

        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("audio/*");
                String shareBody = "Here is the share content body";
                sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(arrayList.get(0).getAudio()));
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });
    }

    //kui list get from server
    private class GetKuiList extends AsyncTask<Void, Void, Void> {
        String json;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Kui.this);
            pDialog.setMessage("...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler jsonParser = new ServiceHandler();
            json = jsonParser.makeServiceCall(URL, ServiceHandler.GET);

            Log.e("Response: ", json);


            if (json != null) {
                try {
//                    JSONObject jsonObj = new JSONObject(json);
//                        JSONArray categories = jsonObj.getJSONArray("categories");

                    JSONArray jsonArray = new JSONArray(json);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        String name = jsonObject.getString("name_kui");
                        String url = jsonObject.getString("url");
                        String author = jsonObject.getString("author_kui");
                        url_array.add(url);
                        name_array.add(name);
                        Log.i("name,author", name + "," + author);
//                        arrayList.add(new Info(name, url, author, imageUrl));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            pDialog.dismiss();
            Collections.sort(arrayList, Info.kuiComparator);
            AdapterKui adapter = new AdapterKui(Kui.this, arrayList);
            listView.setAdapter(adapter);
        }

    }


    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (this.isFinishing()) {
            mp.stop();
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    class Player extends AsyncTask<String, Void, Boolean> {
        private ProgressDialog progress;

        @Override
        protected Boolean doInBackground(String... params) {
            // TODO Auto-generated method stub
            Boolean prepared;
            try {
                mp.setDataSource(params[0]);

                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        // TODO Auto-generated method stub
                        intialStage = true;
                        press = false;
//                        btn_play.setSelected(false);
                        mp.stop();
                        mp.reset();
                    }
                });
                mp.prepare();

                prepared = true;
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                Log.d("IllegarArgument", e.getMessage());
                prepared = false;
                e.printStackTrace();
            } catch (SecurityException e) {
                // TODO Auto-generated catch block
                prepared = false;
                e.printStackTrace();
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                prepared = false;
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                prepared = false;
                e.printStackTrace();
            }
            return prepared;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            if (progress.isShowing()) {
                progress.cancel();
            }

//            totalDuration_long = mp.getDuration();
//            currentDuration = mp.getCurrentPosition();

            Log.d("Prepared", "//" + result);
            mp.start();

            intialStage = false;
        }

        public Player() {
            progress = new ProgressDialog(Kui.this);
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            this.progress.setMessage(getString(R.string.buffering));
            this.progress.show();

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

    private class AdapterKui extends BaseAdapter {
        //        String name[];
        Activity activity;
        ArrayList<Info> kui;

        public AdapterKui(Activity activity, ArrayList<Info> kui) {
            this.activity = activity;
            this.kui = kui;
        }

        @Override
        public int getCount() {
            return kui.size();
        }

        @Override
        public Object getItem(int i) {
            return kui.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup viewGroup) {
            View view = convertView;
            final ViewHolder holder;
            if (view == null) {
                view = View.inflate(activity,
                        R.layout.kui_item_row, null);
                holder = new ViewHolder();

                holder.textViewKuiName = (TextView) view.findViewById(R.id.textViewKuiName);
                holder.textViewAuthorName = (TextView) view.findViewById(R.id.textViewAuthorName);
                holder.thumb = (ImageView) view.findViewById(R.id.imageViewKuiImage);
                holder.play = (ImageButton) view.findViewById(R.id.imageButtonPlay);
                holder.share = (ImageButton) view.findViewById(R.id.imageButtonShare);
                holder.download = (ImageButton) view.findViewById(R.id.imageButtonDownload);

                view.setTag(holder);

            } else {
                holder = (ViewHolder) view.getTag();
            }
            holder.textViewKuiName.setTypeface(type);
            holder.textViewKuiName.setText(kui.get(position).getName());

            holder.textViewAuthorName.setTypeface(type);
            holder.textViewAuthorName.setText(kui.get(position).getAuthor());

            Picasso.with(activity)
                    .load(kui.get(position).getImageUrl())
                    .into(holder.thumb);

            holder.play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (press_adapter) {
                        mp.pause();
                        btn_play.setSelected(false);
                        holder.play.setSelected(false);
                        press_adapter = false;
                    } else {
                        mp.start();
                        btn_play.setSelected(true);
                        holder.play.setSelected(false);
                        press_adapter = true;
                    }
                    pos = position;
                    mp.reset();
                    if (isOnline()) {
                        new Player().execute(arrayList.get(position).getAudio());
                        relativeLayout.setVisibility(View.VISIBLE);
                        textView_kui_name.setText(kui.get(position).getName());
                        textView_author.setText(kui.get(position).getAuthor());
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.internet_connection, Toast.LENGTH_SHORT).show();
                    }
                    btn_play.setSelected(true);
                }
            });

            holder.share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "QAZAQ:");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, kui.get(position).getImageUrl());
                    activity.startActivity(Intent.createChooser(sharingIntent, "Таңдаңыз:"));
                }
            });

            holder.download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse(kui.get(position).getAudio());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            });
            return view;
        }
    }

    private class ViewHolder {
        TextView textViewKuiName;
        TextView textViewAuthorName;
        ImageView thumb;
        ImageButton play, share, download;
    }

}
