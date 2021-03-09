package com.example.simpleloginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class viewFriend extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "MainActivity";

    private User userAccount = new User();
    public static LatLng friendLatLng = null;

    public static String friendUsername;

    private  EditText friendUsernameInput;
    private Button searchButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_friend);

        setUI();
        userAccount = LoginActivity.userAccount;

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                friendUsername = friendUsernameInput.getText().toString();
                getData();
            }
        });
    }

    private void setUI(){
            friendUsernameInput = (EditText)findViewById(R.id.txtFriendUsername);
            searchButton = (Button)findViewById(R.id.btnSearchFriend);
    }

    private void getData(){
        DocumentReference docRef = db.collection("Family ID").document(String.valueOf(userAccount.getFamilyID())).collection(friendUsername).document("Information");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "Document exists!");
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                        HashMap <String, Object> map = (HashMap<String, Object>) document.getData().get("latLng");
                        double lat = (double) map.get("latitude");
                        double lon = (double) map.get("longitude");

                        friendLatLng = new LatLng(lat, lon);

                        Toast.makeText(getApplicationContext(),"Friend Found!",Toast.LENGTH_SHORT).show();

                        findFriend();

                    } else {
                        Toast.makeText(getApplicationContext(),"No user with this username is in your family group.",Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Document does not exist!");
                    }
                } else {
                    Toast.makeText(getApplicationContext(),"Error! Please Try Again.",Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Failed with: ", task.getException());
                }
            }
        });
    }

    private void findFriend(){
        Intent intent = new Intent(this, friendMap.class);
        startActivity(intent);
    }
}