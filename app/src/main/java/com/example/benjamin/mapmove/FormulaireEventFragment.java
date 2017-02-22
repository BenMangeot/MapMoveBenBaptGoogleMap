package com.example.benjamin.mapmove;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.benjamin.mapmove.Instance.Event;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class FormulaireEventFragment extends Fragment {

    private EditText etNameEvent;
    private EditText etLatitude;
    private EditText etLongitude;
    private Button bCreerEvent;
    private DatabaseReference mDatabase;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_formulaire_event, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("events");


       etNameEvent = (EditText) view.findViewById(R.id.etNameEvent);
        etLatitude = (EditText) view.findViewById(R.id.etLatitude);
        etLongitude = (EditText) view.findViewById(R.id.etLongitude);
        bCreerEvent = (Button) view.findViewById(R.id.bCreerEvent);


        bCreerEvent.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view) {String nameEvent = etNameEvent.getText().toString();
                                               Double lat = Double.parseDouble(etLatitude.getText().toString());
                                               Double lg = Double.parseDouble(etLongitude.getText().toString());

                                               Event event = new Event(lat, lg, nameEvent);
                                               mDatabase.push().setValue(event);
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
}
