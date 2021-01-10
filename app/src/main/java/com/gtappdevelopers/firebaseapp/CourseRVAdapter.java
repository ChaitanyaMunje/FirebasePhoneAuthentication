package com.gtappdevelopers.firebaseapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CourseRVAdapter extends RecyclerView.Adapter<CourseRVAdapter.ViewHolder> {
    //creating variables for our arraylist and context
    private ArrayList<Courses>coursesArrayList;
    private Context context;

    //creating constructor for our adapter class
    public CourseRVAdapter(ArrayList<Courses> coursesArrayList, Context context) {
        this.coursesArrayList = coursesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CourseRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //passing our layout file for displaying our card item
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.course_item,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull CourseRVAdapter.ViewHolder holder, int position) {
        //setting data to our text views from our modal class.
        Courses courses = coursesArrayList.get(position);
        holder.courseNameTV.setText(courses.getCourseName());
        holder.courseDurationTV.setText(courses.getCourseDuration());
        holder.courseDescTV.setText(courses.getCourseDescription());
    }

    @Override
    public int getItemCount() {
        //returning the size of our array list.
        return coursesArrayList.size();
    }

     class ViewHolder extends RecyclerView.ViewHolder{
        //creating variables for our text views.
        private final TextView courseNameTV;
        private final TextView courseDurationTV;
        private final TextView courseDescTV;

         public ViewHolder(@NonNull View itemView) {
             super(itemView);
             //initializing our text views.
             courseNameTV = itemView.findViewById(R.id.idTVCourseName);
             courseDurationTV = itemView.findViewById(R.id.idTVCourseDuration);
             courseDescTV = itemView.findViewById(R.id.idTVCourseDescription);

             //here we are adding on click listner for our item of recycler view.
             itemView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     //after clicking of the item of recycler view.
                     //we are passing our course object to the new activity.
                     Courses courses = coursesArrayList.get(getAdapterPosition());
                     //below line is creating a new intent.
                     Intent i =new Intent(context,UpdateCourse.class);
                     //below line is for putting our course object to our next activity.
                     i.putExtra("course",courses);
                     //after passing the data we are starting our activity.
                     context.startActivity(i);

                 }
             });

         }
     }
}
