package cs480teamavatar.cs480androidapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Student implements Parcelable{
    private int s_id;
    private String s_name;
    private String s_email;
    private String s_address;
    private String s_subject;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(s_id);
        out.writeString(s_name);
        out.writeString(s_email);
        out.writeString(s_address);
        out.writeString(s_subject);
    }

    public Student(int id, String name, String email, String address, String subject) {
        this.s_id = id;
        this.s_name = name;
        this.s_email = email;
        this.s_address = address;
        this.s_subject = subject;
    }

    private Student(Parcel in) {
        this.s_id = in.readInt();
        this.s_name = in.readString();
        this.s_email = in.readString();
        this.s_address = in.readString();
        this.s_subject = in.readString();
    }

    public int getID() {
        return s_id;
    }

    public String getName() {
        return s_name;
    }

    public String getEmail() {
        return s_email;
    }

    public String getAddress() {
        return s_address;
    }

    public String getSubject() {
        return s_subject;
    }

    public void setName(String name) {
        s_name = name;
    }

    public void setEmail(String email) {
        s_email = email;
    }

    public void setAddress(String address) {
        s_address = address;
    }

    public void setSubject(String subject) {
        s_subject = subject;
    }

    public static final Parcelable.Creator<Student> CREATOR = new Parcelable.Creator<Student>() {

        @Override
        public Student createFromParcel(Parcel source) {
            return new Student(source);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };
}
