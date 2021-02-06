package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    Button login;
    FirebaseDatabase database;
    DatabaseReference myRef;
    EditText etUsername,etPassword;
    Button btSubmit;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etUsername=findViewById(R.id.et_username );
        etPassword = findViewById(R.id.editText);
        checkBox = findViewById(R.id.checkbox);
        btSubmit=findViewById(R.id.login);
        myRef=FirebaseDatabase.getInstance().getReference();

        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "hello world1", Toast.LENGTH_SHORT);
                if (etUsername.getText() != null && !etUsername.getText().equals("") && etPassword.getText() != null && !etPassword.getText().equals("")) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(etPassword.toString());
                    String password = stringBuilder.reverse().toString();
                    if(!etUsername.getText().equals("")) {
                        myRef.addValueEventListener(new ValueEventListener() {

                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                try {
                                    if (snapshot.child("Users") != null) {
                                        if (snapshot.child("Users").hasChild(etUsername.getText().toString()) && !etUsername.getText().equals("")) {
                                            if (etPassword.getText().toString().equals(snapshot.child("Users").child(etUsername.getText().toString()).child("Pass").getValue().toString())) {
                                                Intent inte = new Intent(getBaseContext(), Home.class);
                                                startActivity(inte);
                                            }
                                            else{
                                                Toast.makeText(MainActivity.this, "Please Recheck the Password and try again", Toast.LENGTH_LONG).show();

                                            }
                                        }
                                    }
                                    else{
                                        Toast.makeText(MainActivity.this, "Please Recheck the Username and try again", Toast.LENGTH_LONG).show();

                                    }

                                }
                                catch(Exception e){
                                    Toast.makeText(MainActivity.this, "Entered Username or Password is incorrect", Toast.LENGTH_LONG).show();

                                }
                            }


                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                       });
                    }
                }
            }
        });

        //Handling Checkbox States-------------------->
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    etPassword.setTransformationMethod(null);
                } else {
                    etPassword.setTransformationMethod(new PasswordTransformationMethod());
                }
            }

        });

    }
}