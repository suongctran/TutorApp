package cs480teamavatar.cs480androidapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import android.view.View;
import android.widget.TextView;
import android.content.Intent;
import android.widget.EditText;

public class SetAppointment extends AppCompatActivity {
    private static final String URL = "jdbc:mysql://db.zer0-one.net/tutorWeb";
    private static final String USER = "TeamAvatar";
    private static final String PASS = "Ie5Jaxae";
    private String studentEmail;
    private String tutorEmail;
    private String tutorName;
    private String date;
    private String subject;
    private int tutorID;
    private int studentID;
    private TextView tutorText;
    private TextView subjectText;
    private Student student;
    private Bundle extras;
    private EditText dateText;
    private TextView invalid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_appointment);
        Bundle check = getIntent().getExtras();
        studentEmail = check.getString("studentEmail");
        tutorEmail = check.getString("tutorEmail");
        tutorText = (TextView) findViewById(R.id.tutor_name_text_view);
        subjectText = (TextView) findViewById(R.id.subject_name_text_view);
        invalid = (TextView) findViewById(R.id.invalid_date_text);
        invalid.setVisibility(View.INVISIBLE);
        new GetInfo().execute();
        dateText = (EditText) findViewById(R.id.date_edit_text);
    }

    public void onSetAppointmentClick(View view) {
        if (dateText.getText().toString().equals("")) {
            invalid.setVisibility(View.VISIBLE);
            dateText.setText("");
        }
        else {
            date = dateText.getText().toString();
            new AddToSession().execute();
        }
    }

    private class GetInfo extends AsyncTask<String, Integer, Integer> {
        @Override
        protected Integer doInBackground(String... params) {
            Connection conn = null;
            Statement stmt = null;
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                conn = DriverManager.getConnection(URL, USER, PASS);
                stmt = conn.createStatement();
                String queryCheck = "SELECT tutorID, tutorName, tutorSubjects FROM tutor WHERE " +
                        "tutorEmail = '" + tutorEmail + "'";
                ResultSet rs = stmt.executeQuery(queryCheck);
                if (rs.next()) {
                    tutorName = rs.getString("tutorName");
                    tutorID = rs.getInt("tutorID");
                    subject = rs.getString("tutorSubjects");
                }
                rs.close();
                queryCheck = "SELECT * FROM student WHERE studentEmail = '" + studentEmail + "'";
                rs = stmt.executeQuery(queryCheck);
                if (rs.next()) {
                    Student student = new Student(rs.getInt("studentID"),
                            rs.getString("studentName"), rs.getString("studentEmail"),
                            rs.getString("studentAddress"), rs.getString("studentSubjects"));
                    studentID = student.getID();
                    extras = new Bundle();
                    extras.putString("packtype", "student");
                    extras.putParcelable("studentPackage", student);
                }
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
            tutorText.setText(tutorName);
            subjectText.setText(subject);
        }
    }

    private class AddToSession extends AsyncTask<String, Integer, Integer> {
        @Override
        protected Integer doInBackground(String... params) {
            Connection conn = null;
            Statement stmt = null;
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                conn = DriverManager.getConnection(URL, USER, PASS);
                stmt = conn.createStatement();
                String queryUpdate = "INSERT INTO sessions (tutorID, studentID, sessionSubject, " +
                        " sessionDate) VALUES (" + tutorID + ", " + studentID + ", '" +
                        subject + "', '" + date + "')";
                stmt.executeUpdate(queryUpdate);
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
            Intent intent = new Intent(SetAppointment.this, MainPage.class);
            intent.putExtras(extras);
            startActivity(intent);
        }
    }
}
