package com.example.connect_odoo_mobile.company;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.connect_odoo_mobile.R;
import com.example.connect_odoo_mobile.contact.AddContactActivity;
import com.example.connect_odoo_mobile.contact.Contact;
import com.example.connect_odoo_mobile.handle.ImageUtils;
import com.example.connect_odoo_mobile.handle.IntentInterface;

import java.util.List;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.CompanyViewHolder> {
    private final Context context;
    private final List<Company> companyArrayList;
    private IntentInterface intentInterface;

    public CompanyAdapter(Context context, List<Company> companyArrayList,IntentInterface intentInterface) {
        this.context = context;
        this.companyArrayList = companyArrayList;
        this.intentInterface = intentInterface;
    }

    @NonNull
    @Override
    public CompanyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_company, parent, false);
        return new CompanyViewHolder(view);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onBindViewHolder(@NonNull CompanyViewHolder holder, int position) {
        Company company = companyArrayList.get(position);
        if (!company.getName().equals(false)) {
            holder.txtName.setText((String) company.getName());
        }
        holder.layoutCompany.setOnClickListener(view -> {
            intentInterface.onClickCompanyItemRecyclerView(company);
        });
    }

    @Override
    public int getItemCount() {
        return companyArrayList.size();
    }

    public static class CompanyViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtName;
        private final ConstraintLayout layoutCompany;

        public CompanyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtCompany);
            layoutCompany = itemView.findViewById(R.id.layoutCompany);
        }
    }
}
