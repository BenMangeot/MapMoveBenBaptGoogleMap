package com.example.benjamin.mapmove;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.benjamin.mapmove.Instance.Event;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

public class DetailEventsUserProActivity extends AppCompatActivity {


    private static final String TAG = "PostListFragment";

    // [START define_database_reference]
    private DatabaseReference mDatabase;
    // [END define_database_reference]

    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;
    private static Context mContext;
    String userProName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_events_user_pro);

        userProName = (String) getIntent().getExtras().getSerializable("my_user");

         /* [START init toolbar ] */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Evenements à venir de "+userProName);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled (true);
        /* [END init toolbar ] */

        /* [END init toolbar ] */

        // [START create_database_reference]
        mDatabase = FirebaseDatabase.getInstance().getReference().child("events");
        // [END create_database_reference]

        mRecycler = (RecyclerView) findViewById(R.id.list);
        mRecycler.setHasFixedSize(true);

        // Set up Layout Manager, reverse layout
        mManager = new LinearLayoutManager(this);
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);


    }



    @Override
    public void onStart() {
        super.onStart();

        final FirebaseRecyclerAdapter<Event,DetailEventsUserProActivity.ListViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Event, DetailEventsUserProActivity.ListViewHolder>(
                Event.class,
                R.layout.list_row,
                DetailEventsUserProActivity.ListViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(DetailEventsUserProActivity.ListViewHolder viewHolder, Event model, int position) {

                long cdate = System.currentTimeMillis();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                String dataString = sdf.format(cdate);
                dataString = dataString.intern();

                String date = model.getDate().intern();
                if (date == dataString && model.getUserPro()== userProName){
                    viewHolder.setTitle(model.getNameEvent());
                    viewHolder.setAdress(model.getAdress());
                    viewHolder.setDebut(model.getDebut());
                    viewHolder.setImage(getApplicationContext(), model.getUriEvent());
                    viewHolder.setFin(model.getFin());
                    viewHolder.setOrga(model.getUserPro());
                    viewHolder.setClickToDetail(model);

                }else { viewHolder.rien();
                }
            }
        };

        mRecycler.setAdapter(firebaseRecyclerAdapter);
    }

    private static class ListViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public ListViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setTitle(String title){
            TextView list_title = (TextView) mView.findViewById(R.id.nameEvent);
            list_title.setText(title);
        }

        public void setAdress(String address){
            TextView list_address = (TextView) mView.findViewById(R.id.adress);
            list_address.setText(address);
        }

        public void setImage(Context ctx, String image){
            ImageView list_image = (ImageView) mView.findViewById(R.id.uriEvent);
            Picasso.with(ctx).load(image).fit().centerCrop().into(list_image);

        }

        public void setDebut(String sDebut){
            TextView list_debut = (TextView) mView.findViewById(R.id.debut);
            list_debut.setText("Débute à : " + sDebut);
        }

        public void setFin(String sFin){
            TextView list_address = (TextView) mView.findViewById(R.id.fin);
            list_address.setText("Fini à : " + sFin);
        }

        public void setOrga(String orga){
            TextView list_orga = (TextView) mView.findViewById(R.id.nomOrga);
            list_orga.setText("Evenement créé par : " + orga);

        }

        public void rien(){
            mView.setVisibility(View.GONE);
            RecyclerView.LayoutParams param = (RecyclerView.LayoutParams)itemView.getLayoutParams();
            param.height = 0;
            param.width = 0;
        }

        public void setClickToDetail(final Event eventToDetail) {
            mView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(mView.getContext(), EventActivity.class);
                            intent.putExtra("my_event", eventToDetail);
                            mView.getContext().startActivity(intent);
                        }
                    }

            );
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
