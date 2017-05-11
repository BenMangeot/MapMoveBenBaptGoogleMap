package com.example.benjamin.mapmove.FragmentPrincipal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.benjamin.mapmove.ActivityPostConnexion.DetailEventsUserProActivity;
import com.example.benjamin.mapmove.Instance.User;
import com.example.benjamin.mapmove.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ResearchProFragment extends Fragment {

    String[] listUserPro;
    ArrayList<String> usersProSearch;
    ArrayAdapter<String> adapter;
    ListView listView;
    EditText editText;
    private DatabaseReference mDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_see, container, false);
        listView = (ListView) view.findViewById(R.id.listview);
        editText = (EditText) view.findViewById(R.id.txtsearch);

        initList();

        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    initList();
                }
                else{
                    // perform search
                    searchItem(s.toString());
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3)
            {
                String value = (String)adapter.getItemAtPosition(position);
                Intent intent = new Intent(getActivity(), DetailEventsUserProActivity.class);
                intent.putExtra("my_user", value);
                startActivity(intent);
            }
        });

        return view;

    }

    public void searchItem(String textToSearch){
        for(String userPro:listUserPro){
            if(!userPro.contains(textToSearch)){
                usersProSearch.remove(userPro);
            }
        }
        adapter.notifyDataSetChanged();
    }

    public void initList(){

        usersProSearch = new ArrayList<String>();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        adapter=new ArrayAdapter<String>(getActivity(), R.layout.list_item, R.id.txtitem, usersProSearch);
        listView.setAdapter(adapter);

       /* [INIT MISE EN PLACE DE LA LISTE DE USER] */
        mDatabase.child("users").addValueEventListener(new ValueEventListener() {

            public void onDataChange(DataSnapshot dataSnapshot){
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {

                    User userPro = postSnapshot.getValue(User.class);
                    if(userPro.getBoolPro()){
                        usersProSearch.add(userPro.getUsername());
                    }
                    listUserPro = usersProSearch.toArray(new String[usersProSearch.size()]);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
       /* [FIN MISE EN PLACE DE LA LISTE DE USER] */


    }
}
