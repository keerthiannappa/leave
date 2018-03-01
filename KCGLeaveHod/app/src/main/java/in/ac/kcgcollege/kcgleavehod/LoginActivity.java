package in.ac.kcgcollege.kcgleavehod;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    ImageView splash;
    TextView a,b,c,d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        splash=(ImageView)findViewById(R.id.imageView);
        a=(TextView)findViewById(R.id.textView6);
        b=(TextView)findViewById(R.id.textView7);
        c=(TextView)findViewById(R.id.textView8);
        d=(TextView)findViewById(R.id.textView9);



        Animation animation= AnimationUtils.loadAnimation(this,R.anim.transition);
        splash.startAnimation(animation);
        a.startAnimation(animation);
        b.startAnimation(animation);
        c.startAnimation(animation);
        d.startAnimation(animation);
        Thread timer=new Thread(){
            public void run()
            {
                try{
                    sleep(6000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                finally{
                    Intent i=new Intent(LoginActivity.this,LoginActivity2.class);
                    startActivity(i);
                    finish();
                }
            }
        };
        timer.start();
    }
    @Override
    public void onBackPressed()
    {

        // super.onBackPressed(); // Comment this super call to avoid calling finish() or fragmentmanager's backstack pop operation.
    }
}
