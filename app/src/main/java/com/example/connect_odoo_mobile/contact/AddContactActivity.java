package com.example.connect_odoo_mobile.contact;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.connect_odoo_mobile.R;
import com.example.connect_odoo_mobile.authenticate.MainActivity;
import com.example.connect_odoo_mobile.company.CompanyActivity;
import com.example.connect_odoo_mobile.country.CountryActivity;
import com.example.connect_odoo_mobile.dialog.ChoosePictureDialog;
import com.example.connect_odoo_mobile.handle.ImageUtils;
import com.example.connect_odoo_mobile.handle.OdooConnect;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Objects;

public class AddContactActivity extends AppCompatActivity {

    private String db, url, pass, path = "object";
    private int uid, country_id;
    String name, email, image, company_name, street,
            street2, zip, country, website, phone, mobile, comment, company_type;
    private static final int REQUEST_CODE = 1;
    private ImageView imgAvatar;
    private Bitmap bitmap;
    private EditText edtName;
    private CheckBox chkIsCompany;
    private Contact contact;
    private TextInputEditText edtCompany, edtStreet, edtStreet2, edtZip, edtCountry, edtEmail, edtPhone, edtMobile, edtWebsite, edtComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        //set toolbar
        setToolbar();
        //handle thread
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //
        mappingView();
        //checkIsCompany
        checkIsCompany();
        //tap Image view
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tapImageView();
        }
        //get data intent
        getDataIntent();
        //action
        setAction();
    }

    @SuppressLint("WrongConstant")
    private void setAction() {
        edtCompany.setOnClickListener(view -> {
            Intent intent = new Intent(this, CompanyActivity.class);
            Bundle bundle = new Bundle();
            getDataEditText();
            bundle.putSerializable("contact_temp", contact);
            intent.putExtras(bundle);
            startActivity(intent);
        });
        edtCountry.setOnClickListener(view -> {
            Intent intent = new Intent(this, CountryActivity.class);
            Bundle bundle = new Bundle();
            getDataEditText();
            bundle.putSerializable("contact_temp", contact);
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void tapImageView() {
        imgAvatar = findViewById(R.id.imgAvatar);
        imgAvatar.setOnClickListener(view -> {
            ChoosePictureDialog choosePictureDialog = new ChoosePictureDialog(this);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                choosePictureDialog.openDialog();
            }
        });
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolBar);
        this.setSupportActionBar(toolbar);
        setTitle("");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    //check company checkbox
    private void checkIsCompany() {
        TextInputLayout layoutCompany = findViewById(R.id.layoutCompany);
        chkIsCompany.setOnCheckedChangeListener((compoundButton, b) -> {
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
                try {
                    addContact();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.nav_cancel:
                Toast.makeText(this, "cancel", Toast.LENGTH_SHORT).show();
                break;
            default:
                onBackPressed();
        }
        return true;
    }

    private void addContact() throws MalformedURLException {
        //get data edittext
        getDataEditText();
        //add in database
        if (name == null) {
            edtName.setError("Required");
        } else {
            OdooConnect odooConnect = new OdooConnect(url, path);
            int id = odooConnect.addContact(db, uid, pass, contact);
            if (id > 0) {
                Toast.makeText(this, "Add successful!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Add failed!", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void getDataEditText() {
        if (bitmap != null) {
            image = ImageUtils.convertBase64(bitmap);
        }
        if (chkIsCompany.isChecked()) {
            company_type = "company";
        } else {
            company_type = "person";
        }
        name = edtName.getText().toString().trim();
        email = edtEmail.getText().toString().trim();
        street = edtStreet.getText().toString().trim();
        street2 = edtStreet2.getText().toString().trim();
        zip = edtZip.getText().toString().trim();
        country = edtCountry.getText().toString().trim();
        website = edtWebsite.getText().toString().trim();
        phone = edtPhone.getText().toString().trim();
        mobile = edtMobile.getText().toString().trim();
        comment = edtComment.getText().toString().trim();
        company_name = edtCompany.getText().toString().trim();
        //init Contact
        contact = new Contact(name, email, image, company_name, street,
                street2, zip, country, website, phone, mobile, comment, company_type);
    }

    private void getDataIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Contact contact = (Contact) bundle.getSerializable("contact_temp");
            edtName.setText((String) contact.getName());

            edtEmail.setText((String) contact.getEmail());
            edtStreet.setText((String) contact.getStreet());
            edtStreet2.setText((String) contact.getStreet2());
            edtZip.setText((String) contact.getZip());
            edtWebsite.setText((String) contact.getWebsite());
            edtPhone.setText((String) contact.getPhone());
            edtMobile.setText((String) contact.getMobile());
            edtComment.setText((String) contact.getComment());
            edtCompany.setText((String) contact.getCompany_name());
            edtCountry.setText((String) contact.getCountry());

            company_name = bundle.getString("company_name", null);
            if (company_name != null) {
                edtCompany.setText(company_name);
            }
            country = bundle.getString("country", null);
            if (country != null) {
                edtCountry.setText(country);
            }
        }
    }


    private void mappingView() {
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
        url = MainActivity.url;
        db = MainActivity.db;
        pass = MainActivity.pass;
        uid = MainActivity.uid;
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