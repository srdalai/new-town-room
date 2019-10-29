package com.newtownroom.userapp.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.newtownroom.userapp.R;
import com.payumoney.core.PayUmoneySdkInitializer;
import com.payumoney.core.entity.TransactionResponse;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class BookingComplete extends AppCompatActivity {

    private static final String TAG = BookingComplete.class.getSimpleName();

    MaterialButton matBtnPayNow;
    TextView textPrice, textDiscount, textSellingPrice, txtPrice, txtNumGuests, txtNumOfNights, txtRomDetails;

    int price = 0, discount = 0, sellingPrice = 0, numOfGuests = 0, numOfRooms = 0, nights = 0;
    //PayU variables
    String key = "0lMDzMDB", salt = "48VSE2mGKk", txnid = "ORDER-OD-201900001", amount = "999", productinfo = "Hotel", firstname = "John", email = "user@email.com", udf1 = "", udf2 = "", udf3 = "", udf4 = "", udf5 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_complete);

        getSupportActionBar().setTitle("Booking Complete");

        price = getIntent().getIntExtra("price", 0);
        discount = getIntent().getIntExtra("discount", 0);
        sellingPrice = getIntent().getIntExtra("sellingPrice", 0);
        numOfGuests = getIntent().getIntExtra("numOfGuests", 0);
        numOfRooms = getIntent().getIntExtra("numOfRooms", 0);
        nights = getIntent().getIntExtra("nights", 0);

        amount = String.valueOf(sellingPrice);

        matBtnPayNow = findViewById(R.id.matBtnPayNow);
        textPrice = findViewById(R.id.textPrice);
        textDiscount = findViewById(R.id.textDiscount);
        textSellingPrice = findViewById(R.id.textSellingPrice);
        txtPrice = findViewById(R.id.txtPrice);
        txtNumGuests = findViewById(R.id.txtNumGuests);
        txtNumOfNights = findViewById(R.id.txtNumOfNights);
        txtRomDetails = findViewById(R.id.txtRomDetails);

        textPrice.setText("\u20B9 " + price);
        textDiscount.setText("-\u20B9 " + discount);
        textSellingPrice.setText("\u20B9 " + sellingPrice);
        txtPrice.setText("\u20B9 " + sellingPrice);

        txtNumGuests.setText(String.valueOf(numOfGuests));
        txtNumOfNights.setText(nights+" Nights(s)");
        txtRomDetails.setText("1 Classic ("+ numOfRooms +"X)");

        matBtnPayNow.setOnClickListener((view -> {
            processPayment();
        }));
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
            PayUmoneyFlowManager.startPayUMoneyFlow(paymentParam, this, R.style.PaymentTheme, true);
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

}
