package com.madiyar.ikazak.kazak.ertegiler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.madiyar.ikazak.kazak.R;

/**
 * Created by Zhenis Madiyar on 5/3/2015.
 */
public class ErtegiList extends Activity {

    ListView listView;
    AdapterErtegiList adapter;
    String locale;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ertegi_list);

        locale =  getResources().getConfiguration().locale.toString();
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setTitle(R.string.ertegiler);

        listView = (ListView) findViewById(R.id.listViewErtegi);
        Log.i("locale", locale);
        if (locale.equals("ru_ru")) {
            adapter = new AdapterErtegiList(this, BookItem.ALL_BOOKS_RU);
        } else if (locale.equals("en_gb")) {
            adapter = new AdapterErtegiList(this, BookItem.ALL_BOOKS_ENG);
        }else {
            adapter = new AdapterErtegiList(this, BookItem.ALL_BOOKS);
        }
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), ErtegiView.class);
                if (locale.equals("ru_ru")) {
                    intent.putExtra("content", BookItem.ALL_BOOKS_RU[i].getContent());
                } else if (locale.equals("en_gb")) {
                    intent.putExtra("content", BookItem.ALL_BOOKS_ENG[i].getContent());
                } else {
                    intent.putExtra("content", BookItem.ALL_BOOKS[i].getContent());
                }
                startActivity(intent);
            }
        });
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
