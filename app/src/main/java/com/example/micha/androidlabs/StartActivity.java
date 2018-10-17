package com.example.micha.androidlabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends Activity {
    Button buttonChat;//lab4
    protected static final String ACTIVITY_NAME="StartActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Log.i(ACTIVITY_NAME,"In onCreate()");

        final Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){
           public void onClick(View v){
               Intent intent = new Intent(StartActivity.this,ListItemsActivity.class);
               int REQUEST_CODE = 50;
               startActivityForResult(intent, REQUEST_CODE);
           }
        });

        /* lab4 */
        buttonChat = findViewById(R.id.chatButton);
        buttonChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(ACTIVITY_NAME,"User clicked Start Chat");
                Intent intent = new Intent(StartActivity.this,ChatWindow.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent data){
        if(responseCode == RESULT_OK && requestCode == 50){
            String messagePassed = data.getStringExtra("Response")+"Returned to StartActivity.onActivityResult";
            Log.i(ACTIVITY_NAME,"Returned to StartActivity.onActivityResult");
            Toast.makeText(this, messagePassed,Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME,"In onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME,"In onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME,"In onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME,"In onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME,"In onDestroy()");
    }
}
