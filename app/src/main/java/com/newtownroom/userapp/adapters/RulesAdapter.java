package com.newtownroom.userapp.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.newtownroom.userapp.R;
import com.newtownroom.userapp.models.RulesData;

import java.util.ArrayList;
import java.util.List;

public class RulesAdapter extends RecyclerView.Adapter<RulesAdapter.RulesViewHolder> {

    Context mContext;
    ArrayList<RulesData> rulesData;

    public RulesAdapter(@NonNull Context context, @NonNull ArrayList<RulesData> rulesData) {
        this.mContext = context;
        this.rulesData = rulesData;
    }

    @NonNull
    @Override
    public RulesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rules_layout, parent, false);
        return new RulesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RulesViewHolder holder, int position) {
        RulesData data = rulesData.get(holder.getAdapterPosition());

        holder.rulesTextView.setText(data.getTitle());

        if (data.getDescription() != null && data.getDescription().trim().length() != 0) {
            holder.infoImageView.setVisibility(View.VISIBLE);
        } else {
            holder.infoImageView.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener((view -> {
            if (data.getDescription() != null && data.getDescription().trim().length() != 0) {
                showDetailsDialog(mContext, "", data.getDescription());
            }
        }));

    }

    @Override
    public int getItemCount() {
        return rulesData.size();
    }

    public static class RulesViewHolder extends RecyclerView.ViewHolder {

        TextView rulesTextView;
        ImageView infoImageView;

        public RulesViewHolder(@NonNull View itemView) {
            super(itemView);
            rulesTextView = itemView.findViewById(R.id.rulesTextView);
            infoImageView = itemView.findViewById(R.id.infoImageView);
        }
    }

    private void showDetailsDialog(Context thisContext, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(thisContext);
        builder.setMessage(message);
        builder.create().show();
    }
}
