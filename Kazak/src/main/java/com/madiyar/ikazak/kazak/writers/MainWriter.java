package com.madiyar.ikazak.kazak.writers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.madiyar.ikazak.kazak.R;
import com.madiyar.ikazak.kazak.database.DataBaseSource;
import com.madiyar.ikazak.kazak.database.DatabaseHelper;
import com.madiyar.ikazak.kazak.database.MakalCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by madiyar on 11/22/14.
 */
public class MainWriter extends Activity {
    ListView listView;
    AdapterWriterList adapter;

    DataBaseSource dataSource;
    List<MakalCategory> listOfCat;
    List<MakalCategory> listOfCat_next;
//    String writerList[] = {"asdad", "asdasd", "asdasd", "asdasd", "asdasd", "asdasd", "asdasd"};
    private String[] allColumns_writer_list = { DatabaseHelper.COLUMN_ID_WL,DatabaseHelper.COLUMN_TAG_WL,DatabaseHelper.COLUMN_CONTENT_WL, DatabaseHelper.COLUMN_BIOGRAPHY_WL};
    public static final String TABLE_WRITER_LIST  = "WriterList";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.writer_main);

        getActionBar().setTitle(R.string.writers);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);


        listOfCat = new ArrayList<MakalCategory>();
        listOfCat_next = new ArrayList<MakalCategory>();
        dataSource = new DataBaseSource(getApplicationContext());
        dataSource.open();
        listOfCat = dataSource.getWriterList(TABLE_WRITER_LIST, allColumns_writer_list);
        dataSource.close();
        listOfCat_next.addAll(listOfCat);

        listView = (ListView) findViewById(R.id.listView);
        adapter = new AdapterWriterList(this, listOfCat_next);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), Writers.class);
                intent.putExtra("tag", listOfCat_next.get(position).getTag());
                intent.putExtra("name", listOfCat_next.get(position).getContent());
                intent.putExtra("pos", position);
//                Log.i("Bio", listOfCat_next.get(position).getBiography());
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
