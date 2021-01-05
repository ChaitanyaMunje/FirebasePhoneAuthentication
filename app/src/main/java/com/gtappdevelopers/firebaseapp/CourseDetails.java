package com.gtappdevelopers.firebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CourseDetails extends AppCompatActivity {

    //creating variabes for our recycler view, array list, adapter, firebase firestore and our progress bar.
    private RecyclerView courseRV;
    private ArrayList<Courses> coursesArrayList;
    private CourseRVAdapter courseRVAdapter;
    private FirebaseFirestore db;
    ProgressBar loadingPB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        //initializing our variables.
        courseRV = findViewById(R.id.idRVCourses);
        loadingPB = findViewById(R.id.idProgressBar);
        //initializing our variable for firebase firestore and getting its instance.
        db = FirebaseFirestore.getInstance();
        //creating our new array list
        coursesArrayList = new ArrayList<>();
        courseRV.setHasFixedSize(true);
        courseRV.setLayoutManager(new LinearLayoutManager(this));
        //adding our array list to our recycler view adapter class.
        courseRVAdapter = new CourseRVAdapter(coursesArrayList, this);
        //setting adapter to our recycler view.
        courseRV.setAdapter(courseRVAdapter);
        //below line is use to get the data from Firebase FIrestore.
        //previously we were saving data on a refrence of Vourses
        //now we will be getting the data from the same refrence.

        db.collection("Courses").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        //after getting the data we are calling on success method
                        //and inside this method we are checking if the recieved query snapshot is empty or not.
                        if (!queryDocumentSnapshots.isEmpty()) {
                            //if the snapshot is not empty we are hiding our progress bar and adding our data in a list.
                            loadingPB.setVisibility(View.GONE);
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                //after getting this list we are passing that list to our object class.
                                Courses c = d.toObject(Courses.class);
                                //below is the updated line of code which we have to add to pass the document id inside our modal class.
                                //we are setting our document id with d.getId() method
                                c.setId(d.getId());
                                //and we will pass this object class inside our arraylist which we have created for recyclcer view.
                                coursesArrayList.add(c);
                            }
                            //after adding the data to recycler view.
                            // we are calling recycler view notifuDataSetChanged method to notify that data has been changed in recycler view.
                            courseRVAdapter.notifyDataSetChanged();
                        } else {
                            //if the snapshot is empty we are displaying a toast message.
                            Toast.makeText(CourseDetails.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //if we donot get any data or any error we are displaying a toast message that we donot get any data
                Toast.makeText(CourseDetails.this, "Fail to get the data.", Toast.LENGTH_SHORT).show();
            }
        });


    }
}