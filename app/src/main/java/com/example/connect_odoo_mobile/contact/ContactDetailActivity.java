package com.example.connect_odoo_mobile.contact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
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
    private ImageView imgAvatar;
    private TextInputEditText edtEmail, edtWebsite, edtPhone, edtMobile, edtNote, edtCountry;
    private TextView txtName;
    private CheckBox chkCompany;
    private int id, uid, country_id;
    private Contact contact;
    private String db, url, pass, path = "object";
    private String image = null, name = null, email = null, company_name = null, street = null, street2 = null, zip = null,
            phone = null, mobile = null, country = null, website = null, comment = null, company_type = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);
        //handle thread
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //set support action bar
        setToolBarForActionBar();
        //mapping view
        mappingView();
        //get data intent contact activity
        getDataIntent();
    }

    @SuppressLint("WrongViewCast")
    private void getDataIntent() {
        //get intent
        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);
        try {
            OdooConnect odooConnect = new OdooConnect(url, path);
            Object[] object = (Object[]) odooConnect.getContactDetail(db, uid, id, pass);
            if (object.length > 0) {
                for (Object i : object) {
                    name = OdooUtils.getString((Map<String, Object>) i, "name");
                    email = OdooUtils.getString((Map<String, Object>) i, "email");
                    company_name = OdooUtils.getString((Map<String, Object>) i, "company_name");
                    street = OdooUtils.getString((Map<String, Object>) i, "street");
                    street2 = OdooUtils.getString((Map<String, Object>) i, "street2");
                    zip = OdooUtils.getString((Map<String, Object>) i, "zip");
                    country = Many2One.getMany2One((Map<String, Object>) i, "country_id").getName();
                    country_id = Many2One.getMany2One((Map<String, Object>) i, "country_id").getId();
                    website = OdooUtils.getString((Map<String, Object>) i, "website");
                    phone = OdooUtils.getString((Map<String, Object>) i, "phone");
                    mobile = OdooUtils.getString((Map<String, Object>) i, "mobile");
                    comment = OdooUtils.getString((Map<String, Object>) i, "comment");
                    company_type = OdooUtils.getString((Map<String, Object>) i, "company_type");
                    image = OdooUtils.getString((Map<String, Object>) i, "image_1920");
                    Log.d("TAG", "getDataIntent: "+image);
                }
                contact = new Contact(id, name, email, image, company_name,
                        company_type, street, street2, zip, country,
                        website, phone, mobile, comment, country_id);
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
        if (!image.equals("")) {
            imgAvatar.setImageBitmap(ImageUtils.getBitmapImage(image));
        } else {
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
        if (company_type.equals("company")) {
            chkCompany.setVisibility(View.VISIBLE);
        } else {
            chkCompany.setVisibility(View.GONE);
        }
    }

    private void mappingView() {
        url = MainActivity.url;
        db = MainActivity.db;
        pass = MainActivity.pass;
        uid = MainActivity.uid;
        //mapping view
        imgAvatar = findViewById(R.id.imgAvatar);
        chkCompany = findViewById(R.id.chkCompany);
        txtName = findViewById(R.id.txtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtWebsite = findViewById(R.id.edtWebsite);
        edtPhone = findViewById(R.id.edtPhone);
        edtMobile = findViewById(R.id.edtMobile);
        edtNote = findViewById(R.id.edtNote);
        edtCountry = findViewById(R.id.edtCountry);
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
                Intent intent = new Intent(ContactDetailActivity.this, EditContactActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("contact_detail", contact);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            default:
                onBackPressed();
                finish();
        }
        return true;
    }

}