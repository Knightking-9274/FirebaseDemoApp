package com.example.firebasedemoapp;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CourseRVAdapter extends RecyclerView.Adapter<CourseRVAdapter.ViewHolder> {
    private ArrayList<CourseRVModel>crvArrList;
    private Context ctx;
    private CourseClickInterface crvInter;

    public CourseRVAdapter(ArrayList<CourseRVModel> crvArrList, Context ctx, CourseClickInterface crvInter) {
        this.crvArrList = crvArrList;
        this.ctx = ctx;
        this.crvInter = crvInter;
    }


    int lastPos = -1;
    @NonNull
    @Override
    public CourseRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.course_rv_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseRVAdapter.ViewHolder holder, int position) {
        CourseRVModel crv1 = crvArrList.get(position);
        holder.txtCourseName.setText(crv1.getCourseName());
        holder.txtCourseprice.setText(crv1.getCoursePrice());
        Picasso.get().load(crv1.getCourseImge()).into(holder.courseIV);
        setAnimation(holder.itemView,position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crvInter.onCourseClick(position);
            }
        });

    }
    private void setAnimation(View itemView, int position){
        if(position>lastPos){
            Animation animation = AnimationUtils.loadAnimation(ctx, android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            lastPos = position;

        }

    }
    @Override
    public int getItemCount() {
        return crvArrList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtCourseName,txtCourseprice;
        private ImageView courseIV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCourseName = itemView.findViewById(R.id.txtCourseName);
            txtCourseprice = itemView.findViewById(R.id.txtCoursePrice);
            courseIV = itemView.findViewById(R.id.imgIVCourse);

        }
    }
    public interface CourseClickInterface{
        void onCourseClick(int pos);


    }
}
