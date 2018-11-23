package com.example.micha.androidlabs;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.micha.androidlabs.DataManager.ChatDatabaseHelper;

import java.util.ArrayList;

import static com.example.micha.androidlabs.LoginActivity.ACTIVITY_NAME;

public class ChatWindow extends Activity {
    protected SQLiteDatabase db;
    protected Cursor cursor;
    protected boolean FrameExists;

    private String chatWindow = ChatWindow.class.getSimpleName();
    ListView chatView;
    Button buttonChat;
    EditText editText;
    ArrayList<String> message;
    //lab5
    ChatDatabaseHelper dbManager;

    ChatAdapter messageAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        //lab7
        FrameExists = (boolean)(findViewById(R.id.frame)!=null);

        /* lab4 */
        chatView = findViewById(R.id.chatView);
        buttonChat = findViewById(R.id.sendButton);
        editText = findViewById(R.id.chatBox);
        message = new ArrayList<>();

        messageAdapter = new ChatAdapter(this);//this == chatWindow
        chatView.setAdapter(messageAdapter);

        //Lab5: Reading from a database file (step 5, 6)
        dbManager = new ChatDatabaseHelper(this);
        db = dbManager.getWritableDatabase();

        //Writting to a database file (Step 7, 8)
        buttonChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = editText.getText().toString();
                message.add(data);
           //     messageAdapter.notifyDataSetChanged();


                //Insert the new message into the database,
                // contentValues object will put the new message
                ContentValues contentValues = new ContentValues();
                contentValues.put(ChatDatabaseHelper.KEY_MESSAGE, editText.getText().toString());


//                long insertCheck =
                 db.insert(ChatDatabaseHelper.TABLE_NAME, null, contentValues);
//                Log.i("StartChat", "insert data result: "+insertCheck);
                cursor = db.rawQuery("SELECT * FROM "+ChatDatabaseHelper.TABLE_NAME+";", null);
                cursor.moveToFirst();

//                messageAdapter.notifyDataSetChanged();

                editText.setText("");
            }
        });

        chatView.setOnItemClickListener((adapterView, view, position, id)->{
            String msg =messageAdapter.getItem(position);
            long ID = id;
            Long id_inChat = messageAdapter.getID(position);

            MessageFragment messageFragment = new MessageFragment();
            Bundle infoToPass = new Bundle();
            infoToPass.putString("Message", msg);
            infoToPass.putLong("ID", ID);
            infoToPass.putLong("IDInChat", id_inChat);

            if(FrameExists)
            {
                messageFragment.setArguments( infoToPass );
                messageFragment.setIsOnTablet(true);
                getFragmentManager().beginTransaction().replace(R.id.frame,messageFragment).commit();
            }
            else //this is a phone:
            {
                messageFragment.setIsOnTablet(false);
                Intent next = new Intent(ChatWindow.this, MessageDetails.class);
                next.putExtra("ChatItem", infoToPass);
                startActivityForResult(next, 1, infoToPass);
            }
        });
    }

    private void showSQL(){
        cursor = db.query(ChatDatabaseHelper.TABLE_NAME, null,null,null,null,null,null );
        if(cursor != null){
            while(cursor.moveToNext()){
                final String chat = cursor.getString( cursor.getColumnIndex( ChatDatabaseHelper.KEY_MESSAGE) );
                Log.i(chatWindow, "SQL MESSAGE:" + chat );

                message.add(chat);
            }
            Log.i(chatWindow, "Cursor's Column Count "+cursor.getColumnCount());
        }

        for(int i=0; i<cursor.getColumnCount(); i++){
            Log.i(chatWindow, "The "+i+" row is "+cursor.getColumnName(i));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode==1 && requestCode==Activity.RESULT_OK){
            Bundle extras = data.getExtras();
            long id = extras.getLong("DeleteID");
            long id_inChat = extras.getLong("IDInChat");
            db.delete(ChatDatabaseHelper.TABLE_NAME, ChatDatabaseHelper.KEY_ID+" = ?", new String[]{Long.toString(id)});
            message.remove((int)id_inChat);
            cursor =db.rawQuery("SELECT * FROM "+ChatDatabaseHelper.TABLE_NAME+";", null);
            cursor.moveToFirst();
            messageAdapter.notifyDataSetChanged();
        }
    }

    public void deleteMessage(long idInDatabase, long idInchat){
        long id = idInDatabase;
        long id_inChat = idInchat;
        db.delete(ChatDatabaseHelper.TABLE_NAME, ChatDatabaseHelper.KEY_ID+" = ?", new String[]{Long.toString(id)});
        message.remove((int)id_inChat);
        cursor = db.rawQuery("SELECT * FROM "+ChatDatabaseHelper.TABLE_NAME+";", null);
        cursor.moveToFirst();
        messageAdapter.notifyDataSetChanged();
    }

//    @Override
//    protected void onStart(){
//        super.onStart();
//        dbManager.onOpen(db);
//        showSQL();
//        Log.i(chatWindow, "In onStart");
//    }

    //Implement the onDestroy() function
    @Override
    protected void onDestroy(){
        super.onDestroy();
        dbManager.close();
    }

    private class ChatAdapter extends ArrayAdapter<String> {
        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }

        @Override
        public int getCount() {
            return message.size();
        }

        @Override
        public long getItemId(int position){
            cursor.moveToPosition(position);
            return cursor.getLong(cursor.getColumnIndex("ID"));
        }
        @Override
        public String getItem(int position) {
            return message.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null;
//            if(position%2 == 1)
            if(position%2 == 0)
                result = inflater.inflate(R.layout.chat_row_incoming,null);
            else
                result = inflater.inflate(R.layout.chat_row_outgoing,null);

            TextView message = result.findViewById(R.id.message);
            message.setText(getItem(position));
            return result;
        }

        public long getID(int position){
            return position;
        }
    }
}