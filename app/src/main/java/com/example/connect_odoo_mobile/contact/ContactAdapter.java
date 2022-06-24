package com.example.connect_odoo_mobile.contact;

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
import com.example.connect_odoo_mobile.handle.BitmapUtils;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
    private Context context;
    private List<Contact> contactArrayList;

    public ContactAdapter(Context context, List<Contact> contactArrayList) {
        this.context = context;
        this.contactArrayList = contactArrayList;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact contact = contactArrayList.get(position);
        if (!contact.getName().equals(false)) {
            holder.txtName.setText((CharSequence) contact.getName());
        }
        if (!contact.getCompany().equals(false)) {
            holder.txtCompany.setText((CharSequence) contact.getCompany());
        }
        if(!contact.getEmail().equals(false)){
            holder.txtEmail.setText((CharSequence) contact.getEmail());
        }
        if(!contact.getImage_128().equals(false)){
            holder.imgAvatar.setImageBitmap(BitmapUtils.getBitmapImage(context, (String) contact.getImage_128()));
        }

        holder.layoutContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context,ContactDetailActivity.class);
                //data transfer to screen contact detail
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactArrayList.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName, txtEmail, txtCompany;
        private ImageView imgAvatar;
        private ConstraintLayout layoutContact;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtCompany = itemView.findViewById(R.id.txtCompany);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            layoutContact = itemView.findViewById(R.id.layoutContact);
        }
    }
}
