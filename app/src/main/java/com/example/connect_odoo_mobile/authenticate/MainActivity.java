package com.example.connect_odoo_mobile.authenticate;

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
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.connect_odoo_mobile.R;
import com.example.connect_odoo_mobile.account.AccountManagerFragment;
import com.example.connect_odoo_mobile.company.CompanyFragment;
import com.example.connect_odoo_mobile.contact.ContactFragment;
import com.example.connect_odoo_mobile.handle.BitmapUtils;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private TextView txtDisplayName, txtEmail;
    private ImageView imgAvatar;
    public static int uid;
    public static String db, url, user, pass, name, email, image;
    private static final int FRAGMENT_CONTACT = 0;
    private static final int FRAGMENT_COMPANY = 1;
    private static final int FRAGMENT_TEST = 2;
    private static final int FRAGMENT_ACCOUNT = 3;
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
        //bat su kien cho navigation view
        navigationView.setNavigationItemSelectedListener(this);
        //chay app mo thang home luôn
        replaceFragment(new ContactFragment());
        //set chọn cái biểu tượng cua thang home
        navigationView.getMenu().findItem(R.id.nav_contact).setChecked(true);
        //get intent
        getIntentSignIn();
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
                break;
            case R.id.nav_company:
                if (currentFragment != FRAGMENT_COMPANY) {
                    replaceFragment(new CompanyFragment());
                    currentFragment = FRAGMENT_COMPANY;
                    Objects.requireNonNull(getSupportActionBar()).setTitle("Company");
                }
                break;
            case R.id.nav_test:
                if (currentFragment != FRAGMENT_TEST) {
                    replaceFragment(new BlankFragment());
                    currentFragment = FRAGMENT_TEST;
                    Objects.requireNonNull(getSupportActionBar()).setTitle("Test");
                }
                break;
            case R.id.nav_account:
                if (currentFragment != FRAGMENT_ACCOUNT) {
                    replaceFragment(new AccountManagerFragment());
                    currentFragment = FRAGMENT_ACCOUNT;
                    Objects.requireNonNull(getSupportActionBar()).setTitle("Account Manager");
                }
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.layoutFrame, fragment);
        fragmentTransaction.commit();
    }

    public final void getIntentSignIn() {
        Intent intent = getIntent();
        uid = intent.getIntExtra("uid", -1);
        name = intent.getStringExtra("name");
        email = intent.getStringExtra("email");
        image = intent.getStringExtra("image");
        db = intent.getStringExtra("db");
        url = intent.getStringExtra("url");
        user = intent.getStringExtra("user");
        pass = intent.getStringExtra("pass");
        if (name != null && email != null && image != null) {
            txtEmail.setText(email);
            txtDisplayName.setText(name);
            imgAvatar.setImageBitmap(BitmapUtils.getBitmapImage(this, image));
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