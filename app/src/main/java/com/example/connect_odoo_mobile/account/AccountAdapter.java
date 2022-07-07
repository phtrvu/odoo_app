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
import com.example.connect_odoo_mobile.handle.ImageUtils;

import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountViewHolder> {
    private final Context context;
    private final List<Account> accountList;

    public AccountAdapter(Context context, List<Account> accountList) {
        this.context = context;
        this.accountList = accountList;
    }

    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_account, parent, false);
        return new AccountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {
        Account account = accountList.get(position);
        holder.txtName.setText(account.getName());
        holder.txtUrl.setText(account.getUrl());
        holder.imgAvatar.setImageBitmap(ImageUtils.getBitmapImage(account.getImage()));

        holder.imgSignOut.setOnClickListener(v -> context.startActivity(new Intent(context, SignInActivity.class)));

        holder.imgDelete.setOnClickListener(v -> {

        });
    }

    @Override
    public int getItemCount() {
        return accountList.size();
    }

    public static class AccountViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtName, txtUrl;
        private final ImageView imgAvatar, imgDelete, imgSignOut;

        public AccountViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtUrl = itemView.findViewById(R.id.txtUrl);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            imgSignOut = itemView.findViewById(R.id.imgSignOut);
        }
    }
}
