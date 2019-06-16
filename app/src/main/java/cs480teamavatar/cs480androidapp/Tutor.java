package cs480teamavatar.cs480androidapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Tutor implements Parcelable {
    private int t_id;
    private String t_name;
    private String t_email;
    private String t_subject;
    private String t_address;
    private String t_description;
    private double t_rate;
    private int t_hours;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(t_id);
        out.writeString(t_name);
        out.writeString(t_email);
        out.writeString(t_subject);
        out.writeString(t_address);
        out.writeString(t_description);
        out.writeDouble(t_rate);
        out.writeInt(t_hours);
    }

    public Tutor(int id, String name, String email, String subject, String address,
            String description, double rate, int hours) {
        this.t_id = id;
        this.t_name = name;
        this.t_email = email;
        this.t_subject = subject;
        this.t_address = address;
        this.t_description = description;
        this.t_rate = rate;
        this.t_hours = hours;
    }

    private Tutor(Parcel in) {
        this.t_id = in.readInt();
        this.t_name = in.readString();
        this.t_email = in.readString();
        this.t_subject = in.readString();
        this.t_address = in.readString();
        this.t_description = in.readString();
        this.t_rate = in.readDouble();
        this.t_hours = in.readInt();
    }

    public int getID() {
        return t_id;
    }

    public String getName() {
        return t_name;
    }

    public String getEmail() {
        return t_email;
    }

    public String getSubject() {
        return t_subject;
    }

    public String getAddress() {
        return t_address;
    }

    public String getDescription() {
        return t_description;
    }

    public double getRate() {
        return t_rate;
    }

    public String getRateString() {
        return "" + t_rate + "";
    }

    public String getHoursString() {
        return "" + t_hours + "";
    }

    public int getHours() {
        return t_hours;
    }

    public void setName(String name) {
        t_name = name;
    }

    public void setEmail(String email) {
        t_email = email;
    }

    public void setSubject(String subject) {
        t_subject = subject;
    }

    public void setAddress(String address) {
        t_address = address;
    }

    public void setDescription(String description) {
        t_description = description;
    }

    public void setRate(double rate) {
        t_rate = rate;
    }

    public void setHours(int hour) {
        t_hours = hour;
    }

    public static final Parcelable.Creator<Tutor> CREATOR = new Parcelable.Creator<Tutor>() {

        @Override
        public Tutor createFromParcel(Parcel source) {
            return new Tutor(source);
        }

        @Override
        public Tutor[] newArray(int size) {
            return new Tutor[size];
        }
    };
}
