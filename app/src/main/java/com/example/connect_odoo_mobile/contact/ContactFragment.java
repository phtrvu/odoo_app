package com.example.connect_odoo_mobile.contact;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.connect_odoo_mobile.authenticate.MainActivity;
import com.example.connect_odoo_mobile.R;
import com.example.connect_odoo_mobile.contact.Contact;
import com.example.connect_odoo_mobile.contact.ContactAdapter;
import com.example.connect_odoo_mobile.handle.OdooConnect;
import com.example.connect_odoo_mobile.handle.OdooUtils;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ContactFragment extends Fragment {
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_contact, container, false);
        //set contact recycler view
        setContactRecyclerView();
        //
        return view;
    }

    private void setContactRecyclerView() {
        RecyclerView layoutView = view.findViewById(R.id.layoutView);
        ProgressBar progressBar = view.findViewById(R.id.progress_bar);
        ContactAdapter contactAdapter;
        List<Contact> contactList;

        progressBar.setVisibility(View.INVISIBLE);
        contactList = getContact();
        progressBar.setVisibility(View.GONE);

        contactAdapter = new ContactAdapter(getContext(), contactList);
        LinearLayoutManager contactManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        layoutView.setLayoutManager(contactManager);
        layoutView.setAdapter(contactAdapter);
    }

    private List<Contact> getContact() {
        String db, url, pass, path = "object";
        int uid;
        url = MainActivity.url;
        db = MainActivity.db;
        pass = MainActivity.pass;
        uid = MainActivity.uid;
        List<Contact> contactList = new ArrayList<>();
        Contact contact;
        try {
            OdooConnect odooConnect = new OdooConnect(url, path);
            Object object = odooConnect.getContact(db, uid, pass);
            Object[] objects = (Object[]) object;
            if (objects.length > 0) {
                for (Object i : objects) {
                    int id = OdooUtils.getInteger((Map<String, Object>) i, "id");
                    String name = OdooUtils.getString((Map<String, Object>) i, "name");
                    String mail = OdooUtils.getString((Map<String, Object>) i, "email");
                    String company = OdooUtils.getString((Map<String, Object>) i, "company_name");
                    String image = OdooUtils.getString((Map<String, Object>) i, "image_128");
                    contact = new Contact(id, name, company, mail, image);
                    contactList.add(contact);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return contactList;
    }
}