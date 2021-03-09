package com.example.simpleloginapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LoginActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "MainActivity";

    private String username;
    private String password;
    public static User userAccount = new User();

    private EditText usernameInput;
    private EditText passwordInput;
    private Button loginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setUI();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                hideKeyboard(v);
                username = usernameInput.getText().toString();
                password = passwordInput.getText().toString();
                getData();
            }
        });
    }

    private void setUI(){
        usernameInput = (EditText)findViewById(R.id.txtUsernameLogin);
        passwordInput = (EditText) findViewById(R.id.txtPassowordLogin);
        loginButton = (Button)findViewById(R.id.btnLogIn);
    }

    private void getData(){
        DocumentReference docRef = db.collection("Users").document(username);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "Document exists!");
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                        userAccount.setName((String)document.getData().get("name"));
                        userAccount.setUsername((String)document.getData().get("username"));
                        userAccount.setPassword((String)document.getData().get("password"));

                        checkLogin();

                    } else {
                        Toast.makeText(getApplicationContext(),"Incorrect Username.",Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Document does not exist!");
                    }
                } else {
                    Toast.makeText(getApplicationContext(),"Error! Please Try Again.",Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Failed with: ", task.getException());
                }
            }
        });
    }

    private void checkLogin(){
        if(password.equals(userAccount.getPassword())){
            Toast.makeText(getApplicationContext(),"You Have Successfully Logged In!",Toast.LENGTH_SHORT).show();
            submitActivity();
        }
        else{
            Toast.makeText(getApplicationContext(),"Incorrect Password.",Toast.LENGTH_SHORT).show();
        }
    }

    private void submitActivity(){
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}