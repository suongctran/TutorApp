package cs480teamavatar.cs480androidapp;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import android.widget.ArrayAdapter;

public class SessionListAdapter extends ArrayAdapter<String> {
    private static final String URL = "jdbc:mysql://db.zer0-one.net/tutorWeb";
    private static final String USER = "TeamAvatar";
    private static final String PASS = "Ie5Jaxae";
    private ArrayList<String> name;
    private ArrayList<String> date;
    private ArrayList<String> subject;
    private Bundle extras;
    private String packType;
    private String currentEmail;

    SessionListAdapter(Context context, ArrayList<String> name, ArrayList<String> date,
                       ArrayList<String> subject) {
        super(context, R.layout.session_list_adapter, name);
        this.name = name;
        this.date = date;
        this.subject = subject;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater listInflater = LayoutInflater.from(getContext());
        View customView = listInflater.inflate(R.layout.session_list_adapter, parent, false);
        String theName = getItem(position);
        String theSubject = subject.get(position);
        String theDate = date.get(position);
        TextView nameText = (TextView) customView.findViewById(R.id.session_adapter_name);
        TextView subjectText = (TextView) customView.findViewById(R.id.session_adapter_subject);
        TextView dateText = (TextView) customView.findViewById(R.id.session_adapter_date);
        nameText.setText(theName);
        subjectText.setText(theSubject);
        dateText.setText(theDate);
        return customView;
    }

}
