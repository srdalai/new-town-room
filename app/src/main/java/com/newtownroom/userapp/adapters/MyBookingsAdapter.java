package com.newtownroom.userapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.newtownroom.userapp.R;
import com.newtownroom.userapp.models.BookingData;
import com.newtownroom.userapp.restmodels.CancelBookingResponse;
import com.newtownroom.userapp.restmodels.SingleBookingID;
import com.newtownroom.userapp.ui.BookingComplete;
import com.newtownroom.userapp.ui.BookingsFragment;
import com.newtownroom.userapp.ui.MainActivity;
import com.newtownroom.userapp.utils.Utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyBookingsAdapter extends RecyclerView.Adapter<MyBookingsAdapter.MyBookingsViewHolder> {

    private String ipDateFormat = "yyyy-MM-dd";
    private String ipDateFormatFull = "yyyy-MM-dd HH:mm:ss";
    private String opDateFormat = "EEE, d MMM";
    private String opDateFormatFull = "MMM dd";

    private Context mContext;
    private ArrayList<BookingData> dataList;
    BookingsFragment bookingsFragment;

    public MyBookingsAdapter(BookingsFragment bookingsFragment, ArrayList<BookingData> dataList) {
        this.bookingsFragment = bookingsFragment;
        this.dataList = dataList;
        mContext = bookingsFragment.requireContext();
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

        if (booking.getBookingStatus().equals("1")) {
            holder.txtActive.setVisibility(View.VISIBLE);
            holder.txtCompleted.setVisibility(View.GONE);
            holder.txtCancelled.setVisibility(View.GONE);
            holder.bookAgain.setVisibility(View.GONE);

        } else if (booking.getBookingStatus().equals("2")) {
            holder.txtActive.setVisibility(View.GONE);
            holder.txtCompleted.setVisibility(View.VISIBLE);
            holder.txtCancelled.setVisibility(View.GONE);
            holder.bookAgain.setVisibility(View.VISIBLE);

        } else {
            holder.txtActive.setVisibility(View.GONE);
            holder.txtCompleted.setVisibility(View.GONE);
            holder.txtCancelled.setVisibility(View.VISIBLE);
            holder.bookAgain.setVisibility(View.VISIBLE);

        }

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                bookingsFragment.cancelBooking(booking.getId());
                return false;
            }
        });

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
