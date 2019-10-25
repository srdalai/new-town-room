package com.newtownroom.userapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.newtownroom.userapp.R;
import com.newtownroom.userapp.models.BookingData;

import java.util.ArrayList;

public class MyBookingsAdapter extends RecyclerView.Adapter<MyBookingsAdapter.MyBookingsViewHolder> {

    private Context mContext;
    private ArrayList<BookingData> dataList;

    public MyBookingsAdapter(Context mContext, ArrayList<BookingData> dataList) {
        this.mContext = mContext;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyBookingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_bookings_layout, parent, false);
        return new MyBookingsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyBookingsViewHolder holder, int position) {

        Glide.with(mContext).load(R.drawable.room_one).circleCrop().into(holder.imageViewRoom);

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class MyBookingsViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewRoom;

        public MyBookingsViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewRoom = itemView.findViewById(R.id.imageViewRoom);
        }
    }
}
