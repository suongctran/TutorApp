package cs480teamavatar.cs480androidapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;
import android.content.Intent;
import java.sql.*;

public class ViewProfile extends AppCompatActivity {
    private static final String URL = "jdbc:mysql://db.zer0-one.net/tutorWeb";
    private static final String USER = "TeamAvatar";
    private static final String PASS = "Ie5Jaxae";
    private TextView status;
    private TextView name;
    private TextView email;
    private TextView address;
    private TextView subject;
    private TextView description;
    private TextView rate;
    private TextView hours;
    private String n;
    private String e;
    private String a;
    private String s;
    private String d;
    private double r;
    private int h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        status = (TextView) findViewById(R.id.vp_status_text_view);
        status.setText("Tutor");
        name = (TextView) findViewById(R.id.vp_name_text_view);
        email = (TextView) findViewById(R.id.vp_email_text_view);
        address = (TextView) findViewById(R.id.vp_address_text_view);
        subject = (TextView) findViewById(R.id.vp_subject_text_view);
        description = (TextView) findViewById(R.id.vp_description_text_view);
        rate = (TextView) findViewById(R.id.vp_rate_text_view);
        hours = (TextView) findViewById(R.id.vp_hours_text_view);
        Intent intent = getIntent();
        e = intent.getStringExtra("email");
        new ProvideInformation().execute();
    }

    private void setText() {
        name.setText(n);
        email.setText(e);
        address.setText(a);
        subject.setText(s);
        description.setText(d);
        rate.setText("" + r + "");
        hours.setText("" + h + "");
    }

    private class ProvideInformation extends AsyncTask<String, Integer, Integer> {
        @Override
        protected Integer doInBackground(String... params) {
            Connection conn = null;
            Statement stmt = null;
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                conn = DriverManager.getConnection(URL, USER, PASS);
                stmt = conn.createStatement();
                String queryCheck = "SELECT * FROM tutor WHERE tutorEmail = '" + e + "'";
                ResultSet rs = stmt.executeQuery(queryCheck);
                while (rs.next()) {
                    n = rs.getString("tutorName");
                    a = rs.getString("tutorAddress");
                    s = rs.getString("tutorSubjects");
                    d = rs.getString("tutorDescription");
                    r = rs.getDouble("tutorRatePerHour");
                    h = rs.getInt("tutortotalHours");
                }
                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        }

        @Override
        protected void onPostExecute(Integer result) {
            setText();
        }
    }

}
