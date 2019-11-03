package com.newtownroom.userapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.newtownroom.userapp.R;
import com.newtownroom.userapp.models.NearBy;

import java.util.ArrayList;

public class NearByAdapter extends RecyclerView.Adapter<NearByAdapter.NearByViewHolder> {

    private Context mContext;
    private ArrayList<NearBy> nearByList;

    public NearByAdapter(Context mContext, ArrayList<NearBy> nearByList) {
        this.mContext = mContext;
        this.nearByList = nearByList;
    }

    @NonNull
    @Override
    public NearByViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nearby_list, parent, false);
        return new NearByViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NearByViewHolder holder, int position) {

        NearBy nearBy = nearByList.get(holder.getAdapterPosition());

        holder.textViewName.setText(nearBy.getName());
        holder.textViewDist.setText(nearBy.getDistance());
        Glide.with(mContext)
                .load(nearBy.getImage())
                .error(R.drawable.background_drawable)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return nearByList.size();
    }

    public class NearByViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textViewName, textViewDist;

        public NearByViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewDist = itemView.findViewById(R.id.textViewDist);

        }
    }
}
