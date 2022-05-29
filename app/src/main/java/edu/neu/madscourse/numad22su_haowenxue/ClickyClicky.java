package edu.neu.madscourse.numad22su_haowenxue;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.res.Configuration;
import android.widget.Toast;


import androidx.annotation.NonNull;

public class ClickyClicky extends AppCompatActivity {
    private Button btnA,btnB,btnC,btnD,btnE,btnF;
//    private TextView pressedTV = (TextView) findViewById(R.id.pressedTV);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicky_clicky);
        ActionBar actionBar = getSupportActionBar(); // calling the bar
        actionBar.setDisplayHomeAsUpEnabled(true); // showing the bar

        btnA = (Button) findViewById(R.id.btnA);
        btnB = (Button) findViewById(R.id.btnB);
        btnC = (Button) findViewById(R.id.btnC);
        btnD = (Button) findViewById(R.id.btnD);
        btnE = (Button) findViewById(R.id.btnE);
        btnF = (Button) findViewById(R.id.btnF);
        TextView pressedTV= (TextView) findViewById(R.id.pressedTV);


        btnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                changePressedViewText(pressedTV, "A");
            }
        });
        btnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                changePressedViewText(pressedTV, "B");
            }
        });
        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                changePressedViewText(pressedTV, "C");
            }
        });
        btnD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                changePressedViewText(pressedTV, "D");
            }
        });
        btnE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                changePressedViewText(pressedTV, "E");
            }
        });
        btnF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                changePressedViewText(pressedTV, "F");
            };
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        TextView pressedTV= (TextView) findViewById(R.id.pressedTV);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            pressedTV.setText("Pressed: - ");
            Toast.makeText(getApplicationContext(), "Landscape Mode", Toast.LENGTH_LONG)
                    .show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            pressedTV.setText("Pressed: - ");
            Toast.makeText(getApplicationContext(), "Portrait Mode", Toast.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void changePressedViewText(TextView pressedTV, String pressedBtn){
        pressedTV.setText("Pressed: " + pressedBtn);
    }
}