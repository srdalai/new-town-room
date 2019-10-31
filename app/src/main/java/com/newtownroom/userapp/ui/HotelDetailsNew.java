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
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.newtownroom.userapp.R;
import com.newtownroom.userapp.adapters.AmenitiesAdapter;
import com.newtownroom.userapp.adapters.RulesAdapter;
import com.newtownroom.userapp.models.AmenitiesData;
import com.newtownroom.userapp.models.AmenitiesListData;
import com.newtownroom.userapp.models.Coupon;
import com.newtownroom.userapp.restmodels.BookingInput;
import com.newtownroom.userapp.restmodels.BookingResponse;
import com.newtownroom.userapp.models.GuestData;
import com.newtownroom.userapp.models.HotelData;
import com.newtownroom.userapp.restmodels.CheckAvailInput;
import com.newtownroom.userapp.restmodels.CheckAvailResponse;
import com.newtownroom.userapp.restmodels.HotelDetailsInput;
import com.newtownroom.userapp.restmodels.HotelDetailsResponse;
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
    private static final int GUEST_ACTIVITY_REQUEST_CODE = 100;
    private static final int COUPON_ACTIVITY_REQUEST_CODE = 200;

    CarouselView carouselView;
    RecyclerView amenitiesRecycler, rulesListView;
    MaterialButton btnProceed, amenitiesViewMore, amenitiesViewLess;
    TextView textViewHotelDesc, textViewHotelName;
    TextView txtTotalPrice;
    View parentView;
    FrameLayout amenitiesFrame;

    //Desc Views
    TextView txtDescription;
    MaterialButton btnDescMore, btnDescLess;

    //Guest Views
    LinearLayout detailsLinear;
    RelativeLayout dateRelative, guestRelative;
    TextView txtStayTime, txtRoomsGuests;

    //Pricing Views
    LinearLayout defCouponLinear, couponLinear;
    ImageView defCouponIcon, couponIcon;
    TextView defCouponDesc, couponDesc, defCouponValue, couponValue, txtViewSavings, textPrice, textSellingPrice;
    CheckBox defCouponCheckBox, couponCheckBox;
    MaterialButton matBtnOffers;

    //Custom Data
    AmenitiesAdapter amenitiesAdapter;
    RulesAdapter rulesAdapter;
    GetDataService service;
    ProgressDialog progressDialog;
    SimpleDateFormat dateFormat = new SimpleDateFormat("E, dd MMM", Locale.getDefault());
    SimpleDateFormat apiDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    Calendar checkInCal, checkOutCal;
    PreferenceManager preferenceManager;
    Coupon appliedCoupon, removedCoupon;

    //Variables
    int[] sampleImages = {R.drawable.hotel_1, R.drawable.hotel_2, R.drawable.hotel_3, R.drawable.hotel_4, R.drawable.hotel_5};
    ArrayList<AmenitiesData> amenitiesList = new ArrayList<>();
    ArrayList<AmenitiesData> amenitiesListSmall = new ArrayList<>();
    ArrayList<AmenitiesData> amenitiesListAll = new ArrayList<>();
    ArrayList<RulesData> rulesDataList = new ArrayList<>();
    int guestNum = 0, roomNum = 0;
    int maxAdult = 0, maxChild = 0;
    int nights = 0;
    float price = 0, sellingPrice = 0, totalAmount = 0, couponDiscount = 0, grandTotal = 0, defaultDiscount = 0;
    String checkInDate = "", checkOutDate = "", apiCheckInDate = "", apiCheckOutDate = "";
    String hotelId, rating;
    ArrayList<PriceData> priceList = new ArrayList<>();
    ArrayList<RoomData> roomDataArrayList = null;
    ArrayList<String> imageUrls = new ArrayList<>();
    int lineCount = 4;
    String fullText = "", truncatedText = "";
    boolean bookingAvailable = false;

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
        rulesListView = findViewById(R.id.rulesListView);
        parentView = findViewById(R.id.parentView);
        textViewHotelDesc = findViewById(R.id.textViewHotelDesc);
        textViewHotelName = findViewById(R.id.textViewHotelName);
        txtTotalPrice = findViewById(R.id.txtTotalPrice);
        btnProceed = findViewById(R.id.btnProceed);
        amenitiesFrame = findViewById(R.id.amenitiesFrame);
        amenitiesViewMore = findViewById(R.id.amenitiesViewMore);
        amenitiesViewLess = findViewById(R.id.amenitiesViewLess);

        //Desc Views
        txtDescription = findViewById(R.id.txtDescription);
        btnDescMore = findViewById(R.id.btnDescMore);
        btnDescLess = findViewById(R.id.btnDescLess);

        //Guest Views
        detailsLinear = findViewById(R.id.detailsLinear);
        dateRelative = findViewById(R.id.dateRelative);
        guestRelative = findViewById(R.id.guestRelative);
        txtStayTime = findViewById(R.id.txtStayTime);
        txtRoomsGuests = findViewById(R.id.txtRoomsGuests);

        //Pricing Views
        defCouponLinear = findViewById(R.id.defCouponLinear);
        couponLinear = findViewById(R.id.couponLinear);
        defCouponIcon = findViewById(R.id.defCouponIcon);
        couponIcon = findViewById(R.id.couponIcon);
        defCouponDesc = findViewById(R.id.defCouponDesc);
        couponDesc = findViewById(R.id.couponDesc);
        defCouponValue = findViewById(R.id.defCouponValue);
        couponValue = findViewById(R.id.couponValue);
        defCouponCheckBox = findViewById(R.id.defCouponCheckBox);
        couponCheckBox = findViewById(R.id.couponCheckBox);
        matBtnOffers = findViewById(R.id.matBtnOffers);
        txtViewSavings = findViewById(R.id.txtViewSavings);
        textPrice = findViewById(R.id.textPrice);
        textSellingPrice = findViewById(R.id.textSellingPrice);


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
            startActivityForResult(intent, GUEST_ACTIVITY_REQUEST_CODE);
        });

        textSellingPrice.setOnClickListener((view -> {
            showPricingDialog();
        }));

        btnProceed.setOnClickListener((view -> {
            //bookingCompleted();
            startBookingFlow();
        }));

        matBtnOffers.setOnClickListener((view) ->{
            if (nights == 0 || roomNum == 0 || guestNum == 0) {
                Snackbar.make(parentView, "Please Select Dates & Guests First", Snackbar.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(HotelDetailsNew.this, CouponsActivity.class);
                startActivityForResult(intent, COUPON_ACTIVITY_REQUEST_CODE);
            }
        });

        carouselView.setImageClickListener(position -> showImageSlider());

        amenitiesViewMore.setOnClickListener((view -> {
            /*Intent intent = new Intent(HotelDetailsNew.this, AmenitiesListing.class);
            Gson gson = new Gson();
            String amenities = gson.toJson(new AmenitiesListData(amenitiesList), AmenitiesListData.class);
            intent.putExtra("amenities", amenities);
            startActivity(intent);*/

            amenitiesList.clear();
            amenitiesList.addAll(amenitiesListAll);
            amenitiesAdapter.notifyDataSetChanged();

            amenitiesViewMore.setVisibility(View.GONE);
            amenitiesViewLess.setVisibility(View.VISIBLE);
        }));

        amenitiesViewLess.setOnClickListener((view) -> {
            amenitiesList.clear();
            amenitiesList.addAll(amenitiesListSmall);
            amenitiesAdapter.notifyDataSetChanged();

            amenitiesViewMore.setVisibility(View.VISIBLE);
            amenitiesViewLess.setVisibility(View.GONE);
        });

        couponCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked) {
                    appliedCoupon = removedCoupon;
                } else {
                    appliedCoupon = null;
                }
                updateUI();
            }
        });

    }

    private void bookingCompleted() {
        Intent intent = new Intent(HotelDetailsNew.this, BookingComplete.class);
        intent.putExtra("price", price*nights);
        intent.putExtra("discount", (defaultDiscount + couponDiscount));
        intent.putExtra("sellingPrice", grandTotal);
        intent.putExtra("numOfGuests", guestNum);
        intent.putExtra("numOfRooms", roomNum);
        intent.putExtra("nights", nights);
        intent.putExtra("checkInDate", checkInDate);
        intent.putExtra("checkOutDate", checkOutDate);
        startActivity(intent);
    }

    private void showImageSlider() {

        new StfalconImageViewer.Builder<>(HotelDetailsNew.this, imageUrls, new ImageLoader<String>() {
            @Override
            public void loadImage(ImageView imageView, String image) {
                //load your image here
                Glide.with(HotelDetailsNew.this)
                        .load(image)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imageView);
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
            Glide.with(HotelDetailsNew.this)
                    .load(imageUrls.get(position))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case GUEST_ACTIVITY_REQUEST_CODE:
                processRoomResult(data);
                bookingAvailable = false;
                break;
            case COUPON_ACTIVITY_REQUEST_CODE:
                processCouponResult(data);
                break;
            default:
        }
        updateUI();



    }

    private void processRoomResult(Intent data) {
        if (data != null) {
            String guest_data = data.getStringExtra("guest_data");

            Gson gson = new Gson();
            GuestData guestData = gson.fromJson(guest_data, GuestData.class);
            roomDataArrayList = guestData.getGuestData();

            for (int i = 0; i < roomDataArrayList.size(); i++) {
                RoomData roomData = roomDataArrayList.get(i);
                Log.d(TAG, "Room " + (i+1) + "\nAdults - " + roomData.getAdults() + "\nChildren - " + roomData.getChildren());
            }

        }
    }

    private void processCouponResult(Intent data) {
        if (data != null) {
            String couponData = data.getStringExtra("couponData");
            Gson gson = new Gson();
            appliedCoupon = gson.fromJson(couponData, Coupon.class);
            removedCoupon = gson.fromJson(couponData, Coupon.class);
            Snackbar.make(parentView, appliedCoupon.getCode() + " Applied", Snackbar.LENGTH_SHORT).show();
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
                String setAPIDate = apiDateFormat.format(selectedCal.getTime());

                if (type.equals("checkin")) {
                    checkInDate = setDate;
                    apiCheckInDate = setAPIDate;
                    checkInCal = selectedCal;
                    showDatePicker("checkout");
                } else {
                    checkOutDate = setDate;
                    apiCheckOutDate = setAPIDate;
                    checkOutCal = selectedCal;
                    bookingAvailable = false;
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
            /**/defaultDiscount = 0;
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
            /**/defaultDiscount = (price - sellingPrice)*nights;
        }

        if (appliedCoupon != null) {
            float discountPercent = Float.parseFloat(appliedCoupon.getAmount());
            couponDiscount = sellingPrice*(discountPercent/100);
            couponDesc.setText(appliedCoupon.getCode() + " applied");
            couponLinear.setVisibility(View.VISIBLE);
            couponCheckBox.setChecked(true);
        } else {
            couponLinear.setVisibility(View.GONE);
        }


        //Updating Price Details
        grandTotal = (sellingPrice * nights) - couponDiscount;

        if (sellingPrice == price) {
            textPrice.setVisibility(View.GONE);
        } else {
            textPrice.setVisibility(View.VISIBLE);
        }

        textPrice.setText(getResources().getString(R.string.rupees_sign) + (price*nights));
        textSellingPrice.setText(getResources().getString(R.string.rupees_sign) + grandTotal);
        txtViewSavings.setText(getResources().getString(R.string.rupees_sign) + (couponDiscount + defaultDiscount));
        defCouponValue.setText("-"+getResources().getString(R.string.rupees_sign) + defaultDiscount);
        couponValue.setText("-"+getResources().getString(R.string.rupees_sign) + couponDiscount);
        txtTotalPrice.setText(getResources().getString(R.string.rupees_sign) + grandTotal);

        if (guestNum != 0 && roomNum != 0 && nights != 0 && !bookingAvailable) {
            checkAvailability();
        }

        btnProceed.setVisibility(bookingAvailable ? View.VISIBLE : View.GONE);

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
        HotelDetailsInput hotelDetailsInput = new HotelDetailsInput(hotelId);

        Call<HotelDetailsResponse> call = service.getHotelDetails(hotelDetailsInput);

        call.enqueue(new Callback<HotelDetailsResponse>() {
            @Override
            public void onResponse(Call<HotelDetailsResponse> call, Response<HotelDetailsResponse> response) {
                progressDialog.dismiss();
                if (HotelDetailsNew.this.isFinishing()) return;
                if (response.isSuccessful() && response.code() == 200) {
                    if (response.body() != null) {
                        HotelDetailsResponse hotelDetailsModel = response.body();
                        prepareHotelData(hotelDetailsModel.getHotelDataList().get(0));
                        //preparePreCoupon(hotelDetailsModel.getPre_coupon_code(), hotelDetailsModel.getPre_coupon_amount());
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
            public void onFailure(Call<HotelDetailsResponse> call, Throwable t) {
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

    private void preparePreCoupon(String coupon, float couponValue) {
        defaultDiscount = couponValue;
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
        //updateUI();
    }

    private void prepareImages(ArrayList<ImageModel> images) {
        imageUrls = new ArrayList<>();
        for (int i = 0; i < images.size(); i++) {
            imageUrls.add(images.get(i).getImageUrl());
        }
        setImages();
    }

    private void prepareAmenities(ArrayList<AmenitiesData> amenitiesData) {

        amenitiesListAll.clear();
        amenitiesListSmall.clear();

        amenitiesListAll.addAll(amenitiesData);

        if (amenitiesData.size() <= 4) {
            amenitiesListSmall.addAll(amenitiesData);

        } else {
            for (int i = 0; i < 4; i++) {
                amenitiesListSmall.add(amenitiesData.get(i));
            }
        }

        amenitiesList.clear();
        amenitiesList.addAll(amenitiesListSmall);
        amenitiesAdapter.notifyDataSetChanged();
        if (amenitiesData.size() > 4) {
            /*int minus = amenitiesData.size() - 4;
            amenitiesViewMore.setText("+"+minus + " More");*/
            amenitiesViewMore.setVisibility(View.VISIBLE);
        } else {
            amenitiesViewMore.setVisibility(View.GONE);
        }
        amenitiesViewLess.setVisibility(View.GONE);
    }

    private void prepareServices(ArrayList<ServiceData> serviceData) {

    }

    private void prepareRules(ArrayList<RulesData> rulesList) {
        rulesDataList.clear();
        rulesDataList.addAll(rulesList);
        rulesAdapter.notifyDataSetChanged();
    }

    private void startBookingFlow() {
        BookingInput bookingInput = new BookingInput();

        bookingInput.setUserID(preferenceManager.getUserID());
        bookingInput.setUniqueID("cc62d0c419b0399cf2aae7745a88ad64");
        bookingInput.setHotelID("5");
        bookingInput.setCoupon("ZOYO50");
        bookingInput.setTotalGuest(2);
        bookingInput.setCheckInDate("2019-11-08");
        bookingInput.setCheckOutDate("2019-11-10");
        bookingInput.setRooms(roomDataArrayList);
        bookingInput.setBookedPrice(1200);
        bookingInput.setExtraServices(new ArrayList<>());

        Call<BookingResponse> call = service.booking(bookingInput);
        call.enqueue(new Callback<BookingResponse>() {
            @Override
            public void onResponse(Call<BookingResponse> call, Response<BookingResponse> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    Log.d("Success", response.toString());
                    BookingResponse bookingResponse = response.body();
                    if (bookingResponse != null) {
                        if (bookingResponse.getCode() == 200) {
                            showSuccessDialog(bookingResponse.getMsg(), false);
                        } else {
                            showErrorDialog(bookingResponse.getMsg(), true);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BookingResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void checkAvailability() {
        CheckAvailInput checkAvailInput = new CheckAvailInput();
        checkAvailInput.setUserId(2);
        checkAvailInput.setUniqid("396a6a775553353534363037");
        checkAvailInput.setHotelId("5");
        checkAvailInput.setCheckin(apiCheckInDate);
        checkAvailInput.setCheckout(apiCheckOutDate);
        checkAvailInput.setRooms(roomDataArrayList);

        Call<CheckAvailResponse> call = service.checkAvailability(checkAvailInput);
        call.enqueue(new Callback<CheckAvailResponse>() {
            @Override
            public void onResponse(Call<CheckAvailResponse> call, Response<CheckAvailResponse> response) {
                if (response.code() == 200) {
                    CheckAvailResponse checkAvailResponse = response.body();
                    if (checkAvailResponse != null) {
                        bookingAvailable = checkAvailResponse.getAvailableStatus() == 1;
                        Snackbar.make(parentView, checkAvailResponse.getMsg(), Snackbar.LENGTH_SHORT).show();
                        updateUI();
                    }
                }
            }

            @Override
            public void onFailure(Call<CheckAvailResponse> call, Throwable t) {

            }
        });
    }

    private void showErrorDialog(String message, boolean shouldCancel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setCancelable(shouldCancel);
        builder.setPositiveButton("Ok", null);
        builder.create().show();
    }

    private void showSuccessDialog(String message, boolean shouldCancel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setCancelable(shouldCancel);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                bookingCompleted();
            }
        });
        builder.create().show();
    }
}
