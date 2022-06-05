package edu.neu.madscourse.numad22su_haowenxue;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Button;
import android.util.Log;
import android.content.Intent;
import android.net.Uri;
import android.content.Context;

import com.google.android.material.snackbar.Snackbar;


public class ALinkViewHolder extends RecyclerView.ViewHolder{
    public TextView linkNameTV;
    public TextView linkURLTV;
    public Button btnGo;
    public ALink aLink;
    private Context context;

    public ALinkViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.linkNameTV = itemView.findViewById(R.id.linkName);
        this.linkURLTV  = itemView.findViewById(R.id.linkURL);
        this.context = context;
        btnGo = (Button) itemView.findViewById(R.id.btnGo);

        itemView.findViewById(R.id.linkURL).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("URL: ", "clicked " + aLink.getLinkName() );
                gotoURL(aLink.getLinkURL());
            }
        });

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Go: ", "clicked " + aLink.getLinkName() );
                gotoURL(aLink.getLinkURL());
            }
        });
    }
    public void bindNameURL(ALink tobeBindedLink) {
        linkNameTV.setText(tobeBindedLink.getLinkName());
        linkURLTV.setText(tobeBindedLink.getLinkURL());
        aLink = tobeBindedLink;
    }

    private void gotoURL(String s){
      Uri uri;
        if (s.startsWith("https://") || s.startsWith("http://")){
            uri=Uri.parse(s);
        }else{
            uri=Uri.parse("https://" + s);
        }
        try{
            context.startActivity(new Intent(Intent.ACTION_VIEW, uri));
        } catch (Exception e) {
            Log.i("Error gotoURL", "Wrong URL");
//            Snackbar snackbar = Snackbar.make(findViewById(R.id.links_recycler_view), "Add URL Success!", Snackbar.LENGTH_LONG);
//            snackbar.show();
        }
    }
}
