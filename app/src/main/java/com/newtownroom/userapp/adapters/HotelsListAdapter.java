package com.newtownroom.userapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
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
import com.newtownroom.userapp.models.HotelData;
import com.newtownroom.userapp.ui.HotelDetails;
import com.newtownroom.userapp.ui.HotelDetailsNew;

import java.util.ArrayList;

public class HotelsListAdapter extends RecyclerView.Adapter<HotelsListAdapter.HotelsListViewHolder> {

    private Context mContext;
    private ArrayList<HotelData> hotelDataList;

    public HotelsListAdapter(Context mContext, ArrayList<HotelData> hotelDataList) {
        this.mContext = mContext;
        this.hotelDataList = hotelDataList;
    }

    @NonNull
    @Override
    public HotelsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hotel_list_layout, parent, false);
        return new HotelsListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelsListViewHolder holder, int position) {
        HotelData hotelData = hotelDataList.get(holder.getAdapterPosition());

        holder.txtHotelName.setText(hotelData.getTitle());

        String originalPrice = mContext.getResources().getString(R.string.rupees_sign) + hotelData.getRetailPrice();
        String discountedPrice = mContext.getResources().getString(R.string.rupees_sign) + hotelData.getSellingPrice();

        holder.txtOriginalPrice.setText(originalPrice);
        holder.txtDiscountedPrice.setText(discountedPrice);

        holder.txtOff.setText(hotelData.getPriceOff());
        String formattedRating = String.format("%.01f", hotelData.getRating());
        holder.txtRating.setText(formattedRating);


        TextView txtOriginalPrice = holder.txtOriginalPrice;
        txtOriginalPrice.setPaintFlags(txtOriginalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        Glide.with(mContext)
                .load(hotelData.getImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.hotelImage);
        holder.itemView.setOnClickListener((view) -> {
            Intent intent = new Intent(mContext, HotelDetailsNew.class);
            intent.putExtra("hotel_id", hotelData.getId());
            intent.putExtra("rating", formattedRating);
            mContext.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return hotelDataList.size();
    }

    public class HotelsListViewHolder extends RecyclerView.ViewHolder {

        TextView txtHotelName, txtAddress, txtDiscountedPrice, txtOriginalPrice, txtOff;
        TextView txtRating;
        ImageView hotelImage;

        public HotelsListViewHolder(@NonNull View itemView) {
            super(itemView);
            txtHotelName = itemView.findViewById(R.id.txtHotelName);
            txtAddress = itemView.findViewById(R.id.txtAddress);
            txtDiscountedPrice = itemView.findViewById(R.id.txtDiscountedPrice);
            txtOriginalPrice = itemView.findViewById(R.id.txtOriginalPrice);
            txtOff = itemView.findViewById(R.id.txtOff);
            txtRating = itemView.findViewById(R.id.txtRating);
            hotelImage = itemView.findViewById(R.id.hotelImage);
        }
    }
}
