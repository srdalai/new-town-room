package com.newtownroom.userapp.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.newtownroom.userapp.R;
import com.newtownroom.userapp.interfaces.OtpReceivedInterface;
import com.newtownroom.userapp.receiver.SmsBroadcastReceiver;
import com.newtownroom.userapp.restmodels.LoginInput;
import com.newtownroom.userapp.restmodels.LoginResponse;
import com.newtownroom.userapp.restmodels.OtpInput;
import com.newtownroom.userapp.restmodels.OtpResponse;
import com.newtownroom.userapp.rest.GetDataService;
import com.newtownroom.userapp.rest.RetrofitClientInstance;
import com.newtownroom.userapp.utils.AppConstants;
import com.newtownroom.userapp.utils.PreferenceManager;

import org.jetbrains.annotations.NotNull;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.newtownroom.userapp.utils.AppConstants.FLOW_FROM;
import static com.newtownroom.userapp.utils.AppConstants.FLOW_FROM_LOGIN;

/**
 * A simple {@link Fragment} subclass.
 */
public class OtpFragment extends Fragment implements OtpReceivedInterface {

    private TextInputEditText editTextOTP;
    private MaterialButton btnSubmit, btnResend;
    private TextView textViewResendTime;
    private LinearLayout resendLinear;

    private String flowFrom = "", phoneNumber;

    private ProgressDialog progressDialog;
    private GetDataService service;
    private PreferenceManager preferenceManager;

    private Timer mTimer;
    private Handler mTimerHandler = new Handler();

    SmsBroadcastReceiver mSmsBroadcastReceiver;

    private int waitTime = 120;  //Wait time in seconds

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_otp, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (requireActivity() instanceof UserAuthentication) {
            ((UserAuthentication) requireActivity()).setActionBarTitle("OTP Verification");
            ((UserAuthentication) requireActivity()).canGoBack(true);
        }

        editTextOTP = view.findViewById(R.id.editTextOTP);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        btnResend = view.findViewById(R.id.btnResend);
        textViewResendTime = view.findViewById(R.id.textViewResendTime);
        resendLinear = view.findViewById(R.id.resendLinear);

        preferenceManager = new PreferenceManager(requireContext());

        Bundle bundle = getArguments();
        flowFrom = bundle != null ? bundle.getString(FLOW_FROM) : null;

        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setMessage("Loading....");

        btnResend.setEnabled(false);

        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        btnSubmit.setOnClickListener((_view) -> {
            phoneNumber = bundle.getString("phoneNumber");
            String otp = editTextOTP.getText().toString().trim();

            if (otp.matches("")) {
                Snackbar.make(requireView(), "Please enter OTP", Snackbar.LENGTH_SHORT).show();
            } else if (otp.length() < 6) {
                Snackbar.make(requireView(), "OTP must be 6 Characters", Snackbar.LENGTH_SHORT).show();
            } else {
                progressDialog.show();
                processOTP(phoneNumber, otp);
            }
        });

        btnResend.setOnClickListener((_view) -> {
            btnSubmit.setEnabled(false);

            /*btnSubmit.setEnabled(true);
            btnResend.setEnabled(false);
            waitTime = 12;
            startTimer();*/

            phoneNumber = bundle.getString("phoneNumber");
            resendOTP(phoneNumber);
        });

        startTimer();
        registerBackPressedCallbacks();

