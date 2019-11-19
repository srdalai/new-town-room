package com.newtownroom.userapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.newtownroom.userapp.R;
import com.newtownroom.userapp.adapters.InterestAdapter;
import com.newtownroom.userapp.models.BookingData;
import com.newtownroom.userapp.models.BookingPrice;
import com.newtownroom.userapp.models.GstModel;
import com.newtownroom.userapp.models.HotelData;
import com.newtownroom.userapp.models.LocalInterest;
import com.newtownroom.userapp.models.PayUResponse;
import com.newtownroom.userapp.models.RulesData;
import com.newtownroom.userapp.models.UserData;
import com.newtownroom.userapp.rest.GetDataService;
import com.newtownroom.userapp.rest.RetrofitClientInstance;
import com.newtownroom.userapp.restmodels.BookingDetailsResponses;
import com.newtownroom.userapp.restmodels.CancelBookingInput;
import com.newtownroom.userapp.restmodels.CancelBookingResponse;
import com.newtownroom.userapp.restmodels.DeleteGstInput;
import com.newtownroom.userapp.restmodels.DeleteGstResponse;
import com.newtownroom.userapp.restmodels.GstResponse;
import com.newtownroom.userapp.restmodels.PaymentInput;
import com.newtownroom.userapp.restmodels.PaymentResponse;
import com.newtownroom.userapp.restmodels.SingleBookingID;
import com.newtownroom.userapp.restmodels.SingleUserID;
import com.newtownroom.userapp.utils.PayUMoneyConstants;
import com.newtownroom.userapp.utils.PreferenceManager;
import com.newtownroom.userapp.utils.Utilities;
import com.payumoney.core.PayUmoneyConfig;
import com.payumoney.core.PayUmoneySdkInitializer;
import com.payumoney.core.entity.TransactionResponse;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;

