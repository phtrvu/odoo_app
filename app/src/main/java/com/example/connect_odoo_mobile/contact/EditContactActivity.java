package com.example.connect_odoo_mobile.contact;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.connect_odoo_mobile.R;
import com.example.connect_odoo_mobile.authenticate.MainActivity;
import com.example.connect_odoo_mobile.handle.ImageUtils;
import com.example.connect_odoo_mobile.handle.Many2One;
import com.example.connect_odoo_mobile.handle.OdooConnect;
import com.example.connect_odoo_mobile.handle.OdooUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;
import java.util.Objects;

public class EditContactActivity extends AppCompatActivity {
    private String db, url, pass, path = "object";
    private int uid, country_id = 0;
    String name, email, image = "", company_name, street,
            street2, zip, country, website, phone, mobile, comment, company_type;
    private static final int REQUEST_CODE = 1;
    private ImageView imgAvatar;
    private Bitmap bitmap;
    private EditText edtName;
    private CheckBox chkIsCompany;
    private Contact contact;
    private TextInputLayout layoutCompany;
    private TextInputEditText edtCompany, edtStreet, edtStreet2, edtZip, edtCountry, edtEmail, edtPhone, edtMobile, edtWebsite, edtComment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        mappingView();
        setToolbar();
        //handle thread
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            getDataIntent();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    private void getDataIntent() throws MalformedURLException {
        Intent intent = getIntent();
        int id = intent.getIntExtra("id",0);
        if(id != 0){
            OdooConnect odooConnect = new OdooConnect(url,path);
            Object[] objects = (Object[]) odooConnect.getContactEdit(db,uid,id,pass);
            for (Object i:objects){
                name = OdooUtils.getString((Map<String,Object>) i,"name");
                email = OdooUtils.getString((Map<String,Object>) i,"email");
                image = OdooUtils.getString((Map<String,Object>) i,"image_1920");
                company_name = OdooUtils.getString((Map<String,Object>) i,"company_name");
                street = OdooUtils.getString((Map<String,Object>) i,"street");
                street2 = OdooUtils.getString((Map<String,Object>) i,"street2");
                zip = OdooUtils.getString((Map<String,Object>) i,"zip");
                country = Many2One.getMany2One((Map<String, Object>) i, "country_id").getName();
                website = OdooUtils.getString((Map<String,Object>) i,"website");
                phone = OdooUtils.getString((Map<String,Object>) i,"phone");
                mobile = OdooUtils.getString((Map<String,Object>) i,"mobile");
                comment = OdooUtils.getString((Map<String,Object>) i,"comment");
                company_type = OdooUtils.getString((Map<String,Object>) i,"company_type");
            }
        }
        edtName.setText(name);
        edtEmail.setText(email);
        edtStreet.setText(street);
        edtStreet2.setText(street2);
        edtZip.setText(zip);
        edtWebsite.setText(website);
        edtPhone.setText(phone);
        edtMobile.setText(mobile);
        edtComment.setText(comment);
        if (!country.equals("")){
            edtCountry.setText(country);
        }
        if(!company_name.equals("")){
            edtCompany.setText(company_name);
        }
        if(!image.equals("")){
            imgAvatar.setImageBitmap(ImageUtils.getBitmapImage(image));
        }
        edtComment.setText(comment);
        if(company_type.equals("company")){
            chkIsCompany.setChecked(true);
            layoutCompany.setVisibility(View.GONE);
        }
        else {
            chkIsCompany.setChecked(false);
            layoutCompany.setVisibility(View.VISIBLE);
        }
    }

    private void mappingView() {
        layoutCompany = findViewById(R.id.layoutCompany);
        edtName = findViewById(R.id.edtName);
        chkIsCompany = findViewById(R.id.chkCompany);
        edtEmail = findViewById(R.id.edtEmail);
        edtStreet = findViewById(R.id.edtStreet);
        edtStreet2 = findViewById(R.id.edtStreet2);
        edtZip = findViewById(R.id.edtZip);
        edtCountry = findViewById(R.id.edtCountry);
        edtWebsite = findViewById(R.id.edtWebsite);
        edtPhone = findViewById(R.id.edtPhone);
        edtMobile = findViewById(R.id.edtMobile);
        edtComment = findViewById(R.id.edtNote);
        edtCompany = findViewById(R.id.edtCompany);
        imgAvatar = findViewById(R.id.imgAvatar);
        url = MainActivity.url;
        db = MainActivity.db;
        pass = MainActivity.pass;
        uid = MainActivity.uid;
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolBar);
        this.setSupportActionBar(toolbar);
        setTitle("");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_contact, menu);
        return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activityResultLauncher.launch(Intent.createChooser(intent, "Choose picture:"));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void checkPermission() {
        int check = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
        if (check == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permission, REQUEST_CODE);
        }

    }

    public final ActivityResultLauncher<Intent> activityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            if (result.getResultCode() == RESULT_OK) {
                                Intent intent = result.getData();
                                if (intent == null) {
                                    return;
                                }
                                Uri uri = intent.getData();
                                try {
                                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                                    imgAvatar.setImageBitmap(bitmap);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
}
