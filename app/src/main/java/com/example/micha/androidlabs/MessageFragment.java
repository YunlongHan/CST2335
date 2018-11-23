package com.example.micha.androidlabs;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MessageFragment extends Fragment {

    private boolean isOnTablet;
    private TextView messageView;
    private TextView idView;
    private Button delete;
    private Bundle bundle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstancesState){
        View view = inflater.inflate(R.layout.activity_message_fragment, container, false);
        messageView = (TextView)view.findViewById(R.id.chatView);
        idView = (TextView)view.findViewById(R.id.mid);
        delete = (Button)view.findViewById(R.id.deleteButton);

        bundle = getArguments();

        String message = bundle.getString("This is a Message");
        final long id = bundle.getLong("ID");
        final long id_inChat = bundle.getLong("IDInChat");

        messageView.setText(message);
        idView.setText(String.valueOf(id));

        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(isOnTablet){
                    ChatWindow chatWindow = (ChatWindow)getActivity();
                    chatWindow.deleteMessage(id, id_inChat);
                    getFragmentManager().beginTransaction().remove(MessageFragment.this).commit();
                }else{
                    Intent intent = new Intent();
                    intent.putExtra("DeleteID", id);
                    intent.putExtra("IDInChat", id_inChat);
                    getActivity().setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                }
            }
        });
        return view;
    }

    public void setIsOnTablet(boolean isOnTablet){
        this.isOnTablet = isOnTablet;
    }
}
