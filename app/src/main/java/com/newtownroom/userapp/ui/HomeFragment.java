package com.newtownroom.userapp.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.snackbar.Snackbar;
import com.newtownroom.userapp.R;
import com.newtownroom.userapp.adapters.HotelsListAdapter;
import com.newtownroom.userapp.models.BannerData;
import com.newtownroom.userapp.models.FeaturedHotel;
import com.newtownroom.userapp.models.HotelData;
import com.newtownroom.userapp.rest.GetDataService;
import com.newtownroom.userapp.rest.RetrofitClientInstance;
import com.newtownroom.userapp.restmodels.HomeResponse;
import com.newtownroom.userapp.utils.Utilities;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    CarouselView carouselView;
    int[] sampleImages = {R.drawable.hotel_1, R.drawable.hotel_2, R.drawable.hotel_3, R.drawable.hotel_4, R.drawable.hotel_5};
    RecyclerView ourHotelRecycler, otherHotelsRecycler;
    ArrayList<HotelData> hotelList;
    GetDataService service;
    HotelsListAdapter hotelsListAdapter;
    ProgressDialog progressDialog;
    LinearLayout parentView;
    ArrayList<String> imageList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        carouselView = view.findViewById(R.id.carouselView);
        ourHotelRecycler = view.findViewById(R.id.ourHotelRecycler);
        otherHotelsRecycler = view.findViewById(R.id.otherHotelsRecycler);
        parentView = view.findViewById(R.id.parentView);

        /*carouselView.setImageListener(imageListener);
        carouselView.setPageCount(sampleImages.length);*/

        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setMessage("Loading....");

        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false);
        hotelList = new ArrayList<>();
        hotelsListAdapter = new HotelsListAdapter(requireContext(), hotelList);
        ourHotelRecycler.setLayoutManager(layoutManager);
        ourHotelRecycler.setAdapter(hotelsListAdapter);


        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 2, GridLayoutManager.HORIZONTAL, false);
        otherHotelsRecycler.setLayoutManager(gridLayoutManager);
        otherHotelsRecycler.setAdapter(hotelsListAdapter);

        getHomeData();
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };

    ImageListener apiImageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            Glide.with(requireContext())
                    .load(imageList.get(position))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        }
    };

    private void getHotelsList() {
        progressDialog.show();
        Call<ArrayList<HotelData>> call = service.getHotelsList();
        call.enqueue(new Callback<ArrayList<HotelData>>() {
            @Override
            public void onResponse(Call<ArrayList<HotelData>> call, Response<ArrayList<HotelData>> response) {
                if (!isAdded()) return;
                progressDialog.dismiss();
                if (response.isSuccessful() && response.code() == 200) {
                    if (response.body() != null) {

                        hotelList.clear();
                        hotelList.addAll(response.body());
                        Snackbar.make(requireView(), "Total " + hotelList.size() + " hotels loaded", Snackbar.LENGTH_LONG).show();
                        hotelsListAdapter.notifyDataSetChanged();
                    } else {
                        Snackbar.make(requireView(), "Something went wrong...Please try later!", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(requireView(), "Something went wrong...Please try later!", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<HotelData>> call, Throwable t) {
                if (!isAdded()) return;
                progressDialog.dismiss();
                Log.d("Error", t.toString());
                Snackbar.make(requireView(), "Something went wrong...Please try later!", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void getHomeData() {
        if (!Utilities.hasInternet(requireContext())) {
            Snackbar.make(parentView, "No internet connection available", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Retry", _view -> getHomeData()).show();
            return;
        }
        progressDialog.show();
        Call<HomeResponse> call = service.getHomeData();
        call.enqueue(new Callback<HomeResponse>() {
            @Override
            public void onResponse(Call<HomeResponse> call, Response<HomeResponse> response) {
                if (!isAdded()) return;
                progressDialog.dismiss();
                if (response.code() == 200) {
                    HomeResponse homeResponse = response.body();
                    if (homeResponse != null) {
                        prepareBannerData(homeResponse.getBanners());
                        prepareFeaturedHotels(homeResponse.getFeaturedHotels());
                    } else {
                        Snackbar.make(requireView(), "Something went wrong...Please try later!", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(requireView(), "Something went wrong...Please try later!", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<HomeResponse> call, Throwable t) {
                if (!isAdded()) return;
                progressDialog.dismiss();
                Log.d("Error", t.toString());
                Snackbar.make(requireView(), "Something went wrong...Please try later!", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void prepareFeaturedHotels(ArrayList<FeaturedHotel> featuredHotels) {
        parentView.removeAllViews();
        for (int i = 0; i < featuredHotels.size(); i++) {
            FeaturedHotel featuredHotel = featuredHotels.get(i);

            View itemView = LayoutInflater.from(requireContext()).inflate(R.layout.home_featured_hotels_layout, null);
            TextView textView = itemView.findViewById(R.id.textView);
            RecyclerView recyclerView = itemView.findViewById(R.id.recyclerView);
            ImageView bannerImage = itemView.findViewById(R.id.bannerImage);

            textView.setText(featuredHotel.getHotelListLabel());
            ArrayList<HotelData> hotelList = featuredHotel.getHotelList();
            Glide.with(requireContext())
                    .load(featuredHotel.getOfferBanner())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(bannerImage);


            RecyclerView.LayoutManager featuredLayoutManager = new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false);
            HotelsListAdapter featuredHotelAdapter = new HotelsListAdapter(requireContext(), hotelList);
            recyclerView.setLayoutManager(featuredLayoutManager);
            recyclerView.setAdapter(featuredHotelAdapter);


            parentView.addView(itemView);
        }
    }

    private void prepareBannerData(ArrayList<BannerData> banners) {
        imageList.clear();
        for (int i = 0; i < banners.size(); i++) {
            imageList.add(banners.get(i).getImage());
        }
        carouselView.setImageListener(apiImageListener);
        carouselView.setPageCount(imageList.size());
    }
}