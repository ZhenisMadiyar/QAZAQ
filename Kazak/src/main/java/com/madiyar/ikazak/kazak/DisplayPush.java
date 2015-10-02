package com.madiyar.ikazak.kazak;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseAnalytics;

import org.json.JSONException;
import org.json.JSONObject;


public class DisplayPush extends Activity {
String jsonData;
@Override
public void onBackPressed(){
   Intent myIntent = new Intent(DisplayPush.this,MainMenu.class);
   startActivity(myIntent);
  //  overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
    finish();
}

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.notification);

    TextView notification_title = (TextView) findViewById(R.id.textViewTitle);
    TextView notification_message = (TextView) findViewById(R.id.textViewMessage);

    ParseAnalytics.trackAppOpened(getIntent());

    Intent intent = getIntent();
    Bundle extras = intent.getExtras();
    if(extras !=null)
      if(extras.containsKey("com.parse.Data"))
         jsonData = extras.getString("com.parse.Data");
    
    
    //Bundle extras = intent.getExtras();
    String jsonData = extras.getString("com.parse.Data");

    try{
        JSONObject notification = new JSONObject(jsonData);
       // String title = notification.getString("push_hash");
        String message = notification.getString("alert");

      //  notification_title.setText(title);
        notification_message.setText(message);
    }
    catch(JSONException e){
        Toast.makeText(getApplicationContext(), "Something went wrong with the notification", Toast.LENGTH_SHORT).show();
    }

}
}