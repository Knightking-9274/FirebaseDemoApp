package com.example.firebasedemoapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CourseRVAdapter.CourseClickInterface{
    private RecyclerView rcycle;
    private CourseRVModel crv;
    private FloatingActionButton faBtn;
    private FirebaseDatabase fBase;
    private DatabaseReference dbRef;
    private ProgressBar prgMain;

    private ArrayList<CourseRVModel> crvArrayList;

    private RelativeLayout rLayout;

    private CourseRVAdapter crvAdpt;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rcycle = findViewById(R.id.rclCourses);
        prgMain = findViewById(R.id.prgMain);
        faBtn = findViewById(R.id.faBtn1);
        rLayout = findViewById(R.id.relativeBtm1);
        fBase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        dbRef = fBase.getReference("Courses");
        crvArrayList = new ArrayList<>();
        crvAdpt = new CourseRVAdapter(crvArrayList,this,this);
        rcycle.setLayoutManager(new LinearLayoutManager(this));
        rcycle.setAdapter(crvAdpt);
        faBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,AddCourseActivity.class));
            }
        });

        getAllOCurses();

    }
    private void getAllOCurses(){
        crvArrayList.clear();
        dbRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                prgMain.setVisibility(View.GONE);
                crvArrayList.add(snapshot.getValue(CourseRVModel.class));
                crvAdpt.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                prgMain.setVisibility(View.GONE);
                crvAdpt.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                prgMain.setVisibility(View.GONE);
                crvAdpt.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                prgMain.setVisibility(View.GONE);
                crvAdpt.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onCourseClick(int pos) {

    }
    private void displayBottomStheet(CourseRVModel crvM){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_dialog,rLayout);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();
        TextView courseName = layout.findViewById(R.id.txtBtmName);
        TextView courseDesc = layout.findViewById(R.id.txtBtmDesc);
        TextView coursePrice = layout.findViewById(R.id.txtBtmPrice);
        TextView courseSuited = layout.findViewById(R.id.txtBtmSuited);
        ImageView imgCourseView = layout.findViewById(R.id.imgBtm);

        Button btnEditCourse = layout.findViewById(R.id.btnBtmEdit);
        Button btnCourseView = layout.findViewById(R.id.btnBtmView);

        courseName.setText(crvM.getCourseName());
        coursePrice.setText("Rs. "+crvM.getCoursePrice());
        courseDesc.setText(crvM.getCourseDesc());
        courseSuited.setText(crvM.getCurseSuitedFor());
        Picasso.get().load(crvM.getCourseImge()).into(imgCourseView);

        btnEditCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,EditCourseActivity.class);
                intent.putExtra("course",crvM);
                startActivity(intent);

            }
        });

        btnCourseView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(crvM.getCourseLink()));
                startActivity(i);

            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
//        switch(id){
//            case R.id.idLogOut:
//                Toast.makeText(MainActivity.this,"User Logged Out!",Toast.LENGTH_SHORT).show();
//                mAuth.signOut();
//                Intent i = new Intent(MainActivity.this,LogInActivity.class);
//                startActivity(i);
//                this.finish();
//                return true;
////                break;
//
//            default:
//                return super.onOptionsItemSelected(item);
//
//
//        }
        if(id==R.id.idLogOut){
            Toast.makeText(MainActivity.this,"User Logged Out!",Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                Intent i = new Intent(MainActivity.this,LogInActivity.class);
                startActivity(i);
                this.finish();
                return true;

        }
        else{
            return super.onOptionsItemSelected(item);
        }



    }
}