import org.jetbrains.annotations.NotNull;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingComplete extends AppCompatActivity {

    private static final String TAG = BookingComplete.class.getSimpleName();
    private static final int REQUEST_PERMISSION_KEY = 899;

    MaterialButton btnCancel, btnShare, btnGetAssistance;
    TextView txtPrice, txtNumGuests, txtNumOfNights, txtRomDetails, txtStartDate, txtEndDate;
    TextView txtUserName, textSuccessMsg, textHotelName, textHotelAddress, textViewMessage, textLocalInterest, txtOnlineDiscount;
    TextView textDirections, textCallNow;
    RecyclerView interestRecycler;
    String checkInDate, checkOutDate, name, hotel_name, hotel_address;
    View interest_layout, parentView;
    ImageView imageViewHotel;
    MaterialButton btnGstUpdate;

    //Payment View
    TextView textPrice, textPriceDrop, textCouponDiscount, couponText, textSellingPrice, textServicePrice;
    RadioGroup paymentRadioGroup;
    MaterialButton matBtnPayNow;
    RadioButton radioFull, radio75, radioHalf, radio25;

    GetDataService service;
    ProgressDialog progressDialog;
    private String ipDateFormat = "yyyy-MM-dd";
    private String opDateFormat = "EEE, d MMM";
    PreferenceManager preferenceManager;

    float price = 0, priceDrop = 0, couponDiscount = 0, sellingPrice = 0, payable_amount = 0, servicePrice = 0;
    int numOfGuests = 0, numOfRooms = 0, nights = 0;
    String booking_id, applied_coupon = "", activity_title = "", onlinePayDiscountText = "", onlinePayDiscountType = "", payStatus = "0", booking_status = "1";
    double lat, lang;
    boolean can_go_back = false;
    int paymentPercent = 100;
    GstModel userGstModel = null;
    String hotelPhone = null;
    String shareText = "", shareTitle = "Share Your Stay Details";
    float onlinePayDiscountAmount = 0;
    int onlinePayDiscountId = 0;
    String intent_booking_status = "";

    //PayU variables
    String txnid, amount, productinfo = "Hotel Booking", phone, firstname, email, udf1 = "", udf2 = "", udf3 = "", udf4 = "", udf5 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_complete);

        preferenceManager = new PreferenceManager(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        setInitialData();

        getSupportActionBar().setTitle("Your Booking Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(can_go_back);

        initView();
        setClickListeners();
        updateUI();
        makeAPICall();
        getGstDetails();
    }

    private void setInitialData() {
        booking_id = getIntent().getStringExtra("booking_id");
        can_go_back = getIntent().getBooleanExtra("can_go_back", false);
        activity_title = getIntent().getStringExtra("activity_title");
        intent_booking_status = getIntent().getStringExtra("intent_booking_status");
        /*price = getIntent().getFloatExtra("price", 0);
        couponDiscount = getIntent().getFloatExtra("discount", 0);
        sellingPrice = getIntent().getFloatExtra("sellingPrice", 0);
        numOfGuests = getIntent().getIntExtra("numOfGuests", 0);
        numOfRooms = getIntent().getIntExtra("numOfRooms", 0);
        nights = getIntent().getIntExtra("nights", 0);
        checkInDate = getIntent().getStringExtra("checkInDate");
        checkOutDate = getIntent().getStringExtra("checkOutDate");

        amount = String.valueOf(sellingPrice);*/
    }

    private void updateUI() {
        textPrice.setText("\u20B9 " + formattedString(price));
        textPriceDrop.setText("-\u20B9 " + formattedString(priceDrop));
        textCouponDiscount.setText("-\u20B9 " + formattedString(couponDiscount));
        textSellingPrice.setText("\u20B9 " + formattedString(sellingPrice));
        txtPrice.setText("\u20B9 " + formattedString(sellingPrice));
        textServicePrice.setText("\u20B9 " + formattedString(servicePrice));

        couponText.setText(applied_coupon);
        txtOnlineDiscount.setText(onlinePayDiscountText);

        txtNumGuests.setText(String.valueOf(numOfGuests));

        if (nights == 1) {
            txtNumOfNights.setText(nights + " Night");
        } else {
            txtNumOfNights.setText(nights + " Nights");
        }
        txtRomDetails.setText("1 Classic (" + numOfRooms + "X)");

        txtStartDate.setText(checkInDate);
        txtEndDate.setText(checkOutDate);

        txtUserName.setText(name);

        String successMsg = "Success! Your stay is confirmed\nBooking ID : " + booking_id;
        textSuccessMsg.setText(successMsg);
        textHotelName.setText(hotel_name);
        textHotelAddress.setText(hotel_address);

        if (!booking_status.equals("1")) {
            matBtnPayNow.setText(intent_booking_status);
            matBtnPayNow.setEnabled(false);
            matBtnPayNow.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_gray)));

            btnCancel.setVisibility(View.GONE);

        } else if (payStatus.equals("1")) {
            matBtnPayNow.setText("Paid");
            matBtnPayNow.setEnabled(false);
            matBtnPayNow.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_gray)));

        }
    }

    private void initView() {


        txtPrice = findViewById(R.id.txtPrice);
        txtNumGuests = findViewById(R.id.txtNumGuests);
        txtNumOfNights = findViewById(R.id.txtNumOfNights);
        txtRomDetails = findViewById(R.id.txtRomDetails);
        txtStartDate = findViewById(R.id.txtStartDate);
        txtEndDate = findViewById(R.id.txtEndDate);
        txtUserName = findViewById(R.id.txtUserName);
        textSuccessMsg = findViewById(R.id.textSuccessMsg);
        textHotelName = findViewById(R.id.textHotelName);
        textHotelAddress = findViewById(R.id.textHotelAddress);
        textDirections = findViewById(R.id.textDirections);
        textCallNow = findViewById(R.id.textCallNow);
        textViewMessage = findViewById(R.id.textViewMessage);
        textLocalInterest = findViewById(R.id.textLocalInterest);
        interestRecycler = findViewById(R.id.interestRecycler);
        btnCancel = findViewById(R.id.btnCancel);
        btnShare = findViewById(R.id.btnShare);
        btnGetAssistance = findViewById(R.id.btnGetAssistance);
        interest_layout = findViewById(R.id.interest_layout);
        parentView = findViewById(R.id.parentView);
        imageViewHotel = findViewById(R.id.imageViewHotel);
        btnGstUpdate = findViewById(R.id.btnGstUpdate);
        txtOnlineDiscount = findViewById(R.id.txtOnlineDiscount);

        //Payment View
        textPrice = findViewById(R.id.textPrice);
        textPriceDrop = findViewById(R.id.textPriceDrop);
        textCouponDiscount = findViewById(R.id.textCouponDiscount);
        textSellingPrice = findViewById(R.id.textSellingPrice);
        couponText = findViewById(R.id.couponText);
        matBtnPayNow = findViewById(R.id.matBtnPayNow);
        paymentRadioGroup = findViewById(R.id.paymentRadioGroup);
        radioFull = findViewById(R.id.radioFull);
        radio75 = findViewById(R.id.radio75);
        radioHalf = findViewById(R.id.radioHalf);
        radio25 = findViewById(R.id.radio25);
        textServicePrice = findViewById(R.id.textServicePrice);

        radioFull.setChecked(true);
    }

    private void setClickListeners() {
        textDirections.setOnClickListener((view -> {
            //String mapUri = "geo:20.2952829,85.8566532";
            //lat = 20.2952829;
            //lang = 85.8566532;
            Toast.makeText(this, "Lat: "+lat+", Long: "+lang, Toast.LENGTH_SHORT).show();
            //String mapUri = "geo:" + lat + "," + lang;
            //String mapUri = "google.navigation:q=" + lat + "," + lang;
            String mapUri = "http://maps.google.com/maps?daddr="+lat+"," + lang;
            Uri gmmIntentUri = Uri.parse(mapUri);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(mapIntent);
            } else {
                Snackbar.make(parentView, "Google Maps not available!", Snackbar.LENGTH_LONG).show();
            }
        }));

        textCallNow.setOnClickListener((view -> {
            //callIntent();
        }));

        matBtnPayNow.setOnClickListener((view -> {
            int selectedID = paymentRadioGroup.getCheckedRadioButtonId();
            switch (selectedID) {
                case R.id.radio75:
                    paymentPercent = 75;
                    break;
                case R.id.radioHalf:
                    paymentPercent = 50;
                    break;
                case R.id.radio25:
                    paymentPercent = 25;
                    break;
                default:
                    paymentPercent = 100;
            }
            payable_amount = sellingPrice*paymentPercent/100;
            initPaymentData();
            Snackbar snackbar = Snackbar.make(parentView, "You will Pay \u20B9" + amount, Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction("Continue", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    processPayment();
                }
            });
            snackbar.show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    snackbar.dismiss();
                }
            }, 5000);
        }));

        btnGetAssistance.setOnClickListener((view -> {
            if (hotelPhone != null) {
                callIntent();
            } else {
                Snackbar.make(parentView, "No Contact Details Available", Snackbar.LENGTH_SHORT).show();
            }
        }));

        btnCancel.setOnClickListener((view -> {
            cancelBooking();
        }));

        btnShare.setOnClickListener((view -> {
            shareIntent();
        }));

        btnGstUpdate.setOnClickListener((_view) -> {
            showBottomSheet();
        });
    }

    private void makeAPICall() {
        progressDialog.show();
        Call<BookingDetailsResponses> call = service.getBookingDetails(new SingleBookingID(Integer.parseInt(booking_id)));
        call.enqueue(new Callback<BookingDetailsResponses>() {
            @Override
            public void onResponse(@NotNull Call<BookingDetailsResponses> call, @NotNull Response<BookingDetailsResponses> response) {
                progressDialog.dismiss();
                if (response.code() == 200) {
                    BookingDetailsResponses bookingDetails = response.body();
                    if (bookingDetails != null && bookingDetails.getCode() == 200) {
                        processBookingData(bookingDetails.getBooking().get(0));
                        processUserData(bookingDetails.getUser().get(0));
                        processHotelData(bookingDetails.getHotel());
                        processPricing(bookingDetails.getBookingPrice());
                        processHotelRules(bookingDetails.getHotel_rules());
                        processInterestsData(bookingDetails.getInterests());
                        shareText = bookingDetails.getShareUrl();
                        updateUI();
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<BookingDetailsResponses> call, @NotNull Throwable t) {
                progressDialog.dismiss();
                Log.d("Error", t.toString());
            }
        });

    }

    private void processBookingData(BookingData bookingData) {
        numOfGuests = Integer.parseInt(bookingData.getTotalGuest());
        numOfRooms = Integer.parseInt(bookingData.getTotalRoom());
        nights = Integer.parseInt(bookingData.getNights());
        checkInDate = Utilities.parseDate(bookingData.getUserCheckin(), ipDateFormat, opDateFormat);
        checkOutDate = Utilities.parseDate(bookingData.getUserCheckout(), ipDateFormat, opDateFormat);
        servicePrice = Integer.parseInt(bookingData.getExtraServicePrice());
        payStatus = bookingData.getPayStatus();
        booking_status = bookingData.getBookingStatus();
    }

    private void processUserData(UserData userData) {
        name = userData.getName();
    }

    private void processHotelData(HotelData hotelData) {
        Log.d("Hotel", new Gson().toJson(hotelData));
        hotel_name = hotelData.getTitle();
        hotel_address = hotelData.getAddress();
        lat = hotelData.getLatitude();
        lang = hotelData.getLongitude();
        Glide.with(this)
                .load(hotelData.getImage())
                .placeholder(R.drawable.hotel_1)
                .error(R.drawable.hotel_1)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageViewHotel);
        hotelPhone = hotelData.getPhone();
    }

    private void processPricing(BookingPrice bookingPrice) {
        Log.d("Price", new Gson().toJson(bookingPrice));
        price = bookingPrice.getBookingPrice();
        priceDrop = bookingPrice.getDroppedPrice();
        couponDiscount = bookingPrice.getCouponPrice();
        sellingPrice = bookingPrice.getPayablePrice();
        applied_coupon = bookingPrice.getCouponLabel();
        onlinePayDiscountId = bookingPrice.getOnlinePayDiscountId();
        onlinePayDiscountText = bookingPrice.getOnlinePayDiscountText();
        onlinePayDiscountType = bookingPrice.getOnlinePayDiscountType();
        onlinePayDiscountAmount =bookingPrice.getOnlinePayDiscountAmount();

        amount = String.valueOf(sellingPrice);
    }

    private void processHotelRules(ArrayList<RulesData> hotel_rules) {
    }

    private void processInterestsData(ArrayList<LocalInterest> interests) {
        Log.d("Total", interests.size()+"");
        if (interests.size() == 0) {
            interest_layout.setVisibility(View.GONE);
            return;
        }
        InterestAdapter interestAdapter = new InterestAdapter(this, interests);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        interestRecycler.setLayoutManager(layoutManager);
        interestRecycler.setAdapter(interestAdapter);
    }

    private void deleteBooking() {
        progressDialog.show();
        Call<CancelBookingResponse> call = service.deleteBooking(new SingleBookingID(Integer.parseInt(booking_id)));
        call.enqueue(new Callback<CancelBookingResponse>() {
            @Override
            public void onResponse(@NotNull Call<CancelBookingResponse> call, @NotNull Response<CancelBookingResponse> response) {
                progressDialog.dismiss();
                if (response.code() == 200) {
                    CancelBookingResponse responseModel = response.body();
                    if (responseModel != null && responseModel.getCode() == 200) {
                        showSuccessDialog("Booking Cancelled Successfully", false);

                    } else {
                        Snackbar.make(parentView, "Something went wrong...Please try later!", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(parentView, "Something went wrong...Please try later!", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<CancelBookingResponse> call, @NotNull Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(parentView, "Something went wrong...Please try later!", Snackbar.LENGTH_LONG).show();
                Log.d(TAG, t.toString());

            }
        });

    }

    private void cancelBooking() {
        progressDialog.show();
        CancelBookingInput cancelBookingInput = new CancelBookingInput();
        cancelBookingInput.setUserId(preferenceManager.getUserID());
        cancelBookingInput.setBookingId(Integer.parseInt(booking_id));
        cancelBookingInput.setUniqid(preferenceManager.getUniqueID());
        Log.d("Cancel Input", new Gson().toJson(cancelBookingInput));
        Call<CancelBookingResponse> call = service.cancelBooking(cancelBookingInput);
        call.enqueue(new Callback<CancelBookingResponse>() {
            @Override
            public void onResponse(@NotNull Call<CancelBookingResponse> call, @NotNull Response<CancelBookingResponse> response) {
                progressDialog.dismiss();
                if (response.code() == 200) {
                    CancelBookingResponse responseModel = response.body();
                    if (responseModel != null && responseModel.getCode() == 200) {
                        showSuccessDialog("Booking Cancelled Successfully", false);

                    } else {
                        Snackbar.make(parentView, "Something went wrong...Please try later!", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(parentView, "Something went wrong...Please try later!", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<CancelBookingResponse> call, @NotNull Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(parentView, "Something went wrong...Please try later!", Snackbar.LENGTH_LONG).show();
                Log.d(TAG, t.toString());

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result Code is -1 send from Payumoney activity
        Log.d(TAG, "request code " + requestCode + " resultcode " + resultCode);
        if (requestCode == PayUmoneyFlowManager.REQUEST_CODE_PAYMENT && resultCode == RESULT_OK && data != null) {
            TransactionResponse transactionResponse = data.getParcelableExtra(PayUmoneyFlowManager.INTENT_EXTRA_TRANSACTION_RESPONSE);

            if (transactionResponse != null && transactionResponse.getPayuResponse() != null) {
                processPaymentData(transactionResponse.getPayuResponse());

                if (transactionResponse.getTransactionStatus().equals(TransactionResponse.TransactionStatus.SUCCESSFUL)) {
                    //Success Transaction
                    Log.d(TAG, transactionResponse.getMessage());
                    Log.d(TAG, transactionResponse.getPayuResponse());
                    Log.d(TAG, transactionResponse.getTransactionDetails());
                    showTxnCompleteDialog(true, transactionResponse.getMessage());

                } else {
                    //Failure Transaction
                    showTxnCompleteDialog(false, transactionResponse.getMessage());
                }

                // Response from Payumoney
                String payuResponse = transactionResponse.getPayuResponse();

                // Response from SURl and FURL
                String merchantResponse = transactionResponse.getTransactionDetails();
            }  /*else if (resultModel != null && resultModel.getError() != null) {
                Log.d(TAG, "Error response : " + resultModel.getError().getTransactionResponse());
            }*/ else {
                Log.d(TAG, "Both objects are null!");
            }
        }
    }

    private void showTxnCompleteDialog(boolean isSuccess, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.component_payment_finish_layout, null);

        ImageView imageViewSuccess = dialogView.findViewById(R.id.imageViewSuccess);
        ImageView imageViewFailure = dialogView.findViewById(R.id.imageViewFailure);
        TextView textMessage = dialogView.findViewById(R.id.textMessage);
        MaterialButton btnSuccess = dialogView.findViewById(R.id.btnSuccess);
        MaterialButton btnFailure = dialogView.findViewById(R.id.btnFailure);

        textMessage.setText(message);

        if (isSuccess) {
            imageViewSuccess.setVisibility(View.VISIBLE);
            btnSuccess.setVisibility(View.VISIBLE);
            imageViewFailure.setVisibility(View.GONE);
            btnFailure.setVisibility(View.GONE);
            textMessage.setTextColor(getResources().getColor(R.color.colorPrimary));

        } else {
            imageViewFailure.setVisibility(View.VISIBLE);
            btnFailure.setVisibility(View.VISIBLE);
            imageViewSuccess.setVisibility(View.GONE);
            btnSuccess.setVisibility(View.GONE);
            textMessage.setTextColor(getResources().getColor(R.color.material_red));

        }

        builder.setView(dialogView);
        builder.setCancelable(false);

        AlertDialog dialog = builder.create();
        dialog.show();

        btnSuccess.setOnClickListener((_view) -> {
            startActivity(new Intent(BookingComplete.this, MainActivity.class));
            dialog.dismiss();
            finish();
        });

        btnFailure.setOnClickListener((_view) -> {
            dialog.dismiss();
        });

    }

    private void shareIntent() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        sendIntent.putExtra(Intent.EXTRA_TITLE, shareTitle);
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }

    private void emailIntent() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "abc@gmail.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }

    private void callIntent() {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + hotelPhone));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            String[] PERMISSIONS = {Manifest.permission.CALL_PHONE};
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_PERMISSION_KEY);
            return;
        }
        startActivity(intent);
    }

    private void showBottomSheet() {
        GstBottomFragment gstBottomFragment = new GstBottomFragment();
        if (userGstModel != null) {
            Bundle bundle = new Bundle();
            bundle.putString("gst_details", new Gson().toJson(userGstModel));
            gstBottomFragment.setArguments(bundle);
        }
        gstBottomFragment.show(getSupportFragmentManager(), gstBottomFragment.getTag());
    }

    void setGstDetails(GstModel gstData) {
        if (gstData != null) {
            if (userGstModel == null) {
                createGst(gstData);
            } else {
                updateGst(gstData);
            }
        }
    }

    private void getGstDetails() {
        Call<GstModel> call = service.getUserGst(new SingleUserID(preferenceManager.getUserID()));
        call.enqueue(new Callback<GstModel>() {
            @Override
            public void onResponse(@NotNull Call<GstModel> call, @NotNull Response<GstModel> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    GstModel gstModel = response.body();
                    if (gstModel != null) {
                        Log.d("getUserGst", new Gson().toJson(gstModel));
                        Snackbar.make(parentView, gstModel.getMsg(), Snackbar.LENGTH_SHORT).show();
                        if (gstModel.getCode() == 200) {
                            userGstModel = gstModel;
                        }
                    } else {
                        Snackbar.make(parentView, "Error Occurred", Snackbar.LENGTH_SHORT).show();
                    }
                } else {
                    Snackbar.make(parentView, "Error Occurred", Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<GstModel> call, @NotNull Throwable t) {
                Log.d("Error", t.toString());
            }
        });
    }

    private void createGst(GstModel gstModel) {
        progressDialog.show();
        Call<GstResponse> call = service.createUserGst(gstModel);
        call.enqueue(new Callback<GstResponse>() {
            @Override
            public void onResponse(@NotNull Call<GstResponse> call, @NotNull Response<GstResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.code() == 200) {
                    GstResponse gstResponse = response.body();
                    if (gstResponse != null) {
                        Log.d("createGst", new Gson().toJson(gstResponse));
                        Snackbar.make(parentView, gstResponse.getMsg(), Snackbar.LENGTH_SHORT).show();
                        if (gstResponse.getCode() == 200) {
                            getGstDetails();
                        }
                    } else {
                        Snackbar.make(parentView, "Error Occurred", Snackbar.LENGTH_SHORT).show();
                    }
                } else {
                    Snackbar.make(parentView, "Error Occurred", Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<GstResponse> call, @NotNull Throwable t) {
                progressDialog.dismiss();
                Log.d("Error", t.toString());
            }
        });
    }

    private void updateGst(GstModel gstModel) {
        progressDialog.show();
        Call<GstResponse> call = service.updateUserGst(gstModel);
        call.enqueue(new Callback<GstResponse>() {
            @Override
            public void onResponse(@NotNull Call<GstResponse> call, @NotNull Response<GstResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.code() == 200) {
                    GstResponse gstResponse = response.body();
                    if (gstResponse != null) {
                        Log.d("updateGst", new Gson().toJson(gstResponse));
                        Snackbar.make(parentView, gstResponse.getMsg(), Snackbar.LENGTH_SHORT).show();
                        if (gstResponse.getCode() == 200) {
                            getGstDetails();
                        }
                    } else {
                        Snackbar.make(parentView, "Error Occurred", Snackbar.LENGTH_SHORT).show();
                    }
                } else {
                    Snackbar.make(parentView, "Error Occurred", Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<GstResponse> call, @NotNull Throwable t) {
                progressDialog.dismiss();
                Log.d("Error", t.toString());
            }
        });
    }

    private void deleteGst() {
        progressDialog.show();
        DeleteGstInput deleteGstInput = new DeleteGstInput();
        deleteGstInput.setUserId(preferenceManager.getUserID());
        //deleteGstInput.setGstId(userGstModel.get);
        Call<DeleteGstResponse> call = service.deleteUserGst(deleteGstInput);
        call.enqueue(new Callback<DeleteGstResponse>() {
            @Override
            public void onResponse(@NotNull Call<DeleteGstResponse> call, @NotNull Response<DeleteGstResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.code() == 200) {
                    DeleteGstResponse deleteGstResponse = response.body();
                    if (deleteGstResponse != null) {
                        Log.d("deleteGst", new Gson().toJson(deleteGstResponse));
                        Snackbar.make(parentView, deleteGstResponse.getMsg(), Snackbar.LENGTH_SHORT).show();
                        if (deleteGstResponse.getCode() == 200) {
                            userGstModel = null;
                        }
                    } else {
                        Snackbar.make(parentView, "Error Occurred", Snackbar.LENGTH_SHORT).show();
                    }
                } else {
                    Snackbar.make(parentView, "Error Occurred", Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<DeleteGstResponse> call, @NotNull Throwable t) {
                progressDialog.dismiss();
                Log.d("Error", t.toString());
            }
        });

    }

    private void processPaymentData(String paymentResponse) {

        PayUResponse payUResponse = new Gson().fromJson(paymentResponse, PayUResponse.class);

        PaymentInput paymentInput = new PaymentInput();
        paymentInput.setAmount(Float.parseFloat(payUResponse.getResult().getAmount()));
        paymentInput.setBookingId(Integer.parseInt(booking_id));
        paymentInput.setPayPercent(String.valueOf(paymentPercent));
        paymentInput.setReponse(payUResponse.getResult().getHash());
        paymentInput.setStatus(payUResponse.getResult().getStatus());
        paymentInput.setTxnid(payUResponse.getResult().getPayuMoneyId());
        paymentInput.setUniqid(preferenceManager.getUniqueID());
        paymentInput.setUserId(preferenceManager.getUserID());
        paymentInput.setOnlinePaymentDiscountID(onlinePayDiscountId);

        Log.d("Payment Input", new Gson().toJson(paymentInput));


        Call<PaymentResponse> call = service.paymentProcess(paymentInput);
        call.enqueue(new Callback<PaymentResponse>() {
            @Override
            public void onResponse(@NotNull Call<PaymentResponse> call, @NotNull Response<PaymentResponse> response) {
                Log.d("Payment Response", new Gson().toJson(response.body()));
            }

            @Override
            public void onFailure(@NotNull Call<PaymentResponse> call, @NotNull Throwable t) {

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION_KEY: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callIntent();
                } else {
                    Toast.makeText(this, "You must accept permissions.", Toast.LENGTH_LONG).show();
                }
            }
        }

    }

    private void initPaymentData() {

        float payAfterDiscount;
        if (onlinePayDiscountType.equals("percent")) {
            payAfterDiscount = payable_amount - (payable_amount*onlinePayDiscountAmount/100);
        } else {
            payAfterDiscount = payable_amount - onlinePayDiscountAmount;
        }

        //txnid = booking_id;
        txnid = String.valueOf(System.currentTimeMillis());

        amount = String.format("%.02f", payAfterDiscount);
        productinfo = "Hotel Booking";

        phone = preferenceManager.getPhoneNumber();
        firstname = preferenceManager.getName();
        email = preferenceManager.getEmail();
    }

    //PayU Methods

    private void processPayment() {

        PayUmoneyConfig payUmoneyConfig = PayUmoneyConfig.getInstance();

        //Use this to set your custom text on result screen button
        //payUmoneyConfig.setDoneButtonText(((EditText) findViewById(R.id.status_page_et)).getText().toString());

        //Use this to set your custom title for the activity
        payUmoneyConfig.setPayUmoneyActivityTitle("Make Payment");

        //payUmoneyConfig.disableExitConfirmation(isDisableExitConfirmation);

        PayUmoneySdkInitializer.PaymentParam.Builder builder = new PayUmoneySdkInitializer.PaymentParam.Builder();
        builder.setAmount(amount)                          // Payment amount
                .setTxnId(txnid)                                             // Transaction ID
                .setPhone(phone)                                           // User Phone number
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
                .setIsDebug(false)                              // Integration environment - true (Debug)/ false(Production)
                .setKey(PayUMoneyConstants.MERCHANT_KEY)                        // Merchant key
                .setMerchantId(PayUMoneyConstants.MERCHANT_ID);             // Merchant ID

        String hashSequence = PayUMoneyConstants.MERCHANT_KEY+"|"+txnid+"|"+amount+"|"+productinfo+"|"+firstname+"|"+email+"|"+udf1+"|"+udf2+"|"+udf3+"|"+udf4+"|"+udf5+"||||||"+PayUMoneyConstants.SALT;
        String serverCalculatedHash= hashCal("SHA-512", hashSequence);

        //declare paymentParam object
        PayUmoneySdkInitializer.PaymentParam paymentParam = null;//set the hash
        try {
            paymentParam = builder.build();
            paymentParam.setMerchantHash(serverCalculatedHash);

            // Invoke the following function to open the checkout page.
            PayUmoneyFlowManager.startPayUMoneyFlow(paymentParam, this, R.style.AppTheme_Green, true);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

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

    @Override
    public void onBackPressed() {
        if (can_go_back) {
            super.onBackPressed();
        } else {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private String formattedString(Object ipString) {
        DecimalFormat df = new DecimalFormat("####0.00");
        String value = df.format(ipString);
        String[] split_value = value.split("\\.");
        if (split_value[1].equals("00")) {
            return split_value[0];
        } else {
            return value;
        }
        /*return String.format(Locale.getDefault(), "%.2f", String.valueOf(ipString));*/
    }

    private void showSuccessDialog(String message, boolean shouldCancel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogGreen);
        builder.setMessage(message);
        builder.setCancelable(shouldCancel);
        builder.setPositiveButton("Ok", (dialogInterface, i) -> {
            if (can_go_back) {
                finish();
            } else {
                startActivity(new Intent(this, MainActivity.class));
            }
            finish();
            dialogInterface.dismiss();
        });
        builder.create().show();
    }
}
