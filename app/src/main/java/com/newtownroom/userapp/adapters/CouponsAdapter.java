package com.newtownroom.userapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.newtownroom.userapp.R;
import com.newtownroom.userapp.models.Coupon;
import com.newtownroom.userapp.ui.CouponsActivity;

import java.util.ArrayList;

public class CouponsAdapter extends RecyclerView.Adapter<CouponsAdapter.CouponsViewHolder> {

    private Context mContext;
    private ArrayList<Coupon> couponDataList;

    public CouponsAdapter(Context mContext, ArrayList<Coupon> couponDataList) {
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

        Coupon coupon = couponDataList.get(holder.getAdapterPosition());

        holder.txtCoupon.setText(coupon.getCode());
        holder.txtSubtitle.setText(coupon.getDescription());

        String couponTitle = "Get " + coupon.getAmount() + "% OFF";
        holder.txtCouponTitle.setText(couponTitle);

        holder.matBtnApply.setOnClickListener((view -> {
            if (mContext instanceof CouponsActivity) {
                ((CouponsActivity) mContext).applyCoupon(holder.getAdapterPosition());
            }
        }));

    }

    @Override
    public int getItemCount() {
        return couponDataList.size();
    }

    public class CouponsViewHolder extends RecyclerView.ViewHolder {

        MaterialButton matBtnApply;
        TextView txtCoupon, txtCouponTitle, txtSubtitle;

        public CouponsViewHolder(@NonNull View itemView) {
            super(itemView);

            matBtnApply = itemView.findViewById(R.id.matBtnApply);
            txtCoupon = itemView.findViewById(R.id.txtCoupon);
            txtCouponTitle = itemView.findViewById(R.id.txtCouponTitle);
            txtSubtitle = itemView.findViewById(R.id.txtSubtitle);
        }
    }
}
