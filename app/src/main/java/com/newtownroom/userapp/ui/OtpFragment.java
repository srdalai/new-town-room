package com.newtownroom.userapp.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.util.Log;
import android.util.TimeUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.newtownroom.userapp.R;
import com.newtownroom.userapp.models.OtpInputModel;
import com.newtownroom.userapp.models.OtpResponseModel;
import com.newtownroom.userapp.rest.GetDataService;
import com.newtownroom.userapp.rest.RetrofitClientInstance;
import com.newtownroom.userapp.utils.AppConstants;
import com.newtownroom.userapp.utils.PreferenceManager;

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
public class OtpFragment extends Fragment {

    TextInputEditText editTextOTP;
    MaterialButton btnSubmit, btnResend;
    TextView textViewResendTime;

    String flowFrom = "", phoneNumber;

    ProgressDialog progressDialog;
    GetDataService service;
    PreferenceManager preferenceManager;

    private Timer mTimer;
    private TimerTask mTimerTask;
    private Handler mTimerHandler = new Handler();

    int waitTime = 120;  //Wait time in seconds

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

        startTimer();
        registerBackPressedCallbacks();
    }

    private void processOTP(String phoneNumber, String otp) {
        Call<OtpResponseModel> call = service.validateOTP(new OtpInputModel(phoneNumber, otp));

        call.enqueue(new Callback<OtpResponseModel>() {
            @Override
            public void onResponse(Call<OtpResponseModel> call, Response<OtpResponseModel> response) {
                if (!isAdded()) return;
                progressDialog.dismiss();
                if (response.isSuccessful() && response.code() == 200) {
                    OtpResponseModel otpModel = response.body();
                    if(otpModel != null) {
                        Log.d("Response = > ", response.body().getMessage());
                        int code = response.body().getResponseCode();
                        if (code == 200) {
                            preferenceManager.setIsLoggedIn(true);
                            preferenceManager.setName("Name");
                            preferenceManager.setUserID(otpModel.getUserID());
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
            public void onFailure(Call<OtpResponseModel> call, Throwable t) {
                if (!isAdded()) return;
                progressDialog.dismiss();
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
        mTimerTask = new TimerTask() {
            public void run() {
                mTimerHandler.post(new Runnable() {
                    public void run(){
                        //TODO
                        waitTime--;
                        Log.d("Wait Time", waitTime+"");
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
            stopTimer();
        } else {
            textViewResendTime.setText(formatTime(String.valueOf(waitTime)));
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
}
