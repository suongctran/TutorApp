package cs480teamavatar.cs480androidapp;

import android.widget.ArrayAdapter;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Session extends AppCompatActivity {
    private static final String URL = "jdbc:mysql://db.zer0-one.net/tutorWeb";
    private static final String USER = "TeamAvatar";
    private static final String PASS = "Ie5Jaxae";
    private String packType;
    private int id;
    private Student student;
    private Tutor tutor;
    private ArrayList<String> names;
    private ArrayList<Integer> ids;
    private ArrayList<String> dates;
    private ArrayList<String> subjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);
        Bundle check = getIntent().getExtras();
        packType = check.getString("packtype");
        ids = new ArrayList<Integer>();
        names = new ArrayList<String>();
        subjects = new ArrayList<String>();
        dates = new ArrayList<String>();
        if (packType.charAt(0) == 't') {
            tutor = check.getParcelable("tutorPackage");
            id = tutor.getID();
        }
        else {
            student = check.getParcelable("studentPackage");
            id = student.getID();
        }
        new SearchIDs().execute();
        new SearchNames().execute();
    }

    private void displayList() {
        ArrayAdapter arrayAdapter = new SessionListAdapter(this, names, dates, subjects);
        ListView listView = (ListView) findViewById(R.id.session_list_view);
        listView.setAdapter(arrayAdapter);
    }

    private class SearchIDs extends AsyncTask<String, Integer, Integer> {
        @Override
        protected Integer doInBackground(String... params) {
            Connection conn = null;
            Statement stmt = null;
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                conn = DriverManager.getConnection(URL, USER, PASS);
                stmt = conn.createStatement();
                if (packType.charAt(0) == 't') {
                    String queryCheck = "SELECT * FROM sessions WHERE" +
                            " tutorID = " + id;
                    ResultSet rs = stmt.executeQuery(queryCheck);
                    while (rs.next()) {
                        ids.add(rs.getInt("studentID"));
                        subjects.add(rs.getString("sessionSubject"));
                        dates.add(rs.getString("sessionDate"));
                    }
                    rs.close();
                }
                else {
                    String queryCheck = "SELECT * FROM sessions WHERE" +
                            " studentID = " + id;
                    ResultSet rs = stmt.executeQuery(queryCheck);
                    while (rs.next()) {
                        ids.add(rs.getInt("tutorID"));
                        subjects.add(rs.getString("sessionSubject"));
                        dates.add(rs.getString("sessionDate"));
                    }
                    rs.close();
                }
                stmt.close();
            } catch (SQLException se) {
                se.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        }
    }

    private class SearchNames extends AsyncTask<String, Integer, Integer> {
        @Override
        protected Integer doInBackground(String... params) {
            Connection conn = null;
            Statement stmt = null;
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                conn = DriverManager.getConnection(URL, USER, PASS);
                stmt = conn.createStatement();
                int index = 0;
                if (packType.charAt(0) == 't') {
                    while (index < ids.size()) {
                        String queryCheck = "SELECT * FROM student WHERE" +
                                " studentID = " + ids.get(index++);
                        ResultSet rs = stmt.executeQuery(queryCheck);
                        if (rs.next()) {
                            names.add(rs.getString("studentName"));
                        }
                        rs.close();
                    }
                }
                else {
                    while (index < ids.size()) {
                        String queryCheck = "SELECT * FROM tutor WHERE" +
                                " tutorID = " + ids.get(index++);
                        ResultSet rs = stmt.executeQuery(queryCheck);
                        if (rs.next()) {
                            names.add(rs.getString("tutorName"));
                        }
                        rs.close();
                    }
                }
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
