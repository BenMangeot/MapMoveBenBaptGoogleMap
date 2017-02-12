package com.example.benjamin.mapmove.Instance;

import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.android.volley.VolleyLog.TAG;

/**
 * Created by LestavelBaptiste on 10/02/2017.
 */

public class BddService {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    List<Event> events = new ArrayList<>();




    private static BddService instance;

    public static BddService getInstance() {
        if (instance == null) {
            instance = new BddService();
        }
        return instance;
    }

    private BddService() {
    }

    public List<Event> getAllEvents(){

        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();



        mDatabase.child("events").addValueEventListener(new ValueEventListener() {

            public void onDataChange(DataSnapshot dataSnapshot){

                Log.e("Count " ,""+dataSnapshot.getChildrenCount());
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Event event = postSnapshot.getValue(Event.class);
                    System.out.println("dans le for "+event.getLat());
                    events.add(event);
                }

                System.out.println("On data change "+events.size());


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        /** Event event1 = new Event (50.629728,3.043672);
        Event event2 = new Event (50.632989,3.042298);
        events.add(event1);
        events.add(event2); */

        return events;
    }

}
