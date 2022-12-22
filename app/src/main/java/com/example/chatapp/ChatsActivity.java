package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ChatsActivity extends AppCompatActivity {

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    ImageButton signOutBtn;
    ImageButton addChatBtn;

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private ImageView chatIcon;
    private TextView textChat;


    private RecyclerView mChatRecycler;
    private List<BaseChat> mChatList=new ArrayList<BaseChat>();
    private ChatsListAdapter mChatsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);


        signOutBtn = findViewById(R.id.signout);
        addChatBtn = findViewById(R.id.addchat);

//        BaseChat c2 = new BaseChat();
//        c2.text_chat = "Name_Chat";
//        mChatList.add(c2);


        mChatRecycler = (RecyclerView) findViewById(R.id.recycler_gchat2);
        mChatsAdapter = new ChatsListAdapter(this, mChatList);
        mChatRecycler.setLayoutManager(new LinearLayoutManager(this));
        mChatRecycler.setAdapter(mChatsAdapter);


        database = FirebaseDatabase.getInstance("https://chatapp-fa812-default-rtdb.europe-west1.firebasedatabase.app/");
        myRef = database.getReference("chats");
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String key = snapshot.getKey();
               String  value=snapshot.getValue().toString();
                byte[] decodedString = Base64.decode(value, Base64.URL_SAFE);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);


                BaseChat c1 = new BaseChat();
                c1.icon_chat = decodedByte;
                c1.text_chat = key;
                mChatList.add(c1);
                mChatsAdapter.notifyDataSetChanged();
            // chatRefresh();




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
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(ChatsActivity.this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(ChatsActivity.this);
        if(acct != null){
            UserInfo.user_name = acct.getDisplayName();
            String personEmail = acct.getEmail();

            Toast toast = Toast.makeText(ChatsActivity.this, "Добро пожаловать " +  UserInfo.user_name, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

        }

        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });

        addChatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChatsActivity.this, MainActivityAddChat.class);
                startActivity(intent);
            }
        });

    //



    }

    void chatRefresh (){
        mChatsAdapter = new ChatsListAdapter(this, mChatList);
        mChatRecycler.setLayoutManager(new LinearLayoutManager(this));
        mChatRecycler.setAdapter(mChatsAdapter);
    }
    void signOut(){
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                finish();
                startActivity(new Intent(ChatsActivity.this, MainActivity.class));
            }
        });
    }




}