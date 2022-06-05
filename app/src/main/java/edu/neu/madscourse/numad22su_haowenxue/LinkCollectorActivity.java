package edu.neu.madscourse.numad22su_haowenxue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;

import java.lang.reflect.Array;
import java.util.List;
import java.util.ArrayList;

import android.view.View;
import android.view.LayoutInflater;
import android.widget.Button;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import android.util.Log;
import android.widget.EditText;
import android.content.DialogInterface;

public class LinkCollectorActivity extends AppCompatActivity{
    RecyclerView linksRecyclerView;
    List<ALink> listOfLinks;
    FloatingActionButton fbtnAdd;
    String[] a_input_Name_link= {"", ""};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_collector);
        ActionBar actionBar = getSupportActionBar(); // calling the back bar
        actionBar.setDisplayHomeAsUpEnabled(true); // showing the back bar
        fbtnAdd=(FloatingActionButton) findViewById(R.id.fbtnAdd);

        //start here:
        listOfLinks = new ArrayList<>();
//        List<String> sampleNames = new ArrayList<>(List.of("Aarav", "Beth","Chun","Dasya","Ed","Faith","Gran","Hem","Isaac","Jing","Karl","Liang","Marvin","Nimit"));
//        for (String name : sampleNames) {
//            listOfLinks.add(new ALink(name, "http://www.bing.com"));
//        }

        linksRecyclerView = findViewById(R.id.links_recycler_view);
        linksRecyclerView.setHasFixedSize(true);
        linksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        linksRecyclerView.setAdapter(new ALinkAdapter(listOfLinks, this));

        fbtnAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.i("tag:","float btn clicked!!");
//                openDialog();

                AlertDialog.Builder inputName = new AlertDialog.Builder(LinkCollectorActivity.this);
                inputName.setTitle("Give a Name for the link:");
                final EditText linkNameInput = new EditText(LinkCollectorActivity.this);
                linkNameInput.setInputType(InputType.TYPE_CLASS_TEXT);
                inputName.setView(linkNameInput);

                inputName.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("tag", "Input name: " + linkNameInput.getText());
                        a_input_Name_link[0] = linkNameInput.getText().toString();

                        AlertDialog.Builder inputURL = new AlertDialog.Builder(LinkCollectorActivity.this);
                        inputURL.setTitle("Enter the URL:");
//                        inputURL.setT
                        final EditText linkURLInput = new EditText(LinkCollectorActivity.this);
                        linkURLInput.setInputType(InputType.TYPE_CLASS_TEXT);
                        linkURLInput.setText("https://");
                        inputURL.setView(linkURLInput);

                        inputURL.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                a_input_Name_link[1] = linkURLInput.getText().toString();
                                Log.i("tag", "Input Name & URL: " + a_input_Name_link[0] + a_input_Name_link[1]);
                                if (listOfLinks.add(new ALink(a_input_Name_link[0], a_input_Name_link[1])) == true) {
                                    Snackbar snackbar = Snackbar.make(findViewById(R.id.links_recycler_view), "Add URL Success!", Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                } else {
                                    Snackbar snackbar = Snackbar.make(findViewById(R.id.links_recycler_view), "Add URL Failed!", Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                }
                            }
                        });

                        inputURL.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        inputURL.show();
                    }
                });

                inputName.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                inputName.show();
            };
        });

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
}

//    public void openAddLinkWindow(){
//
//    }
//}