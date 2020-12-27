package com.gtappdevelopers.firebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //button for logout and initialzing our button.
        Button logoutBtn = findViewById(R.id.idBtnLogout);

        //adding onclick listner for our logout button.
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //below line is for getting instance for AuthUi and after that calling a signout method from FIrebase.
                AuthUI.getInstance()
                        .signOut(HomeActivity.this)
                        //after signout is executed we are redirecting our user to MainActivity where our login flow is being displayed.
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                //below method is used after loggin out from device.
                                Toast.makeText(HomeActivity.this, "User Signed Out", Toast.LENGTH_SHORT).show();
                                //below line is to go to MainActivity via an intent.
                                Intent i=new Intent(HomeActivity.this,MainActivity.class);
                                startActivity(i);

                            }
                        });

            }
        });
    }
}