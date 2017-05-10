package com.example.benjamin.mapmove;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.benjamin.mapmove.Instance.Event;
import com.example.benjamin.mapmove.Instance.User;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;
import static com.android.volley.VolleyLog.TAG;


public class FormulaireEventFragment extends Fragment {

    private EditText etNameEvent, etAddress, etDescription;
    private ImageButton ibSelectImage;
    private Button bCreerEvent;
    private DatabaseReference mDatabase;
    private StorageReference mStorage;
    private Uri uriEvent =  null;
    ProgressBar progressBar;
    private MapFragment mMapFragment;
    private android.support.v4.app.FragmentTransaction fragmentTransaction;
    private static final int GALLERY_INTENT = 2;
    Uri imageUri = null;
    private ProgressDialog mProgressDialog;
    private User mUser;

    private static RadioGroup radioGroup;
    private static RadioButton radioButton;
    private EditText dateEvent;
    private EditText debutEvent;
    private EditText finEvent;
    public String addressName = "";
    public Double lat = 0.0;
    public Double longi = 0.0;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_formulaire_event, container, false);

        mStorage = FirebaseStorage.getInstance().getReference();

        etNameEvent = (EditText) view.findViewById(R.id.etNameEvent);
        etDescription = (EditText) view.findViewById(R.id.etDescription);
        etAddress = (EditText) view.findViewById(R.id.addressEdit);
        bCreerEvent = (Button) view.findViewById(R.id.bCreerEvent);
        ibSelectImage = (ImageButton) view.findViewById(R.id.ibSelectImage);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mProgressDialog = new ProgressDialog(getActivity());
        radioGroup = (RadioGroup) view.findViewById(R.id.rg);
        dateEvent = (EditText) view.findViewById(R.id.date);
        debutEvent = (EditText) view.findViewById(R.id.heureDebut) ;
        finEvent = (EditText) view.findViewById(R.id.heureFin);


        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();

        /* MODIFICATION DE L'INTERFACE A PARTIR DE LA BDD */
        mDatabase.child("users").child(fbUser.getUid()).addValueEventListener(new ValueEventListener() {

            public void onDataChange(DataSnapshot dataSnapshot){

                mUser = dataSnapshot.getValue(User.class);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        /* --FIN--MODIFICATION A PARTIR DE LA BDD */

        mDatabase = FirebaseDatabase.getInstance().getReference().child("events");



        ibSelectImage.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view) {
                                               Intent galleryIntent = new Intent (Intent.ACTION_GET_CONTENT);

                                               galleryIntent.setType("image/*");
                                               startActivityForResult(galleryIntent, GALLERY_INTENT);
                                           }
                                       }
        );

        bCreerEvent.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view) {
                                              CreateEvent createevent = (CreateEvent) new CreateEvent().execute();
                                               createevent.doInBackground();


                                               System.out.println("adress" +addressName);
                                               System.out.println("lat" + lat);
                                               System.out.println("lonig" + longi);


                                               String nameEvent = etNameEvent.getText().toString().trim();
                                               if (TextUtils.isEmpty(nameEvent)) {
                                                   Toast.makeText(getActivity(), "Enter le nom de l'évenement", Toast.LENGTH_SHORT).show();
                                                   return;
                                               }

                                               String descriptionEvent = etDescription.getText().toString().trim();



                                               int selectId = radioGroup.getCheckedRadioButtonId();
                                               System.out.println(selectId);
                                               String type ="autre";

                                               if (selectId != -1){
                                               radioButton = (RadioButton) getView().findViewById(selectId);

                                               type = (String) radioButton.getText();}



                                               String date = dateEvent.getText().toString().trim();


                                               if (TextUtils.isEmpty(date)) {
                                                   Toast.makeText(getActivity(), "Enter une date", Toast.LENGTH_SHORT).show();
                                                   return;
                                               }

                                               String debut = debutEvent.getText().toString().trim();
                                               if (TextUtils.isEmpty(debut)) {
                                                   Toast.makeText(getActivity(), "Enter une heure de début", Toast.LENGTH_SHORT).show();
                                                   return;
                                               }


                                               String fin = finEvent.getText().toString().trim();
                                               if (TextUtils.isEmpty(fin)) {
                                                   Toast.makeText(getActivity(), "Enter une heure de fin", Toast.LENGTH_SHORT).show();
                                                   return;
                                               }

                                               if (TextUtils.isEmpty(descriptionEvent)) {
                                                   Toast.makeText(getActivity(), "Enter une description", Toast.LENGTH_SHORT).show();
                                                   return;
                                               }

                                          /*     Event event = new Event(lat, longi, nameEvent, descriptionEvent, addressName,type, date, mUser.getUsername(), debut, fin);
                                               if(uriEvent != null){
                                                   event.setUriEvent(uriEvent.toString());
                                               }
                                               mDatabase.push().setValue(event);*/

                                               Toast.makeText(getActivity(),"L'évenement a bien été crée !",Toast.LENGTH_SHORT).show();
                                               mMapFragment = MapFragment.newInstance();
                                           }
                                       }
        );

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
         super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK){

            mProgressDialog.setMessage("Uploaoding...");
            mProgressDialog.show();
            imageUri = data.getData();
            ibSelectImage.setImageURI(imageUri);

            StorageReference filepath = mStorage.child("Photos").child(imageUri.getLastPathSegment());

            filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    uriEvent = taskSnapshot.getDownloadUrl();

                    //Picasso.with(getActivity()).load(downloadUri).fit().centerCrop().into(mImageView);

                    Toast.makeText(getActivity(), "Upload Done", Toast.LENGTH_LONG).show();
                    mProgressDialog.dismiss();
                }
            });
        }

    }

    /**
     ******** CREATEVENT ********
     * Classe qui nous permet de créer un nouvel evenement dans la base de données
     * */
    class CreateEvent extends AsyncTask<Void, Void, Address> {

        String errorMessage = "";

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Address doInBackground(Void ... none) {
            Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
            List<Address> addresses = null;

            String name =etAddress.getText().toString().trim();
            try {
                addresses = geocoder.getFromLocationName(name, 1);
            } catch (IOException e) {
                errorMessage = "Service not available";
                Log.e(TAG, errorMessage, e);
            }


            if(addresses != null && addresses.size() > 0)
                return addresses.get(0);

            return null;
        }

        protected void onPostExecute(Address address) {
            if(address == null) {
                progressBar.setVisibility(View.INVISIBLE);
            }
            else {
                for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                    addressName += "" + address.getAddressLine(i);
                    lat = address.getLatitude();
                    longi= address.getLongitude();
                }

                System.out.println(addressName);
                System.out.println(lat);
                System.out.println(longi);

                progressBar.setVisibility(View.INVISIBLE);
            }}





    }


}
