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
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;


public class DetailEventFragment extends Fragment {


    private static final String DESCRIBABLE_KEY = "describable_key";
    private Event event;
    private TextView tvNameEvent;
    private ImageView ivEvent;

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

        Picasso.with(getActivity()).load(event.getUriEvent().toString()).into(ivEvent);
        System.out.println("push");

        return view;

    }


    public DetailEventFragment() {
        // Required empty public constructor
    }



}
