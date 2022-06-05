package edu.neu.madscourse.numad22su_haowenxue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import java.util.List;
import java.util.ArrayList;

import android.view.View;
import android.view.LayoutInflater;
import android.widget.Button;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.util.Log;
import android.widget.EditText;
import android.content.DialogInterface;



public class LinkCollectorActivity extends AppCompatActivity {
    RecyclerView linksRecyclerView;
    List<ALink> listOfLinks;
    FloatingActionButton fbtnAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_collector);
        ActionBar actionBar = getSupportActionBar(); // calling the back bar
        actionBar.setDisplayHomeAsUpEnabled(true); // showing the back bar
        fbtnAdd=(FloatingActionButton) findViewById(R.id.fbtnAdd);

        //start here:
        listOfLinks = new ArrayList<>();
        List<String> sampleNames = new ArrayList<>(List.of("Aarav", "Beth","Chun","Dasya","Ed","Faith","Gran","Hem","Isaac","Jing","Karl","Liang","Marvin","Nimit"));
        for (String name : sampleNames) {
            listOfLinks.add(new ALink(name, "http://www.bing.com"));
        }

        linksRecyclerView = findViewById(R.id.links_recycler_view);
        linksRecyclerView.setHasFixedSize(true);
        linksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        linksRecyclerView.setAdapter(new ALinkAdapter(listOfLinks, this));

        fbtnAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.i("tag:","float btn clicked!!");

                AlertDialog.Builder linkDialog = new AlertDialog.Builder(LinkCollectorActivity.this);
                linkDialog.setTitle("Please input a link:");
                final EditText linkNameInput = new EditText(LinkCollectorActivity.this);
                linkNameInput.setInputType(InputType.TYPE_CLASS_TEXT);

                final EditText linkURLInput = new EditText(LinkCollectorActivity.this);
                linkURLInput.setInputType(InputType.TYPE_CLASS_TEXT);
                linkDialog.setView(linkURLInput);


                linkDialog.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("tag", "Input text: " + linkURLInput.getText());
                    }
                });

                linkDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                linkDialog.show();
            }
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

//    public void openAddLinkWindow(){
//
//    }
}