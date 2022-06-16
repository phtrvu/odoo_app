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

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.connect_odoo_mobile.fragment.CustomerFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private Intent intent;
    private TextView txtDisplayName,txtEmail;
    private ImageView imgAvatar;
    private int uid;
    private static final int FRAGMENT_CUSTOMER = 0;
    private int currentFragment = FRAGMENT_CUSTOMER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //mapping view
        mapping();
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Customer");

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.nav_drawer_open,
                R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //bat su kien cho navcigationview
        navigationView.setNavigationItemSelectedListener(this);
        //chay app mo thang home luôn
        replaceFragment(new CustomerFragment());
        //set chọn cái biểu tượng cảu thàng home
        navigationView.getMenu().findItem(R.id.nav_customer).setChecked(true);
        //get uid
        getUid();
    }
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_customer:
                if (currentFragment != FRAGMENT_CUSTOMER) {
                    replaceFragment(new CustomerFragment());
                    currentFragment = FRAGMENT_CUSTOMER;
                    Objects.requireNonNull(getSupportActionBar()).setTitle("Customer");
                }
            case 1:
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
        uid = intent.getIntExtra("uid",-1);
    }

    private void mapping() {
        toolbar = findViewById(R.id.toolBar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        txtDisplayName=navigationView.getHeaderView(0).findViewById(R.id.txtName);
        txtEmail=navigationView.getHeaderView(0).findViewById(R.id.txtEmail);
        imgAvatar=navigationView.getHeaderView(0).findViewById(R.id.imgAvatar);

    }
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}