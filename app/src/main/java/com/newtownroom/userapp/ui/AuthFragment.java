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
import com.newtownroom.userapp.R;
import com.newtownroom.userapp.utils.AppConstants;
import com.newtownroom.userapp.utils.PreferenceManager;

import static com.newtownroom.userapp.utils.AppConstants.FLOW_FROM;
import static com.newtownroom.userapp.utils.AppConstants.FLOW_FROM_LOGIN;

/**
 * A simple {@link Fragment} subclass.
 */
public class AuthFragment extends Fragment {

    MaterialButton btnLogin, btnSignup;
    PreferenceManager preferenceManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_auth, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (requireActivity() instanceof UserAuthentication) {
            ((UserAuthentication) requireActivity()).setActionBarTitle("User Authentication");
            ((UserAuthentication) requireActivity()).canGoBack(false);
        }

        preferenceManager = new PreferenceManager(requireContext());
        preferenceManager.setPhoneNumber(null);

        btnLogin = view.findViewById(R.id.btnLogin);
        btnSignup = view.findViewById(R.id.btnSignup);


        btnLogin.setOnClickListener((_view) -> {
            Bundle bundle = new Bundle();
            bundle.putString(FLOW_FROM, AppConstants.FLOW_FROM_AUTH);
            Navigation.findNavController(requireView()).navigate(R.id.loginFragment, bundle);
        });

        btnSignup.setOnClickListener((_view) -> {
            Bundle bundle = new Bundle();
            bundle.putString(FLOW_FROM, AppConstants.FLOW_FROM_AUTH);
            Navigation.findNavController(requireView()).navigate(R.id.registerFragment, bundle);
        });

        registerBackPressedCallbacks();
    }

    private void registerBackPressedCallbacks() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().finishAffinity();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }


}
