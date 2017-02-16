package com.example.benjamin.mapmove;

/**
 * Created by Benjamin on 16/02/2017.
 */

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.benjamin.mapmove.Instance.Event;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class CreateEventWithAddress extends AppCompatActivity {

    EditText addressEdit, etnameEvent;
    ProgressBar progressBar;
    TextView infoText;
    private DatabaseReference mDatabase;


    private static final String TAG = "MAIN_ACTIVITY_ASYNC";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event_adress);


        addressEdit = (EditText) findViewById(R.id.addressEdit);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        infoText = (TextView) findViewById(R.id.infoText);
        etnameEvent = (EditText) findViewById(R.id.nameEvent);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("events");
    }



    public void onButtonClicked(View view) {
        new GeocodeAsyncTask().execute();
    }

    class GeocodeAsyncTask extends AsyncTask<Void, Void, Address> {

        String errorMessage = "";

        @Override
        protected void onPreExecute() {
            infoText.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Address doInBackground(Void ... none) {
            Geocoder geocoder = new Geocoder(CreateEventWithAddress.this, Locale.getDefault());
            List<Address> addresses = null;

                String name =addressEdit.getText().toString().trim();
                try {
                    addresses = geocoder.getFromLocationName(name, 1);
                } catch (IOException e) {
                    errorMessage = "Service not available";
                    Log.e(TAG, errorMessage, e);
                }


            if(addresses != null && addresses.size() > 0)
                return addresses.get(0);

            return null;
        }

        protected void onPostExecute(Address address) {
            if(address == null) {
                progressBar.setVisibility(View.INVISIBLE);
                infoText.setVisibility(View.VISIBLE);
                infoText.setText(errorMessage);
            }
            else {
                String addressName = "";
                for(int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                    addressName += " --- " + address.getAddressLine(i);
                }
                progressBar.setVisibility(View.INVISIBLE);
                infoText.setVisibility(View.VISIBLE);
                infoText.setText("Latitude: " + address.getLatitude() + "\n" +
                        "Longitude: " + address.getLongitude() + "\n" +
                        "Address: " + addressName);

                String nameEvent = etnameEvent.getText().toString();

                Event event = new Event(address.getLatitude(), address.getLongitude(), nameEvent);

                mDatabase.push().setValue(event);
                startActivity(new Intent(CreateEventWithAddress.this, MapsActivity.class));
                finish();
            }
        }
    }
}
