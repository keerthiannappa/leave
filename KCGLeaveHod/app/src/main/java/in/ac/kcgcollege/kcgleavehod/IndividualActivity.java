package in.ac.kcgcollege.kcgleavehod;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class IndividualActivity extends AppCompatActivity implements View.OnClickListener{
    TextView lfname,lfreg,lfdays,lffrom,lfto,lfstatus,lfreason,lfnodata,lftype,lfty,lfnod,lfnleave;
    String lfsname,lfsdays,lfsfrom,lfsto,lfsreason,lfsstatus,lfstype,flag,nod,nleave;
    FrameLayout frameLayout;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mRef = database.getReference("Applications");
    DatabaseReference myRef= database.getReference("student");
    Intent i;
    Button grant,reject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual);

        i=getIntent();

        lfname=(TextView)findViewById(R.id.frag_leave_name);
        lfreg=(TextView)findViewById(R.id.frag_leave_reg);
        lfdays=(TextView)findViewById(R.id.frag_leave_days);
        lffrom=(TextView)findViewById(R.id.frag_leave_from);
        lfto=(TextView)findViewById(R.id.frag_leave_to);
        lfstatus=(TextView)findViewById(R.id.frag_leave_status);
        lfreason=(TextView)findViewById(R.id.frag_leave_reason);
        lfnodata=(TextView)findViewById(R.id.frag_leave_nodata);
        lftype=(TextView)findViewById(R.id.frag_leave_type);
        frameLayout=(FrameLayout)findViewById(R.id.frag_leave_layout);
        lfnod=(TextView)findViewById(R.id.frag_leave_nod);
        lfnleave=(TextView)findViewById(R.id.frag_leave_nleave);
        lfty=(TextView)findViewById(R.id.frag_leave_ty);
        grant=(Button)findViewById(R.id.frag_leave_grant);
        reject=(Button)findViewById(R.id.frag_leave_reject);

        grant.setOnClickListener(this);
        reject.setOnClickListener(this);


        if(i.getStringExtra("type")=="0"){
            lftype.setVisibility(View.INVISIBLE);
            lfty.setVisibility(View.INVISIBLE);
            flag="OD";
        }
        else{
            flag="Leave";
        }

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(flag).hasChild(i.getStringExtra("ref"))){
                    lfsdays=dataSnapshot.child(flag).child(i.getStringExtra("ref")).child("days").getValue(String.class);
                    lfsfrom=dataSnapshot.child(flag).child(i.getStringExtra("ref")).child("from").getValue(String.class);
                    lfsto=dataSnapshot.child(flag).child(i.getStringExtra("ref")).child("to").getValue(String.class);
                    lfsreason=dataSnapshot.child(flag).child(i.getStringExtra("ref")).child("reason").getValue(String.class);
                    lfsstatus=dataSnapshot.child(flag).child(i.getStringExtra("ref")).child("status").getValue(String.class);
                    if(flag=="Leave"){
                        lfstype=dataSnapshot.child(flag).child(i.getStringExtra("ref")).child("type").getValue(String.class);
                    }
                    lfreg.setText(i.getStringExtra("ref"));
                    lfdays.setText(lfsdays);
                    lffrom.setText(lfsfrom);
                    lfto.setText(lfsto);
                    lfreason.setText(lfsreason);
                    lftype.setText(lfstype);
                    if(Integer.parseInt(lfsstatus)==0){
                        lfstatus.setText("Still Waiting");
                        lfstatus.setBackgroundColor(Color.RED);
                    }
                    if(Integer.parseInt(lfsstatus)==1){
                        lfstatus.setText("Confirmed by CT");
                        lfstatus.setBackgroundColor(Color.YELLOW);
                    }
                    if(Integer.parseInt(lfsstatus)==2){
                        lfstatus.setText("Confirmed by HOD");
                        lfstatus.setBackgroundColor(Color.GREEN);
                    }
                    if(Integer.parseInt(lfsstatus)==3){
                        lfstatus.setText("Rejected by CT");
                        lfstatus.setBackgroundColor(Color.RED);
                    }
                    if(Integer.parseInt(lfsstatus)==4){
                        lfstatus.setText("Rejected by HOD");
                        lfstatus.setBackgroundColor(Color.RED);
                    }
                }
                else{
                    frameLayout.setVisibility(View.INVISIBLE);
                    lfnodata.setVisibility(View.VISIBLE);

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lfsname=dataSnapshot.child(i.getStringExtra("ref")).child("name").getValue(String.class);
                nod=dataSnapshot.child(i.getStringExtra("ref")).child("numberod").getValue(String.class);
                nleave=dataSnapshot.child(i.getStringExtra("ref")).child("numberleave").getValue(String.class);
                lfname.setText(lfsname);
                lfnod.setText(nod);
                lfnleave.setText(nleave);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void onClick(View v){
        if(v==grant){
            mRef.child(flag).child(i.getStringExtra("ref")).child("status").setValue("2");
            if(i.getStringExtra("type")=="0"){
                myRef.child(i.getStringExtra("ref")).child("numberod").setValue(String.valueOf(Integer.parseInt(nod)+1));
            }
            if(i.getStringExtra("type")=="1"){
                myRef.child(i.getStringExtra("ref")).child("numberleave").setValue(String.valueOf(Integer.parseInt(nleave)+1));
            }
        }
        if(v==reject){
            mRef.child(flag).child(i.getStringExtra("ref")).child("status").setValue("4");
        }
    }
}
