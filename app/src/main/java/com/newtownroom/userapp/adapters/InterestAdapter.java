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
import com.newtownroom.userapp.models.LocalInterest;

import java.util.ArrayList;

public class InterestAdapter extends RecyclerView.Adapter<InterestAdapter.InterestViewHolder> {

    private Context mContext;
    private ArrayList<LocalInterest> interests;

    public InterestAdapter(Context mContext, ArrayList<LocalInterest> interests) {
        this.mContext = mContext;
        this.interests = interests;
    }

    @NonNull
    @Override
    public InterestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_interest_list, parent, false);
        return new InterestViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InterestViewHolder holder, int position) {
        LocalInterest interest = interests.get(holder.getAdapterPosition());

        holder.textView.setText(interest.getName());
        Glide.with(mContext)
                .load(interest.getImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return interests.size();
    }

    public class InterestViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView;

        public InterestViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
