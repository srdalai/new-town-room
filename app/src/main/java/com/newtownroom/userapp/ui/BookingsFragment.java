package com.newtownroom.userapp.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingsFragment extends Fragment {

    private RecyclerView bookingsRecycler;
    private ArrayList<BookingData> bookingDataList = new ArrayList<>();
    private ArrayList<BookingData> upcomingList = new ArrayList<>();
    private ArrayList<BookingData> completedList = new ArrayList<>();
    private ArrayList<BookingData> cancelledList = new ArrayList<>();
    private MyBookingsAdapter myBookingsAdapter;
    private GetDataService service;
    private ProgressDialog progressDialog;
    private PreferenceManager preferenceManager;
    private TextView noDataTextView;

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

        TabLayout tabLayout = view.findViewById(R.id.tablayout);
        /*TabItem tabUpcoming = view.findViewById(R.id.tabUpcoming);
        TabItem tabCompleted = view.findViewById(R.id.tabCompleted);
        TabItem tabCancelled = view.findViewById(R.id.tabCancelled);
        ViewPager viewPager = view.findViewById(R.id.viewPager);*/

        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setMessage("Loading....");

        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        myBookingsAdapter = new MyBookingsAdapter(this, bookingDataList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
        bookingsRecycler.setLayoutManager(layoutManager);
        bookingsRecycler.setAdapter(myBookingsAdapter);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    //Toast.makeText(requireContext(), "Upcoming", Toast.LENGTH_SHORT).show();
                    changeDataSet(upcomingList);
                } else if (tab.getPosition() == 1) {
                    //Toast.makeText(requireContext(), "Completed", Toast.LENGTH_SHORT).show();
                    changeDataSet(completedList);
                } else {
                    //Toast.makeText(requireContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                    changeDataSet(cancelledList);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

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
            public void onResponse(@NotNull Call<AllBookingsResponse> call, @NotNull Response<AllBookingsResponse> response) {
                progressDialog.dismiss();
                if (response.code() == 200) {
                    AllBookingsResponse allBookingsResponse = response.body();
                    bookingDataList.clear();
                    if (allBookingsResponse != null && (allBookingsResponse.getUpcomingBookings().size() > 0 || allBookingsResponse.getCompletedBookings().size() > 0 || allBookingsResponse.getCanceledBookings().size() > 0)) {
                        upcomingList.addAll(allBookingsResponse.getUpcomingBookings());
                        completedList.addAll(allBookingsResponse.getCompletedBookings());
                        cancelledList.addAll(allBookingsResponse.getCanceledBookings());

                        bookingDataList.addAll(allBookingsResponse.getUpcomingBookings());
                        bookingDataList.addAll(allBookingsResponse.getCompletedBookings());
                        bookingDataList.addAll(allBookingsResponse.getCanceledBookings());
                    } else {
                        noDataTextView.setVisibility(View.VISIBLE);
                    }
                    myBookingsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(@NotNull Call<AllBookingsResponse> call, @NotNull Throwable t) {
                progressDialog.dismiss();
                Log.d("TAG", t.toString());
            }
        });
    }

    private void changeDataSet(ArrayList<BookingData> bookingList) {
        bookingDataList.clear();
        bookingDataList.addAll(bookingList);
        myBookingsAdapter.notifyDataSetChanged();
        if (bookingList.size() > 0) {
            noDataTextView.setVisibility(View.GONE);
            bookingsRecycler.setVisibility(View.VISIBLE);
        } else {
            noDataTextView.setVisibility(View.VISIBLE);
            bookingsRecycler.setVisibility(View.GONE);
        }
    }

    public void cancelBooking(String booking_id) {
        progressDialog.show();
        Call<CancelBookingResponse> call = service.deleteBooking(new SingleBookingID(Integer.parseInt(booking_id)));
        call.enqueue(new Callback<CancelBookingResponse>() {
            @Override
            public void onResponse(@NotNull Call<CancelBookingResponse> call, @NotNull Response<CancelBookingResponse> response) {
                progressDialog.dismiss();
                if (response.code() == 200) {
                    CancelBookingResponse responseModel = response.body();
                    if (responseModel != null && responseModel.getCode() == 200) {
                        ((MainActivity) requireContext()).showSnack("Booking Cancelled Successfully", Snackbar.LENGTH_LONG);

                        getAllBookings();
                    } else {
                        ((MainActivity) requireContext()).showSnack("Something went wrong...Please try later!", Snackbar.LENGTH_LONG);
                    }
                } else {
                    ((MainActivity) requireContext()).showSnack("Something went wrong...Please try later!", Snackbar.LENGTH_LONG);
                }
            }

            @Override
            public void onFailure(@NotNull Call<CancelBookingResponse> call, @NotNull Throwable t) {
                progressDialog.dismiss();
                ((MainActivity) requireContext()).showSnack("Something went wrong...Please try later!", Snackbar.LENGTH_LONG);
                Log.d("Retro Error", t.toString());

            }
        });

    }
}