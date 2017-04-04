package com.example.benjamin.mapmove;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.benjamin.mapmove.Instance.Post;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ListFragment extends Fragment {

    private static final String TAG = "PostListFragment";

    // [START define_database_reference]
    private DatabaseReference mDatabase;
    // [END define_database_reference]

    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;

    public ListFragment() {}



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        // [START create_database_reference]
        mDatabase = FirebaseDatabase.getInstance().getReference().child("events");
        // [END create_database_reference]

        mRecycler = (RecyclerView) rootView.findViewById(R.id.list);
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

        final FirebaseRecyclerAdapter<Post,ListViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Post, ListViewHolder>(
                Post.class,
                R.layout.list_row,
                ListViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(ListViewHolder viewHolder, Post model, int position) {

                viewHolder.setTitle(model.getNameEvent());
                viewHolder.setDesc(model.getDescriptionEvent());
                viewHolder.setAdress(model.getAdress());
                viewHolder.setImage(getContext(), model.getUriEvent());


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
            Picasso.with(ctx).load(image).into(list_image);

        }
    }
}
