package cs480teamavatar.cs480androidapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.view.View;
import android.widget.EditText;
import android.os.AsyncTask;
import android.widget.TextView;
import java.sql.*;


public class RegisterPage extends AppCompatActivity {
    private static final String URL = "jdbc:mysql://db.zer0-one.net/tutorWeb";
    private static final String USER = "TeamAvatar";
    private static final String PASS = "Ie5Jaxae";
    private static RadioGroup radioGroup;
    private static RadioButton radioButton;
    private TextView textOut;
    private EditText email;
    private EditText pass;
    private EditText n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        textOut = (TextView) findViewById(R.id.register_text_view);
        textOut.setVisibility(View.INVISIBLE);
    }

    public void onClick(View view) {
        email = (EditText) findViewById(R.id.reg_email_text);
        pass = (EditText) findViewById((R.id.reg_password_text));
        n = (EditText) findViewById(R.id.reg_name_text);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        int selected_id = radioGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(selected_id);
        String username = email.getText().toString();
        String password = pass.getText().toString();
        String name = n.getText().toString();
        String radio = radioButton.getText().toString();
        String[] storage = new String[4];
        storage[0] = username;
        storage[1] = password;
        storage[2] = radio;
        storage[3] = name;
        new RegisterUser().execute(storage);
    }

    private class RegisterUser extends AsyncTask<String, Integer, Integer> {
        @Override
        protected Integer doInBackground(String... params) {
            Connection conn = null;
            Statement stmt = null;
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                conn = DriverManager.getConnection(URL, USER, PASS);
                stmt = conn.createStatement();
                ResultSet rs;
                String sql, queryCheck;
                queryCheck = "SELECT * FROM tutor, student WHERE tutorEmail = '" +
                        params[0] + "' OR studentEmail = '" + params[0] + "'";
                rs = stmt.executeQuery(queryCheck);
                if (!rs.next()) {
                    rs.close();
                    if (params[2].equals("Tutor")) {
                        sql = "INSERT INTO tutor (tutorPassword, tutorName, tutorEmail) " +
                                "VALUES ('" + params[1] + "', '" + params[3] + "', '"
                                + params[0] + "')";
                        stmt.executeUpdate(sql);
                    }
                    else if (params[2].equals("Student")) {
                        sql = "INSERT INTO student (studentPassWord, studentName, " +
                                "studentEmail) VALUES ('" + params[1] + "', '" +
                                params[3] + "', '" + params[0] + "')";
                        stmt.executeUpdate(sql);
                    }
                }
                else {
                    rs.close();
                    return -1;
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
            if (result == 0) {
                startActivity(new Intent(RegisterPage.this, StartUp.class));
            }
            else {
                email.setText("");
                pass.setText("");
                n.setText("");
                textOut.setVisibility(View.VISIBLE);
            }
        }
    }
}

