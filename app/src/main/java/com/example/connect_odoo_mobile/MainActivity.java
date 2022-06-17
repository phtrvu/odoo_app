package com.example.connect_odoo_mobile;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.connect_odoo_mobile.data.GetDataFromOdoo;
import com.example.connect_odoo_mobile.data_models.Contact;
import com.example.connect_odoo_mobile.fragment.CompanyFragment;
import com.example.connect_odoo_mobile.fragment.ContactFragment;
import com.example.connect_odoo_mobile.read_json.ReadJSON;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import org.apache.xmlrpc.XmlRpcException;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private Intent intent;
    private TextView txtDisplayName, txtEmail;
    private ImageView imgAvatar;
    private GetDataFromOdoo getDataFromOdoo = new GetDataFromOdoo();
    private Gson gson = new Gson();
    private int uid;
    private String name, email;
    private static final int FRAGMENT_CONTACT = 0;
    private static final int FRAGMENT_COMPANY = 1;
    private static final int FRAGMENT_INVOICE = 2;
    private int currentFragment = FRAGMENT_CONTACT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //mapping view
        mapping();
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Contact");

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.nav_drawer_open,
                R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //bat su kien cho navcigationview
        navigationView.setNavigationItemSelectedListener(this);
        //chay app mo thang home luôn
        replaceFragment(new ContactFragment());
        //set chọn cái biểu tượng cua thàng home
        navigationView.getMenu().findItem(R.id.nav_contact).setChecked(true);
        //get uid
        getUid();
        //get profile
    }

    @SuppressLint("NonConstantResourceId")
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_contact:
                if (currentFragment != FRAGMENT_CONTACT) {
                    replaceFragment(new ContactFragment());
                    currentFragment = FRAGMENT_CONTACT;
                    Objects.requireNonNull(getSupportActionBar()).setTitle("Contact");
                }
            case R.id.nav_company:
                if (currentFragment != FRAGMENT_COMPANY) {
                    replaceFragment(new CompanyFragment());
                    currentFragment = FRAGMENT_COMPANY;
                    Objects.requireNonNull(getSupportActionBar()).setTitle("Company");
                }
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.layoutFrame, fragment);
        fragmentTransaction.commit();
    }

    public void getUid() {
        intent = getIntent();
        uid = intent.getIntExtra("uid", -1);
        name = intent.getStringExtra("name");
        email = intent.getStringExtra("email");
        if(name != null && email != null){
            txtEmail.setText(email);
            txtDisplayName.setText(name);
        }
    }

    private void mapping() {
        toolbar = findViewById(R.id.toolBar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        txtDisplayName = navigationView.getHeaderView(0).findViewById(R.id.txtName);
        txtEmail = navigationView.getHeaderView(0).findViewById(R.id.txtEmail);
        imgAvatar = navigationView.getHeaderView(0).findViewById(R.id.imgAvatar);

    }

    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}