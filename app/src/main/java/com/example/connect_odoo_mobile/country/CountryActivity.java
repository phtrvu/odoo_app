package com.example.connect_odoo_mobile.country;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;

import com.example.connect_odoo_mobile.R;
import com.example.connect_odoo_mobile.authenticate.MainActivity;
import com.example.connect_odoo_mobile.company.Company;
import com.example.connect_odoo_mobile.company.CompanyAdapter;
import com.example.connect_odoo_mobile.contact.AddContactActivity;
import com.example.connect_odoo_mobile.contact.Contact;
import com.example.connect_odoo_mobile.handle.CountryInterface;
import com.example.connect_odoo_mobile.handle.OdooConnect;
import com.example.connect_odoo_mobile.handle.OdooUtils;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CountryActivity extends AppCompatActivity {
    private Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);
        setToolbar();
        //handle thread
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        getDataIntent();
        try {
            getCountry();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    private void getDataIntent(){
        Bundle bundle = getIntent().getExtras();
        contact = (Contact) bundle.getSerializable("contact_temp");
    }
    private void getCountry() throws MalformedURLException {
        String db, url, pass,path = "object";
        int uid;
        url = MainActivity.url;
        db = MainActivity.db;
        pass = MainActivity.pass;
        uid = MainActivity.uid;
        RecyclerView rvCountry = findViewById(R.id.rvCountry);
        Country country;
        List<Country> countryList = new ArrayList<>();
        OdooConnect odooConnect = new OdooConnect(url,path);
        Object[] objects = (Object[]) odooConnect.getCountry(db,uid,pass);
        for(Object i: objects){
            int id = OdooUtils.getInteger((Map<String, Object>) i, "id");
            String name = OdooUtils.getString((Map<String, Object>) i, "name");
            country = new Country(id,name);
            countryList.add(country);
        }
        CountryAdapter countryAdapter = new CountryAdapter(countryList, new CountryInterface() {
            @Override
            public void onClickCountryItemRecyclerView(Country country) {
                Intent intent = new Intent(CountryActivity.this, AddContactActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("contact_temp",contact);
                bundle.putString("country", String.valueOf(country.getName()));
                bundle.putInt("id", country.getId());
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
        LinearLayoutManager CountryManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvCountry.setLayoutManager(CountryManager);
        rvCountry.setAdapter(countryAdapter);
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