package com.example.benjamin.mapmove.FragmentPrincipal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.benjamin.mapmove.ActivityPostConnexion.EventModifActivity;
import com.example.benjamin.mapmove.Instance.Event;
import com.example.benjamin.mapmove.Instance.User;
import com.example.benjamin.mapmove.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class GererEventFragment extends Fragment {

    private static final String TAG = "PostListFragment";

    // [START define_database_reference]
    private DatabaseReference mDatabase;
    // [END define_database_reference]
    private User thisUserPro;

    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;
    private Activity mActivity;

    public GererEventFragment() {}



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        mActivity = getActivity();

        View rootView = inflater.inflate(R.layout.fragment_gerer_event, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();


        /* RECUPERATION DU USER CO */
        mDatabase.child("users").child(fbUser.getUid()).addValueEventListener(new ValueEventListener() {

            public void onDataChange(DataSnapshot dataSnapshot){

                thisUserPro = dataSnapshot.getValue(User.class);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        /* --FIN--RECUPERATION DU USER CO */



        mRecycler = (RecyclerView) rootView.findViewById(R.id.listgerer);
        mRecycler.setHasFixedSize(true);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Set up Layout Manager, reverse layout
        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);
    }


    @Override
    public void onStart() {
        super.onStart();

        final FirebaseRecyclerAdapter<Event,GererEventFragment.ListViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Event, GererEventFragment.ListViewHolder>(
                Event.class,
                R.layout.list_row_gerer,
                GererEventFragment.ListViewHolder.class,
                mDatabase.child("events")
        ) {
            @Override
            protected void populateViewHolder(GererEventFragment.ListViewHolder viewHolder, Event mEvent, int position) {

                System.out.println(mEvent.getUserPro());
                System.out.println(thisUserPro.getUsername());

                if(mEvent.getUserPro().equals(thisUserPro.getUsername())){
                    viewHolder.setTitle(mEvent.getNameEvent());
                    viewHolder.setDesc(mEvent.getDescriptionEvent());
                    viewHolder.setAdress(mEvent.getAdress());
                    viewHolder.setImage(getContext(), mEvent.getUriEvent());
                    viewHolder.setDebut(mEvent.getDebut());
                    viewHolder.setFin(mEvent.getFin());
                    viewHolder.setModifier(mEvent);
                    viewHolder.setSupprimer(mEvent);
                }else{
                    viewHolder.rien();
                }

            }
        };

        mRecycler.setAdapter(firebaseRecyclerAdapter);
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public ListViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setTitle(String title){
            TextView list_title = (TextView) mView.findViewById(R.id.nameEvent);
            list_title.setText(title);
        }

        public void setDesc(String desc){
            TextView list_desc = (TextView) mView.findViewById(R.id.descriptionEvent);
            list_desc.setText(desc);
        }

        public void setAdress(String address){
            TextView list_address = (TextView) mView.findViewById(R.id.adress);
            list_address.setText(address);
        }

        public void setImage(Context ctx,String image){
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

        public void setModifier(final Event eventToModif){
            final TextView modifier = (TextView) mView.findViewById(R.id.tvModifier);
            modifier.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mView.getContext(), EventModifActivity.class);
                    intent.putExtra("my_event", eventToModif);
                    mView.getContext().startActivity(intent);

                }
            });
        }

        public void setSupprimer(final Event mEvent){

            final TextView supprimer = (TextView) mView.findViewById(R.id.tvSupprimer);
            supprimer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                        Query applesQuery = ref.child("events").orderByChild("nameEvent").equalTo(mEvent.getNameEvent());

                        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                    appleSnapshot.getRef().removeValue();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.e(TAG, "onCancelled", databaseError.toException());
                            }
                        });
                }
            });
        }

        public void rien(){
            mView.setVisibility(View.GONE);
            RecyclerView.LayoutParams param = (RecyclerView.LayoutParams)itemView.getLayoutParams();
            param.height = 0;
            param.width = 0;
        }
    }
}

