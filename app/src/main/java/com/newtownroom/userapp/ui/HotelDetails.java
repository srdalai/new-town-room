package com.newtownroom.userapp.ui;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.os.Bundle;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.newtownroom.userapp.R;
import com.newtownroom.userapp.adapters.AmenitiesAdapter;
import com.newtownroom.userapp.models.AmenitiesData;
import com.newtownroom.userapp.models.HotelData;
import com.newtownroom.userapp.restmodels.HotelDetailsInputModel;
import com.newtownroom.userapp.restmodels.HotelDetailsResponseModel;
import com.newtownroom.userapp.models.ImageModel;
import com.newtownroom.userapp.models.PriceData;
import com.newtownroom.userapp.models.ServiceData;
import com.newtownroom.userapp.rest.GetDataService;
import com.newtownroom.userapp.rest.RetrofitClientInstance;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

public class HotelDetails extends AppCompatActivity {


    //component_stay_details
    TextInputLayout checkInInput, checkOutInput, guestInput, roomInput;
    TextInputEditText txtCheckInTime, txtCheckOutTime, txtGuestsNum, txtRoomNum;
    TextView txtNumOfNights;

    //component_pricing_details
    TextView textViewCancellation, txtGrossTotal, textBtnApply, txtPrice, txtSellingPrice, textViewPriceDropped;
    EditText editTextCoupon;
    ImageView infoImageView;

    //component_booking_details
    TextInputEditText editTextName, editTextPhone, editTextEmail;

    //Other Views
    RecyclerView amenitiesRecycler;
    CarouselView carouselView;
    MaterialButton btnBookNow;
    View parentView;
    TextView txtHotelName, textViewPayAmt, textViewTotalDue, textViewIncompleteOne, textViewIncompleteTwo, textViewIncl, txtRating;

    //Data & Variables
    int[] sampleImages = {R.drawable.hotel_1, R.drawable.hotel_2, R.drawable.hotel_3, R.drawable.hotel_4, R.drawable.hotel_5};
    ArrayList<AmenitiesData> amenitiesList = new ArrayList<>();
    int guestNum = 0, roomNum = 0;
    String checkInDate = "", checkOutDate = "";
    boolean stayDetailsUpdated = false, pricingDetailsUpdated = false;
    SimpleDateFormat dateFormat = new SimpleDateFormat("E, dd MMM", Locale.getDefault());
    int nights = 0;
    int price = 0, sellingPrice = 0, totalAmount = 0, couponDiscount = 0, grandTotal = 0;
    Calendar checkInCal, checkOutCal;
    String hotelId, rating;

    GetDataService service;
    ProgressDialog progressDialog;
    AmenitiesAdapter amenitiesAdapter;

