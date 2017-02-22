package com.example.benjamin.mapmove;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class FormulaireEventActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private EditText etNameEvent;
    private EditText etLatitude;
    private EditText etLongitude;
    private Button bCreerEvent;
    private FirebaseAuth auth;
    private DatabaseReference mDatabase;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulaire_event);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_map) {
            // Handle the camera action
        } else if (id == R.id.nav_account) {


        } else if (id == R.id.nav_logout) {
            Toast.makeText(this,"Log out",Toast.LENGTH_LONG).show();
            signOut();
        } else if (id == R.id.nav_manage) {
            startActivity(new Intent(this, SettingsActivity.class));
            finish();
        } else if (id == R.id.nav_creer_event) {
            startActivity(new Intent(this, FormulaireEventActivity.class));
            finish();
        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void signOut() {
        Intent intent = new Intent(FormulaireEventActivity.this, LoginActivity.class);
        startActivity(intent);
    }

}


