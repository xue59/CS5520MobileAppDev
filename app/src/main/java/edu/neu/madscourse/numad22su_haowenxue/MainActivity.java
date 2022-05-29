package edu.neu.madscourse.numad22su_haowenxue;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.util.Log;
import android.widget.Toast;
import android.content.Intent;


public class MainActivity extends AppCompatActivity {
    private static final String MyTAG="LogTag";
    private Button btnAboutMe, btnHelloWorld, btnClicky;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnAboutMe = (Button) findViewById(R.id.btnAboutMe); //select the aboutMe button by ID
        btnHelloWorld = (Button) findViewById(R.id.btnHelloWorld); //select the helloWorld button by ID
        btnClicky = (Button) findViewById(R.id.btnClicky); //select clicky button

        btnAboutMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(MyTAG, "clicked about me!");
                Toast.makeText(getApplicationContext(), "Zack-Haowen Xue <xue.haow@northeastern.edu>", Toast.LENGTH_LONG)
                        .show();
            }
        });
        btnHelloWorld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHelloWorldActivity();
            }
        });
        btnClicky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openClickyClickyActivity();
            }
        });

    }
    public void openHelloWorldActivity() {
        Intent intent = new Intent(this, HelloWorldActivity.class);
        startActivity(intent);
    }

    public void openClickyClickyActivity(){
        Intent intent = new Intent(this, ClickyClicky.class);
        startActivity(intent);
    }

}