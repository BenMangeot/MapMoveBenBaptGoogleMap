package com.example.benjamin.mapmove.ActivityPostConnexion;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.benjamin.mapmove.Instance.Event;
import com.example.benjamin.mapmove.R;
import com.squareup.picasso.Picasso;

public class EventActivity extends AppCompatActivity {


    private static final String DESCRIBABLE_KEY = "describable_key";
    private Event mEvent;
    private TextView tvNameEvent, tvDescription, tvCreator, hdebut, hfin, tvAdress, date;
    private ImageView ivEvent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        mEvent = (Event) getIntent().getExtras().getSerializable("my_event");

        /*[ INIT TOOLBAR ] */
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled (true);
        /*[FIN DE TOOLBAR]*/

        tvNameEvent = (TextView) findViewById(R.id.tvTitreEvent);
        ivEvent =(ImageView) findViewById(R.id.ivEvent);
        tvNameEvent.setText(mEvent.getNameEvent());
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        tvDescription.setText(mEvent.getDescriptionEvent());
        tvAdress = (TextView) findViewById(R.id.tvAddress);
        date = (TextView) findViewById(R.id.date);

        String str_text = "<a href=http://maps.google.com/maps?q="+mEvent.getLat()+","+mEvent.getLg()+">" + mEvent.getAdress() +"</a>";
        tvAdress.setMovementMethod(LinkMovementMethod.getInstance());
        tvAdress.setText(Html.fromHtml(str_text));

        tvCreator = (TextView) findViewById(R.id.tvCreatore);
        tvCreator.setText("Cet événement a été créé par "+mEvent.getUserPro());
        hdebut = (TextView) findViewById(R.id.debut);
        hdebut.setText("débute à : " + mEvent.getDebut());
        hfin = (TextView) findViewById(R.id.fin);
        hfin.setText("fini à : " + mEvent.getFin());
        date.setText("l'évènement aura lieu le : " + mEvent.getDate());


        Picasso.with(this).load(mEvent.getUriEvent().toString()).fit().centerCrop().into(ivEvent);

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
