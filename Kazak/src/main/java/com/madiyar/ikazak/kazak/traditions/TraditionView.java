package com.madiyar.ikazak.kazak.traditions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import com.madiyar.ikazak.kazak.R;

/**
 * Created by Madiyar on 03.02.2015.
 */
public class TraditionView extends Activity{
   TextView textView;
    String con;
    String content;
    ShareActionProvider mShareActionProvider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tradition_view);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        textView = (TextView) findViewById(R.id.textViewTr);
        Intent intent = getIntent();
        content = intent.getStringExtra("content");
        con = intent.getStringExtra("con");
        getActionBar().setTitle(con);
        textView.setText(content);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu resource file.
        getMenuInflater().inflate(R.menu.menu_views, menu);

        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.menu_item_share);
        // Fetch and store ShareActionProvider
        mShareActionProvider = (ShareActionProvider) item.getActionProvider();
        mShareActionProvider.setShareIntent(getShareIntent());
        // Return true to display menu
        return true;
    }
    private Intent getShareIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("plain/text");
        shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "QAZAQ:"+con);
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, content);
//        startActivity(Intent.createChooser(shareIntent, "Share with"));
        return shareIntent;
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
