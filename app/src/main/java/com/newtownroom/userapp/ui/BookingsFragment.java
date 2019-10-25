package com.newtownroom.userapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.newtownroom.userapp.R;
import com.newtownroom.userapp.adapters.MyBookingsAdapter;
import com.newtownroom.userapp.models.BookingData;

import java.util.ArrayList;

public class BookingsFragment extends Fragment {

    RecyclerView bookingsRecycler;
    ArrayList<BookingData> bookingDataList;
    MyBookingsAdapter myBookingsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bookings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bookingsRecycler = view.findViewById(R.id.bookingsRecycler);

        bookingDataList = new ArrayList<>();
        myBookingsAdapter = new MyBookingsAdapter(requireContext(), bookingDataList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
        bookingsRecycler.setLayoutManager(layoutManager);
        bookingsRecycler.setAdapter(myBookingsAdapter);

    }
}