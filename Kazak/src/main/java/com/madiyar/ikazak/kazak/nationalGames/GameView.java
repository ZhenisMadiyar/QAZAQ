package com.madiyar.ikazak.kazak.nationalGames;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import com.madiyar.ikazak.kazak.R;

/**
 * Created by Zhenis Madiyar on 4/8/2015.
 */
public class GameView extends Activity{
    TextView textView;
    ShareActionProvider mShareActionProvider;
    String content;
    String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_view);

        textView = (TextView) findViewById(R.id.gameTextView);

        Intent intent = getIntent();
        content = intent.getStringExtra("content");
        title = intent.getStringExtra("title");
        getActionBar().setTitle(title);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
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
        shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "QAZAQ:"+title);
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, content);
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
