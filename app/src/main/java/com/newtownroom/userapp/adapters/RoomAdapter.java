package com.newtownroom.userapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.newtownroom.userapp.R;
import com.newtownroom.userapp.models.RoomData;
import com.newtownroom.userapp.ui.GuestDetails;

import java.util.ArrayList;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {

    Context mContext;
    ArrayList<RoomData> roomList;

    public RoomAdapter(Context mContext, ArrayList<RoomData> roomList) {
        this.mContext = mContext;
        this.roomList = roomList;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room_guest_layout, parent, false);
        return new RoomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {

        RoomData roomData = roomList.get(holder.getAdapterPosition());

        if (position == 0) {
            //holder.roomCard.expand();
        }

        //holder.roomCard.setTitle("Room " + (position+1));
        holder.txtRoom.setText("Room " + (position+1));

        if (position == roomList.size() - 1) {
            holder.btnAddRoom.setVisibility(View.VISIBLE);
        } else {
            holder.btnAddRoom.setVisibility(View.GONE);
        }

        if (position == 0) {
            holder.btnDeleteRoom.setVisibility(View.GONE);
        } else {
            holder.btnDeleteRoom.setVisibility(View.VISIBLE);
        }

        holder.txtNumAdult.setText(String.valueOf(roomData.getAdults()));
        holder.txtNumChild.setText(String.valueOf(roomData.getChildren()));
        String guestText = "";
        if (roomData.getChildren() == 0) {
            guestText = roomData.getAdults() + " Guests";
        } else {
            guestText = roomData.getAdults() + " Guests, " + roomData.getChildren() + " Child" ;
        }

        holder.txtGuest.setText(guestText);

        holder.imageViewMinus.setOnClickListener((view -> {
            ((GuestDetails) mContext).removeAdult(position);
        }));

        holder.imageViewPlus.setOnClickListener((view -> {
            ((GuestDetails) mContext).addAdult(position);
        }));

        holder.imageViewMinusChild.setOnClickListener((view -> {
            ((GuestDetails) mContext).removeChildren(position);
        }));

        holder.imageViewPlusChild.setOnClickListener((view -> {
            ((GuestDetails) mContext).addChildren(position);
        }));

        holder.btnAddRoom.setOnClickListener((view -> {
            ((GuestDetails) mContext).addRoom();
        }));

        holder.btnDeleteRoom.setOnClickListener((view -> {
            ((GuestDetails) mContext).deleteRoom(position);
        }));

        /*holder.childrenCheckBox.setOnCheckedChangeListener((compoundButton, checked) -> {
            if (mContext instanceof GuestDetails) {
                ((GuestDetails) mContext).setChildren(position);
            }
        });*/

        /*if (roomData.getChildren() == 0) {
            holder.childrenCheckBox.setChecked(false);
        } else {
            holder.childrenCheckBox.setChecked(true);
        }*/




    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder {

        //ExpandableCardView roomCard;
        TextView txtRoom, txtGuest, txtNumAdult, txtNumChild;
        MaterialButton btnDeleteRoom, btnAddRoom;
        ImageView imageViewMinus, imageViewPlus, imageViewMinusChild, imageViewPlusChild;
        CheckBox childrenCheckBox;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            //roomCard = itemView.findViewById(R.id.roomCard);
            txtRoom = itemView.findViewById(R.id.txtRoom);
            txtGuest = itemView.findViewById(R.id.txtGuest);
            txtNumAdult = itemView.findViewById(R.id.txtNumAdult);
            btnDeleteRoom = itemView.findViewById(R.id.btnDeleteRoom);
            btnAddRoom = itemView.findViewById(R.id.btnAddRoom);
            imageViewMinus = itemView.findViewById(R.id.imageViewMinus);
            imageViewPlus = itemView.findViewById(R.id.imageViewPlus);
            childrenCheckBox = itemView.findViewById(R.id.childrenCheckBox);
            txtNumChild = itemView.findViewById(R.id.txtNumChild);
            imageViewMinusChild = itemView.findViewById(R.id.imageViewMinusChild);
            imageViewPlusChild = itemView.findViewById(R.id.imageViewPlusChild);
        }
    }
}
