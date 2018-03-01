package in.ac.kcgcollege.kcgleavehod;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ODFragment extends Fragment {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mRef = database.getReference("Applications").child("OD");

    ListView lv;
    String s;

    ArrayAdapter<String> arrayAdapter;
    List<String> item_list = new ArrayList<String>();


    public ODFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_od, container, false);

        lv=(ListView)view.findViewById(R.id.leave_lv);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                item_list.clear();
                for (DataSnapshot s:dataSnapshot.getChildren()){
                    if(s.child(s.getKey()).child("status").getValue(String.class)== "1"){
                        item_list.add(s.getKey());
                    }
                }
                arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, item_list);
                lv.setAdapter(arrayAdapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                s=lv.getItemAtPosition(position).toString();
                Intent intent=new Intent(getContext(),IndividualActivity.class);
                intent.putExtra("ref",s);
                intent.putExtra("type","0");
                startActivity(intent);

            }
        });



        return view;
    }

}
