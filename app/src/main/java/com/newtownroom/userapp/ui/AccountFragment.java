package com.newtownroom.userapp.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.newtownroom.userapp.R;
import com.newtownroom.userapp.rest.GetDataService;
import com.newtownroom.userapp.rest.RetrofitClientInstance;
import com.newtownroom.userapp.restmodels.UpdateUserInput;
import com.newtownroom.userapp.restmodels.UpdateUserResponse;
import com.newtownroom.userapp.utils.PreferenceManager;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountFragment extends Fragment {

    private static final String TAG = AccountFragment.class.getSimpleName();

    private MaterialButton loginButton, logoutButton, updateButton;
    private PreferenceManager preferenceManager;
    private ProgressDialog progressDialog;
    private LinearLayout linearLayout;

    private TextInputEditText editTextEmail, editTextMobile, editTextName;
    private String email = "", name = "", phone = "";
    private GetDataService service;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        preferenceManager = new PreferenceManager(requireContext());

        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setMessage("Loading....");

        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        initView(view);

        if (preferenceManager.isLoggedIn()) {
            loginButton.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
        } else {
            loginButton.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.GONE);
        }

        if (preferenceManager.getEmail() != null) {
            email = preferenceManager.getEmail();
        }

        if (preferenceManager.getPhoneNumber() != null) {
            phone = preferenceManager.getPhoneNumber();
        }

        if (preferenceManager.getName() != null) {
            name = preferenceManager.getName();
        }

        editTextEmail.setText(email);
        editTextMobile.setText(preferenceManager.getPhoneNumber());
        editTextName.setText(preferenceManager.getName());

        setUpOnClickListeners();
    }

    private void initView(View view) {
        loginButton = view.findViewById(R.id.loginButton);
        logoutButton = view.findViewById(R.id.logoutButton);
        linearLayout = view.findViewById(R.id.linearLayout);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextMobile = view.findViewById(R.id.editTextMobile);
        editTextName = view.findViewById(R.id.editTextName);
        updateButton = view.findViewById(R.id.updateButton);

        editTextEmail.setText(email);
        editTextName.setText(name);
    }

    private void setUpOnClickListeners() {

        loginButton.setOnClickListener((v -> {
            startActivity(new Intent(requireContext(), UserAuthentication.class));
        }));

        logoutButton.setOnClickListener((v) -> {
            progressDialog.setMessage("Logging you out....");
            progressDialog.show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                    preferenceManager.clearSharedPrefs();
                    startActivity(new Intent(requireContext(), UserAuthentication.class));
                    requireActivity().finish();
                }
            }, 2000);
        });

        updateButton.setOnClickListener((view -> {
            String newEmail = editTextEmail.getText().toString();
            String newName = editTextName.getText().toString();
            makeAPICall(newEmail, newName);
        }));

        editTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                updateUI();
            }
        });

        editTextName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                updateUI();
            }
        });
    }

    private void updateUI() {
        String newEmail = editTextEmail.getText().toString();
        String newName = editTextName.getText().toString();

        if (newEmail.equals(email) && newName.equals(name)) {
            updateButton.setVisibility(View.GONE);
        } else {
            updateButton.setVisibility(View.VISIBLE);
        }
    }

    private void makeAPICall(String newEmail, String newName) {
        progressDialog.setMessage("Processing Request...");
        progressDialog.show();
        UpdateUserInput updateUserInput = new UpdateUserInput();
        updateUserInput.setUserId(preferenceManager.getUserID());
        updateUserInput.setEmail(newEmail);
        updateUserInput.setName(newName);
        updateUserInput.setUniqid(preferenceManager.getUniqueID());

        Call<UpdateUserResponse> call = service.updateUser(updateUserInput);
        call.enqueue(new Callback<UpdateUserResponse>() {
            @Override
            public void onResponse(@NotNull Call<UpdateUserResponse> call, @NotNull Response<UpdateUserResponse> response) {
                progressDialog.dismiss();
                if (response.code() == 200) {
                    if (response.body().getCode() == 200) {
                        updateButton.setVisibility(View.GONE);
                        preferenceManager.setEmail(newEmail);
                        preferenceManager.setName(newName);
                    }
                    ((MainActivity) requireContext()).showSnack(response.body().getMsg(), Snackbar.LENGTH_LONG);
                }
            }

            @Override
            public void onFailure(@NotNull Call<UpdateUserResponse> call, @NotNull Throwable t) {
                progressDialog.dismiss();
                Log.d(TAG, t.toString());

            }
        });
    }
}