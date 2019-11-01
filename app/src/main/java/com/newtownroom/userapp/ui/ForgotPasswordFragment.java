package com.newtownroom.userapp.ui;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.newtownroom.userapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForgotPasswordFragment extends Fragment {

    private MaterialButton btnSubmit;
    private TextInputEditText editTextEmail;
    private String email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forgot_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnSubmit = view.findViewById(R.id.btnSubmit);
        editTextEmail = view.findViewById(R.id.editTextEmail);

        btnSubmit.setOnClickListener((_view) -> {
            if (editTextEmail.getText().toString().length() != 0) {
                email = editTextEmail.getText().toString();
                Snackbar.make(view, "Password reset instruction has been sent to \"" + email + "\"", Snackbar.LENGTH_SHORT).show();
            } else {
                Snackbar.make(view, "Please enter a valid Email!", Snackbar.LENGTH_SHORT).show();
            }

        });

        registerBackPressedCallbacks(view);


    }

    private void registerBackPressedCallbacks(View view) {
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(view).navigate(R.id.loginFragment);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }
}
