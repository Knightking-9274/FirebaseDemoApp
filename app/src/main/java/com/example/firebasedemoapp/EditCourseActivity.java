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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class EditCourseActivity extends AppCompatActivity {

    private Button btnUpdateCourse,btnDeleteCourse;
    private TextInputEditText edtCourse,edtPrice, edtCourseSuited,edtImageLink,edtCourseLink,edtDescrp;

    private ProgressBar prgCourse;
    private FirebaseDatabase fBase;
    private DatabaseReference databaseReference;
    private String courseID;
    private CourseRVModel crv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);
        btnUpdateCourse = findViewById(R.id.btnupdateCourse);
        btnDeleteCourse = findViewById(R.id.btndltCourse);
        edtCourse = findViewById(R.id.edtCourseName);
        edtPrice = findViewById(R.id.edtCoursePrice);
        edtCourseSuited = findViewById(R.id.suitedCourse);
        edtImageLink = findViewById(R.id.edtImageLink);
        edtCourseLink = findViewById(R.id.courseLink);
        edtDescrp = findViewById(R.id.courseDescription);
        prgCourse = findViewById(R.id.prgCourse);
        fBase =FirebaseDatabase.getInstance();
        crv = getIntent().getParcelableExtra("course");
        if(crv!=null){
            edtCourse.setText(crv.getCourseName());
            edtPrice.setText(crv.getCoursePrice());
            edtCourseSuited.setText(crv.getCurseSuitedFor());
            edtImageLink.setText(crv.getCourseImge());
            edtDescrp.setText(crv.getCourseDesc());
            edtCourseLink.setText(crv.getCourseLink());
            courseID = crv.getCourseID();


        }
        databaseReference = fBase.getReference("Courses").child(courseID);

        btnUpdateCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prgCourse.setVisibility(View.VISIBLE);
                String course = edtCourse.getText().toString();
                String price = edtPrice.getText().toString();
                String suited = edtCourseSuited.getText().toString();
                String image = edtImageLink.getText().toString();
                String courseLink = edtCourseLink.getText().toString();
                String desc = edtDescrp.getText().toString();

                Map<String,Object> map = new HashMap<>();
                map.put("courseName",course);
                map.put("coursePrice",price);
                map.put("curseSuitedFor",suited);
                map.put("courseImge",image);
                map.put("courseLink",courseLink);
                map.put("courseDesc",desc);
                map.put("courseID",courseID);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        prgCourse.setVisibility(View.GONE);
                        databaseReference.updateChildren(map);
                        Toast.makeText(EditCourseActivity.this, "Course Updated!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditCourseActivity.this,MainActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(EditCourseActivity.this,"Failed to Update Course!",Toast.LENGTH_SHORT).show();


                    }
                });
            }
        });

        btnDeleteCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCourse();

            }
        });

    }
    private void deleteCourse(){
        databaseReference.removeValue();
        Toast.makeText(EditCourseActivity.this,"Course Deleted!",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(EditCourseActivity.this,MainActivity.class));

    }}