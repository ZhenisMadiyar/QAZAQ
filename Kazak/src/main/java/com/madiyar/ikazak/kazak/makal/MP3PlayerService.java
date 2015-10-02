package com.madiyar.ikazak.kazak.makal;


import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

/**
 * Created by madiyar on 12/28/14.
 */
public class MP3PlayerService extends Service{

    public final IBinder localBinder = new LocalBinder();
    private MediaPlayer mplayer;
    private boolean created = false;
    @Override
    public IBinder onBind(Intent intent) {
        return localBinder;
    }

    @Override
    public void onCreate() {

    }

    public class LocalBinder extends Binder {
        MP3PlayerService getService(){
            return MP3PlayerService.this;
        }
    }
    public void playSong(Context c, int sound) {
        if(!created){
            this.mplayer = MediaPlayer.create(c, sound);
            created = true;
        }
        this.mplayer.start();
        if(!mplayer.isPlaying()) {
            mplayer.release();
        }

    }

    public void pauseSong(Activity activity) {
        this.mplayer.pause();
    }

    public void release(Context c) {
        this.mplayer.release();
    }
}
