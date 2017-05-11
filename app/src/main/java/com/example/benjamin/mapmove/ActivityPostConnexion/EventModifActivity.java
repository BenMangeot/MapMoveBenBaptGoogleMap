package com.example.benjamin.mapmove.ActivityPostConnexion;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.benjamin.mapmove.Instance.Event;
import com.example.benjamin.mapmove.ActivityPreConnexion.LoginActivity;
import com.example.benjamin.mapmove.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class EventModifActivity extends AppCompatActivity {
    private Button btnChangeTitle, btnChangeDescription, btnChangeHeureDebut, btnChangeHeureFin,
            btnChangeDate, btnRemove, changeTitle, changeDescription, changeHeureDebut, changeHeureFin,
            changeDate;

    private EditText newTitle, newDescription, newDate, newHeureFin, newHeureDebut;
    private ProgressBar progressBar;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private DatabaseReference mdatabase;
    private Event eventToModif;
    private String mKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_modif);

        /* [START init toolbar ] */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled (true);
        /* [END init toolbar ] */

        /*[ INIT FIREBASE ] */
        auth = FirebaseAuth.getInstance();
        mdatabase = FirebaseDatabase.getInstance().getReference();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        /*[ FIN INIT FIREBASE ] */

        /*[ RECUPERATION DE L'EVENT A MODIF] */
        eventToModif = (Event) getIntent().getExtras().getSerializable("my_event");
        /*[ FIN RECUPERATION DE L'EVENT A MODIF] */

        getKeyEvent(eventToModif);




        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    startActivity(new Intent(EventModifActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };

        /*[ INIT XML FIND VIEW BY ID ] */
        changeTitle = (Button) findViewById(R.id.changeTitle);
        changeDescription = (Button) findViewById(R.id.changeDescription);
        changeDate = (Button) findViewById(R.id.changeDate);
        changeHeureDebut = (Button) findViewById(R.id.changeHeureDebut);
        changeHeureFin = (Button) findViewById(R.id.changeHeureFin);

        btnChangeTitle = (Button) findViewById(R.id.btn_change_title);
        btnChangeDescription = (Button) findViewById(R.id.btn_change_descriptionn);
        btnChangeDate = (Button) findViewById(R.id.btn_change_date);
        btnChangeHeureDebut = (Button) findViewById(R.id.btn_change_heure_debut);
        btnChangeHeureFin = (Button) findViewById(R.id.btn_change_heure_fin);
        btnRemove = (Button) findViewById(R.id.btn_remove_event);



        newTitle = (EditText) findViewById(R.id.new_title);
        newDescription = (EditText) findViewById(R.id.new_description);
        newDate = (EditText) findViewById(R.id.new_date);
        newHeureDebut = (EditText) findViewById(R.id.new_heure_debut);
        newHeureFin = (EditText) findViewById(R.id.new_heure_fin);

        changeTitle.setVisibility(View.GONE);
        changeDescription.setVisibility(View.GONE);
        changeDate.setVisibility(View.GONE);
        changeHeureDebut.setVisibility(View.GONE);
        changeHeureFin.setVisibility(View.GONE);

        newTitle.setVisibility(View.GONE);
        newDescription.setVisibility(View.GONE);
        newDate.setVisibility(View.GONE);
        newHeureDebut.setVisibility(View.GONE);
        newHeureFin.setVisibility(View.GONE);



        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
        /*[ FIN XML FIND VIEW BY ID ] */


        btnChangeTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTitle.setVisibility(View.VISIBLE);
                changeDescription.setVisibility(View.GONE);
                changeDate.setVisibility(View.GONE);
                changeHeureDebut.setVisibility(View.GONE);
                changeHeureFin.setVisibility(View.GONE);

                newTitle.setVisibility(View.VISIBLE);
                newDescription.setVisibility(View.GONE);
                newDate.setVisibility(View.GONE);
                newHeureDebut.setVisibility(View.GONE);
                newHeureFin.setVisibility(View.GONE);

            }
        });

        btnChangeDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTitle.setVisibility(View.GONE);
                changeDescription.setVisibility(View.VISIBLE);
                changeDate.setVisibility(View.GONE);
                changeHeureDebut.setVisibility(View.GONE);
                changeHeureFin.setVisibility(View.GONE);

                newTitle.setVisibility(View.GONE);
                newDescription.setVisibility(View.VISIBLE);
                newDate.setVisibility(View.GONE);
                newHeureDebut.setVisibility(View.GONE);
                newHeureFin.setVisibility(View.GONE);

            }
        });

        btnChangeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTitle.setVisibility(View.GONE);
                changeDescription.setVisibility(View.GONE);
                changeDate.setVisibility(View.VISIBLE);
                changeHeureDebut.setVisibility(View.GONE);
                changeHeureFin.setVisibility(View.GONE);

                newTitle.setVisibility(View.GONE);
                newDescription.setVisibility(View.GONE);
                newDate.setVisibility(View.VISIBLE);
                newHeureDebut.setVisibility(View.GONE);
                newHeureFin.setVisibility(View.GONE);

            }
        });

        btnChangeHeureDebut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTitle.setVisibility(View.GONE);
                changeDescription.setVisibility(View.GONE);
                changeDate.setVisibility(View.GONE);
                changeHeureDebut.setVisibility(View.VISIBLE);
                changeHeureFin.setVisibility(View.GONE);

                newTitle.setVisibility(View.GONE);
                newDescription.setVisibility(View.GONE);
                newDate.setVisibility(View.GONE);
                newHeureDebut.setVisibility(View.VISIBLE);
                newHeureFin.setVisibility(View.GONE);
            }
        });

        btnChangeHeureFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTitle.setVisibility(View.GONE);
                changeDescription.setVisibility(View.GONE);
                changeDate.setVisibility(View.GONE);
                changeHeureDebut.setVisibility(View.GONE);
                changeHeureFin.setVisibility(View.VISIBLE);

                newTitle.setVisibility(View.GONE);
                newDescription.setVisibility(View.GONE);
                newDate.setVisibility(View.GONE);
                newHeureDebut.setVisibility(View.GONE);
                newHeureFin.setVisibility(View.VISIBLE);

            }
        });

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                  }
        });

        changeTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                eventToModif.setNameEvent(newTitle.getText().toString().trim());
                if (eventToModif != null && !newTitle.getText().toString().trim().equals("")) {
                    Toast.makeText(EventModifActivity.this, "Le titre de l'événement a bien été modifié.", Toast.LENGTH_SHORT).show();
                    updateEvent(eventToModif);
                } else if (newTitle.getText().toString().trim().equals("")) {
                    newTitle.setError("Entrez un titre");
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        changeDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                eventToModif.setDescriptionEvent(newDescription.getText().toString().trim());
                if (eventToModif != null && !newDescription.getText().toString().trim().equals("")) {
                    Toast.makeText(EventModifActivity.this, "La Description de l'événement a bien été modifié.", Toast.LENGTH_SHORT).show();
                    updateEvent(eventToModif);
                } else if (newDescription.getText().toString().trim().equals("")) {
                    newDescription.setError("Entrez une description");
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        changeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                eventToModif.setDate(newDate.getText().toString().trim());

                if (eventToModif != null && !newDate.getText().toString().trim().equals("")) {
                    Toast.makeText(EventModifActivity.this, "La Date de l'événement a bien été modifié.", Toast.LENGTH_SHORT).show();
                    updateEvent(eventToModif);
                } else if (newDate.getText().toString().trim().equals("")) {
                    newDescription.setError("Entrez une date");
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        changeHeureDebut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                eventToModif.setDebut(newHeureDebut.getText().toString().trim());

                if (eventToModif != null && !newHeureDebut.getText().toString().trim().equals("")) {
                    Toast.makeText(EventModifActivity.this, "L'heure de début a bien été modifié.", Toast.LENGTH_SHORT).show();
                    updateEvent(eventToModif);
                } else if (newHeureDebut.getText().toString().trim().equals("")) {
                    newDescription.setError("Entrez une heure");
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        changeHeureFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                eventToModif.setFin(newHeureFin.getText().toString().trim());

                if (eventToModif != null && !newHeureFin.getText().toString().trim().equals("")) {
                    Toast.makeText(EventModifActivity.this, "L'heure de fin a bien été modifié.", Toast.LENGTH_SHORT).show();
                    updateEvent(eventToModif);
                } else if (newHeureFin.getText().toString().trim().equals("")) {
                    newDescription.setError("Entrez une heure");
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

    }
    /*[ FIN ON CREATE ] */


    private void getKeyEvent(Event event) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query applesQuery = ref.child("events").orderByChild("nameEvent").equalTo(event.getNameEvent());

        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                    mKey = appleSnapshot.getRef().getKey();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void updateEvent(Event event) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("events").child(mKey).setValue(event);
        startActivity(new Intent(EventModifActivity.this, MainActivity.class));
        finish();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
