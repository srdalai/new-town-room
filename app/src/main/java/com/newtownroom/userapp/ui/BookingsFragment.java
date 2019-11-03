package com.newtownroom.userapp.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.newtownroom.userapp.R;
import com.newtownroom.userapp.adapters.MyBookingsAdapter;
import com.newtownroom.userapp.models.BookingData;
import com.newtownroom.userapp.rest.GetDataService;
import com.newtownroom.userapp.rest.RetrofitClientInstance;
import com.newtownroom.userapp.restmodels.AllBookingsInput;
import com.newtownroom.userapp.restmodels.AllBookingsResponse;
import com.newtownroom.userapp.restmodels.CancelBookingResponse;
import com.newtownroom.userapp.restmodels.SingleBookingID;
import com.newtownroom.userapp.utils.PreferenceManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingsFragment extends Fragment {

    private RecyclerView bookingsRecycler;
    private ArrayList<BookingData> bookingDataList = new ArrayList<>();
    private MyBookingsAdapter myBookingsAdapter;
    private GetDataService service;
    private ProgressDialog progressDialog;
    private PreferenceManager preferenceManager;
    TextView noDataTextView;

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
        noDataTextView = view.findViewById(R.id.noDataTextView);

        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setMessage("Loading....");

        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        myBookingsAdapter = new MyBookingsAdapter(this, bookingDataList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
        bookingsRecycler.setLayoutManager(layoutManager);
        bookingsRecycler.setAdapter(myBookingsAdapter);

        getAllBookings();

    }

    private void getAllBookings() {
        progressDialog.show();

        AllBookingsInput allBookingsInput = new AllBookingsInput();
        allBookingsInput.setUserId(preferenceManager.getUserID());
        allBookingsInput.setUniqid(preferenceManager.getUniqueID());
        Call<AllBookingsResponse> call = service.getBookingList(allBookingsInput);
        call.enqueue(new Callback<AllBookingsResponse>() {
            @Override
            public void onResponse(Call<AllBookingsResponse> call, Response<AllBookingsResponse> response) {
                progressDialog.dismiss();
                if (response.code() == 200) {
                    AllBookingsResponse allBookingsResponse = response.body();
                    bookingDataList.clear();
                    if (allBookingsResponse != null && (allBookingsResponse.getUpcomingBookings().size() > 0 || allBookingsResponse.getCompletedBookings().size() > 0 || allBookingsResponse.getCanceledBookings().size() > 0)) {
                        bookingDataList.addAll(response.body().getUpcomingBookings());
                    } else {
                        noDataTextView.setText("No Booking Available");
                        noDataTextView.setVisibility(View.VISIBLE);
                    }
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

    public void cancelBooking(String booking_id) {
        progressDialog.show();
        Call<CancelBookingResponse> call = service.deleteBooking(new SingleBookingID(Integer.parseInt(booking_id)));
        call.enqueue(new Callback<CancelBookingResponse>() {
            @Override
            public void onResponse(Call<CancelBookingResponse> call, Response<CancelBookingResponse> response) {
                progressDialog.dismiss();
                if (response.code() == 200) {
                    CancelBookingResponse responseModel = response.body();
                    if (responseModel != null && responseModel.getCode() == 200) {
                        Snackbar.make(requireView(), "Booking Cancelled Successfully", Snackbar.LENGTH_LONG).show();
                        getAllBookings();
                    } else {
                        Snackbar.make(requireView(), "Something went wrong...Please try later!", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(requireView(), "Something went wrong...Please try later!", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CancelBookingResponse> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(requireView(), "Something went wrong...Please try later!", Snackbar.LENGTH_LONG).show();
                Log.d("Retro Error", t.toString());

            }
        });

    }
}