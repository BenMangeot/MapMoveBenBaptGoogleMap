package com.example.benjamin.mapmove;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.benjamin.mapmove.Instance.Event;
import com.example.benjamin.mapmove.Instance.User;
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
import com.squareup.picasso.Picasso;


import org.w3c.dom.Text;

import java.net.URI;

import static android.app.Activity.RESULT_OK;

public class CompteFragment extends Fragment {

/** DECLARATION */
    /**
     * Decla Firebase
     */
    private DatabaseReference mDatabase;
    private StorageReference mStorage;
    /**
     * Decla XML
     */
    private TextView tvUserName, tvMail;
    private View mView;
    private ImageButton ibAddPhotoUser;
    private ImageView ivPhotoUser;
    private Button bChangePhotoUser;
    private ProgressDialog mProgressDialog;
    /**
     * Provient des tuto
     */
    private static final int GALLERY_INTENT = 2;
    private Uri photoUri =  null;

    /**
     * Variable "final"
     */
    User mUser;
    FirebaseUser mUserFirebase;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

/** INITIALISATION DES VALEURS */
        /**
         * Init XML
         * */
        mView = inflater.inflate(R.layout.fragment_compte, container, false);
        tvUserName = (TextView) mView.findViewById(R.id.tvUserName);
        tvMail = (TextView) mView.findViewById(R.id.tvMail);
        ibAddPhotoUser = (ImageButton) mView.findViewById(R.id.ibAddPhotoUser);
        bChangePhotoUser = (Button) mView.findViewById(R.id.bChangePhotoUser);
        ivPhotoUser = (ImageView) mView.findViewById(R.id.ivPhotoUser);
        mProgressDialog = new ProgressDialog(getActivity());
        /**
         * Init FireBase
         * */
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mUserFirebase = FirebaseAuth.getInstance().getCurrentUser();
        mStorage = FirebaseStorage.getInstance().getReference();


/** ********* FIREBASE ******* */
        // On se postionne dans l'arbe au bon id
        mDatabase.child("users").child(mUserFirebase.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Recupération de l'objet USER
                mUser = dataSnapshot.getValue(User.class);

                // Modification des TextView avec les Valeurs */
                tvUserName.setText(mUser.getUsername());
                tvMail.setText(mUser.getEmail());

        /**
         * MODIFICATION DE L'AFFICHAGE EN FCT DU COMPTE UTI (photo ou non)
         * */
                if (mUser.getUriUser() == null) {
                    ivPhotoUser.setVisibility(mView.GONE);
                    bChangePhotoUser.setVisibility(mView.GONE);

                } else {
                    ibAddPhotoUser.setVisibility(mView.GONE);
                    Picasso.with(getActivity()).load(mUser.getUriUser().toString()).into(ivPhotoUser);
                }
    /**
        *   Ajout d'une photo a partie de l'ImageButton *
     */
                ibAddPhotoUser.setOnClickListener(new View.OnClickListener() {
                                                      @Override
                                                      public void onClick(View view) {
                                                          Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);

                                                          galleryIntent.setType("image/*");
                                                          startActivityForResult(galleryIntent, GALLERY_INTENT);
                                                          System.out.println(mUser.getUriUser());
                                                          mDatabase.child("users").child(mUserFirebase.getUid()).setValue(mUser);
                                                      }
                                                  }

                );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {

            mProgressDialog.setMessage("Uploaoding...");
            mProgressDialog.show();
            photoUri = data.getData();
            ibAddPhotoUser.setImageURI(photoUri);

            StorageReference filepath = mStorage.child("PhotosUser").child(photoUri.getLastPathSegment());

            filepath.putFile(photoUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    photoUri = taskSnapshot.getDownloadUrl();
                    mUser.setUriUser(photoUri.toString());
                    Toast.makeText(getActivity(), "Upload Done", Toast.LENGTH_LONG).show();
                    mProgressDialog.dismiss();
                    mDatabase.child("users").child(mUserFirebase.getUid()).setValue(mUser);
                    Toast.makeText(getActivity(), "L'image a bien été changé !", Toast.LENGTH_LONG).show();

                }
            });
        }


    }
}