package com.example.benjamin.mapmove.FragmentPrincipal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.benjamin.mapmove.Instance.User;
import com.example.benjamin.mapmove.R;
import com.example.benjamin.mapmove.ActivityPostConnexion.SettingsActivity;
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

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class CompteFragment extends Fragment {

    private TextView nameUser, mailUser;
    private CircleImageView uriUser;
    private DatabaseReference mDatabase;
    private StorageReference mStorage;
    private Button btnPhoto, btnSettings;
    private ProgressDialog mProgressDialog;

    private static final int GALLERY_INTENT = 2;

    User mUser;
    FirebaseUser mUserFirebase;
    Uri imageUri = null;
    private Uri newUriUser =  null;



    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_compte, container, false);

        nameUser = (TextView) view.findViewById(R.id.nameUser);
        mailUser = (TextView) view.findViewById(R.id.mailUser);
        btnPhoto = (Button) view.findViewById(R.id.btn_photo);
        btnSettings = (Button) view.findViewById(R.id.btn_setting);

        mProgressDialog = new ProgressDialog(getActivity());


        uriUser = (CircleImageView) view.findViewById(R.id.uriUser);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        mUserFirebase = FirebaseAuth.getInstance().getCurrentUser();
        mStorage = FirebaseStorage.getInstance().getReference();

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SettingsActivity.class));
            }
        });

        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galleryIntent = new Intent (Intent.ACTION_GET_CONTENT);

                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_INTENT);


            }
        });

        mDatabase.child(mUserFirebase.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Recupération de l'objet USER
                mUser = dataSnapshot.getValue(User.class);

                // Modification des TextView avec les Valeurs */
                nameUser.setText(mUser.getUsername());
                mailUser.setText(mUser.getEmail());
                Picasso.with(getContext()).load(mUser.getUriUser()).into(uriUser);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        return view;

    }

    private void updatePhoto(FirebaseUser userFromUpdateEmail, String newUriUser) {
        final String userId = userFromUpdateEmail.getUid();

        mDatabase.child(userId).child("uriUser").setValue(newUriUser);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK){

            mProgressDialog.setMessage("Uploaoding...");
            mProgressDialog.show();
            imageUri = data.getData();

            StorageReference filepath = mStorage.child("PhotosUser").child(imageUri.getLastPathSegment());

            filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    newUriUser = taskSnapshot.getDownloadUrl();

                    //Picasso.with(getActivity()).load(downloadUri).fit().centerCrop().into(mImageView);

                    Toast.makeText(getActivity(), "Upload Done", Toast.LENGTH_LONG).show();
                    mProgressDialog.dismiss();
                    updatePhoto(mUserFirebase, newUriUser.toString());
                }
            });
        }

    }

}