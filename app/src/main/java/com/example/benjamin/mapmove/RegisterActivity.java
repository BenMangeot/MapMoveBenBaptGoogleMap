package com.example.benjamin.mapmove;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {


    private DatabaseReference mDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mDataBase  = FirebaseDatabase.getInstance().getReference();


        final EditText etPseudo =(EditText) findViewById(R.id.etPseudo);
        final EditText etMail =(EditText) findViewById(R.id.etMail);
        final EditText etPassword =(EditText) findViewById(R.id.etPassword);

        final Button bEnregistrer = (Button) findViewById(R.id.bEnregistrer);

        bEnregistrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String pseudo = etPseudo.getText().toString();
                final String mail = etMail.getText().toString();
                final String password = etPassword.getText().toString();




            }
        });
    }
}
