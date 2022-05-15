package edu.neu.madscourse.numad22su_haowenxue;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.util.Log;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private static final String MyTAG="LogTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnAboutMe = (Button) findViewById(R.id.btnAboutMe); //select the button by ID
        btnAboutMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(MyTAG,"clicked about me!");
                Toast.makeText(getApplicationContext(), "Zack-Haowen Xue <xue.haow@northeastern.edu>", Toast.LENGTH_LONG)
                        .show();
            }
        });



    }
}