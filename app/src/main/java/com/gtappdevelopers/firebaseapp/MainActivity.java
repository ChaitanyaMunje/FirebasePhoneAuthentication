package com.gtappdevelopers.firebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    //variable for Firebase Auth
    private FirebaseAuth mFirebaseAuth;
    //declaring a const int value which we will be using in Firebase auth.
    public static final int RC_SIGN_IN = 1;
    //creating an auth listner for our Firebase auth
    private FirebaseAuth.AuthStateListener mAuthStateListner;

    //below is the list which we have created in which we can add the authentication which we have todisplay inside our app.
    List<AuthUI.IdpConfig> providers = Arrays.asList(
            //below is the line for adding email and password authentication.
            new AuthUI.IdpConfig.EmailBuilder().build(),
            //below line is used for adding google authentication builder in our app.
            new AuthUI.IdpConfig.GoogleBuilder().build(),
            //below line is used for adding phone authentication builder in our app.
            new AuthUI.IdpConfig.PhoneBuilder().build());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //below line is for getting instance for our firebase auth
        mFirebaseAuth=FirebaseAuth.getInstance();
        //below line is used for calling auth listner for oue Firebase authentication.
        mAuthStateListner=new FirebaseAuth.AuthStateListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                //we are calling method for on authentication state changed.
                //below line is used for getting current user which is authenticated previously.
                FirebaseUser user=firebaseAuth.getCurrentUser();
                //checking if the user is null or not.
                if (user!=null)
                {
                    //if the user is already authenticated then we will redirect our user to next screen which is our home screen.
                    //we are redirecting to new screen via an intent.
                    Intent i=new Intent(MainActivity.this,HomeActivity.class);
                    startActivity(i);
                    //we are caling finish method to kill or mainactivity which is displaying our login ui.
                    finish();
                }
                else {
                    //this method is called when our user is not authenticated previously.
                    startActivityForResult(
                            //below line is used for getting our authentication instance.
                            AuthUI.getInstance()
                                    //below line is used to create our sign in intet
                                    .createSignInIntentBuilder()
                                    //below line is used for adding smart lock for our authentication.
                                    //smart lock is used to check if the user is authentication through different devices.
                                    //currently we are disabling it.
                                    .setIsSmartLockEnabled(false)
                                    //we are adding different login providers which we have mentioned above in our list.
                                    // we can add more providers according toour requirement which are avaliable in firebase.
                                    .setAvailableProviders(providers)
                                    //below line is for customizing our theme for login screen and set logo method is used for adding logo for our login page.
                                    .setLogo(R.drawable.gfgimage).setTheme(R.style.Theme)
                                    //after setting our theme and logo we are calling a build() method to build our login screen.
                                    .build(),
                            //and lastly we are passing our const integer which is declared above.
                            RC_SIGN_IN
                    );

                }
            }
        };

    }

    @Override
    protected void onResume() {
        super.onResume();
        //we are calling our auth listner method on app resume.
        mFirebaseAuth.addAuthStateListener(mAuthStateListner);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //here we are calling remove auth listner method on stop.
        mFirebaseAuth.removeAuthStateListener(mAuthStateListner);

    }







}