package com.example.connect_odoo_mobile.account;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.connect_odoo_mobile.authenticate.MainActivity;
import com.example.connect_odoo_mobile.R;

import java.util.ArrayList;
import java.util.List;

public class AcountManagerFragment extends Fragment {
    private View view;
    private RecyclerView layoutAccount;
    private ConstraintLayout layoutAddAccount;
    private List<Account> accountList;
    private AccountAdapter accountAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_account_manager, container, false);
        //set layout account
        setRecyclerView();
        //add account for list account
        addAccount();
        return view;
    }
    private void addAccount(){
        layoutAddAccount=view.findViewById(R.id.layoutAddAccount);
        layoutAddAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void setRecyclerView() {
        layoutAccount = view.findViewById(R.id.layoutAccountManager);
        accountList = getListAccount();
        accountAdapter = new AccountAdapter(getContext(), accountList);
        LinearLayoutManager accountManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        layoutAccount.setLayoutManager(accountManager);
        layoutAccount.setAdapter(accountAdapter);
    }

    private List<Account> getListAccount() {
        String image, name, url,db,pass;
        int uid;
        image = MainActivity.image;
        name = MainActivity.name;
        url = MainActivity.url;
        db = MainActivity.db;
        pass = MainActivity.pass;
        uid = MainActivity.uid;
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account(name,url,image,db,pass,uid));
        return accounts;
    }
}