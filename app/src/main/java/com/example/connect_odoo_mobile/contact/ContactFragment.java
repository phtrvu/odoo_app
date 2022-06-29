package com.example.connect_odoo_mobile.contact;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.connect_odoo_mobile.authenticate.MainActivity;
import com.example.connect_odoo_mobile.R;
import com.example.connect_odoo_mobile.handle.ConnectOdoo;
import com.example.connect_odoo_mobile.handle.Many2One;
import com.example.connect_odoo_mobile.handle.OdooConnect;
import com.example.connect_odoo_mobile.handle.OdooUtils;
import com.google.gson.Gson;

import org.apache.xmlrpc.XmlRpcException;

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
        SetContactRecyclerView();
        //
        return view;
    }

    private void SetContactRecyclerView() {
        RecyclerView layoutView = view.findViewById(R.id.layoutView);
        ProgressBar progressBar = view.findViewById(R.id.progress_bar);
        ContactAdapter contactAdapter;
        List<Contact> contactList;

        progressBar.setVisibility(View.INVISIBLE);
        contactList = GetContact();
        progressBar.setVisibility(View.GONE);

        contactAdapter = new ContactAdapter(getContext(), contactList);
        LinearLayoutManager contactManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        layoutView.setLayoutManager(contactManager);
        layoutView.setAdapter(contactAdapter);
    }

    private List<Contact> GetContact() {
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
            Object object = odooConnect.GetContact(db, uid, pass);
            Object[] objects = (Object[]) object;
            if (objects.length > 0) {
                for (Object i : objects) {
                    int id = OdooUtils.getInteger((Map<String,Object>) i,"id");
                    String name= OdooUtils.getString((Map<String, Object>) i, "name");
                    String mail= OdooUtils.getString((Map<String, Object>) i, "email");
                    String company= OdooUtils.getString((Map<String, Object>) i, "company_name");
                    String phone= OdooUtils.getString((Map<String, Object>) i, "phone");
                    String mobile= OdooUtils.getString((Map<String, Object>) i, "mobile");
                    String country= Many2One.getMany2One((Map<String, Object>) i, "country_id").getName();
                    String website= OdooUtils.getString((Map<String, Object>) i, "website");
                    String comment= OdooUtils.getString((Map<String, Object>) i, "comment");
                    String image= OdooUtils.getString((Map<String, Object>) i, "image_128");
                    contact = new Contact(id,name,company,mail,image,phone,mobile,country,comment,website);
                    contactList.add(contact);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return contactList;
    }
}