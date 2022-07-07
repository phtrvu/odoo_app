package com.example.connect_odoo_mobile.contact.contact_detail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.connect_odoo_mobile.R;
import com.example.connect_odoo_mobile.handle.ImageUtils;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class ContactDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);
        //handle thread
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //set support action bar
        setToolBarForActionBar();
        //get data intent contact activity
        getDataIntent();
    }

    @SuppressLint("WrongViewCast")
    private void getDataIntent() {
        ImageView imgAvatar = findViewById(R.id.imgAvatar);
        TextInputEditText edtEmail, edtWebsite, edtPhone, edtMobile, edtNote, edtCountry;
        TextView txtName;
        txtName = findViewById(R.id.txtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtWebsite = findViewById(R.id.edtWebsite);
        edtPhone = findViewById(R.id.edtPhone);
        edtMobile = findViewById(R.id.edtMobile);
        edtNote = findViewById(R.id.edtNote);
        edtCountry = findViewById(R.id.edtCountry);
        //
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);
        String name = intent.getStringExtra("name");
        String email = intent.getStringExtra("email");
        String image = intent.getStringExtra("image");
        if (name != null) {
            txtName.setText(name);
        }
        if (email != null) {
            edtEmail.setText(email);
        }
        if (image != null) {
            imgAvatar.setImageBitmap(ImageUtils.getBitmapImage(image));
        }
    }

    private void setToolBarForActionBar() {
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        setTitle("Contact");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contact_detail, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_edit:
                Toast.makeText(this, "toast", Toast.LENGTH_SHORT).show();
                break;
            default:
                onBackPressed();
        }
        return true;
    }

}