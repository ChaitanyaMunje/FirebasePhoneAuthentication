package com.gtappdevelopers.firebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdateCourse extends AppCompatActivity {

    //creating variables for our edit text
    private EditText courseNameEdt, courseDurationEdt, courseDescriptionEdt;
    //creating a strings for storing our values from edittext fields.
    private String courseName, courseDuration, courseDescription;
    //creating a variable for firebasefirestore.
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_course);
        Courses courses = (Courses) getIntent().getSerializableExtra("course");

        //getting our instance from Firebase Firestore.
        db = FirebaseFirestore.getInstance();
        //initializing our edittext and buttons
        courseNameEdt = findViewById(R.id.idEdtCourseName);
        courseDescriptionEdt = findViewById(R.id.idEdtCourseDescription);
        courseDurationEdt = findViewById(R.id.idEdtCourseDuration);
        //creating variable for button
        Button updateCOurseBtn = findViewById(R.id.idBtnUpdateCourse);
        Button deleteBtn = findViewById(R.id.idBtnDeleteCourse);

        courseNameEdt.setText(courses.getCourseName());
        courseDescriptionEdt.setText(courses.getCourseDescription());
        courseDurationEdt.setText(courses.getCourseDuration());
        //adding on click listne for delete button
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //calling method to delete the course.
                deleteCourse(courses);
            }
        });


        updateCOurseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                courseName = courseNameEdt.getText().toString();
                courseDescription = courseDescriptionEdt.getText().toString();
                courseDuration = courseDurationEdt.getText().toString();

                //validating the text fileds if empty or not.
                if (TextUtils.isEmpty(courseName)) {
                    courseNameEdt.setError("Please enter Course Name");

                } else if (TextUtils.isEmpty(courseDescription)) {
                    courseDescriptionEdt.setError("Please enter Course Description");
                } else if (TextUtils.isEmpty(courseDuration)) {
                    courseDurationEdt.setError("Please enter Course Duration");
                } else {
                    //calling a method to update our course.
                    //we are passing our object class, course name, course description and course duration from our edittext field.
                    updateCourses(courses, courseName, courseDescription, courseDuration);
                }

            }
        });


    }

    private void deleteCourse(Courses courses) {
        //below line is for getting the collection where we are storing our courses.
        db.collection("Courses").
                //after that we are getting the document which we have to delete.
                        document(courses.getId()).
                //after passing the document id we are calling delete method to delete this document.
                        delete().
                //after deleting call on complete listner method to delete this data.
                        addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //inside on complete method we are checking if the task is succes or not.
                        if (task.isSuccessful()) {
                            //this method is called when the task is success
                            //after deleting we are starting our MainActivity.
                            Toast.makeText(UpdateCourse.this, "Course has been deleted from Databse.", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(UpdateCourse.this, MainActivity.class);
                            startActivity(i);
                        } else {
                            //if the delete operation is failed we are displaying a toast message.
                            Toast.makeText(UpdateCourse.this, "Fail to delete the course. ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void updateCourses(Courses courses, String courseName, String courseDescription, String courseDuration) {
        //inside this method we are passing our updated values inside our object class and later on we
        //will pass our whole object tofirebase Firestore.
        Courses updatedCourse = new Courses(courseName, courseDescription, courseDuration);

        //after passing data to object class weare sending it to firebase with specificdocument id.
        //below line is use to get the collection of our Firebase Firestore.
        db.collection("Courses").
                //below line is use toset the id of document where we have to perform update operation.
                        document(courses.getId()).
                //after setting our document id we are passing our whole object class to it.
                        set(updatedCourse).
                //after passing our object class we are calling a method for on succes listner.
                        addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //on successful completion of this process
                        //we are displaying the toast message.
                        Toast.makeText(UpdateCourse.this, "Course has been updated..", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            //inside on failure method we are displaying a failure message.
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateCourse.this, "Fail to update the data..", Toast.LENGTH_SHORT).show();
            }
        });


    }
}