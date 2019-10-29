package com.newtownroom.userapp.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.newtownroom.userapp.R;
import com.newtownroom.userapp.utils.PreferenceManager;

public class AccountFragment extends Fragment {

    private MaterialButton loginButton, logoutButton, editButton, updateButton;
    private PreferenceManager preferenceManager;
    private ProgressDialog progressDialog;
    private LinearLayout linearLayout;

    private TextInputEditText editTextEmail, editTextMobile, editTextName;

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

        initView(view);

        if (preferenceManager.isLoggedIn()) {
            loginButton.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
        } else {
            loginButton.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.GONE);

        }

        if (preferenceManager.getEmail() != null) {
            editTextEmail.setText(preferenceManager.getEmail());
        }

        if (preferenceManager.getPhoneNumber() != null) {
            editTextMobile.setText(preferenceManager.getPhoneNumber());
        }

        if (preferenceManager.getName() != null) {
            editTextName.setText(preferenceManager.getName());
        }

        setUpOnClickListeners();
    }

    private void initView(View view) {
        loginButton = view.findViewById(R.id.loginButton);
        logoutButton = view.findViewById(R.id.logoutButton);
        linearLayout = view.findViewById(R.id.linearLayout);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextMobile = view.findViewById(R.id.editTextMobile);
        editTextName = view.findViewById(R.id.editTextName);
        editButton = view.findViewById(R.id.editButton);
        updateButton = view.findViewById(R.id.updateButton);
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

        editButton.setOnClickListener((v -> {

            editTextEmail.setClickable(true);
            editTextEmail.setFocusable(true);
            editTextEmail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

            editTextName.setClickable(true);
            editTextName.setFocusable(true);
            editTextName.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

            logoutButton.setVisibility(View.GONE);
            editButton.setVisibility(View.GONE);
            updateButton.setVisibility(View.VISIBLE);
        }));

        updateButton.setOnClickListener((view -> {

            editTextEmail.setClickable(false);
            editTextEmail.setFocusable(false);
            editTextEmail.setInputType(InputType.TYPE_NULL);

            editTextName.setClickable(false);
            editTextName.setFocusable(false);
            editTextName.setInputType(InputType.TYPE_NULL);

            logoutButton.setVisibility(View.VISIBLE);
            editButton.setVisibility(View.VISIBLE);
            updateButton.setVisibility(View.GONE);
        }));
    }
}