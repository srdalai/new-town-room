package com.newtownroom.userapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.newtownroom.userapp.R;
import com.newtownroom.userapp.models.BookingData;
import com.newtownroom.userapp.utils.Utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class MyBookingsAdapter extends RecyclerView.Adapter<MyBookingsAdapter.MyBookingsViewHolder> {

    private String ipDateFormat = "yyyy-MM-dd";
    private String ipDateFormatFull = "yyyy-MM-dd HH:mm:ss";
    private String opDateFormat = "EEE, d MMM";
    private String opDateFormatFull = "MMM dd";

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
        BookingData booking = dataList.get(holder.getAdapterPosition());

        Glide.with(mContext).load(R.drawable.room_one).circleCrop().into(holder.imageViewRoom);

        holder.txtBookingCity.setText(booking.getLocation());
        holder.txtBookingDate.setText("Booked on "+Utilities.parseDate(booking.getCreatedDate(), ipDateFormatFull, opDateFormatFull));
        holder.txtHotelName.setText(booking.getTitle());
        holder.bookedFrom.setText(Utilities.parseDate(booking.getUserCheckin(), ipDateFormat, opDateFormat));
        holder.txtBadge.setText(booking.getNights() + "N");
        holder.bookedTill.setText(Utilities.parseDate(booking.getUserCheckout(), ipDateFormat, opDateFormat));
        holder.txtGuests.setText(booking.getTotalGuest() + " Guest");
        holder.txtRooms.setText(booking.getTotalRoom() + " Room");

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyBookingsViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewRoom;
        TextView txtBookingCity, txtBookingDate, txtHotelName, bookedFrom, txtBadge, bookedTill, txtGuests, txtRooms;
        RelativeLayout bookAgain;
        TextView txtActive, txtCompleted, txtCancelled;

        public MyBookingsViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewRoom = itemView.findViewById(R.id.imageViewRoom);
            txtBookingCity = itemView.findViewById(R.id.txtBookingCity);
            txtBookingDate = itemView.findViewById(R.id.txtBookingDate);
            txtHotelName = itemView.findViewById(R.id.txtHotelName);
            bookedFrom = itemView.findViewById(R.id.bookedFrom);
            txtBadge = itemView.findViewById(R.id.txtBadge);
            bookedTill = itemView.findViewById(R.id.bookedTill);
            txtGuests = itemView.findViewById(R.id.txtGuests);
            txtRooms = itemView.findViewById(R.id.txtRooms);
            bookAgain = itemView.findViewById(R.id.bookAgain);
            txtActive = itemView.findViewById(R.id.txtActive);
            txtCompleted = itemView.findViewById(R.id.txtCompleted);
            txtCancelled = itemView.findViewById(R.id.txtCancelled);
        }
    }
}
