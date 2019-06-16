package cs480teamavatar.cs480androidapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.content.Intent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Profile extends AppCompatActivity {
    private static final String URL = "jdbc:mysql://db.zer0-one.net/tutorWeb";
    private static final String USER = "TeamAvatar";
    private static final String PASS = "Ie5Jaxae";
    private EditText status;
    private EditText name;
    private EditText email;
    private EditText address;
    private EditText subject;
    private EditText description;
    private EditText rate;
    private EditText totalHours;
    private Tutor tutor;
    private Student student;
    private Intent intent;
    private Bundle extras;
    private String packType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Bundle pack = getIntent().getExtras();
        packType = pack.getString("packtype");
        status = (EditText) findViewById(R.id.p_status_edit_text);
        name = (EditText) findViewById(R.id.name_edit_text);
        email = (EditText) findViewById(R.id.email_edit_text);
        address = (EditText) findViewById(R.id.address_edit_text);
        subject = (EditText) findViewById(R.id.subject_edit_text);
        description = (EditText) findViewById(R.id.description_edit_text);
        rate = (EditText) findViewById(R.id.rate_edit_text);
        totalHours = (EditText) findViewById(R.id.total_hours_edit_text);
        if (packType.charAt(0) == 't') {
            tutor = pack.getParcelable("tutorPackage");
            status.setText("Tutor");
            name.setText(tutor.getName());
            email.setText(tutor.getEmail());
            address.setText(tutor.getAddress());
            subject.setText(tutor.getSubject());
            description.setText(tutor.getDescription());
            rate.setText(tutor.getRateString());
            totalHours.setText(tutor.getHoursString());
        }
        else {
            TextView d = (TextView) findViewById(R.id.p_des_text_view);
            d.setVisibility(View.INVISIBLE);
            TextView r = (TextView) findViewById(R.id.p_rate_text_view);
            r.setVisibility(View.INVISIBLE);
            TextView h = (TextView) findViewById(R.id.p_th_text_view);
            h.setVisibility(View.INVISIBLE);
            description.setVisibility(View.INVISIBLE);
            rate.setVisibility(View.INVISIBLE);
            totalHours.setVisibility(View.INVISIBLE);
            status.setText("Student");
            student = pack.getParcelable("studentPackage");
            name.setText(student.getName());
            email.setText(student.getEmail());
            address.setText(student.getAddress());
            subject.setText(student.getSubject());
        }
    }

    public void onBackClick(View view) {
        intent = new Intent(Profile.this, MainPage.class);
        extras = new Bundle();
        if (packType.charAt(0) == 't') {
            extras.putString("packtype", packType);
            extras.putParcelable("tutorPackage", tutor);
            intent.putExtras(extras);
        }
        else {
            extras.putString("packtype", packType);
            extras.putParcelable("studentPackage", student);
            intent.putExtras(extras);
        }
        startActivity(intent);
    }

    public void onSaveEditClick(View view) {
        EditText saveName = (EditText) findViewById(R.id.name_edit_text);
        EditText saveEmail = (EditText) findViewById(R.id.email_edit_text);
        EditText saveAddress = (EditText) findViewById(R.id.address_edit_text);
        EditText saveSubject = (EditText) findViewById(R.id.subject_edit_text);
        EditText saveDescription = (EditText) findViewById(R.id.description_edit_text);
        EditText saveRate = (EditText) findViewById(R.id.rate_edit_text);
        EditText saveHours = (EditText) findViewById(R.id.total_hours_edit_text);
        String[] paramToSaveEdit;
        intent = new Intent(Profile.this, Profile.class);
        extras = new Bundle();
        switch (packType) {
            case "tutor":
                if (!saveName.getText().toString().equals("")) {
                    name.setText(saveName.getText().toString());
                    tutor.setName(saveName.getText().toString());
                }
                else if (saveName.getText().toString().equals("")) {
                    name.setText("");
                    tutor.setName("");
                }
                if (!saveEmail.getText().toString().equals("")) {
                    email.setText(saveEmail.getText().toString());
                    tutor.setEmail(saveEmail.getText().toString());
                }
                else if (saveEmail.getText().toString().equals("")) {
                    email.setText("");
                    tutor.setEmail("");
                }
                if (!saveAddress.getText().toString().equals("")) {
                    address.setText(saveAddress.getText().toString());
                    tutor.setAddress(saveAddress.getText().toString());
                }
                else if (saveAddress.getText().toString().equals("")) {
                    address.setText("");
                    tutor.setAddress("");
                }
                if (!saveSubject.getText().toString().equals("")) {
                    subject.setText(saveSubject.getText().toString());
                    tutor.setSubject(saveSubject.getText().toString());
                }
                else if (saveSubject.getText().toString().equals("")) {
                    subject.setText("");
                    tutor.setSubject("");
                }
                if (!saveDescription.getText().toString().equals("")) {
                    description.setText(saveDescription.getText().toString());
                    tutor.setDescription(saveDescription.getText().toString());
                }
                else if (saveDescription.getText().toString().equals("")) {
                    description.setText("");
                    tutor.setDescription("");
                }
                if (!saveRate.getText().toString().equals("")) {
                    rate.setText(saveRate.getText().toString());
                    tutor.setRate(Double.parseDouble(saveRate.getText().toString()));
                }
                else if (saveRate.getText().toString().equals("")) {
                    rate.setText("0.0");
                    tutor.setRate(0.0);
                }
                if (!saveHours.getText().toString().equals("")) {
                    totalHours.setText(saveHours.getText().toString());
                    tutor.setHours(Integer.parseInt(saveHours.getText().toString()));
                }
                else if (saveHours.getText().toString().equals("")) {
                    totalHours.setText("0");
                    tutor.setHours(0);
                }
                extras.putString("packtype", packType);
                extras.putParcelable("tutorPackage", tutor);
                intent.putExtras(extras);
                paramToSaveEdit = new String[8];
                paramToSaveEdit[0] = packType;
                paramToSaveEdit[1] = name.getText().toString();
                paramToSaveEdit[2] = email.getText().toString();
                paramToSaveEdit[3] = address.getText().toString();
                paramToSaveEdit[4] = subject.getText().toString();
                paramToSaveEdit[5] = description.getText().toString();
                paramToSaveEdit[6] = rate.getText().toString();
                paramToSaveEdit[7] = totalHours.getText().toString();
                new SaveEdit().execute(paramToSaveEdit);
                break;
            case "student":
                if (!saveName.getText().toString().equals("")) {
                    name.setText(saveName.getText().toString());
                    student.setName(saveName.getText().toString());
                }
                else if (saveName.getText().toString().equals("")) {
                    name.setText("");
                    student.setName("");
                }
                if (!saveEmail.getText().toString().equals("")) {
                    email.setText(saveEmail.getText().toString());
                    student.setEmail(saveEmail.getText().toString());
                }
                else if (saveEmail.getText().toString().equals("")) {
                    email.setText("");
                    student.setEmail("");
                }
                if (!saveAddress.getText().toString().equals("")) {
                    address.setText(saveAddress.getText().toString());
                    student.setAddress(saveAddress.getText().toString());
                }
                else if (saveAddress.getText().toString().equals("")) {
                    address.setText("");
                    student.setAddress("");
                }
                if (!saveSubject.getText().toString().equals("")) {
                    subject.setText(saveSubject.getText().toString());
                    student.setSubject(saveSubject.getText().toString());
                }
                else if (saveSubject.getText().toString().equals("")) {
                    subject.setText("");
                    student.setSubject("");
                }
                extras.putString("packtype", packType);
                extras.putParcelable("studentPackage", student);
                intent.putExtras(extras);
                paramToSaveEdit = new String[5];
                paramToSaveEdit[0] = packType;
                paramToSaveEdit[1] = name.getText().toString();
                paramToSaveEdit[2] = email.getText().toString();
                paramToSaveEdit[3] = address.getText().toString();
                paramToSaveEdit[4] = subject.getText().toString();
                new SaveEdit().execute(paramToSaveEdit);
                break;
        }
    }

    private class SaveEdit extends AsyncTask<String, Integer, Integer> {
        @Override
        protected Integer doInBackground(String... params) {
            Connection conn = null;
            Statement stmt = null;
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                conn = DriverManager.getConnection(URL, USER, PASS);
                stmt = conn.createStatement();
                String queryUpdate = "";
                if (params[0].equals("tutor")) {
                    queryUpdate = "UPDATE tutor SET tutorName = '" + params[1] +
                            "', tutorEmail = '" + params[2] + "', tutorAddress = '" +
                            params[3] + "', tutorSubjects = '" + params[4] +
                            "', tutorDescription = '" + params[5] + "', tutorRatePerHour = " +
                            Double.parseDouble(params[6]) + ", tutortotalHours = " +
                            Integer.parseInt(params[7]) + " WHERE tutorID = " + tutor.getID();
                }
                else {
                    queryUpdate = "UPDATE student SET studentName = '" + params[1] +
                            "', studentEmail = '" + params[2] + "', studentAddress = '" +
                            params[3] + "', studentSubjects = '" + params[4] +
                            "', tutorDescription = '" + params[5] + "' WHERE studentID = " +
                            student.getID();
                }
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
            startActivity(intent);
        }
    }
}
