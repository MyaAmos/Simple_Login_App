package com.example.simpleloginapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class Activity2 extends AppCompatActivity {

    private String name;
    private String username;
    private String password;
    private int familyID;

    private User userAccount;
    private Friends friendProfile;

    EditText nameInput;
    EditText usernameInput;
    EditText passwordInput;
    EditText familyIDInput;
    TextView resultText;
    Button submitButton;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        createUI();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                hideKeyboard(v);
                if(checkInputs()){
                    name = nameInput.getText().toString();
                    username = usernameInput.getText().toString();
                    password = passwordInput.getText().toString();
                    familyID = Integer.parseInt(familyIDInput.getText().toString());

                    userAccount = new User(name, username, password, familyID);
                    friendProfile = new Friends(username);

                    isValid();

                }
                else{
                    resultText.setText("Please Complete All Fields");
                    Toast.makeText(Activity2.this, "Please Complete All Fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void createUI(){
        nameInput = (EditText)findViewById(R.id.txtName);
        usernameInput = (EditText)findViewById(R.id.txtUsername);
        passwordInput = (EditText)findViewById(R.id.txtPassword);
        familyIDInput = (EditText)findViewById(R.id.txtFamilyID);
        resultText = (TextView)findViewById(R.id.txtResult);
        submitButton = (Button)findViewById(R.id.btnSubmit);
    }

    private boolean checkInputs(){
        if(nameInput.getText().toString().length() > 0 && usernameInput.getText().toString().length() > 0 && passwordInput.getText().toString().length() > 0
            && familyIDInput.getText().toString().length() > 0){
            return true;
        }
        else{
            return false;
        }
    }

    private void signUp(){
        saveToCloud();
        Toast.makeText(getApplicationContext(),"Account Successfully Created!",Toast.LENGTH_SHORT).show();
        submitActivity();
    }

    private void isValid(){
        DocumentReference docRef = db.collection("Users").document(username);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Toast.makeText(getApplicationContext(),"Please Select A Different Username",Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Document exists!");
                    } else {
                        signUp();
                        Log.d(TAG, "Document does not exist!");
                    }
                } else {
                    Toast.makeText(getApplicationContext(),"Error! Please Try Again.",Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Failed with: ", task.getException());
                }
            }
        });
    }

    private void saveToCloud(){
        db.collection("Users").document(username)
                .set(userAccount)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });

        db.collection("Family ID").document(String.valueOf(userAccount.getFamilyID())).collection(userAccount.getUsername()).document("Information")
                .set(friendProfile)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }

    private void submitActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}