package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class MainActivityChat extends AppCompatActivity {
    private RecyclerView mMessageRecycler;
    private MessageListAdapter mMessageAdapter;
    private List<BaseMessage> mMessageList=new ArrayList<BaseMessage>();
    private ImageButton back_to_chats;
    private ImageView icon_chat;
    private TextView name_chat;
    private ImageView btn_send;
    private EditText str_send;

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat);
        back_to_chats = findViewById(R.id.imageButtonBackChats);
        icon_chat = findViewById(R.id.imageView2);
        name_chat = findViewById(R.id.textView2);
        btn_send = findViewById(R.id.imageButton3);
        str_send = findViewById(R.id.editTextTextPersonName);

        icon_chat.setImageBitmap(UserInfo.chat.icon_chat);
        name_chat.setText(UserInfo.chat.text_chat);


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

        database = FirebaseDatabase.getInstance("https://chatapp-fa812-default-rtdb.europe-west1.firebasedatabase.app/");
        myRef = database.getReference("messages");
        myRef.orderByChild("chat").equalTo(UserInfo.chat.text_chat).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {


                    BaseMessage message =new BaseMessage();
                    message.message= snapshot.child("message").getValue().toString();
                    message.sender = snapshot.child("sender").getValue().toString();
                    message.chat = snapshot.child("chat").getValue().toString();
                    mMessageList.add(message);
                    mMessageAdapter.notifyDataSetChanged();

                //добавить пуш уведомления без сортировки, snapshot.child("message") добавлялось не в лист, а текст пуша (создать класс выше)




            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id_message = UUID.randomUUID().toString();
                myRef.child(id_message).child("message").setValue(str_send.getText().toString());
                myRef.child(id_message).child("sender").setValue(UserInfo.user_name);
                myRef.child(id_message).child("chat").setValue(UserInfo.chat.text_chat);
                str_send.setText("");
            }
        });
    }


}