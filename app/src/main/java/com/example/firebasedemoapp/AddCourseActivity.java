package com.example.firebasedemoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddCourseActivity extends AppCompatActivity {

    private Button btnCourse;
    private TextInputEditText edtCourse,edtPrice, edtCourseSuited,edtImageLink,edtCourseLink,edtDescrp;

    private ProgressBar prgCourse;
    private FirebaseDatabase fBase;
    private DatabaseReference databaseReference;
    private String courseID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        btnCourse = findViewById(R.id.btnAddCourse);
        edtCourse = findViewById(R.id.edtCourseName);
        edtPrice = findViewById(R.id.edtCoursePrice);
        edtCourseSuited = findViewById(R.id.suitedCourse);
        edtImageLink = findViewById(R.id.edtImageLink);
        edtCourseLink = findViewById(R.id.courseLink);
        edtDescrp = findViewById(R.id.courseDescription);
        prgCourse = findViewById(R.id.prgCourse);
        fBase =FirebaseDatabase.getInstance();
        databaseReference = fBase.getReference("Courses");

        btnCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String course = edtCourse.getText().toString();
                String price = edtPrice.getText().toString();
                String suited = edtCourseSuited.getText().toString();
                String image = edtImageLink.getText().toString();
                String courseLink = edtCourseLink.getText().toString();
                String desc = edtDescrp.getText().toString();
                courseID = course;

                CourseRVModel crv = new CourseRVModel(course,price,desc,suited,image,courseID,courseLink);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.child(courseID).setValue(crv);
                        Toast.makeText(AddCourseActivity.this,"Course Added!",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddCourseActivity.this,MainActivity.class));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                        Toast.makeText(AddCourseActivity.this,"Error has occured!",Toast.LENGTH_SHORT).show();
                        

                    }
                });
            }
        });


    }
}