package com.newtownroom.userapp.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
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
import com.newtownroom.userapp.rest.GetDataService;
import com.newtownroom.userapp.rest.RetrofitClientInstance;
import com.newtownroom.userapp.restmodels.AllBookingsInput;
import com.newtownroom.userapp.restmodels.AllBookingsResponse;
import com.newtownroom.userapp.utils.PreferenceManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingsFragment extends Fragment {

    RecyclerView bookingsRecycler;
    ArrayList<BookingData> bookingDataList = new ArrayList<>();
    MyBookingsAdapter myBookingsAdapter;
    GetDataService service;
    ProgressDialog progressDialog;
    PreferenceManager preferenceManager;

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

        preferenceManager = new PreferenceManager(requireContext());

        bookingsRecycler = view.findViewById(R.id.bookingsRecycler);

        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setMessage("Loading....");

        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        myBookingsAdapter = new MyBookingsAdapter(requireContext(), bookingDataList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
        bookingsRecycler.setLayoutManager(layoutManager);
        bookingsRecycler.setAdapter(myBookingsAdapter);

        getAllBookings();

    }

    private void getAllBookings() {
        progressDialog.show();

        AllBookingsInput allBookingsInput = new AllBookingsInput();
        allBookingsInput.setUserId(preferenceManager.getUserID());
        allBookingsInput.setUniqid("cc62d0c419b0399cf2aae7745a88ad62");
        Call<AllBookingsResponse> call = service.getBookingList(allBookingsInput);
        call.enqueue(new Callback<AllBookingsResponse>() {
            @Override
            public void onResponse(Call<AllBookingsResponse> call, Response<AllBookingsResponse> response) {
                progressDialog.dismiss();
                if (response.code() == 200) {
                    bookingDataList.clear();
                    bookingDataList.addAll(response.body().getUpcomingBookings());
                    myBookingsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<AllBookingsResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("TAG", t.toString());
            }
        });
    }
}