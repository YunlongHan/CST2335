package com.example.micha.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class ChatWindow extends Activity {

    ListView chatView;
    Button buttonChat;
    EditText editText;
    ArrayList<String> message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        /* lab4 */
        chatView = findViewById(R.id.chatView);
        buttonChat = findViewById(R.id.sendButton);
        editText = findViewById(R.id.chatBox);
        message = new ArrayList<>();

        final ChatAdapter messageAdapter = new ChatAdapter(this);//this == chatWindow
        chatView.setAdapter(messageAdapter);

        buttonChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = editText.getText().toString();
                message.add(data);
                messageAdapter.notifyDataSetChanged();
                editText.setText("");
            }
        });
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
        public String getItem(int position) {
            return message.get(position);
        }

        @Override
        public long getItemId(int position){
            return super.getItemId(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null;
            if(position%2 == 1)
                result = inflater.inflate(R.layout.chat_row_incoming,null);
            else
                result = inflater.inflate(R.layout.chat_row_outgoing,null);

            TextView message = result.findViewById(R.id.message);
            message.setText(getItem(position));
            return result;
        }

    }
}