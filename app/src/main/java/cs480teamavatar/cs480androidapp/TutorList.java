package cs480teamavatar.cs480androidapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.os.AsyncTask;
import java.util.ArrayList;
import java.sql.*;

public class TutorList extends AppCompatActivity {
    private static final String URL = "jdbc:mysql://db.zer0-one.net/tutorWeb";
    private static final String USER = "TeamAvatar";
    private static final String PASS = "Ie5Jaxae";
    private ArrayList<String> tutorListDisplayName;
    private ArrayList<String> tutorListEmail;
    private ListView tutorListView;
    private Bundle extras;
    private String packType;
    private String searchType;
    private Tutor tutor;
    private Student student;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_list);
        tutorListDisplayName = new ArrayList<String>();
        tutorListEmail = new ArrayList<String>();
        Bundle check = getIntent().getExtras();
        packType = check.getString("packtype");
        searchType = check.getString("searchType");
        extras = new Bundle();
        if (packType.charAt(0) == 't') {
            tutor = check.getParcelable("tutorPackage");
            email = tutor.getEmail();
        }
        else {
            student = check.getParcelable("studentPackage");
            email = student.getEmail();
        }
        new CreateList().execute();
    }

    private void displayList() {
        ArrayAdapter tutorAdapter = new TutorListAdapter(this, tutorListDisplayName, tutorListEmail,
                packType, email);
        tutorListView = (ListView) findViewById(R.id.tutor_list_view);
        tutorListView.setAdapter(tutorAdapter);
    }

    private class CreateList extends AsyncTask<String, Integer, Integer> {
        @Override
        protected Integer doInBackground(String... params) {
            Connection conn = null;
            Statement stmt = null;
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                conn = DriverManager.getConnection(URL, USER, PASS);
                stmt = conn.createStatement();
                String queryCheck = "SELECT tutorEmail, tutorName FROM tutor " +
                        "WHERE tutorSubjects LIKE '%" + searchType + "%'";
                ResultSet rs = stmt.executeQuery(queryCheck);
                while (rs.next()) {
                    tutorListEmail.add(rs.getString("tutorEmail"));
                    tutorListDisplayName.add(rs.getString("tutorName"));
                }
                rs.close();
                stmt.close();
            } catch (SQLException se) {
                se.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        }

        @Override
        protected void onPostExecute(Integer result) {
            displayList();
        }
    }
}
