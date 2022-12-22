package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivityChat extends AppCompatActivity {
    private RecyclerView mMessageRecycler;
    private MessageListAdapter mMessageAdapter;
    private List<BaseMessage> mMessageList;
    private ImageButton back_to_chats;
    private ImageView icon_chat;
    private TextView name_chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat);
        back_to_chats = findViewById(R.id.imageButtonBackChats);
        icon_chat = findViewById(R.id.imageView2);
        name_chat = findViewById(R.id.textView2);

        icon_chat.setImageBitmap(UserInfo.chat.icon_chat);
        name_chat.setText(UserInfo.chat.text_chat);

        UserInfo.user_name = "Вика";
        mMessageList = new ArrayList<BaseMessage>();
        BaseMessage m1 = new BaseMessage();
        m1.message = "Сообщение";
        m1.sender = "Злата";
        mMessageList.add(m1);

        BaseMessage m2 = new BaseMessage();
        m2.message = "Мое сообщение";
        m2.sender = "Вика";
        mMessageList.add(m2);

        BaseMessage m3 = new BaseMessage();
        m3.message = "Сообщение";
        m3.sender = "Олег";
        mMessageList.add(m3);
        BaseMessage m4 = new BaseMessage();
        m4.message = "Сообщение";
        m4.sender = "Олег";
        mMessageList.add(m4);
        BaseMessage m5 = new BaseMessage();
        m5.message = "Сообщение";
        m5.sender = "Олег";
        for(int i=0;i<10;i++){
            mMessageList.add(m1);
        }
        mMessageList.add(m5);
        BaseMessage m6 = new BaseMessage();
        m6.message = "Сообщение СообщениеСообщениеСообщениеСообщениеСообщениеСообщениеСообщениеСообщениеСообщениеСообщениеСообщениеСообщениеСообщениеСообщениеСообщениеСообщениеСообщениеСообщениеСообщениеСообщениеСообщениеСообщениеСообщение";
        m6.sender = "Олег";
        mMessageList.add(m6);

        BaseMessage m7 = new BaseMessage();
        m7.message = "Мое сообщение 2";
        m7.sender = "Вика";
        mMessageList.add(m7);

        BaseMessage m8 = new BaseMessage();
        m8.message = "Мое сообщение 3";
        m8.sender = "Вика";
        mMessageList.add(m8);



        mMessageRecycler = (RecyclerView) findViewById(R.id.recycler_gchat);
        mMessageAdapter = new MessageListAdapter(this, mMessageList);
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));
        mMessageRecycler.setAdapter(mMessageAdapter);

        back_to_chats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


}