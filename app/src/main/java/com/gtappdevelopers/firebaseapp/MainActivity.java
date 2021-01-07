package com.gtappdevelopers.firebaseapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.TextureView;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    //creating variables for our adapter, array list, firebasefirestore and our sliderview.
    private SliderAdapter adapter;
    private ArrayList<SliderData> sliderDataArrayList;
    FirebaseFirestore db;
    private SliderView sliderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //creating a new array list fr our array list.
        sliderDataArrayList = new ArrayList<>();
        //initializing our slider view and firebaseFirestore instance.
        sliderView = findViewById(R.id.slider);
        db = FirebaseFirestore.getInstance();
        //calling our method to load images.
        loadImages();
    }

    private void loadImages() {
        //getting data from our colletion and after that calling a method for on success listner.
        db.collection("Slider").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                //inside the on success metho we are running a for loop and we are getting the data from Firebase FIrestore
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    //after we get the data we are passing inside our object class.
                    SliderData sliderData = documentSnapshot.toObject(SliderData.class);
                    SliderData model = new SliderData();
                    //below line is use for setting our image url for our modal class.
                    model.setImgUrl(sliderData.getImgUrl());
                    //after that we are adding that data inside our array list.
                    sliderDataArrayList.add(model);
                    //after adding data to our array list we are passing that array list inside our adapter class.
                    adapter = new SliderAdapter(MainActivity.this, sliderDataArrayList);
                    //belos line is for setting adapter to our slider view
                    sliderView.setSliderAdapter(adapter);
                    //below line is for setting animation to our slider.
                    sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
                    //below line is for setting auto cycle duration.
                    sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
                    //below line is for setting scroll timeanimation
                    sliderView.setScrollTimeInSec(3);
                    //below line is for setting auto cycle animation to our slider
                    sliderView.setAutoCycle(true);
                    //below line is use to start the animation of our slider view.
                    sliderView.startAutoCycle();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //if we get any error from Firebase we are dispaying a toast message for failure
                Toast.makeText(MainActivity.this, "Fail to load slider data..", Toast.LENGTH_SHORT).show();
            }
        });


    }

}