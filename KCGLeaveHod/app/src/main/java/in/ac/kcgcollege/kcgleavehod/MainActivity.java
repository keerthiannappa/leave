package in.ac.kcgcollege.kcgleavehod;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    Fragment fragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_od:

                    fragment=new ODFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,fragment,fragment.getTag()).commit();
                    return true;

                case R.id.navigation_leave:
                    fragment=new LeaveFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,fragment,fragment.getTag()).commit();
                    return true;

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragment=new ODFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,fragment,fragment.getTag()).commit();



        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
