package com.example.connect_odoo_mobile.account;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.connect_odoo_mobile.R;
import com.example.connect_odoo_mobile.authenticate.SignInActivity;
import com.example.connect_odoo_mobile.handle.BitmapUtils;

import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AcountViewHolder> {
    private Context context;
    private List<Account> acountList;

    public AccountAdapter(Context context, List<Account> acountList) {
        this.context = context;
        this.acountList = acountList;
    }

    @NonNull
    @Override
    public AcountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_account,parent,false);
        return new AcountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AcountViewHolder holder, int position) {
        Account acount = acountList.get(position);
        holder.txtName.setText(acount.getName());
        holder.txtUrl.setText(acount.getUrl());
        holder.imgAvatar.setImageBitmap(BitmapUtils.getBitmapImage(context,acount.getImage()));

        holder.imgSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, SignInActivity.class));
            }
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return acountList.size();
    }

    public class AcountViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName, txtUrl;
        private ImageView imgAvatar, imgDelete, imgSignOut;

        public AcountViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtUrl = itemView.findViewById(R.id.txtUrl);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            imgSignOut = itemView.findViewById(R.id.imgSignOut);
        }
    }
}
