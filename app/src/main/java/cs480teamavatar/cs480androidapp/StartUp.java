package cs480teamavatar.cs480androidapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class StartUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_u);
        Button login = (Button) findViewById(R.id.login2_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartUp.this, LoginPage.class);
                startActivity(intent);
            }
        });

        Button register = (Button) findViewById(R.id.reg1_button);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartUp.this, RegisterPage.class);
                startActivity(intent);
            }
        });
    }
}
