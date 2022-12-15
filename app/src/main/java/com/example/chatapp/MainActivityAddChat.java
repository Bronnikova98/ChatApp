package com.example.chatapp;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class MainActivityAddChat extends AppCompatActivity {
    EditText t;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private ImageButton icon;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_add_chat);
        icon = findViewById(R.id.imageViewIcon);
        database = FirebaseDatabase.getInstance("https://chatapp-fa812-default-rtdb.europe-west1.firebasedatabase.app/");
        myRef = database.getReference("chats");
        t = findViewById(R.id.editTextTextPersonName3);

        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                mStartForResult.launch(Intent.createChooser(i, "Select Picture"));


            }
        });
    }

    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(  new ActivityResultContracts
            .StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    if(result.getResultCode() == Activity.RESULT_OK){
                        assert result.getData() != null;
                        Uri selectedImageUri = result.getData().getData();


                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(
                                    getApplicationContext().getContentResolver(),
                                    selectedImageUri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        icon.setImageBitmap(bitmap);
                    }
                }
            });

    public void test(View view) {

        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bao); // bmp is bitmap from user image file

        byte[] byteArray = bao.toByteArray();
        String imageB64 = Base64.encodeToString(byteArray, Base64.URL_SAFE);
        myRef.child(t.getText().toString()).setValue(imageB64);
        this.finish();
    }


}