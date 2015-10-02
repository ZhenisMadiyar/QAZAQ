package com.example.server;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    private static String db_URL = "http://192.168.26.1/connect.php";
    TextView textView;
//    JSONParser jParser = new JSONParser();
//    ServiceHandler sha = new ServiceHandler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textview);
//        new Load().execute();
    }

//    class Load extends AsyncTask<String, String, String> {
//
//        @Override
//        protected String doInBackground(String... strings) {
//            String jsonResGroupStr = sha.makeServiceCall(db_URL, ServiceHandler.GET);
//            textView.setText(jsonResGroupStr);
//            return null;
//        }
//
//        protected void onPostExecute(String file_url) {
//
//        }
//    }

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
}
