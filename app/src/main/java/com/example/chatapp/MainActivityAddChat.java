package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivityAddChat extends AppCompatActivity {
EditText t;
private FirebaseDatabase database;
private DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_add_chat);
        database = FirebaseDatabase.getInstance("https://chatapp-fa812-default-rtdb.europe-west1.firebasedatabase.app/");
        myRef = database.getReference("chats");
    t=findViewById(R.id.editTextTextPersonName3);
    }

    public void test(View view) {
        myRef.child(t.getText().toString()).setValue("Картинка");
    }
}