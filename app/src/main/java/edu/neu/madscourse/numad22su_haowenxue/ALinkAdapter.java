package edu.neu.madscourse.numad22su_haowenxue;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class ALinkAdapter extends RecyclerView.Adapter<ALinkViewHolder>{

    private final List<ALink> listOfLinks;
    private final Context context;

    public ALinkAdapter(List<ALink> listOfLinks, Context context) {
        this.listOfLinks = listOfLinks;
        this.context = context;
    }

    @Override
    @NonNull
    public ALinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ALinkViewHolder(LayoutInflater.from(context).inflate(R.layout.linkname_linkurl, null), context);
    }

    @Override
    public void onBindViewHolder(@NonNull ALinkViewHolder holder, int position) {
        holder.bindNameURL(listOfLinks.get(position));
    }

    @Override
    public int getItemCount(){
        return listOfLinks.size();
    }
}
