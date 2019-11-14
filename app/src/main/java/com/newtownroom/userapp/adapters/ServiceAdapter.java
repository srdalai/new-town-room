package com.newtownroom.userapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.newtownroom.userapp.R;
import com.newtownroom.userapp.models.ServiceData;
import com.newtownroom.userapp.ui.HotelDetailsNew;

import java.util.ArrayList;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder> {

    private Context mContext;
    private ArrayList<ServiceData> serviceList;

    public ServiceAdapter(Context mContext, ArrayList<ServiceData> serviceList) {
        this.mContext = mContext;
        this.serviceList = serviceList;
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_services_list, parent, false);
        return new ServiceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        ServiceData serviceData = serviceList.get(holder.getAdapterPosition());

        Glide.with(mContext)
                .load(serviceData.getImage())
                .error(R.drawable.ic_action_dinner)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);

        holder.textViewName.setText(serviceData.getName());
        holder.textViewDesc.setText(serviceData.getDescription());
        holder.textViewPrice.setText(mContext.getResources().getString(R.string.rupees_sign) + serviceData.getPrice());

        holder.btnAdd.setOnClickListener((view -> {
            if (mContext instanceof HotelDetailsNew) {
                ((HotelDetailsNew) mContext).addService(holder.getAdapterPosition());
            }
            holder.btnRemove.setVisibility(View.VISIBLE);
            holder.btnAdd.setVisibility(View.GONE);
            if (mContext instanceof HotelDetailsNew) {
                ((HotelDetailsNew) mContext).showSnackBar("\"" + serviceList.get(holder.getAdapterPosition()).getName() + "\" has been added to Extra Services", Snackbar.LENGTH_SHORT);
            }
        }));

        holder.btnRemove.setOnClickListener((view -> {
            if (mContext instanceof HotelDetailsNew) {
                ((HotelDetailsNew) mContext).removeService(holder.getAdapterPosition());
            }
            holder.btnRemove.setVisibility(View.GONE);
            holder.btnAdd.setVisibility(View.VISIBLE);
            if (mContext instanceof HotelDetailsNew) {
                ((HotelDetailsNew) mContext).showSnackBar("\"" + serviceList.get(holder.getAdapterPosition()).getName() + "\" has been removed from Extra Services", Snackbar.LENGTH_SHORT);
            }
        }));

    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    public class ServiceViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textViewName, textViewDesc, textViewPrice;
        MaterialButton btnAdd, btnRemove;

        public ServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewDesc = itemView.findViewById(R.id.textViewDesc);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            btnAdd = itemView.findViewById(R.id.btnAdd);
            btnRemove = itemView.findViewById(R.id.btnRemove);
        }
    }
}
