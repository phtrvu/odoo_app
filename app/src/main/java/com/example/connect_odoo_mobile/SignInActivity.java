package com.example.connect_odoo_mobile;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.connect_odoo_mobile.data.ConnectOdoo;
import com.example.connect_odoo_mobile.data_models.Contact;
import com.example.connect_odoo_mobile.read_json.ReadJSON;
import com.google.gson.Gson;

import org.apache.xmlrpc.XmlRpcException;

import java.util.ArrayList;
import java.util.List;

public class SignInActivity extends AppCompatActivity {
    private ConnectOdoo connectOdoo = new ConnectOdoo();
    private EditText edtUrl, edtUser, edtPassword;
    private Spinner spinDB;
    private ProgressBar pbLoading;
    private ImageView imgCheck;
    private List<String> listDB;
    private Button btnSignIn;
    private Boolean isCheckDB = false;
    public String url = "", user = "", db = "", pass = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        //handle threah
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //view mapping
        mapping();
        //set action
        action();
    }

    private void action() {
        eventButtuonSignIn();
        eventEdittextUrl();
    }

    private void eventButtuonSignIn() {
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                db = spinDB.getSelectedItem().toString();
//                user = edtUser.getText().toString();
//                pass = edtPassword.getText().toString();
                db = "bitnami_odoo";
                user = "vunpt@t4tek.co" ;
                pass = "12062001";
                url = "https://android.t4erp.cf";
                if(isCheckDB){
                    Toast.makeText(SignInActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                } else if (url.equals("")) {
                    edtUrl.setError("Enter self-hosted URL!");
                } else if (user.equals("")) {
                    edtUser.setError("Enter your username or email!");
                } else if (pass.equals("")) {
                    edtPassword.setError("Enter your password!");
                } else {
                    try {
                        int uid = connectOdoo.checkSignIn(db, url, user, pass);
                        if (uid > 0) {
                            List<Contact> contact = connectOdoo.getProfile(db, url, user, pass, uid);
                            Log.d(TAG, "onClick: " + contact);
                            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                            intent.putExtra("name", (String) contact.get(0).getName());
                            intent.putExtra("email", (String) contact.get(0).getEmail());
                            intent.putExtra("db",db);
                            intent.putExtra("url",url);
                            intent.putExtra("user",user);
                            intent.putExtra("pass",pass);
                            intent.putExtra("uid",uid);
                            startActivity(intent);
                        }
                    } catch (XmlRpcException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void eventEdittextUrl() {
        listDB = new ArrayList<>();
        edtUrl.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && listDB != null){
                    getDatabase();
                }
            }
        });
        edtUrl.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //key enter and key next
                if (event != null && listDB != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                        || actionId == EditorInfo.IME_ACTION_NEXT) {
                    getDatabase();
                }

                return false;
            }
        });
    }
    private void getDatabase(){
        String server_url = edtUrl.getText().toString();
        //Check format url
        StringBuilder serverURL = new StringBuilder();
        if (!server_url.contains("http://") && !server_url.contains("https://")) {
            serverURL.append("https://");
        }
        serverURL.append(server_url);
        pbLoading.setVisibility(View.VISIBLE);
        listDB = connectOdoo.getDB(serverURL.toString());
        if (listDB == null) {
            imgCheck.setImageResource(R.drawable.ic_baseline_block_25);
        } else if (listDB.size() > 1) {
            spinDB.setVisibility(View.VISIBLE);
            //dump data spinner
            dataSpinner(listDB);
            pbLoading.setVisibility(View.INVISIBLE);
            imgCheck.setVisibility(View.VISIBLE);
            imgCheck.setImageResource(R.drawable.ic_baseline_check_25);
            isCheckDB = true;
        } else if (listDB.size() == 1) {
            db = listDB.get(0);
            pbLoading.setVisibility(View.INVISIBLE);
            imgCheck.setVisibility(View.VISIBLE);
            imgCheck.setImageResource(R.drawable.ic_baseline_check_25);
            isCheckDB = true;
        } else {
            imgCheck.setImageResource(R.drawable.ic_baseline_block_25);
        }
        url = serverURL.toString();
    }

    private void dataSpinner(List<String> listDB) {
        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listDB);
        arrayAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinDB.setAdapter(arrayAdapter);
    }

    private void mapping() {
        edtUrl = findViewById(R.id.edtHttps);
        edtUser = findViewById(R.id.edtUser);
        edtPassword = findViewById(R.id.edtPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        pbLoading = findViewById(R.id.pbLoading);
        imgCheck = findViewById(R.id.imgCheck);
        spinDB = findViewById(R.id.spinDB);
    }
}