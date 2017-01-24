package com.example.benjamin.mapmove;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ConnectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);

        final EditText etId =(EditText) findViewById(R.id.etId);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);

        final Button bConnection = (Button) findViewById(R.id.bConnection);

        final TextView registerLink = (TextView) findViewById(R.id.tvRegister);
        final TextView mapLink = (TextView) findViewById(R.id.tvMap);

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(ConnectionActivity.this, RegisterActivity.class);
                ConnectionActivity.this.startActivity(registerIntent);
            }
        });

        mapLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapIntent = new Intent(ConnectionActivity.this, RegisterActivity.class);
                ConnectionActivity.this.startActivity(mapIntent);
            }
        });
    }
}
