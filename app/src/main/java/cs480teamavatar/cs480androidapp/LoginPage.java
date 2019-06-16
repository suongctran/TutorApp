package cs480teamavatar.cs480androidapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.content.Intent;
import java.sql.*;

public class LoginPage extends AppCompatActivity {
    private static final String URL = "jdbc:mysql://db.zer0-one.net/tutorWeb";
    private static final String USER = "TeamAvatar";
    private static final String PASS = "Ie5Jaxae";
    private EditText email;
    private EditText pass;
    private TextView textOut;
    private Intent intent;
    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        textOut = (TextView) findViewById(R.id.login_text_view);
        textOut.setVisibility(View.INVISIBLE);
    }

    public void onClick(View view) {
        email = (EditText) findViewById(R.id.login_email_text);
        pass = (EditText) findViewById(R.id.login_password_text);
        String username = email.getText().toString();
        String password = pass.getText().toString();
        String[] storage = new String[2];
        storage[0] = username;
        storage[1] = password;
        new LoginUser().execute(storage);
    }

    private class LoginUser extends AsyncTask<String, Integer, Integer> {
        @Override
        protected Integer doInBackground(String... params) {
            Connection conn = null;
            Statement stmt = null;
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                conn = DriverManager.getConnection(URL, USER, PASS);
                stmt = conn.createStatement();
                String queryCheck = "SELECT * FROM student WHERE studentEmail = '" + params[0]
                        + "' AND studentPassword = '" + params[1] + "'";
                ResultSet rs = stmt.executeQuery(queryCheck);
                if (rs.next()) {
                    Student student = new Student(rs.getInt("studentID"),
                            rs.getString("studentName"), rs.getString("studentEmail"),
                            rs.getString("studentAddress"), rs.getString("studentSubjects"));
                    intent = new Intent(LoginPage.this, MainPage.class);
                    extras = new Bundle();
                    extras.putString("packtype", "student");
                    extras.putParcelable("studentPackage", student);
                    intent.putExtras(extras);
                    rs.close();
                    return 0;
                }
                rs.close();
                queryCheck = "SELECT * FROM tutor where tutorEmail = '" + params[0] +
                            "' AND tutorPassword = '" + params[1] + "'";
                rs = stmt.executeQuery(queryCheck);
                if (rs.next()) {
                    Tutor tutor = new Tutor(rs.getInt("tutorID"), rs.getString("tutorName"),
                            rs.getString("tutorEmail"), rs.getString("tutorAddress"),
                            rs.getString("tutorSubjects"), rs.getString("tutorDescription"),
                            rs.getDouble("tutorRatePerHour"), rs.getInt("tutortotalHours"));
                    intent = new Intent(LoginPage.this, MainPage.class);
                    extras = new Bundle();
                    extras.putString("packtype", "tutor");
                    extras.putParcelable("tutorPackage", tutor);
                    intent.putExtras(extras);
                    rs.close();
                    return 0;
                }
                rs.close();
                stmt.close();
                conn.close();
                return -1;
            } catch (SQLException se) {
                se.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result == 0) {
                startActivity(intent);
            }
            else {
                email.setText("");
                pass.setText("");
                textOut.setVisibility(View.VISIBLE);
            }
        }
    }
}