    //PayU variables
    String key = "0lMDzMDB", salt = "48VSE2mGKk", txnid = "ORDER-OD-201900001", amount = "999", productinfo = "Hotel", firstname = "John", email = "user@email.com", udf1 = "", udf2 = "", udf3 = "", udf4 = "", udf5 = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_details);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        initView();
        initClickListeners();

        hotelId = getIntent().getStringExtra("hotel_id");
        rating = getIntent().getStringExtra("rating");
        txtRating.setText(rating);

        updateUI();

        carouselView.setImageListener(imageListener);
        carouselView.setPageCount(sampleImages.length);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);

        prepareAmenitiesData();

        amenitiesAdapter = new AmenitiesAdapter(this, amenitiesList, false);
        amenitiesRecycler.setLayoutManager(layoutManager);
        amenitiesRecycler.setAdapter(amenitiesAdapter);


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");

        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        getHotelDetails();

    }

    private void initView() {

        //component_pricing_details
        textViewCancellation = findViewById(R.id.textViewCancellation);
        txtGrossTotal = findViewById(R.id.txtGrossTotal);
        textBtnApply = findViewById(R.id.textBtnApply);
        txtPrice = findViewById(R.id.txtPrice);
        txtSellingPrice = findViewById(R.id.txtSellingPrice);
        textViewPriceDropped = findViewById(R.id.textViewPriceDropped);
        editTextCoupon = findViewById(R.id.editTextCoupon);
        infoImageView = findViewById(R.id.infoImageView);

        //component_booking_details
        editTextName = findViewById(R.id.editTextName);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextEmail = findViewById(R.id.editTextEmail);

        //component_stay_details
        checkInInput = findViewById(R.id.checkInInput);
        checkOutInput = findViewById(R.id.checkOutInput);
        guestInput = findViewById(R.id.guestInput);
        roomInput = findViewById(R.id.roomInput);
        txtCheckInTime = findViewById(R.id.txtCheckInTime);
        txtCheckOutTime = findViewById(R.id.txtCheckOutTime);
        txtGuestsNum = findViewById(R.id.txtGuestsNum);
        txtRoomNum = findViewById(R.id.txtRoomNum);
        txtNumOfNights = findViewById(R.id.txtNumOfNights);

        //Other Views
        amenitiesRecycler = findViewById(R.id.amenitiesRecycler);
        carouselView = findViewById(R.id.carouselView);
        btnBookNow = findViewById(R.id.btnBookNow);
        parentView = findViewById(R.id.parentView);
        txtHotelName = findViewById(R.id.txtHotelName);
        textViewPayAmt = findViewById(R.id.textViewPayAmt);
        textViewTotalDue = findViewById(R.id.textViewTotalDue);
        textViewIncompleteOne = findViewById(R.id.textViewIncompleteOne);
        textViewIncompleteTwo = findViewById(R.id.textViewIncompleteTwo);
        textViewIncl = findViewById(R.id.textViewIncl);
        txtRating = findViewById(R.id.txtRating);


    }

    private void initClickListeners() {
        txtGuestsNum.setOnClickListener(v -> showGuestNumDialog("Number of Guests", 20, "guests"));

        txtRoomNum.setOnClickListener((view) -> showGuestNumDialog("Number of Rooms", 8, "rooms"));


        txtCheckInTime.setOnClickListener((view) -> {
            showDatePicker("checkin");
        });

        txtCheckOutTime.setOnClickListener((view) -> {
            if (checkInDate.equals("")) {
                Snackbar.make(parentView, "Please set Check-In date first", Snackbar.LENGTH_LONG).show();
            } else {
                showDatePicker("checkout");
            }
        });

        btnBookNow.setOnClickListener((view) -> {}/*processPayment()*/);
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
                } else {
                    checkOutDate = setDate;
                    checkOutCal = selectedCal;
                }
                updateUI();
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
;
        if (type.equals("checkin")) {
            datePickerDialog.getDatePicker().setMinDate(calendar.getTime().getTime());
        } else {
            Calendar instanceCal = Calendar.getInstance();
            instanceCal.setTime(checkInCal.getTime());
            instanceCal.add(Calendar.DATE, 1);
            datePickerDialog.getDatePicker().setMinDate(instanceCal.getTime().getTime());
        }

        datePickerDialog.show();
    }

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

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };

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
                } else {
                    roomNum = Integer.parseInt(value);
                }
                updateUI();
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void updateUI() {
        if (guestNum == 0) {
            txtGuestsNum.setText("No of Guests");
        } else if (guestNum == 1) {
            txtGuestsNum.setText(guestNum + " Guest");
        } else {
            txtGuestsNum.setText(guestNum + " Guests");
        }

        if (roomNum == 0) {
            txtRoomNum.setText("No of Rooms");
        } else if (roomNum == 1) {
            txtRoomNum.setText(roomNum + " Room");
        }else {
            txtRoomNum.setText(roomNum + " Rooms");
        }

        if (checkInDate.equals("")) {
            txtCheckInTime.setText("Set Date");
        } else {
            txtCheckInTime.setText(checkInDate);
        }

        if (checkOutDate.equals("")) {
            txtCheckOutTime.setText("Set Date");
        } else {
            txtCheckOutTime.setText(checkOutDate);
        }

        if (!checkInDate.equals("") && !checkOutDate.equals("")) {
            calculateDays();
            String days = nights + " Nights";
            txtNumOfNights.setText(days);
        } else {
            txtNumOfNights.setText("- - -");
        }

        //Updating Price Details
        txtPrice.setText(getResources().getString(R.string.rupees_sign) + price);
        txtSellingPrice.setText(getResources().getString(R.string.rupees_sign) + sellingPrice);

        if (sellingPrice == price) {
            txtPrice.setVisibility(View.GONE);
        } else {
            txtPrice.setVisibility(View.VISIBLE);
        }

        grandTotal = ((sellingPrice * nights)*roomNum) - couponDiscount;
        txtGrossTotal.setText(getResources().getString(R.string.rupees_sign) + grandTotal);
        textViewPayAmt.setText(getResources().getString(R.string.rupees_sign) + grandTotal);

        if (roomNum != 0 && guestNum != 0 && nights != 0) {
            textViewIncompleteOne.setVisibility(View.GONE);
            txtGrossTotal.setVisibility(View.VISIBLE);
            textViewTotalDue.setVisibility(View.VISIBLE);

            textViewIncompleteTwo.setVisibility(View.GONE);
            textViewPayAmt.setVisibility(View.VISIBLE);
            textViewIncl.setVisibility(View.VISIBLE);
            btnBookNow.setVisibility(View.VISIBLE);
        } else {
            textViewIncompleteOne.setVisibility(View.VISIBLE);
            txtGrossTotal.setVisibility(View.GONE);
            textViewTotalDue.setVisibility(View.GONE);

            textViewIncompleteTwo.setVisibility(View.VISIBLE);
            textViewPayAmt.setVisibility(View.GONE);
            textViewIncl.setVisibility(View.GONE);
            btnBookNow.setVisibility(View.GONE);

        }

        //Setting strikethrough Text
        txtPrice.setPaintFlags(txtPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
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
                if (HotelDetails.this.isFinishing()) return;
                if (response.isSuccessful() && response.code() == 200) {
                    if (response.body() != null) {
                        HotelDetailsResponseModel hotelDetailsModel = response.body();
                        prepareHotelData(hotelDetailsModel.getHotelDataList().get(0));
                        preparePricing(hotelDetailsModel.getPriceDataList().get(0));
                        prepareImages(hotelDetailsModel.getImageList());
                        prepareAmenities(hotelDetailsModel.getAmenitiesList());
                        prepareServices(hotelDetailsModel.getExtraServicesList());

                    } else {
                        Snackbar.make(parentView, "Something went wrong...Please try later!", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(parentView, "Something went wrong...Please try later!", Snackbar.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<HotelDetailsResponseModel> call, Throwable t) {
                if (HotelDetails.this.isFinishing()) return;
                progressDialog.dismiss();
                Log.d("Error", t.toString());
                Snackbar.make(parentView, "Something went wrong...Please try later!", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void prepareHotelData(HotelData hotelData) {
        txtHotelName.setText(hotelData.getTitle());
    }

    private void preparePricing(PriceData priceData) {

        if (priceData.getPrice() != null) {
            price = Integer.parseInt(priceData.getPrice());
        }

        if (priceData.getSellingPrice() != null) {
            sellingPrice = Integer.parseInt(priceData.getSellingPrice());
        } else {
            sellingPrice = price;
        }
        updateUI();
    }

    private void prepareImages(ArrayList<ImageModel> images) {

    }

    private void prepareAmenities(ArrayList<AmenitiesData> amenitiesData) {
        amenitiesList.clear();
        amenitiesList.addAll(amenitiesData);
        amenitiesAdapter.notifyDataSetChanged();
    }

    private void prepareServices(ArrayList<ServiceData> serviceData) {

    }


    //PayU Methods

    /*private void processPayment() {
        PayUmoneySdkInitializer.PaymentParam.Builder builder = new PayUmoneySdkInitializer.PaymentParam.Builder();
        builder.setAmount(amount)                          // Payment amount
                .setTxnId(txnid)                                             // Transaction ID
                .setPhone("9556798434")                                           // User Phone number
                .setProductName(productinfo)                   // Product Name or description
                .setFirstName(firstname)                              // User First name
                .setEmail(email)                                            // User Email ID
                .setsUrl("https://www.payumoney.com/mobileapp/payumoney/success.php")                    // Success URL (surl)
                .setfUrl("https://www.payumoney.com/mobileapp/payumoney/failure.php")                     //Failure URL (furl)
                .setUdf1(udf1)
                .setUdf2(udf2)
                .setUdf3(udf3)
                .setUdf4(udf4)
                .setUdf5(udf5)
                .setIsDebug(true)                              // Integration environment - true (Debug)/ false(Production)
                .setKey(key)                        // Merchant key
                .setMerchantId("6836640");             // Merchant ID

        String hashSequence = key+"|"+txnid+"|"+amount+"|"+productinfo+"|"+firstname+"|"+email+"|"+udf1+"|"+udf2+"|"+udf3+"|"+udf4+"|"+udf5+"|"+salt;
        String serverCalculatedHash= hashCal("SHA-512", hashSequence);

        //declare paymentParam object
        PayUmoneySdkInitializer.PaymentParam paymentParam = null;//set the hash
        try {
            paymentParam = builder.build();
            paymentParam.setMerchantHash(serverCalculatedHash);

            // Invoke the following function to open the checkout page.
            PayUmoneyFlowManager.startPayUMoneyFlow(paymentParam, this, R.style.AppTheme_NoActionBar, false);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }*/

    public static String hashCal(String type, String hashString) {
        StringBuilder hash = new StringBuilder();
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance(type);
            messageDigest.update(hashString.getBytes());
            byte[] mdbytes = messageDigest.digest();
            for (byte hashByte : mdbytes) {
                hash.append(Integer.toString((hashByte & 0xff) + 0x100, 16).substring(1));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hash.toString();
    }
}
