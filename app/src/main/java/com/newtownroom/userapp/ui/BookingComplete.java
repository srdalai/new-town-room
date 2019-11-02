package com.newtownroom.userapp.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.newtownroom.userapp.R;
import com.newtownroom.userapp.adapters.InterestAdapter;
import com.newtownroom.userapp.models.BookingData;
import com.newtownroom.userapp.models.HotelData;
import com.newtownroom.userapp.models.Interest;
import com.newtownroom.userapp.models.RulesData;
import com.newtownroom.userapp.models.UserData;
import com.newtownroom.userapp.rest.GetDataService;
import com.newtownroom.userapp.rest.RetrofitClientInstance;
import com.newtownroom.userapp.restmodels.BookingDetailsResponses;
import com.newtownroom.userapp.restmodels.SingleBookingID;
import com.newtownroom.userapp.utils.Utilities;
import com.payumoney.core.PayUmoneyConfig;
import com.payumoney.core.PayUmoneySdkInitializer;
import com.payumoney.core.entity.TransactionResponse;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingComplete extends AppCompatActivity {

    private static final String TAG = BookingComplete.class.getSimpleName();

    MaterialButton matBtnPayNow;
    TextView textPrice, textDiscount, textSellingPrice, txtPrice, txtNumGuests, txtNumOfNights, txtRomDetails, txtStartDate, txtEndDate;
    TextView txtUserName, textSuccessMsg, textHotelName, textHotelAddress, textViewMessage, textLocalInterest;
    TextView textDirections, textCallNow;
    RecyclerView interestRecycler;
    String checkInDate, checkOutDate, name, hotel_name, hotel_address;

    GetDataService service;
    ProgressDialog progressDialog;
    private String ipDateFormat = "yyyy-MM-dd";
    private String opDateFormat = "EEE, d MMM";

    float price = 0, discount = 0, sellingPrice = 0;
    int numOfGuests = 0, numOfRooms = 0, nights = 0;
    int booking_id = 4;
    float lat, lang;
    //PayU variables
    String key = "0lMDzMDB", salt = "48VSE2mGKk", txnid = "ORDER-OD-201900001", amount = "999", productinfo = "Hotel", firstname = "John", email = "user@email.com", udf1 = "", udf2 = "", udf3 = "", udf4 = "", udf5 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_complete);

        getSupportActionBar().setTitle("Booking Complete");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        initView();
        setClickListeners();
        setInitialData();
        updateUI();
        makeAPICall();
    }

    private void setInitialData() {
        price = getIntent().getFloatExtra("price", 0);
        discount = getIntent().getFloatExtra("discount", 0);
        sellingPrice = getIntent().getFloatExtra("sellingPrice", 0);
        numOfGuests = getIntent().getIntExtra("numOfGuests", 0);
        numOfRooms = getIntent().getIntExtra("numOfRooms", 0);
        nights = getIntent().getIntExtra("nights", 0);
        checkInDate = getIntent().getStringExtra("checkInDate");
        checkOutDate = getIntent().getStringExtra("checkOutDate");

        amount = String.valueOf(sellingPrice);
    }

    private void updateUI() {
        textPrice.setText("\u20B9 " + price);
        textDiscount.setText("-\u20B9 " + discount);
        textSellingPrice.setText("\u20B9 " + sellingPrice);
        txtPrice.setText("\u20B9 " + sellingPrice);

        txtNumGuests.setText(String.valueOf(numOfGuests));

        if (nights == 1) {
            txtNumOfNights.setText(nights+" Night");
        } else {
            txtNumOfNights.setText(nights+" Nights");
        }
        txtRomDetails.setText("1 Classic ("+ numOfRooms +"X)");

        txtStartDate.setText(checkInDate);
        txtEndDate.setText(checkOutDate);

        txtUserName.setText(name);

        String successMsg = "Success! Your stay is confirmed\nBooking ID : " + booking_id;
        textSuccessMsg.setText(successMsg);
        textHotelName.setText(hotel_name);
        textHotelAddress.setText(hotel_address);
    }

    private void initView() {
        matBtnPayNow = findViewById(R.id.matBtnPayNow);
        textPrice = findViewById(R.id.textPrice);
        textDiscount = findViewById(R.id.textDiscount);
        textSellingPrice = findViewById(R.id.textSellingPrice);
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
    }

    private void setClickListeners() {
        textDirections.setOnClickListener((view -> {
            //String mapUri = "geo:37.7749,-122.4194";
            String mapUri = "geo:"+ lat+","+lang;
            Uri gmmIntentUri = Uri.parse(mapUri);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(mapIntent);
            }
        }));

        matBtnPayNow.setOnClickListener((view -> {
            processPayment();
        }));
    }

    private void makeAPICall() {

        Call<BookingDetailsResponses> call = service.getBookingDetails(new SingleBookingID(4));
        call.enqueue(new Callback<BookingDetailsResponses>() {
            @Override
            public void onResponse(Call<BookingDetailsResponses> call, Response<BookingDetailsResponses> response) {
                progressDialog.dismiss();
                if (response.code() == 200) {
                    BookingDetailsResponses bookingDetails = response.body();
                    if (bookingDetails != null && bookingDetails.getCode() == 200) {
                        processBookingData(bookingDetails.getBooking().get(0));
                        processUserData(bookingDetails.getUser().get(0));
                        processHotelData(bookingDetails.getHotel().get(0));
                        processHotelRules(bookingDetails.getHotel_rules());
                        processInterestsData(bookingDetails.getInterests());
                        updateUI();
                    }
                }
            }

            @Override
            public void onFailure(Call<BookingDetailsResponses> call, Throwable t) {
                progressDialog.dismiss();
            }
        });

    }

    private void processBookingData(BookingData bookingData) {
        numOfGuests = Integer.parseInt(bookingData.getTotalGuest());
        numOfRooms = Integer.parseInt(bookingData.getTotalRoom());
        nights = Integer.parseInt(bookingData.getNights());
        checkInDate = Utilities.parseDate(bookingData.getUserCheckin(), ipDateFormat, opDateFormat);
        checkOutDate = Utilities.parseDate(bookingData.getUserCheckout(), ipDateFormat, opDateFormat);
    }

    private void processUserData(UserData userData) {
        name = userData.getName();
    }

    private void processHotelData(HotelData hotelData) {
        hotel_name = hotelData.getTitle();
        hotel_address = hotelData.getAddress();
        lat = hotelData.getLatitude();
        lang = hotelData.getLongitude();
    }

    private void processHotelRules(ArrayList<RulesData> hotel_rules) {
    }

    private void processInterestsData(ArrayList<Interest> interests) {
        InterestAdapter interestAdapter = new InterestAdapter(this, interests);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        interestRecycler.setLayoutManager(layoutManager);
        interestRecycler.setAdapter(interestAdapter);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result Code is -1 send from Payumoney activity
        Log.d(TAG, "request code " + requestCode + " resultcode " + resultCode);
        if (requestCode == PayUmoneyFlowManager.REQUEST_CODE_PAYMENT && resultCode == RESULT_OK && data != null) {
            TransactionResponse transactionResponse = data.getParcelableExtra( PayUmoneyFlowManager.INTENT_EXTRA_TRANSACTION_RESPONSE );

            if (transactionResponse != null && transactionResponse.getPayuResponse() != null) {

                if(transactionResponse.getTransactionStatus().equals( TransactionResponse.TransactionStatus.SUCCESSFUL )){
                    //Success Transaction
                    Log.d(TAG, transactionResponse.getMessage());
                    Log.d(TAG, transactionResponse.getPayuResponse());
                    Log.d(TAG, transactionResponse.getTransactionDetails());
                    showTxnSuccessDialog(transactionResponse.getMessage());
                } else{
                    //Failure Transaction
                    showTxnFailedDialog(transactionResponse.getMessage());
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

    private void showTxnSuccessDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(BookingComplete.this, MainActivity.class));
                finish();
            }
        });
        builder.create().show();
    }

    private void showTxnFailedDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
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

        String hashSequence = key+"|"+txnid+"|"+amount+"|"+productinfo+"|"+firstname+"|"+email+"|"+udf1+"|"+udf2+"|"+udf3+"|"+udf4+"|"+udf5+"||||||"+salt;
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
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
