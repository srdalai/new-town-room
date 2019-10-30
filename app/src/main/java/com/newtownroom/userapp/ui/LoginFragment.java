package com.newtownroom.userapp.ui;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.newtownroom.userapp.R;
import com.newtownroom.userapp.restmodels.LoginInput;
import com.newtownroom.userapp.restmodels.LoginResponse;
import com.newtownroom.userapp.rest.GetDataService;
import com.newtownroom.userapp.rest.RetrofitClientInstance;
import com.newtownroom.userapp.utils.PreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.newtownroom.userapp.utils.AppConstants.FLOW_FROM;
import static com.newtownroom.userapp.utils.AppConstants.FLOW_FROM_LOGIN;
import static com.newtownroom.userapp.utils.AppConstants.FLOW_FROM_REGISTER;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private MaterialButton btnCreateAccount, btnForgot, btnLogin;
    TextInputEditText editTextEmail, editTextPassword, editTextPhone;
    ProgressDialog progressDialog;
    GetDataService service;

    PreferenceManager preferenceManager;
    String flowFrom = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (requireActivity() instanceof UserAuthentication) {
            ((UserAuthentication) requireActivity()).setActionBarTitle("Login");
            ((UserAuthentication) requireActivity()).canGoBack(true);
        }

        btnCreateAccount = view.findViewById(R.id.btnCreateAccount);
        btnForgot = view.findViewById(R.id.btnForgot);
        btnLogin = view.findViewById(R.id.btnLogin);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        editTextPhone = view.findViewById(R.id.editTextPhone);

        preferenceManager = new PreferenceManager(requireContext());

        if (preferenceManager.getPhoneNumber() != null) {
            editTextPhone.setText(preferenceManager.getPhoneNumber());
        }

        Bundle bundle = getArguments();
        flowFrom = bundle != null ? bundle.getString(FLOW_FROM) : null;

        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setMessage("Loading....");

        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        btnCreateAccount.setOnClickListener((_view) -> {
            Bundle data = new Bundle();
            data.putString(FLOW_FROM, FLOW_FROM_LOGIN);
            Navigation.findNavController(requireView()).navigate(R.id.registerFragment, data);
        });

        btnForgot.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.forgotPasswordFragment));

        btnLogin.setOnClickListener((_view) -> {
            String phoneNumber = editTextPhone.getText().toString().trim();
            if (phoneNumber.length() < 10) {
                Snackbar.make(view, "Please enter a valid Phone Number!", Snackbar.LENGTH_LONG).show();
            } else {

                preferenceManager.setPhoneNumber(phoneNumber);
                progressDialog.show();
                processLoginData(phoneNumber);
                /*preferenceManager.setIsLoggedIn(true);
                preferenceManager.setUserID("1");
                startActivity(new Intent(requireContext(), MainActivity.class));
                requireActivity().finish();*/
            }
        });

        registerBackPressedCallbacks();
    }

    private void processLoginData(String phoneNumber) {
        Call<LoginResponse> call = service.postLoginRequest(new LoginInput(phoneNumber));
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (!isAdded()) return;
                progressDialog.dismiss();
                if (response.isSuccessful() && response.code() == 200) {
                    LoginResponse logInModel = response.body();
                    if(logInModel != null) {
                        Log.d("Response = > ", response.raw().toString());
                        int code = response.body().getResponseCode();
                        if (code == 200) {
                            Bundle bundle = new Bundle();
                            bundle.putString(FLOW_FROM, FLOW_FROM_LOGIN);
                            bundle.putString("phoneNumber", logInModel.getPhoneNumber());
                            Navigation.findNavController(requireView()).navigate(R.id.otpFragment, bundle);
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
                if (flowFrom.equals(FLOW_FROM_REGISTER)) {
                    Bundle bundle = new Bundle();
                    bundle.putString(FLOW_FROM, FLOW_FROM_LOGIN);
                    Navigation.findNavController(requireView()).navigate(R.id.authFragment, bundle);
                } else {
                    Navigation.findNavController(requireView()).navigate(R.id.authFragment);
                }
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }
}
