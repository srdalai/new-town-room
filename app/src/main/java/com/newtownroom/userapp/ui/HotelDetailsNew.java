package com.newtownroom.userapp.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.media.Image;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.newtownroom.userapp.R;
import com.newtownroom.userapp.adapters.AmenitiesAdapter;
import com.newtownroom.userapp.adapters.RulesAdapter;
import com.newtownroom.userapp.models.AmenitiesData;
import com.newtownroom.userapp.models.AmenitiesListData;
import com.newtownroom.userapp.models.BookingInputModel;
import com.newtownroom.userapp.models.BookingOutputModel;
import com.newtownroom.userapp.models.GuestData;
import com.newtownroom.userapp.models.HotelData;
import com.newtownroom.userapp.models.HotelDetailsInputModel;
import com.newtownroom.userapp.models.HotelDetailsResponseModel;
import com.newtownroom.userapp.models.ImageModel;
import com.newtownroom.userapp.models.PriceData;
import com.newtownroom.userapp.models.RoomData;
import com.newtownroom.userapp.models.RulesData;
import com.newtownroom.userapp.models.ServiceData;
import com.newtownroom.userapp.rest.GetDataService;
import com.newtownroom.userapp.rest.RetrofitClientInstance;
import com.newtownroom.userapp.utils.PreferenceManager;
import com.stfalcon.imageviewer.StfalconImageViewer;
import com.stfalcon.imageviewer.loader.ImageLoader;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HotelDetailsNew extends AppCompatActivity {

    private static final String TAG = HotelDetailsNew.class.getSimpleName();

    CarouselView carouselView;
    RecyclerView amenitiesRecycler, rulesListView;
    RelativeLayout dateRelative, guestRelative;
    MaterialButton btnDescMore, btnProceed, matBtnOffers, btnDescLess, amenitiesViewMore;
    TextView txtDescription, txtStayTime, txtRoomsGuests, textViewHotelDesc, textViewHotelName;
    TextView textSellingPrice, textPrice, txtTotalPrice, txtViewSavings, textViewCouponValue;
    LinearLayout detailsLinear;
    View parentView;
    FrameLayout amenitiesFrame;

    AmenitiesAdapter amenitiesAdapter;
    RulesAdapter rulesAdapter;
    GetDataService service;
    ProgressDialog progressDialog;
    SimpleDateFormat dateFormat = new SimpleDateFormat("E, dd MMM", Locale.getDefault());
    Calendar checkInCal, checkOutCal;
    PreferenceManager preferenceManager;

    int[] sampleImages = {R.drawable.hotel_1, R.drawable.hotel_2, R.drawable.hotel_3, R.drawable.hotel_4, R.drawable.hotel_5};
    ArrayList<AmenitiesData> amenitiesList = new ArrayList<>();
    ArrayList<RulesData> rulesDataList = new ArrayList<>();
    int guestNum = 0, roomNum = 0;
    int maxAdult = 0, maxChild = 0;
    int nights = 0;
    int price = 0, sellingPrice = 0, totalAmount = 0, couponDiscount = 0, grandTotal = 0, defaultDiscount = 0;
    String checkInDate = "", checkOutDate = "";
    String hotelId, rating;
    ArrayList<PriceData> priceList = new ArrayList<>();
    ArrayList<RoomData> roomDataArrayList = null;
    ArrayList<String> imageUrls = new ArrayList<>();
    int lineCount = 4;
    String fullText = "", truncatedText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_details_new);

        preferenceManager = new PreferenceManager(this);
        initView();
        initClickListeners();

        hotelId = getIntent().getStringExtra("hotel_id");
        rating = getIntent().getStringExtra("rating");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        amenitiesAdapter = new AmenitiesAdapter(this, amenitiesList, false);
        amenitiesRecycler.setLayoutManager(layoutManager);
        amenitiesRecycler.setAdapter(amenitiesAdapter);

        /*carouselView.setImageListener(imageListener);
        carouselView.setPageCount(sampleImages.length);*/

        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(this);
        rulesAdapter = new RulesAdapter(this, rulesDataList);
        rulesListView.setLayoutManager(layoutManager1);
        rulesListView.setAdapter(rulesAdapter);

        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        updateUI();
        getHotelDetails();
    }

    private void initView() {
        carouselView = findViewById(R.id.carouselView);
        amenitiesRecycler = findViewById(R.id.amenitiesRecycler);
        dateRelative = findViewById(R.id.dateRelative);
        guestRelative = findViewById(R.id.guestRelative);
        btnDescMore = findViewById(R.id.btnDescMore);
        txtDescription = findViewById(R.id.txtDescription);
        detailsLinear = findViewById(R.id.detailsLinear);
        txtStayTime = findViewById(R.id.txtStayTime);
        txtRoomsGuests = findViewById(R.id.txtRoomsGuests);
        rulesListView = findViewById(R.id.rulesListView);
        parentView = findViewById(R.id.parentView);
        textViewHotelDesc = findViewById(R.id.textViewHotelDesc);
        textViewHotelName = findViewById(R.id.textViewHotelName);
        textSellingPrice = findViewById(R.id.textSellingPrice);
        textPrice = findViewById(R.id.textPrice);
        txtTotalPrice = findViewById(R.id.txtTotalPrice);
        txtViewSavings = findViewById(R.id.txtViewSavings);
        textViewCouponValue = findViewById(R.id.textViewCouponValue);
        btnProceed = findViewById(R.id.btnProceed);
        matBtnOffers = findViewById(R.id.matBtnOffers);
        btnDescLess = findViewById(R.id.btnDescLess);
        amenitiesFrame = findViewById(R.id.amenitiesFrame);
        amenitiesViewMore = findViewById(R.id.amenitiesViewMore);

    }

    private void initClickListeners() {
        btnDescMore.setOnClickListener((view) -> {
            txtDescription.setMaxLines(lineCount);
            btnDescMore.setVisibility(View.GONE);
            btnDescLess.setVisibility(View.VISIBLE);
        });

        btnDescLess.setOnClickListener((view) -> {
            txtDescription.setMaxLines(4);
            btnDescMore.setVisibility(View.VISIBLE);
            btnDescLess.setVisibility(View.GONE);
            amenitiesFrame.requestFocus();

        });

        dateRelative.setOnClickListener((view) -> {
            /*Intent intent = new Intent(HotelDetailsNew.this, CheckOutDetails.class);
            startActivityForResult(intent, 400);*/
            showDatePicker("checkin");
        });

        guestRelative.setOnClickListener((view) -> {
            //showGuestNumDialog("Number of Rooms", 8, "rooms");
            Intent intent = new Intent(HotelDetailsNew.this, GuestDetails.class);
            intent.putExtra("maxAdult", maxAdult);
            intent.putExtra("maxChild", maxChild);
            if (roomDataArrayList != null) {
                GuestData guestData = new GuestData(roomDataArrayList);
                Gson gson = new Gson();
                String guest_data = gson.toJson(guestData);
                intent.putExtra("guest_data", guest_data);
            }
            startActivityForResult(intent, 100);
        });

        textSellingPrice.setOnClickListener((view -> {
            showPricingDialog();
        }));

        btnProceed.setOnClickListener((view -> {
            Intent intent = new Intent(HotelDetailsNew.this, BookingComplete.class);
            intent.putExtra("price", price*nights);
            intent.putExtra("discount", ((defaultDiscount*nights) + couponDiscount));
            intent.putExtra("sellingPrice", sellingPrice*nights);
            intent.putExtra("numOfGuests", guestNum);
            intent.putExtra("numOfRooms", roomNum);
            intent.putExtra("nights", nights);
            startActivity(intent);
            //continueToBook();
        }));

        matBtnOffers.setOnClickListener((view) ->{
            startActivity(new Intent(HotelDetailsNew.this, CouponsActivity.class));
        });

        carouselView.setImageClickListener(position -> showImageSlider());

        amenitiesViewMore.setOnClickListener((view -> {
            Intent intent = new Intent(HotelDetailsNew.this, AmenitiesListing.class);
            Gson gson = new Gson();
            String amenities = gson.toJson(new AmenitiesListData(amenitiesList), AmenitiesListData.class);
            intent.putExtra("amenities", amenities);
            startActivity(intent);
        }));

    }

    private void showImageSlider() {

        new StfalconImageViewer.Builder<>(HotelDetailsNew.this, imageUrls, new ImageLoader<String>() {
            @Override
            public void loadImage(ImageView imageView, String image) {
                //load your image here
                Glide.with(HotelDetailsNew.this).load(image).into(imageView);
            }
        }).show();
    }

    private void setImages() {
        carouselView.setImageListener(apiImageListener);
        carouselView.setPageCount(imageUrls.size());

    }

    ImageListener apiImageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            Glide.with(HotelDetailsNew.this).load(imageUrls.get(position)).into(imageView);
        }
    };

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };

    public void prepareAmenitiesData() {
        AmenitiesData amenitiesData = new AmenitiesData(R.drawable.ic_action_wifi, "Free Wifi", true);
        amenitiesList.add(amenitiesData);

        amenitiesData = new AmenitiesData(R.drawable.ic_action_ac_unit, "AC", true);
        amenitiesList.add(amenitiesData);

        amenitiesData = new AmenitiesData(R.drawable.ic_action_live_tv, "TV", true);
        amenitiesList.add(amenitiesData);

        amenitiesData = new AmenitiesData(R.drawable.ic_action_power, "Power Backup", true);
        amenitiesList.add(amenitiesData);

        amenitiesData = new AmenitiesData(R.drawable.ic_action_credit_card, "Card Payment", true);
        amenitiesList.add(amenitiesData);

        amenitiesData = new AmenitiesData(R.drawable.ic_action_videocam, "CCTV Cameras", true);
        amenitiesList.add(amenitiesData);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            String guest_data = data.getStringExtra("guest_data");

            Gson gson = new Gson();
            GuestData guestData = gson.fromJson(guest_data, GuestData.class);
            roomDataArrayList = guestData.getGuestData();

            for (int i = 0; i < roomDataArrayList.size(); i++) {
                RoomData roomData = roomDataArrayList.get(i);
                Log.d(TAG, "Room " + (i+1) + "\nAdults - " + roomData.getAdults() + "\nChildren - " + roomData.getChildren());
            }
            updateUI();
        }
    }

    private void showDatePicker(String type /*"checkin"/"checkout"*/) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar selectedCal = Calendar.getInstance();
                selectedCal.clear();
                selectedCal.set(year, month, dayOfMonth);
                String setDate = dateFormat.format(selectedCal.getTime());

                if (type.equals("checkin")) {
                    checkInDate = setDate;
                    checkInCal = selectedCal;
                    showDatePicker("checkout");
                } else {
                    checkOutDate = setDate;
                    checkOutCal = selectedCal;
                    updateUI();
                }

            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));

        View titleView = LayoutInflater.from(this).inflate(R.layout.ui_custom_title_layout, null);
        TextView titleText = titleView.findViewById(R.id.titleText);
        if (type.equals("checkin")) {
            titleText.setText("Set Check In Date");
            datePickerDialog.getDatePicker().setMinDate(calendar.getTime().getTime());
        } else {
            titleText.setText("Set Check Out Date");
            Calendar instanceCal = Calendar.getInstance();
            instanceCal.setTime(checkInCal.getTime());
            instanceCal.add(Calendar.DATE, 1);
            datePickerDialog.getDatePicker().setMinDate(instanceCal.getTime().getTime());
        }
        datePickerDialog.setCustomTitle(titleView);

        datePickerDialog.show();
    }

    private void showGuestNumDialog(String title, int maxNum, String type /*"guests"/"rooms"*/) {
        ArrayList<String> numberList = new ArrayList<>();
        for (int i = 0; i < maxNum; i++) {
            numberList.add(String.valueOf(i+1));
        }
        String[] numbersChars = numberList.toArray(new String[0]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);

        int selectedID = -1;
        if (type.equals("guests") && guestNum != 0) {
            selectedID = numberList.indexOf(String.valueOf(guestNum));
        } else if (type.equals("rooms") && roomNum != 0) {
            selectedID = numberList.indexOf(String.valueOf(roomNum));
        }

        builder.setSingleChoiceItems(numbersChars, selectedID, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String value = numbersChars[which];
                if (type.equals("guests")) {
                    guestNum = Integer.parseInt(value);
                    updateUI();
                    dialog.dismiss();

                } else {
                    roomNum = Integer.parseInt(value);
                    dialog.dismiss();
                    showGuestNumDialog("Number of Guests", 20, "guests");
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showPricingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

    }

    private void updateUI() {
        String dateString = "Set Dates";
        if (!checkInDate.equals("") && !checkOutDate.equals("")) {
            dateString = checkInDate + " - " + checkOutDate;
            calculateDays();
        }

        txtStayTime.setText(dateString);

        if (roomDataArrayList != null) {
            guestNum = 0;
            roomNum = roomDataArrayList.size();
            for (int i = 0; i < roomDataArrayList.size(); i++) {
                RoomData roomData = roomDataArrayList.get(i);
                guestNum = guestNum + roomData.getAdults();
            }
        }

        String guestRoom = "Select Guests";
        if (guestNum != 0 && roomNum != 0) {
            guestRoom = roomNum + " Room, " + guestNum + " Guest";
        }
        txtRoomsGuests.setText(guestRoom);

        if (roomDataArrayList != null) {
            price = 0;
            sellingPrice = 0;
            couponDiscount = 0;
            defaultDiscount = 0;
            for (int i = 0; i < roomDataArrayList.size(); i++) {
                int numOfAdults = roomDataArrayList.get(i).getAdults();

                for (int j = 0; j < priceList.size(); j++) {
                    PriceData priceData = priceList.get(j);
                    if (priceData.getGuest().equals(String.valueOf(numOfAdults))) {
                        int roomPrice = Integer.parseInt(priceData.getPrice());
                        int roomSellingPrice;
                        if (priceData.getSellingPrice() != null) {
                            roomSellingPrice = Integer.parseInt(priceData.getSellingPrice());
                        } else {
                            roomSellingPrice = Integer.parseInt(priceData.getPrice());
                        }

                        price = price + roomPrice;
                        sellingPrice = sellingPrice + roomSellingPrice;
                        //couponDiscount = Integer.parseInt(priceData.getDiscountAmount());
                    }
                }
            }
            defaultDiscount = price - sellingPrice;
        }




        //Updating Price Details
        textPrice.setText(getResources().getString(R.string.rupees_sign) + price);
        textSellingPrice.setText(getResources().getString(R.string.rupees_sign) + sellingPrice);
        txtViewSavings.setText(getResources().getString(R.string.rupees_sign) + (couponDiscount + defaultDiscount));
        textViewCouponValue.setText("-"+getResources().getString(R.string.rupees_sign) + defaultDiscount);

        if (sellingPrice == price) {
            textPrice.setVisibility(View.GONE);
        } else {
            textPrice.setVisibility(View.VISIBLE);
        }

        grandTotal = (sellingPrice * nights) - couponDiscount;
        //txtGrossTotal.setText(getResources().getString(R.string.rupees_sign) + grandTotal);
        txtTotalPrice.setText(getResources().getString(R.string.rupees_sign) + grandTotal);

        if (guestNum == 0 || roomNum == 0 || nights == 0) {
            btnProceed.setVisibility(View.GONE);
        } else {
            btnProceed.setVisibility(View.VISIBLE);
        }

        //Setting strikethrough Text
        textPrice.setPaintFlags(textPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    private void calculateDays() {
        long days = 0;
        try {
            Date chkInDate = dateFormat.parse(checkInDate);
            Date chkOutDate = dateFormat.parse(checkOutDate);
            assert chkOutDate != null;
            assert chkInDate != null;
            long difference = chkOutDate.getTime() - chkInDate.getTime();
            days = TimeUnit.MILLISECONDS.toDays(difference);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        nights = (int) days;
    }

    private void getHotelDetails() {
        progressDialog.show();
        HotelDetailsInputModel hotelDetailsInputModel = new HotelDetailsInputModel(hotelId);

        Call<HotelDetailsResponseModel> call = service.getHotelDetails(hotelDetailsInputModel);

        call.enqueue(new Callback<HotelDetailsResponseModel>() {
            @Override
            public void onResponse(Call<HotelDetailsResponseModel> call, Response<HotelDetailsResponseModel> response) {
                progressDialog.dismiss();
                if (HotelDetailsNew.this.isFinishing()) return;
                if (response.isSuccessful() && response.code() == 200) {
                    if (response.body() != null) {
                        HotelDetailsResponseModel hotelDetailsModel = response.body();
                        prepareHotelData(hotelDetailsModel.getHotelDataList().get(0));
                        preparePricing(hotelDetailsModel.getPriceDataList());
                        prepareImages(hotelDetailsModel.getImageList());
                        prepareAmenities(hotelDetailsModel.getAmenitiesList());
                        prepareServices(hotelDetailsModel.getExtraServicesList());
                        prepareRules(hotelDetailsModel.getRulesDataList());

                    } else {
                        Snackbar.make(parentView, "Something went wrong...Please try later!", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(parentView, "Something went wrong...Please try later!", Snackbar.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<HotelDetailsResponseModel> call, Throwable t) {
                if (HotelDetailsNew.this.isFinishing()) return;
                progressDialog.dismiss();
                Log.d("Error", t.toString());
                Snackbar.make(parentView, "Something went wrong...Please try later!", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void prepareHotelData(HotelData hotelData) {
        textViewHotelName.setText(hotelData.getTitle());

        String data = Html.fromHtml(hotelData.getDescription()).toString();
        textViewHotelDesc.setText(data);
        //txtDescription.setText(data);
        fullText = getResources().getString(R.string.large_text).trim();
        txtDescription.setText(R.string.large_text);
        maxAdult = hotelData.getMaxAdult();
        maxChild = hotelData.getMaxGuest() - hotelData.getMaxAdult();

        txtDescription.post(new Runnable() {
            @Override
            public void run() {
                lineCount = txtDescription.getLineCount();
                txtDescription.setMaxLines(4);
                txtDescription.setEllipsize(TextUtils.TruncateAt.END);
                truncatedText = txtDescription.getText().toString();
            }
        });
    }

    private void preparePricing(ArrayList<PriceData> priceDataList) {
        priceList.clear();
        priceList.addAll(priceDataList);
        PriceData priceData = priceDataList.get(0);

        if (priceData.getPrice() != null) {
            price = Integer.parseInt(priceData.getPrice());
        }

        if (priceData.getSellingPrice() != null) {
            sellingPrice = Integer.parseInt(priceData.getSellingPrice());
        } else {
            sellingPrice = price;
        }
        if (maxAdult == 0) {
            maxAdult = priceDataList.size();
        }
        updateUI();
    }

    private void prepareImages(ArrayList<ImageModel> images) {
        imageUrls = new ArrayList<>();
        for (int i = 0; i < images.size(); i++) {
            imageUrls.add(images.get(i).getImageUrl());
        }
        setImages();
    }

    private void prepareAmenities(ArrayList<AmenitiesData> amenitiesData) {
        amenitiesList.clear();
        amenitiesList.addAll(amenitiesData);
        amenitiesAdapter.notifyDataSetChanged();
        if (amenitiesData.size() > 4) {
            int minus = amenitiesData.size() - 4;
            amenitiesViewMore.setText("+"+minus + " More");
            amenitiesViewMore.setVisibility(View.VISIBLE);
        } else {
            amenitiesViewMore.setVisibility(View.GONE);
        }
    }

    private void prepareServices(ArrayList<ServiceData> serviceData) {

    }

    private void prepareRules(ArrayList<RulesData> rulesList) {
        rulesDataList.clear();
        rulesDataList.addAll(rulesList);
        rulesAdapter.notifyDataSetChanged();
    }

    private void continueToBook() {
        BookingInputModel bookingInputModel = new BookingInputModel();
        bookingInputModel.setCheckOutDate(checkInDate);
        bookingInputModel.setCheckOutDate(checkOutDate);
        bookingInputModel.setHotelID(Integer.parseInt(hotelId));
        bookingInputModel.setUserID(Integer.parseInt("19"));
        bookingInputModel.setTotalGuest(String.valueOf(guestNum));
        bookingInputModel.setRooms(roomDataArrayList);
        bookingInputModel.setBookedPrice(grandTotal);
        bookingInputModel.setExtraServices(new ArrayList<>());

        Call<BookingOutputModel> call = service.booking(bookingInputModel);
        call.enqueue(new Callback<BookingOutputModel>() {
            @Override
            public void onResponse(Call<BookingOutputModel> call, Response<BookingOutputModel> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    Log.d("Success", response.toString());
                }
            }

            @Override
            public void onFailure(Call<BookingOutputModel> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
