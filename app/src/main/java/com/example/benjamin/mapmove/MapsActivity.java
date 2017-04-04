package com.example.benjamin.mapmove;


import android.Manifest;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.benjamin.mapmove.Instance.Event;
import com.example.benjamin.mapmove.MapsHelper.MyInfoWindowMarker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener{


    // [Start decla]
    private GoogleMap mMap;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private MapFragment mMapFragment;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        //Set the toggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        /** Initialisation du fragment */
        setFragToMaps();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        /** Initialisation du set Text dans le menu */
        mDatabase.child("users").child(user.getUid()).child("username").addValueEventListener(new ValueEventListener() {

            public void onDataChange(DataSnapshot dataSnapshot){

                String name = dataSnapshot.getValue().toString();

                View v = navigationView.getHeaderView(0);
                TextView avatarContainer = (TextView ) v.findViewById(R.id.tvUsername);
                avatarContainer.setText(name);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mDatabase.child("events").addValueEventListener(new ValueEventListener() {

            public void onDataChange(DataSnapshot dataSnapshot){
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {

                    /** On crée un marqueur par event */
                    Event event = postSnapshot.getValue(Event.class);
                    LatLng posEvent =  new LatLng(event.getLat(), event.getLg());
                    Marker marker = mMap.addMarker(new MarkerOptions().position(posEvent).title(event.getNameEvent()));
                    marker.setTag(event);

                    mMap.setInfoWindowAdapter(new MyInfoWindowMarker(getLayoutInflater()));
                    mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {
                            marker.showInfoWindow();

                            return true;
                        }
                    });

                    mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

                        @Override
                        public void onInfoWindowClick(Marker marker) {
                            Event eventToDetail = (Event) marker.getTag();
                            Toast.makeText(MapsActivity.this, eventToDetail.getNameEvent(),
                                    Toast.LENGTH_LONG).show();
                            getFragmentManager().beginTransaction().remove(mMapFragment).commit();
                            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            Fragment fragment = DetailEventFragment.newInstance(eventToDetail);
                            ft.replace(R.id.fragment_container, fragment);
                            ft.commit();
                        }
                    });
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        LatLng posEvent =  new LatLng(50.629728, 3.043672);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posEvent, 10));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        mMap.setMyLocationEnabled(true);
    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_map) {
            setFragToMaps();
        } else if (id == R.id.nav_account) {
            Intent intent = new Intent(MapsActivity.this, ListEventsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
            Toast.makeText(this,"Log out",Toast.LENGTH_LONG).show();
            signOut();
        } else if (id == R.id.nav_manage) {
            setFragToSettings();
        } else if (id == R.id.nav_creer_event) {
            setFragToFormEvent();
        } else if (id == R.id.nav_send) {
            setFragToCompte();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void signOut() {
        mAuth.signOut();
        Intent intent = new Intent(MapsActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void setFragToMaps(){
        mMapFragment = MapFragment.newInstance();
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, mMapFragment);
        fragmentTransaction.commit();

        mMapFragment.getMapAsync(this);
    }

    private void setFragToCompte(){
        getFragmentManager().beginTransaction().remove(mMapFragment).commit();
        CompteFragment fragment = new CompteFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }
    
    private void setFragToFormEvent(){
        getFragmentManager().beginTransaction().remove(mMapFragment).commit();
        FormulaireEventFragment fragment = new FormulaireEventFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    private void setFragToSettings(){
        getFragmentManager().beginTransaction().remove(mMapFragment).commit();
        SettingsFragment fragment = new SettingsFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    public void onButtonClicked(View view) {
        Intent intent = new Intent(MapsActivity.this, MapsActivity.class);
        startActivity(intent);
    }
}


