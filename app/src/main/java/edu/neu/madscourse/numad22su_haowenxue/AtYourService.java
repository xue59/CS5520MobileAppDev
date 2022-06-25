package edu.neu.madscourse.numad22su_haowenxue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.os.Bundle;
import android.view.View;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ProgressBar;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class AtYourService extends AppCompatActivity {
    private static final String TAG="Zack At your service activity";

    public EditText inputZip;
    public Button btnLookupZip;
    ProgressBar loadingCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_at_your_service);
        ActionBar actionBar = getSupportActionBar(); // calling the bar
        actionBar.setDisplayHomeAsUpEnabled(true); // showing the bar

        //code start here:

//        if (savedInstanceState != null){
//
//        }

        inputZip = (EditText) findViewById(R.id.inputZip);
        btnLookupZip = (Button) findViewById(R.id.btnLookupZip);
        loadingCircle =(ProgressBar)findViewById(R.id.loadingCircle); // initiate the active indicator

        btnLookupZip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callZipcodebaseLookupHelper();
                loadingCircle.setVisibility(View.VISIBLE);
            }
        });

    }

    public void callZipcodebaseLookupHelper() {
        LookupZipService lookupZipService = new LookupZipService();
        String url_zip = "https://app.zipcodebase.com/api/v1/search?apikey=fb8b3a70-f457-11ec-84b5-351a69c8a7b1&country=us&codes="
                + inputZip.getText().toString();
        //Log.d("zipcode", url_zip);
        try {
//            String url = NetworkUtil.validInput(url_zip);
            lookupZipService.execute(url_zip);
        } catch (Exception e){
            Toast.makeText(getApplication(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private class LookupZipService extends AsyncTask<String, Integer, JSONObject> {
        //public TextView country, zip, latitude, longitude, city, state, county;
        TextView country = (TextView) findViewById(R.id.country);
        TextView zip = (TextView) findViewById(R.id.zip);
        TextView latitude = (TextView) findViewById(R.id.latitude);
        TextView longitude = (TextView) findViewById(R.id.longitude);
        TextView city = (TextView) findViewById(R.id.city);
        TextView state = (TextView) findViewById(R.id.state);
        TextView county = (TextView) findViewById(R.id.county);

        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.d("into pon progress", "Processing...");
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject jObject = new JSONObject();
            try {
                URL url = new URL(params[0]);
                String resp = httpResponse(url);

                jObject = new JSONObject(resp);
                return jObject;
            } catch (MalformedURLException e) {
                Log.e(TAG, "MalformedURLException");
                e.printStackTrace();
            } catch (IOException e) {
                Log.e(TAG, "IOException");
                e.printStackTrace();
            } catch (JSONException e) {
                Log.e(TAG, "JSONException");
                e.printStackTrace();
            }

            return jObject;
        }

        @Override
        protected void onPostExecute(JSONObject jObject) {
            super.onPostExecute(jObject);
            //TextView result_view = (TextView)findViewById(R.id.country);

            String query_zip= null;

            try {
                query_zip = jObject.getJSONObject("query").getJSONArray("codes").get(0).toString();
                Log.d("test zip", query_zip);
                country.setText( "USA");
                zip.setText(jObject.getJSONObject("results").getJSONArray(query_zip).getJSONObject(0).getString("postal_code"));
                latitude.setText(jObject.getJSONObject("results").getJSONArray(query_zip).getJSONObject(0).getString("latitude"));
                longitude.setText(jObject.getJSONObject("results").getJSONArray(query_zip).getJSONObject(0).getString("longitude"));
                city.setText(jObject.getJSONObject("results").getJSONArray(query_zip).getJSONObject(0).getString("city"));
                state.setText(jObject.getJSONObject("results").getJSONArray(query_zip).getJSONObject(0).getString("state"));
                county.setText(jObject.getJSONObject("results").getJSONArray(query_zip).getJSONObject(0).getString("province"));
                //Log.d("zack_test: ", "" + jObject.getJSONArray("results").toString());
            } catch (JSONException e) {
                //country.setText("Something went wrong!");
                Log.d("onPost Error", "invalid zip code");
                country.setText( "-");
                zip.setText( "-");
                latitude.setText( "-");
                longitude.setText( "-");
                city.setText( "-");
                state.setText( "-");
                county.setText( "-");
                final AlertDialog.Builder builder = new AlertDialog.Builder(AtYourService.this);
                builder.setMessage("Invalid ZIP Code \nPlease enter a valid 5-diget US ZIP code. ");
                builder.setNegativeButton("Got it!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
            loadingCircle.setVisibility(View.INVISIBLE);
        }
    }
    // convert stream to string
    public static String convertStreamToString(InputStream inputStream){
        StringBuilder stringBuilder=new StringBuilder();
        try {
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            String len;
            while((len=bufferedReader.readLine())!=null){
                stringBuilder.append(len);
            }
            bufferedReader.close();
            return stringBuilder.toString().replace(",", ",\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    //following code sent a http request
    public static String httpResponse(URL url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.connect();

        InputStream inputStream = conn.getInputStream();
        String resp = convertStreamToString(inputStream);

        return resp;
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

    // following function save the information on the screen before it distoried
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        TextView country = (TextView) findViewById(R.id.country);
        outState.putString("country", country.getText().toString());

        TextView zip = (TextView) findViewById(R.id.zip);
        outState.putString("zip", zip.getText().toString());

        TextView latitude = (TextView) findViewById(R.id.latitude);
        outState.putString("latitude", latitude.getText().toString());

        TextView longitude = (TextView) findViewById(R.id.longitude);
        outState.putString("longitude", longitude.getText().toString());

        TextView city = (TextView) findViewById(R.id.city);
        outState.putString("city", city.getText().toString());

        TextView state = (TextView) findViewById(R.id.state);
        outState.putString("state", state.getText().toString());

        TextView county = (TextView) findViewById(R.id.county);
        outState.putString("county", county.getText().toString());
    }
    //following code restore the saved view datas
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Toast.makeText(getApplicationContext(), "Screen Rotated", Toast.LENGTH_SHORT).show();

        TextView country = (TextView) findViewById(R.id.country);
        country.setText(savedInstanceState.getString("country"));

        TextView zip = (TextView) findViewById(R.id.zip);
        zip.setText(savedInstanceState.getString("zip"));

        TextView latitude = (TextView) findViewById(R.id.latitude);
        latitude.setText(savedInstanceState.getString("latitude"));

        TextView longitude = (TextView) findViewById(R.id.longitude);
        longitude.setText(savedInstanceState.getString("longitude"));

        TextView city = (TextView) findViewById(R.id.city);
        city.setText(savedInstanceState.getString("city"));

        TextView state = (TextView) findViewById(R.id.state);
        state.setText(savedInstanceState.getString("state"));

        TextView county = (TextView) findViewById(R.id.county);
        county.setText(savedInstanceState.getString("county"));
    }
}

