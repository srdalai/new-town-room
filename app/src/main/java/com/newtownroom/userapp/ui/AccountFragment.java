package com.newtownroom.userapp.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

    private MaterialButton loginButton, logoutButton;
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

        loginButton = view.findViewById(R.id.loginButton);
        logoutButton = view.findViewById(R.id.logoutButton);
        linearLayout = view.findViewById(R.id.linearLayout);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextMobile = view.findViewById(R.id.editTextMobile);
        editTextName = view.findViewById(R.id.editTextName);

        if (preferenceManager.isLoggedIn()) {
            loginButton.setVisibility(View.GONE);
            logoutButton.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.VISIBLE);
        } else {
            loginButton.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.GONE);
            logoutButton.setVisibility(View.GONE);

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
    }
}