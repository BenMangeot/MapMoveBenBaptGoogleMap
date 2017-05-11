package com.example.benjamin.mapmove;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.benjamin.mapmove.Instance.Event;
import com.example.benjamin.mapmove.Instance.User;
import com.example.benjamin.mapmove.MapsHelper.MyInfoWindowMarker;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class ResearchFriendActivity extends AppCompatActivity {

    String[] listUserPro;
    ArrayList<String> usersProSearch;
    ArrayAdapter<String> adapter;
    ListView listView;
    EditText editText;
    private DatabaseReference mDatabase;

    /* [START ON CREATE] */
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_research_friend);
        listView=(ListView)findViewById(R.id.listview);
        editText=(EditText)findViewById(R.id.txtsearch);

        initList();

        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    initList();
                }
                else{
                    // perform search
                    searchItem(s.toString());
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3)
            {
                String value = (String)adapter.getItemAtPosition(position);
                Toast.makeText(ResearchFriendActivity.this, value, Toast.LENGTH_LONG).show();
            }
        });
    }
    /* [END ON CREATE] */



    public void searchItem(String textToSearch){
        for(String userPro:listUserPro){
            if(!userPro.contains(textToSearch)){
                usersProSearch.remove(userPro);
            }
        }
        adapter.notifyDataSetChanged();
    }

    public void initList(){

        usersProSearch = new ArrayList<String>();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        adapter=new ArrayAdapter<String>(this, R.layout.list_item, R.id.txtitem, usersProSearch);
        listView.setAdapter(adapter);

       /* [INIT MISE EN PLACE DE LA LISTE DE USER] */
        mDatabase.child("users").addValueEventListener(new ValueEventListener() {

            public void onDataChange(DataSnapshot dataSnapshot){
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {

                    User userPro = postSnapshot.getValue(User.class);
                    usersProSearch.add(userPro.getUsername());
                    listUserPro = usersProSearch.toArray(new String[usersProSearch.size()]);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });



       /* [FIN MISE EN PLACE DE LA LISTE DE USER] */


    }


}