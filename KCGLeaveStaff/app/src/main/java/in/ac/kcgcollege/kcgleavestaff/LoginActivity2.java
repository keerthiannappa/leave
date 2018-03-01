package in.ac.kcgcollege.kcgleavestaff;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.graphics.Color.parseColor;

public class LoginActivity2 extends AppCompatActivity implements View.OnClickListener{

    ScrollView scrollView;
    ImageView theavenue;
    Button login;
    EditText emailtext , passwordtext;
    TextView auth_failed , emailrequired , passwordrequired;
    View authview;
    ProgressBar progressBar;

    private FirebaseAuth mAuth;
    public static final String TAG="EmailPassword";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity2.this, MainActivity.class));
            finish();
        }

        //UI elements initalization
        theavenue=(ImageView) findViewById(R.id.logocse);

        scrollView=(ScrollView) findViewById(R.id.login_scrollview);

        login=(Button) findViewById(R.id.login_button);

        emailtext=(EditText) findViewById(R.id.email_edit_text);
        passwordtext=(EditText) findViewById(R.id.password_edit_text);

        auth_failed=(TextView) findViewById(R.id.authfailed);
        emailrequired=(TextView) findViewById(R.id.emailrequired);
        passwordrequired=(TextView) findViewById(R.id.passwordrequired);


        authview=(View) findViewById(R.id.viewauth);
        progressBar=(ProgressBar) findViewById(R.id.progressBar2);


        //button listeners
        login.setOnClickListener(this);

    }


    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }
    @Override
    public void onStop() {
        super.onStop();

    }



    //sign-in definition
    private void signIn(String email, String password) {

        Log.d(TAG, "signIn:" + email);
        if (!validateForm())
        {
            progressBar.setVisibility(View.INVISIBLE);
            login.setVisibility(View.VISIBLE);
            return;
        }


        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        progressBar.setVisibility(View.INVISIBLE);
                        login.setVisibility(View.VISIBLE);
                        if (!task.isSuccessful()) {

                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                        }
                        else {
                            Intent intent = new Intent(LoginActivity2.this, MainActivity.class);
                            startActivity(intent);
                            finish();

                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
                            authview.setBackgroundColor(parseColor("#d50000"));
                            auth_failed.setText("Authentication Failed");
                        }

                        // [END_EXCLUDE]
                    }
                });

        // [END sign_in_with_email]
    }


    //check entered data
    private boolean validateForm() {
        boolean valid = true;

        String email = emailtext.getText().toString();
        if (TextUtils.isEmpty(email)) {
            emailrequired.setError("Required.");
            valid = false;
        } else {
            emailrequired.setError(null);
        }

        String password = passwordtext.getText().toString();
        if (TextUtils.isEmpty(password)) {
            passwordrequired.setError("Required.");
            valid = false;
        } else {
            passwordrequired.setError(null);
        }

        return valid;
    }


    //button click definition
    @Override
    public void onClick(View v)
    {
        if(v==login)
        {

            progressBar.setVisibility(View.VISIBLE);
            login.setVisibility(View.INVISIBLE);
            signIn("staff"+emailtext.getText().toString(),passwordtext.getText().toString());

        }

    }
    @Override
    public void onBackPressed()
    {

        // super.onBackPressed(); // Comment this super call to avoid calling finish() or fragmentmanager's backstack pop operation.
    }
}
