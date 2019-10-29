package com.newtownroom.userapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.newtownroom.userapp.R;
import com.newtownroom.userapp.models.CouponData;
import com.newtownroom.userapp.ui.CouponsActivity;

import java.util.ArrayList;

public class CouponsAdapter extends RecyclerView.Adapter<CouponsAdapter.CouponsViewHolder> {

    private Context mContext;
    private ArrayList<CouponData> couponDataList;

    public CouponsAdapter(Context mContext, ArrayList<CouponData> couponDataList) {
        this.mContext = mContext;
        this.couponDataList = couponDataList;
    }

    @NonNull
    @Override
    public CouponsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coupon_layout, parent, false);
        return new CouponsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CouponsViewHolder holder, int position) {

        holder.matBtnApply.setOnClickListener((view -> {
            if (mContext instanceof CouponsActivity) {
                ((CouponsActivity) mContext).applyCoupon();
            }
        }));

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class CouponsViewHolder extends RecyclerView.ViewHolder {

        MaterialButton matBtnApply;

        public CouponsViewHolder(@NonNull View itemView) {
            super(itemView);

            matBtnApply = itemView.findViewById(R.id.matBtnApply);
        }
    }
}
