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
        if (!contact.getName().equals("")) {
            holder.txtName.setText(contact.getName());
        }
        if (!contact.getCompany_name().equals("")) {
            holder.txtCompany.setText(contact.getCompany_name());
        }
        if(!contact.getEmail().equals("")){
            holder.txtEmail.setText(contact.getEmail());
        }
        if(!contact.getImage_128().equals("")){
            holder.imgAvatar.setImageBitmap(BitmapUtils.getBitmapImage(context,contact.getImage_128()));
        }
        holder.layoutContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context,ContactDetailActivity.class);
                //data transfer to screen contact detail
                intent.putExtra("id", contact.getId());
                intent.putExtra("image", contact.getImage_128());
                intent.putExtra("name", contact.getName());
                intent.putExtra("email", contact.getEmail());
                intent.putExtra("website", contact.getWebsite());
                intent.putExtra("phone", contact.getPhone());
                intent.putExtra("mobile", contact.getMobile());
                intent.putExtra("comment", contact.getComment());
                intent.putExtra("country",contact.getCountry());
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
