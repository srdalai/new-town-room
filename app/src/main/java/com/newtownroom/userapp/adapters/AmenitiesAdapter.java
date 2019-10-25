package com.newtownroom.userapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.newtownroom.userapp.R;
import com.newtownroom.userapp.models.AmenitiesData;

import java.util.ArrayList;

public class AmenitiesAdapter extends RecyclerView.Adapter<AmenitiesAdapter.AmenitiesViewHolder> {

    private Context mContext;
    private ArrayList<AmenitiesData> dataList;

    public AmenitiesAdapter(Context mContext, ArrayList<AmenitiesData> dataList) {
        this.mContext = mContext;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public AmenitiesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_amenities_list_layout, parent, false);
        return new AmenitiesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AmenitiesViewHolder holder, int position) {
        AmenitiesData data = dataList.get(position);

        holder.textView.setText(data.getName());

        holder.imageView.setImageResource(data.getIconDrawable());

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class AmenitiesViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;

        public AmenitiesViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
        }
    }
}
