package com.example.connect_odoo_mobile.company;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.connect_odoo_mobile.R;
import com.example.connect_odoo_mobile.handle.BitmapUtils;

import java.util.List;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.CompanyViewHolder> {
    private Context context;
    private List<Company> companyArrayList;

    public CompanyAdapter(Context context, List<Company> companyArrayList) {
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
        if (!company.getName().equals(false)) {
            holder.txtName.setText((CharSequence) company.getName());
        }
        if (!company.getCity().equals(false)) {
            holder.txtCity.setText((CharSequence) company.getCity());
        }
        if (!company.getEmail().equals(false)) {
            holder.txtEmail.setText((CharSequence) company.getEmail());
        }
        if (!company.getLogo().equals(false)) {
            holder.imgAvatar.setImageBitmap(BitmapUtils.getBitmapImage(context, (String) company.getLogo()));
        }
    }

    @Override
    public int getItemCount() {
        return companyArrayList.size();
    }

    public class CompanyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName, txtEmail, txtCity;
        private ImageView imgAvatar;

        public CompanyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtCity = itemView.findViewById(R.id.txtCity);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
        }
    }
}
