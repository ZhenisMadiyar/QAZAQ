package com.madiyar.ikazak.kazak.writers;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import com.madiyar.ikazak.kazak.R;

/**
 * Created by madiyar on 11/25/14.
 */
public class LyricView extends Activity {
    TextView textView;
    int p = 0;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    ShareActionProvider mShareActionProvider;
    String lyric;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.writer_full_lyric);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        textView = (TextView) findViewById(R.id.textViewLyric);

        p = prefs.getInt("textsize",30);
        Intent intent = getIntent();
        lyric = intent.getStringExtra("lyric");
        textView.setText(lyric);
        textView.setTextSize(p);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_lyric_view, menu);

        MenuItem item = menu.findItem(R.id.menu_item_share);
        // Fetch and store ShareActionProvider
        mShareActionProvider = (ShareActionProvider) item.getActionProvider();
        mShareActionProvider.setShareIntent(getShareIntent());
        return true;
    }

    private Intent getShareIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("plain/text");
        shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "QAZAQ:");
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, lyric);
//        startActivity(Intent.createChooser(shareIntent, "Share with"));
        return shareIntent;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.font_size:
                final Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.custom_dialog);
                dialog.setTitle("Көлемін өзгерту");

                SeekBar seekBar = (SeekBar) dialog.findViewById(R.id.seekBar);
                seekBar.setProgress(p);
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        p = progress;
                        textView.setTextSize(p);
                        editor = prefs.edit();
                        editor.putInt("textsize", p);
                        editor.commit();
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
//                        if (p < 30) {
//                            p = 30;
                            seekBar.setProgress(p);
                    }
                });
                Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();

                break;
            case android.R.id.home:
                finish();
                return (true);
        }

        return (super.onOptionsItemSelected(item));
    }
}