        // init broadcast receiver
        mSmsBroadcastReceiver = new SmsBroadcastReceiver();
        mSmsBroadcastReceiver.setOnOtpListeners(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION);
        requireContext().registerReceiver(mSmsBroadcastReceiver, intentFilter); //TODO unregister reciever
        //startSMSListener();
    }

    private void processOTP(String phoneNumber, String otp) {
        Call<OtpResponse> call = service.validateOTP(new OtpInput(phoneNumber, otp));

        call.enqueue(new Callback<OtpResponse>() {
            @Override
            public void onResponse(@NotNull Call<OtpResponse> call, @NotNull Response<OtpResponse> response) {
                if (!isAdded()) return;
                progressDialog.dismiss();
                if (response.isSuccessful() && response.code() == 200) {
                    OtpResponse otpModel = response.body();
                    if(otpModel != null) {
                        Log.d("Response = > ", otpModel.getMessage());
                        int code = otpModel.getResponseCode();
                        if (code == 200) {
                            preferenceManager.setIsLoggedIn(true);
                            preferenceManager.setUserID(Integer.parseInt(otpModel.getUserID()));
                            preferenceManager.setName(otpModel.getName());
                            preferenceManager.setEmail(otpModel.getEmail());
                            preferenceManager.setUniqueID(otpModel.getUniqid());
                            startActivity(new Intent(requireContext(), MainActivity.class));
                            requireActivity().finish();
                        } else {
                            Snackbar.make(requireView(), otpModel.getMessage(), Snackbar.LENGTH_LONG).show();
                        }
                    } else {
                        Snackbar.make(requireView(), "Something went wrong...Please try later!", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(requireView(), "Something went wrong...Please try later!", Snackbar.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(@NotNull Call<OtpResponse> call, @NotNull Throwable t) {
                if (!isAdded()) return;
                progressDialog.dismiss();
                Log.d("Error", t.toString());
                Snackbar.make(requireView(), "Something went wrong...Please try later!", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void resendOTP(String phone) {
        progressDialog.show();
        Call<LoginResponse> call = service.postLoginRequest(new LoginInput(phone));
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (!isAdded()) return;
                progressDialog.dismiss();
                if (response.isSuccessful() && response.code() == 200) {
                    LoginResponse logInModel = response.body();
                    if(logInModel != null) {
                        int code = logInModel.getResponseCode();
                        if (code == 200) {
                            btnSubmit.setEnabled(true);
                            btnResend.setEnabled(false);
                            waitTime = 120;
                            startTimer();
                        } else {
                            Snackbar.make(requireView(), logInModel.getMessage(), Snackbar.LENGTH_LONG).show();
                        }
                    } else {
                        Snackbar.make(requireView(), "Something went wrong...Please try later!", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(requireView(), "Something went wrong...Please try later!", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                if (!isAdded()) return;
                Log.d("Error", t.toString());
                Snackbar.make(requireView(), "Something went wrong...Please try later!", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void registerBackPressedCallbacks() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                if (flowFrom.equals(FLOW_FROM_LOGIN)) {
                    Bundle bundle = new Bundle();
                    bundle.putString(FLOW_FROM, AppConstants.FLOW_FROM_OTP);
                    Navigation.findNavController(requireView()).navigate(R.id.loginFragment, bundle);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString(FLOW_FROM, AppConstants.FLOW_FROM_OTP);
                    Navigation.findNavController(requireView()).navigate(R.id.registerFragment, bundle);
                }
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    private void stopTimer(){
        if(mTimer != null){
            mTimer.cancel();
            mTimer.purge();
        }
    }

    private void startTimer(){
        mTimer = new Timer();
        //TODO
        TimerTask mTimerTask = new TimerTask() {
            public void run() {
                mTimerHandler.post(new Runnable() {
                    public void run() {
                        //TODO
                        waitTime--;
                        Log.d("Wait Time", waitTime + "");
                        updateUI();
                    }
                });
            }
        };

        mTimer.schedule(mTimerTask, 1, 1000);
    }

    private void updateUI() {
        if (waitTime == 0) {
            btnResend.setEnabled(true);
            btnResend.setText("Resend OTP");
            stopTimer();
        } else {
            textViewResendTime.setText(formatTime(String.valueOf(waitTime)));
            btnResend.setText("Resend in " + formatTime(String.valueOf(waitTime)));
        }
    }

    private String formatTime(String timeInSecs) {
        long time = Long.parseLong(timeInSecs);
        String formattedTime = "00:00";
        if (time < 60) {
            formattedTime = "00:" + time;
        } else if (time < 3600) {
            long minutes = TimeUnit.SECONDS.toMinutes(time);
            long secs = time - (minutes * 60);
            formattedTime = minutes + ":" + secs;
        } else {
            long hours = TimeUnit.SECONDS.toHours(time);
            long remainingMinutes = time - (hours * 60);
            long minutes = TimeUnit.SECONDS.toMinutes(remainingMinutes);
            long secs = remainingMinutes - (minutes * 60);
            formattedTime = hours + ":" + minutes + ":" + secs;
        }

        return formattedTime;
    }

    public void startSMSListener() {
        SmsRetrieverClient mClient = SmsRetriever.getClient(requireContext());
        Task<Void> mTask = mClient.startSmsRetriever();
        mTask.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override public void onSuccess(Void aVoid) {
                /*layoutInput.setVisibility(View.GONE);
                layoutVerify.setVisibility(View.VISIBLE);*/
                Toast.makeText(requireContext(), "SMS Retriever starts", Toast.LENGTH_LONG).show();
            }
        });
        mTask.addOnFailureListener(new OnFailureListener() {
            @Override public void onFailure(@NonNull Exception e) {
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onOtpReceived(String otp) {
        //Toast.makeText(requireContext(), "Your OTP is => " + otp, Toast.LENGTH_SHORT).show();
        editTextOTP.setText(otp);
    }

    @Override
    public void onOtpTimeout() {

    }

    @Override
    public void onPause() {
        stopTimer();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION);
        requireContext().registerReceiver(mSmsBroadcastReceiver, intentFilter);
        super.onPause();
    }
}
