package com.example.connect_odoo_mobile.contact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.connect_odoo_mobile.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class AddContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        //set toolbar
        setToolbar();
        //checkIsCompany
        checkIsCompany();
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolBar);
        this.setSupportActionBar(toolbar);
        setTitle("");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    //check company checkbox
    private void checkIsCompany() {
        CheckBox chkCompany = findViewById(R.id.chkCompany);
        TextInputLayout layoutCompany = findViewById(R.id.layoutCompany);
        chkCompany.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                layoutCompany.setVisibility(View.GONE);
            } else {
                layoutCompany.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_contact, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_save:
                Toast.makeText(this, "save", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_cancel:
                Toast.makeText(this, "cancel", Toast.LENGTH_SHORT).show();
                break;
            default:
                onBackPressed();
        }
        return true;
    }
}