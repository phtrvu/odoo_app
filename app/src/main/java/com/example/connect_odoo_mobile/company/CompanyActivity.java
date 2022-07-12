package com.example.connect_odoo_mobile.company;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;

import com.example.connect_odoo_mobile.R;
import com.example.connect_odoo_mobile.authenticate.MainActivity;
import com.example.connect_odoo_mobile.handle.OdooConnect;
import com.example.connect_odoo_mobile.handle.OdooUtils;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CompanyActivity extends AppCompatActivity {
    private CompanyAdapter companyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);
        setToolbar();
        //handle thread
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            getCompany();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    private void getCompany() throws MalformedURLException {
        String db, url, pass,path = "object";
        int uid;
        url = MainActivity.url;
        db = MainActivity.db;
        pass = MainActivity.pass;
        uid = MainActivity.uid;
        RecyclerView rvCompany = findViewById(R.id.rvCompany);
        Company company;
        List<Company> companyList = new ArrayList<>();
        OdooConnect odooConnect = new OdooConnect(url,path);
        Object[] objects = (Object[]) odooConnect.getCompany(db,uid,pass);
        for(Object i: objects){
            int id = OdooUtils.getInteger((Map<String, Object>) i, "id");
            String name = OdooUtils.getString((Map<String, Object>) i, "name");
            company = new Company(id,name);
            companyList.add(company);
        }
        companyAdapter = new CompanyAdapter(this, companyList);
        LinearLayoutManager companyManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvCompany.setLayoutManager(companyManager);
        rvCompany.setAdapter(companyAdapter);
    }
    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolBar);
        this.setSupportActionBar(toolbar);
        setTitle("");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return true;
    }
}