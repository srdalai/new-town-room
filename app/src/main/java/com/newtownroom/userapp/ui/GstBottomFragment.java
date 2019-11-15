package com.newtownroom.userapp.ui;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.newtownroom.userapp.R;
import com.newtownroom.userapp.models.GstModel;
import com.newtownroom.userapp.utils.PreferenceManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class GstBottomFragment extends BottomSheetDialogFragment {

    public GstBottomFragment() {
        // Required empty public constructor
    }

    private EditText editTextGstName, editTextGstNumber, editTextGstAddress, editTextGstContactNo, editTextGstEmail;
    private MaterialButton btnApplyGstDetails;
    GstModel gstModel = null;
    PreferenceManager preferenceManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.component_gst_details_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        preferenceManager = new PreferenceManager(requireContext());

        editTextGstName = view.findViewById(R.id.editTextGstName);
        editTextGstNumber = view.findViewById(R.id.editTextGstNumber);
        editTextGstAddress = view.findViewById(R.id.editTextGstAddress);
        editTextGstContactNo = view.findViewById(R.id.editTextGstContactNo);
        editTextGstEmail = view.findViewById(R.id.editTextGstEmail);
        btnApplyGstDetails = view.findViewById(R.id.btnApplyGstDetails);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String gst_details = bundle.getString("gst_details");
            gstModel = new Gson().fromJson(gst_details, GstModel.class);
            editTextGstName.setText(gstModel.getLegalName());
            editTextGstNumber.setText(gstModel.getGstNumber());
            editTextGstAddress.setText(gstModel.getGstAddress());
            editTextGstContactNo.setText(gstModel.getGstContact());
            editTextGstEmail.setText(gstModel.getGstEmail());
        }

        btnApplyGstDetails.setOnClickListener((_view) -> {
            //setGstData();
            setEmptyGstData();
        });


    }

    private void setGstData() {
        if (editTextGstName.getText().toString().trim().equals("")) {
            Toast.makeText(requireContext(), "Please provide a valid name", Toast.LENGTH_SHORT).show();
            editTextGstName.requestFocus();
        } else if (editTextGstNumber.getText().toString().trim().equals("")) {
            Toast.makeText(requireContext(), "Please provide a valid GST number", Toast.LENGTH_SHORT).show();
            editTextGstNumber.requestFocus();
        } else if (editTextGstAddress.getText().toString().trim().equals("")) {
            Toast.makeText(requireContext(), "Please provide a valid address", Toast.LENGTH_SHORT).show();
            editTextGstAddress.requestFocus();
        } else if (editTextGstContactNo.getText().toString().trim().equals("")) {
            Toast.makeText(requireContext(), "Please provide a valid contact number", Toast.LENGTH_SHORT).show();
            editTextGstContactNo.requestFocus();
        } else if (editTextGstEmail.getText().toString().trim().equals("")) {
            Toast.makeText(requireContext(), "Please provide a valid email", Toast.LENGTH_SHORT).show();
            editTextGstEmail.requestFocus();
        } else {
            String name = editTextGstName.getText().toString();
            String gstNum = editTextGstNumber.getText().toString();
            String gstAddr = editTextGstAddress.getText().toString();
            String gstContact = editTextGstContactNo.getText().toString();
            String gstEmail = editTextGstEmail.getText().toString();

            gstModel = new GstModel();
            gstModel.setUserId(preferenceManager.getUserID());
            gstModel.setLegalName(name);
            gstModel.setGstNumber(gstNum);
            gstModel.setGstAddress(gstAddr);
            gstModel.setGstContact(gstContact);
            gstModel.setGstEmail(gstEmail);

            if (requireActivity() instanceof BookingComplete) {
                ((BookingComplete)requireContext()).setGstDetails(gstModel);
            }

            dismiss();
        }
    }

    private void setEmptyGstData() {
        String name = editTextGstName.getText().toString();
        String gstNum = editTextGstNumber.getText().toString();
        String gstAddr = editTextGstAddress.getText().toString();
        String gstContact = editTextGstContactNo.getText().toString();
        String gstEmail = editTextGstEmail.getText().toString();

        gstModel = new GstModel();
        gstModel.setUserId(preferenceManager.getUserID());
        gstModel.setLegalName(name);
        gstModel.setGstNumber(gstNum);
        gstModel.setGstAddress(gstAddr);
        gstModel.setGstContact(gstContact);
        gstModel.setGstEmail(gstEmail);

        if (requireActivity() instanceof BookingComplete) {
            ((BookingComplete)requireContext()).setGstDetails(gstModel);
        }

        dismiss();
    }




    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
        if (requireActivity() instanceof BookingComplete) {
            ((BookingComplete)requireContext()).gstDismissed();
        }
    }
}
