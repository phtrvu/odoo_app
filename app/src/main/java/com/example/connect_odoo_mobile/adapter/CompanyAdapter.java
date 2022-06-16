package com.example.connect_odoo_mobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.connect_odoo_mobile.R;
import com.example.connect_odoo_mobile.data_models.Company;
import com.example.connect_odoo_mobile.data_models.Contact;

import java.util.ArrayList;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.CompanyViewHolder> {
    private Context context;
    private ArrayList<Company> companyArrayList;

    public CompanyAdapter(Context context, ArrayList<Company> companyArrayList) {
        this.context = context;
        this.companyArrayList = companyArrayList;
    }

    @NonNull
    @Override
    public CompanyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_company, parent, false);
        return new CompanyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyViewHolder holder, int position) {
        Company company = companyArrayList.get(position);
        holder.txtName.setText(company.getName());
        holder.txtCity.setText(company.getCity());
        holder.txtEmail.setText(company.getEmail());
    }

    @Override
    public int getItemCount() {
        return companyArrayList.size();
    }

    public class CompanyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName, txtEmail, txtCity;

        public CompanyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtCity = itemView.findViewById(R.id.txtCity);
            txtEmail = itemView.findViewById(R.id.txtEmail);
        }
    }
}
