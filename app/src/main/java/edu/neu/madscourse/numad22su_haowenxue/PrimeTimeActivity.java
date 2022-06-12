package edu.neu.madscourse.numad22su_haowenxue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class PrimeTimeActivity extends AppCompatActivity {
    TextView searchLoopText, textPrimeOrNot;
    Button btnFindPrimes, btnTerminateSearch;
    private Handler textHandler = new Handler();
    boolean terminateBtnClicked, findPrimeBtnClicked;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prime_time);
        ActionBar actionBar = getSupportActionBar(); // calling the back bar
        actionBar.setDisplayHomeAsUpEnabled(true); // showing the back bar
        //Acitvity code start here:
        searchLoopText = findViewById(R.id.searchLoopText);
        textPrimeOrNot = findViewById(R.id.textPrimeOrNot);
        btnFindPrimes  = findViewById(R.id.btnFindPrimes);
        btnTerminateSearch  = findViewById(R.id.btnTerminateSearch);
        terminateBtnClicked = false;
        findPrimeBtnClicked = false;

//        SearchLoopThread SearchLoopThread = new SearchLoopThread();
//        btnFindPrimes.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SearchLoopThread.start();
//            }
//        });
        btnTerminateSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                terminateBtnClicked = true;
                findPrimeBtnClicked = false;
            }
        });
    }

//    start running search loop thread & set event clicker in layout button
    public void runSearchLoopThread (View view) {
        SearchLoopThread SearchLoopThread = new SearchLoopThread();
        terminateBtnClicked = false;
        if (findPrimeBtnClicked == true){
            // if the primeBtn is true, it is clicked , no need to start a new thread
            Toast.makeText(getApplicationContext(), "Can't start a new search, please terminate the current search.", Toast.LENGTH_LONG)
                    .show();
        }else {
            // if primeBtn is False, it is not clicked, so start a thread
            findPrimeBtnClicked = true;
            SearchLoopThread.start();
        }
    }

//    public terminateSearch(View view, SearchLoopThread SearchLoopThread){
//        SearchLoopThread.interrupt();
//    }

    //class with search loop thread
    class SearchLoopThread extends Thread {
        @Override
        public void run() {
            int i=2;
            while (!terminateBtnClicked){
                final int outputNum = i;
                textHandler.post(new Runnable() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void run() {
                        searchLoopText.setText( " " + outputNum);
                        if(isPrime(outputNum)){
                            textPrimeOrNot.setText("Yes");
                        }else{
                            textPrimeOrNot.setText("No");
                        }
                    }
                });
                Log.d("PrimeTime:", "runing prime number loop on i" + outputNum);
                try {
                    Thread.sleep(20); //Makes the thread sleep or be inactive for 10 seconds
                } catch (InterruptedException e) {
                    Log.d("ERROR Interrupted:", "Interrupted!");
                    e.printStackTrace();
                }
                i++;
            }
        }
    }



    // following function help identify a number is Prime or not
    public static boolean isPrime(int number) {
        int sqrt = (int) Math.sqrt(number) + 1;
        for (int i = 2; i < sqrt; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onBackPressed(){
        terminateBtnClicked = true;
        if (findPrimeBtnClicked == true) {
            findPrimeBtnClicked = false;
            // if the primeBtn is clicked, it is running search ,so double verify if user want out or not
            final AlertDialog.Builder builder = new AlertDialog.Builder(PrimeTimeActivity.this);
            builder.setMessage("R u sure terminate the search and go back?");
            builder.setCancelable(true);
            builder.setPositiveButton("Terminate & Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    terminateBtnClicked = false;
                    finish();
                }
            });
            builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else {
            // if the primeBtn is not clicked(==false), exit the activity directly
            this.finish();
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}