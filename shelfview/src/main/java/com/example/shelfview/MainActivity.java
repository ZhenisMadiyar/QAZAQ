package com.example.shelfview;

import android.app.Activity;
import android.app.ProgressDialog;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.IOException;


public class MainActivity extends Activity implements MediaPlayer.OnPreparedListener,
        MediaPlayer.OnCompletionListener {
    Button button;
    private boolean playPause;
    private boolean intialStage = true;
    static final String AUDIO_PATH = "http://cf-media.sndcdn.com/OsecReDnNWT1.128.mp3?Policy=eyJTdGF0ZW1lbnQiOlt7IlJlc291cmNlI" +
            "joiKjovL2NmLW1lZGlhLnNuZGNkbi5jb20vT3NlY1JlRG5OV1QxLjEyOC5tcDMiLCJDb25kaXRpb24iOnsiRGF0ZUxlc3NUaGFuIjp7IkFXUzpFcG9jaF" +
            "RpbWUiOjE0MzA2MTE1NTN9fX1dfQ__&Signature=cEScdK8T9xrlf00z-JRiaub3knWdcpU4DVaLO5sRVPnMlfCbf9ETrDZijbJ4wL~s8~RakxoX7aW9X" +
            "4Qsgxn0t1XUNfQ0h2vojdLq9SS3F43nwPwisCuGvV~WDjp0P1gqyQUEaDTi7EjTu-4ZnZb9cyuZQ-IS4CXWc9Om-e3TQjoIU17Ks5TCZDgDkscKVUM~vR1" +
            "oqU84zrDevshUK4tsAvHC9ivOnXdnri0FlraXFjMthon8N~i4vyOhT3f" +
            "U8ONjzoZH9ZYWBx5Tg-nF7SSbp4waaQh03-8jL3P8cCagaYbzlloAWN4DV0HZ5IAjkvXZbVy-x9ikt29e09lVyVBksg__&Key-Pair-Id=APKAJAGZ7VMH" +
            "2PFPW6UQ";
    private MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        button.setOnClickListener(pausePlay);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private View.OnClickListener pausePlay = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            // TODO Auto-generated method stub

            if (!playPause) {
                button.setBackgroundResource(R.drawable.pause);
                if (intialStage)
                    new Player()
                            .execute("https://8734250daabdef7e6ee27fa625a238e5cef42d26.googledrive.com/host/0B5SiEwm5ZOqfV0ZzZndiYktlZzA");
                else {
                    if (!mediaPlayer.isPlaying())
                        mediaPlayer.start();
                }
                playPause = true;
            } else {
                button.setBackgroundResource(R.drawable.play);
                if (mediaPlayer.isPlaying())
                    mediaPlayer.pause();
                playPause = false;
            }
        }
    };

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    class Player extends AsyncTask<String, Void, Boolean> {
        private ProgressDialog progress;

        @Override
        protected Boolean doInBackground(String... params) {
            // TODO Auto-generated method stub
            Boolean prepared;
            try {

                mediaPlayer.setDataSource(params[0]);

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        // TODO Auto-generated method stub
                        intialStage = true;
                        playPause=false;
                        button.setBackgroundResource(R.drawable.play);
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                    }
                });
                mediaPlayer.prepare();
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
            Log.d("Prepared", "//" + result);
            mediaPlayer.start();

            intialStage = false;
        }

        public Player() {
            progress = new ProgressDialog(MainActivity.this);
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            this.progress.setMessage("Buffering...");
            this.progress.show();

        }
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
