package cs480teamavatar.cs480androidapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainPage extends AppCompatActivity {
    private static final String URL = "jdbc:mysql://db.zer0-one.net/tutorWeb";
    private static final String USER = "TeamAvatar";
    private static final String PASS = "Ie5Jaxae";
    private Student student;
    private Tutor tutor;
    private Intent intent;
    private String packType;
    private Bundle extras;
    private String search;
    private EditText searchText;
    private TextView invalidSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        Bundle check = getIntent().getExtras();
        packType = check.getString("packtype");
        extras = new Bundle();
        if (packType.charAt(0) == 't') {
            tutor = check.getParcelable("tutorPackage");
            extras.putParcelable("tutorPackage", tutor);
            extras.putString("packtype", packType);
        }
        else {
            student = check.getParcelable("studentPackage");
            extras.putParcelable("studentPackage", student);
            extras.putString("packtype", packType);
        }
        searchText = (EditText) findViewById(R.id.search_edit_text);
        invalidSearch = (TextView) findViewById(R.id.invalid_search_text);
        invalidSearch.setVisibility(View.INVISIBLE);
    }

    public void onClickProfile(View view) {
        intent = new Intent(MainPage.this, Profile.class);
        intent.putExtras(extras);
        startActivity(intent);
    }

    public void onSessionClick(View view) {
        intent = new Intent(MainPage.this, Session.class);
        intent.putExtras(extras);
        startActivity(intent);
    }

    public void onLogoutClick(View view) {
        intent = new Intent(MainPage.this, StartUp.class);
        startActivity(intent);
    }

    public void onSearchClick(View view) {
        if (searchText.getText() != null) {
            search = searchText.getText().toString();
            new SearchEngine().execute();
        }
    }

    private class SearchEngine extends AsyncTask<String, Integer, Integer> {
        @Override
        protected Integer doInBackground(String... params) {
            Connection conn = null;
            Statement stmt = null;
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                conn = DriverManager.getConnection(URL, USER, PASS);
                stmt = conn.createStatement();
                String queryCheck = "SELECT tutorEmail FROM tutor " +
                        "WHERE tutorSubjects LIKE '%" + search + "%'";
                ResultSet rs = stmt.executeQuery(queryCheck);
                if (rs.next()) {
                    intent = new Intent(MainPage.this, TutorList.class);
                    extras.putString("searchType", search);
                    intent.putExtras(extras);
                }
                else {
                    rs.close();
                    stmt.close();
                    conn.close();
                    return -1;
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
            if (result == 0) {
                startActivity(intent);
            }
            else {
                invalidSearch.setVisibility(View.VISIBLE);
            }
        }
    }

}
