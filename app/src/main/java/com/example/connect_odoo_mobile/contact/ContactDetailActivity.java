package com.example.connect_odoo_mobile.contact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.connect_odoo_mobile.R;
import com.example.connect_odoo_mobile.authenticate.MainActivity;
import com.example.connect_odoo_mobile.handle.ImageUtils;
import com.example.connect_odoo_mobile.handle.Many2One;
import com.example.connect_odoo_mobile.handle.OdooConnect;
import com.example.connect_odoo_mobile.handle.OdooUtils;
import com.google.android.material.textfield.TextInputEditText;

import java.net.MalformedURLException;
import java.util.Map;
import java.util.Objects;

public class ContactDetailActivity extends AppCompatActivity {
    private int id;

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
        String db, url, pass, path = "object";
        int uid;
        url = MainActivity.url;
        db = MainActivity.db;
        pass = MainActivity.pass;
        uid = MainActivity.uid;
        //mapping view
        ImageView imgAvatar = findViewById(R.id.imgAvatar);
        TextInputEditText edtEmail, edtWebsite, edtPhone, edtMobile, edtNote, edtCountry;
        TextView txtName;
        CheckBox chkCompany;
        chkCompany = findViewById(R.id.chkCompany);
        txtName = findViewById(R.id.txtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtWebsite = findViewById(R.id.edtWebsite);
        edtPhone = findViewById(R.id.edtPhone);
        edtMobile = findViewById(R.id.edtMobile);
        edtNote = findViewById(R.id.edtNote);
        edtCountry = findViewById(R.id.edtCountry);
        //get intent
        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);
        String name = intent.getStringExtra("name");
        String email = intent.getStringExtra("email");
        String image = null;
        String phone = null, mobile = null, country = null, website = null, comment = null, company_type = null;
        try {
            OdooConnect odooConnect = new OdooConnect(url, path);
            Object[] object = (Object[]) odooConnect.getContactDetail(db, uid, id, pass);
            if (object.length > 0) {
                for (Object i : object) {
                    phone = OdooUtils.getString((Map<String, Object>) i, "phone");
                    mobile = OdooUtils.getString((Map<String, Object>) i, "mobile");
                    country = Many2One.getMany2One((Map<String, Object>) i, "country_id").getName();
                    website = OdooUtils.getString((Map<String, Object>) i, "website");
                    comment = OdooUtils.getString((Map<String, Object>) i, "comment");
                    image = OdooUtils.getString((Map<String, Object>) i, "image_1024");
                    company_type = OdooUtils.getString((Map<String, Object>) i, "company_type");
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if (name != null) {
            txtName.setText(name);
        }
        if (email != null) {
            edtEmail.setText(email);
        }
        if (image != null) {
            imgAvatar.setImageBitmap(ImageUtils.getBitmapImage(image));
        }else {
            imgAvatar.setImageResource(R.drawable.ic_launcher_background);
        }
        if (phone != null) {
            edtPhone.setText(phone);
        }
        if (mobile != null) {
            edtMobile.setText(mobile);

        }
        if (country != null) {
            edtCountry.setText(country);

        }
        if (website != null) {
            edtWebsite.setText(website);
        }
        if (comment != null) {
            edtNote.setText(comment);
        }
        if(company_type.equals("company")){
            chkCompany.setVisibility(View.VISIBLE);
        }else {
            chkCompany.setVisibility(View.GONE);
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
                Intent intent = new Intent(ContactDetailActivity.this,EditContactActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
                break;
            default:
                onBackPressed();
                finish();
        }
        return true;
    }

}