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
import android.view.MenuItem;
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
        //get data intent
        getDataIntent();
    }

    private void getDataIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            contact = (Contact) bundle.getSerializable("contact_detail");
            edtName.setText((String) contact.getName());
            edtEmail.setText((String) contact.getEmail());
            edtStreet.setText((String) contact.getStreet());
            edtStreet2.setText((String) contact.getStreet2());
            edtZip.setText((String) contact.getZip());
            edtWebsite.setText((String) contact.getWebsite());
            edtPhone.setText((String) contact.getPhone());
            edtMobile.setText((String) contact.getMobile());
            edtComment.setText((String) contact.getComment());
            if (!contact.getCountry().equals("")) {
                edtCountry.setText((String) contact.getCountry());
            }

            if (!contact.getCompany_name().equals("")) {
                edtCompany.setText((String) contact.getCompany_name());
            }

            if (!contact.getImage().equals("") || contact.getImage() != null) {
                imgAvatar.setImageBitmap(ImageUtils.getBitmapImage((String) contact.getImage()));
            }

            if (contact.getCompany_type().equals("company")) {
                chkIsCompany.setChecked(true);
                layoutCompany.setVisibility(View.GONE);
            } else {
                chkIsCompany.setChecked(false);
                layoutCompany.setVisibility(View.VISIBLE);
            }
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        finish();
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
