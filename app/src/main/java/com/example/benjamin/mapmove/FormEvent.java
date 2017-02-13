package com.example.benjamin.mapmove;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.benjamin.mapmove.Instance.Event;
import com.example.benjamin.mapmove.Instance.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class FormEvent extends AppCompatActivity {

    private EditText etNameEvent;
    private EditText etLatitude;
    private EditText etLongitude;
    private Button bCreerEvent;
    private FirebaseAuth auth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_event);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("events");
        auth = FirebaseAuth.getInstance();


        etNameEvent = (EditText) findViewById(R.id.etNameEvent);
        etLatitude = (EditText) findViewById(R.id.etLatitude);
        etLongitude = (EditText) findViewById(R.id.etLongitude);
        bCreerEvent = (Button) findViewById(R.id.bCreerEvent);
        

        bCreerEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameEvent = etNameEvent.getText().toString();
                Double lat = Double.parseDouble(etLatitude.getText().toString());
                Double lg = Double.parseDouble(etLongitude.getText().toString());

                Event event = new Event(lat, lg, nameEvent);

                mDatabase.push().setValue(event);
                startActivity(new Intent(FormEvent.this, MapsActivity.class));
                finish();
            }
            }
        );

    }

    private void createNewUser(FirebaseUser userFromRegistration) {



    }
}
