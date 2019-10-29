package com.newtownroom.userapp.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.newtownroom.userapp.R;
import com.newtownroom.userapp.restmodels.SignUpInputModel;
import com.newtownroom.userapp.restmodels.SignUpResponseModel;
import com.newtownroom.userapp.rest.GetDataService;
import com.newtownroom.userapp.rest.RetrofitClientInstance;
import com.newtownroom.userapp.utils.PreferenceManager;
import com.newtownroom.userapp.utils.Utilities;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.LOCATION_SERVICE;
import static com.newtownroom.userapp.utils.AppConstants.FLOW_FROM;
import static com.newtownroom.userapp.utils.AppConstants.FLOW_FROM_LOGIN;
import static com.newtownroom.userapp.utils.AppConstants.FLOW_FROM_REGISTER;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment implements LocationListener {

    static final int REQUEST_PERMISSION_KEY = 899;
    private LocationManager locationService;
    private LocationManager locationManager;
    private String provider;
    String address = "Waiting for location...";

    TextInputEditText editTextName, editTextPhone, editTextEmail, editTextPassword;
    MaterialButton btnRegister, btnLogin;
    String name, email, phone;
    ProgressDialog progressDialog;

    GetDataService service;
    PreferenceManager preferenceManager;
    String flowFrom = "";
    double lat = 0, lng = 0;
    boolean useLocation = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (requireActivity() instanceof UserAuthentication) {
            ((UserAuthentication) requireActivity()).setActionBarTitle("Signup");
            ((UserAuthentication) requireActivity()).canGoBack(true);
        }

        editTextName = view.findViewById(R.id.editTextName);
        editTextPhone = view.findViewById(R.id.editTextPhone);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        btnRegister = view.findViewById(R.id.btnRegister);
        btnLogin = view.findViewById(R.id.btnLogin);

        preferenceManager = new PreferenceManager(requireContext());

        locationService = (LocationManager) requireContext().getSystemService(LOCATION_SERVICE);

        if (preferenceManager.getPhoneNumber() != null) {
            editTextPhone.setText(preferenceManager.getPhoneNumber());
        }

        Bundle bundle = getArguments();
        flowFrom = bundle != null ? bundle.getString(FLOW_FROM) : null;

        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setMessage("Loading....");

        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        btnRegister.setOnClickListener((_view) -> {

            name = editTextName.getText().toString().trim();
            email = editTextEmail.getText().toString().trim();
            phone = editTextPhone.getText().toString().trim();

            if (phone.length() < 10) {
                Snackbar.make(view, "Please enter a valid Phone Number!", Snackbar.LENGTH_LONG).show();
            } else if (name.length() == 0) {
                Snackbar.make(view, "Please enter a valid Name!", Snackbar.LENGTH_LONG).show();
            } else {
                preferenceManager.setPhoneNumber(phone);
                progressDialog.show();
                processRegisterData(name, email, phone);
            }
        });

        btnLogin.setOnClickListener((_view) -> {
            Bundle data = new Bundle();
            data.putString(FLOW_FROM, FLOW_FROM_REGISTER);
            Navigation.findNavController(requireView()).navigate(R.id.loginFragment, data);
        });

        registerBackPressedCallbacks();

    }

    @SuppressLint("MissingPermission")
    private void init() {
        // Get the location manager
        locationManager = (LocationManager) requireContext().getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the locatioin provider -> use
        // default
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        provider = LocationManager.GPS_PROVIDER;
        Location location = locationManager.getLastKnownLocation(provider);

        // Initialize the location fields
        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            onLocationChanged(location);
        } else {
            /*latituteField.setText("Waiting for location...");
            longitudeField.setText("Waiting for location...");
            textAddress.setText(address);*/
            Snackbar.make(requireView(), "Location not available yet!", Snackbar.LENGTH_LONG).show();
        }

        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION_KEY: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    useLocation = true;
                    init();
                } else {
                    useLocation = false;
                    Toast.makeText(requireContext(), "You must accept permissions.", Toast.LENGTH_LONG).show();
                }
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        boolean enabled = locationService.isProviderEnabled(LocationManager.GPS_PROVIDER);

        String[] PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        if (useLocation) {
            if(!Utilities.hasPermissions(requireContext(), PERMISSIONS)){
                ActivityCompat.requestPermissions(requireActivity(), PERMISSIONS, REQUEST_PERMISSION_KEY);
                useLocation = false;

            } else if (!enabled) {
                showLocationEnableDialog();

            } else {
                init();
            }
        }

    }

    private void showLocationEnableDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Enable Location");
        builder.setMessage("You need to enable location to use the app effectively.");
        builder.setCancelable(false);
        builder.setPositiveButton("Enable", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Don't Use", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                useLocation = false;
                if (locationManager != null)
                    locationManager.removeUpdates(RegisterFragment.this);
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (locationManager != null)
            locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {

        lat = location.getLatitude();
        lng = location.getLongitude();
        Log.d("Location Changed", "Lat => " + lat + " Long => " + lng);
        Snackbar snackbar = Snackbar.make(requireView(), "Location Changed to Lat => " + lat + " Long => " + lng, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Dismiss", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
        address = getAddress(location.getLatitude(), location.getLongitude());
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {
        Toast.makeText(requireContext(), "Enabled new provider " + provider,
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onProviderDisabled(String s) {
        Toast.makeText(requireContext(), "Disabled provider " + provider,
                Toast.LENGTH_SHORT).show();

    }

    public String getAddress(double latitude, double longitude) {
        StringBuilder stringBuilder = new StringBuilder();
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(requireContext(), Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL

            if (address != null && !address.matches("")) {
                stringBuilder.append(address);

            } else {

                if (knownName != null && !knownName.matches("")) {
                    stringBuilder.append(knownName).append(", ");

                }

                if (city != null && !city.matches("")) {
                    stringBuilder.append(city).append(", ");
                }

                if (state != null && !state.matches("")) {
                    stringBuilder.append(state).append(", ");
                }

                if (postalCode != null && !postalCode.matches("")) {
                    stringBuilder.append(postalCode).append(", ");
                }

                if (country != null && !country.matches("")) {
                    stringBuilder.append(country);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();

    }

    private void processRegisterData(String name, String email, String phone) {
        SignUpInputModel model = new SignUpInputModel();
        model.setPhoneNumber(phone);
        model.setName(name);
        if (email.length() != 0) {
            model.setEmailAddress(email);
        }

        if (lat != 0 && lng != 0) {
            String latString = String.format("%.02f", lat);
            String lngString = String.format("%.02f", lng);
            model.setLatitude(latString);
            model.setLongitude(lngString);
        }

        Call<SignUpResponseModel> call = service.postSignUpRequest(model);
        call.enqueue(new Callback<SignUpResponseModel>() {
            @Override
            public void onResponse(Call<SignUpResponseModel> call, Response<SignUpResponseModel> response) {
                if (!isAdded()) return;
                progressDialog.dismiss();
                if (response.isSuccessful() && response.code() == 200) {
                    SignUpResponseModel signUpModel = response.body();
                    if(signUpModel != null) {
                        Log.d("Response = > ", response.raw().toString());
                        int code = signUpModel.getResponseCode();
                        if (code == 200) {
                            Bundle bundle = new Bundle();
                            bundle.putString(FLOW_FROM, FLOW_FROM_REGISTER);
                            bundle.putString("phoneNumber", phone);
                            Navigation.findNavController(requireView()).navigate(R.id.otpFragment, bundle);
                        } else {
                            Snackbar.make(requireView(), signUpModel.getMessage(), Snackbar.LENGTH_LONG).show();
                        }
                    } else {
                        Snackbar.make(requireView(), "Something went wrong...Please try later!", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(requireView(), "Something went wrong...Please try later!", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SignUpResponseModel> call, Throwable t) {
                if (!isAdded()) return;
                progressDialog.dismiss();
                Log.d("Error", t.toString());
                Snackbar.make(requireView(), "Something went wrong...Please try later!", Snackbar.LENGTH_LONG).show();
            }
        });


    }

    private void registerBackPressedCallbacks() {
        // This callback will only be called when MyFragment is at least Started.
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                if (flowFrom.equals(FLOW_FROM_LOGIN)) {
                    Bundle bundle = new Bundle();
                    bundle.putString(FLOW_FROM, FLOW_FROM_REGISTER);
                    Navigation.findNavController(requireView()).navigate(R.id.loginFragment, bundle);
                } else {
                    Navigation.findNavController(requireView()).navigate(R.id.authFragment);
                }
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        // The callback can be enabled or disabled here or in handleOnBackPressed()
    }
}
