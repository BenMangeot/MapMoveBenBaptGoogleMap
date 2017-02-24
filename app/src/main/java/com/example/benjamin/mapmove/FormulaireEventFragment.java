package com.example.benjamin.mapmove;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.benjamin.mapmove.Instance.Event;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.android.volley.VolleyLog.TAG;


public class FormulaireEventFragment extends Fragment {

    private EditText etNameEvent;
    private EditText etAdress;
    private Button bCreerEvent, bSelecImage;
    private DatabaseReference mDatabase;
    private StorageReference mStorage;
    ProgressBar progressBar;
    TextView infoText;
    private static final int GALLERY_INTENT = 2;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_formulaire_event, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("events");
        mStorage = FirebaseStorage.getInstance().getReference();

        etNameEvent = (EditText) view.findViewById(R.id.etNameEvent);
        etAdress = (EditText) view.findViewById(R.id.addressEdit);
        bCreerEvent = (Button) view.findViewById(R.id.bCreerEvent);
        bSelecImage = (Button) view.findViewById(R.id.bSelecImage);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        infoText = (TextView) view.findViewById(R.id.infoText);


        bSelecImage.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view) {
                                               Intent intent = new Intent (Intent.ACTION_PICK);

                                               intent.setType("image/*");
                                               startActivityForResult(intent, GALLERY_INTENT);

                                           }
                                       }

        );

        bCreerEvent.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view) {
                                               new CreateEvent().execute();
                                               Intent intent = new Intent(getActivity(), MapsActivity.class);
                                               Toast.makeText(getActivity(),"L'évenement a bien été crée !",Toast.LENGTH_SHORT).show();

                                               startActivity(intent);
                                           }
                                       }
        );

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }


    /** Classe qui nous permet de créer un nouvel evenement dans la base de données */
    class CreateEvent extends AsyncTask<Void, Void, Address> {

        String errorMessage = "";

        @Override
        protected void onPreExecute() {
            infoText.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Address doInBackground(Void ... none) {
            Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
            List<Address> addresses = null;

            String name =etAdress.getText().toString().trim();
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

                String nameEvent = etNameEvent.getText().toString();

                Event event = new Event(address.getLatitude(), address.getLongitude(), nameEvent);

                mDatabase.push().setValue(event);
            }
        }
    }
}
