package com.example.micha.androidlabs;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.support.design.widget.Snackbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TestToolbar extends AppCompatActivity {

    Button snackbarBtn;
    private String currentMessage = "You selected item 1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);

        Toolbar lab8_toolbar = (Toolbar)findViewById(R.id.lab8_toolbar);
        setSupportActionBar(  lab8_toolbar) ;

        snackbarBtn = (Button)findViewById(R.id.snackbarNotification);
        snackbarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Message to show", Snackbar.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu (Menu m){
        getMenuInflater().inflate(R.menu.toolbar_menu, m);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem mi){
        int id = mi.getItemId();
        switch (id){
            case R.id.action_one:
                Log.d("TestToolbar", "action 1 selected");
                Snackbar.make(findViewById(R.id.action_one), currentMessage, Snackbar.LENGTH_LONG).show();
                break;
            case R.id.action_two:
                AlertDialog.Builder builder = new AlertDialog.Builder(TestToolbar.this);
                builder.setTitle(R.string.dialog_title2);
                // Add the buttons
                builder.setPositiveButton(R.string.ok2, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        finish();
                    }
                });
                builder.setNegativeButton(R.string.cancel2, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
                // Create the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();
                break;

            case R.id.action_three:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(TestToolbar.this);
                final View view = this.getLayoutInflater().inflate(R.layout.dialog_item3,null);
                builder1.setView(view);
                builder1.setPositiveButton(R.string.defaultMsg, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText message = (EditText) view.findViewById(R.id.newMessage);
                        currentMessage = message.getText().toString();
                    }
                });
                builder1.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
                AlertDialog dialog1 = builder1.create();
                dialog1.show();
                break;
            case R.id.action_four:
                Toast.makeText(this,"version 1.0, by Yunlong Han", Toast.LENGTH_LONG)
                        .show();
                break;
        }
        return true;
    }
}
