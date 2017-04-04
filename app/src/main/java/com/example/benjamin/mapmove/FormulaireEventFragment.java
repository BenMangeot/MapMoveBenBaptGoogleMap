package com.example.benjamin.mapmove;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.benjamin.mapmove.Instance.Event;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URI;
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
    TextView infoText;
    private MapFragment mMapFragment;
    private android.support.v4.app.FragmentTransaction fragmentTransaction;
    private static final int GALLERY_INTENT = 2;
    Uri imageUri = null;
    private ProgressDialog mProgressDialog;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_formulaire_event, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("events");
        mStorage = FirebaseStorage.getInstance().getReference();

        etNameEvent = (EditText) view.findViewById(R.id.etNameEvent);
        etDescription = (EditText) view.findViewById(R.id.etDescription);
        etAddress = (EditText) view.findViewById(R.id.addressEdit);
        bCreerEvent = (Button) view.findViewById(R.id.bCreerEvent);
        ibSelectImage = (ImageButton) view.findViewById(R.id.ibSelectImage);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        infoText = (TextView) view.findViewById(R.id.infoText);
        mProgressDialog = new ProgressDialog(getActivity());


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
                                               new CreateEvent().execute();
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

    /** Classe qui nous permet de créer un nouvel evenement dans la base de données */

    class CreateEvent extends AsyncTask<Void, Void, Address> {

        String errorMessage = "";

        @Override
        protected void onPreExecute() {
            infoText.setVisibility(View.INVISIBLE);
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
                infoText.setVisibility(View.VISIBLE);
                infoText.setText(errorMessage);
            }
            else {
                String addressName = "";
                for(int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                    addressName += " --- " + address.getAddressLine(i);
                }
                progressBar.setVisibility(View.INVISIBLE);
                infoText.setVisibility(View.VISIBLE);
                infoText.setText("Latitude: " + address.getLatitude() + "\n" +
                        "Longitude: " + address.getLongitude() + "\n" +
                        "Address: " + addressName);

                String nameEvent = etNameEvent.getText().toString();
                String descriptionEvent = etDescription.getText().toString();

                Event event = new Event(address.getLatitude(), address.getLongitude(), nameEvent, descriptionEvent, addressName);
                if(uriEvent != null){
                    event.setUriEvent(uriEvent.toString());
                }
                mDatabase.push().setValue(event);

            }
        }
    }


}