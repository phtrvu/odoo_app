package com.example.connect_odoo_mobile.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.connect_odoo_mobile.MainActivity;
import com.example.connect_odoo_mobile.R;
import com.example.connect_odoo_mobile.adapter.ContactAdapter;
import com.example.connect_odoo_mobile.data.ConnectOdoo;
import com.example.connect_odoo_mobile.data_models.Contact;
import com.google.gson.Gson;

import org.apache.xmlrpc.XmlRpcException;

import java.util.List;

public class ContactFragment extends Fragment {
    private View view;
    private ContactAdapter contactAdapter;
    private List<Contact> contactList;
    private RecyclerView layoutView;
    private ConnectOdoo getDataFromOdoo = new ConnectOdoo();
    private Gson gson = new Gson();
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_contact, container, false);

        //mapping view
        mapping();
        //set contact recycler view
        setContactRecyclerView();
        //
        return view;
    }

    private void setContactRecyclerView() {
        String db,url,user,pass;
        url = MainActivity.url;
        db = MainActivity.db;
        user = MainActivity.user;
        pass = MainActivity.pass;
//        db = "bitnami_odoo";
//        user = "vunpt@t4tek.co" ;
//        pass = "12062001";
//        url = "https://android.t4erp.cf";
        try {
            progressBar.setVisibility(View.INVISIBLE);
            contactList = getDataFromOdoo.getContact(db,url,user,pass);
            progressBar.setVisibility(View.GONE);
        } catch (XmlRpcException e) {
            e.printStackTrace();
        }
        contactAdapter = new ContactAdapter(getContext(), contactList);
        LinearLayoutManager contactManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        layoutView.setLayoutManager(contactManager);
        layoutView.setAdapter(contactAdapter);
    }

    private void mapping() {
        layoutView = view.findViewById(R.id.layoutView);
        progressBar = view.findViewById(R.id.progress_bar);
    }
}