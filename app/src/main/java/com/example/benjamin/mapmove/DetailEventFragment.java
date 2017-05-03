package com.example.benjamin.mapmove;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.benjamin.mapmove.Instance.Event;
import com.example.benjamin.mapmove.Instance.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class DetailEventFragment extends Fragment {


    private static final String DESCRIBABLE_KEY = "describable_key";
    private Event event;
    private TextView tvNameEvent, tvDescription, tvCreator;
    private ImageView ivEvent;
    private User userPro;

    private DatabaseReference mDatabase;

    public static DetailEventFragment newInstance(Event event) {
        DetailEventFragment fragment = new DetailEventFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(DESCRIBABLE_KEY, event);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        event = (Event) getArguments().getSerializable(DESCRIBABLE_KEY);
        View view = inflater.inflate(R.layout.fragment_detail_event, container, false);
        tvNameEvent = (TextView) view.findViewById(R.id.tvTitreEvent);
        ivEvent =(ImageView) view.findViewById(R.id.ivEvent);
        tvNameEvent.setText(event.getNameEvent());
        tvDescription = (TextView) view.findViewById(R.id.tvDescription);
        tvDescription.setText(event.getDescriptionEvent());
        tvCreator = (TextView) view.findViewById(R.id.tvCreator);
        tvCreator.setText("coucou");
        Picasso.with(getActivity()).load(event.getUriEvent().toString()).into(ivEvent);
        System.out.println("push");

        return view;

    }


    public DetailEventFragment() {
        // Required empty public constructor
    }



}
