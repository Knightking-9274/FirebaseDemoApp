package com.example.firebasedemoapp;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class CourseRVModel implements Parcelable {

    private String courseName;
    private String coursePrice;
    private String courseDesc;
    private String curseSuitedFor;
    private String courseImge;
    private String courseID;

    public String getCourseLink() {
        return courseLink;
    }

    public void setCourseLink(String courseLink) {
        this.courseLink = courseLink;
    }

    private String courseLink;

    public CourseRVModel(){

    }

    protected CourseRVModel(Parcel in) {
        courseName = in.readString();
        coursePrice = in.readString();
        courseDesc = in.readString();
        curseSuitedFor = in.readString();
        courseImge = in.readString();
        courseID = in.readString();
        courseLink = in.readString();
    }

    public static final Creator<CourseRVModel> CREATOR = new Creator<CourseRVModel>() {
        @Override
        public CourseRVModel createFromParcel(Parcel in) {
            return new CourseRVModel(in);
        }

        @Override
        public CourseRVModel[] newArray(int size) {
            return new CourseRVModel[size];
        }
    };

    public String getCourseName() {
        return courseName;
    }

    public CourseRVModel(String courseName, String coursePrice, String courseDesc, String curseSuitedFor, String courseImge, String courseID,String courseLink) {
        this.courseName = courseName;
        this.coursePrice = coursePrice;
        this.courseDesc = courseDesc;
        this.curseSuitedFor = curseSuitedFor;
        this.courseImge = courseImge;
        this.courseID = courseID;
        this.courseLink = courseLink;
    }


    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCoursePrice() {
        return coursePrice;
    }

    public void setCoursePrice(String coursePrice) {
        this.coursePrice = coursePrice;
    }

    public String getCourseDesc() {
        return courseDesc;
    }

    public void setCourseDesc(String courseDesc) {
        this.courseDesc = courseDesc;
    }

    public String getCurseSuitedFor() {
        return curseSuitedFor;
    }

    public void setCurseSuitedFor(String curseSuitedFor) {
        this.curseSuitedFor = curseSuitedFor;
    }

    public String getCourseImge() {
        return courseImge;
    }

    public void setCourseImge(String courseImge) {
        this.courseImge = courseImge;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(courseName);
        parcel.writeString(coursePrice);
        parcel.writeString(courseDesc);
        parcel.writeString(curseSuitedFor);
        parcel.writeString(courseImge);
        parcel.writeString(courseID);
    }
}
