package com.example.connect_odoo_mobile.authenticate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
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

import com.example.connect_odoo_mobile.R;
import com.example.connect_odoo_mobile.handle.OdooConnect;
import com.example.connect_odoo_mobile.handle.OdooUtils;

import org.apache.xmlrpc.XmlRpcException;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SignInActivity extends AppCompatActivity {
    private EditText edtUrl, edtUser, edtPassword;
    private Spinner spinDB;
    private ProgressBar pbLoading;
    private ImageView imgCheck;
    private List<String> listDB;
    private Button btnSignIn;
    private OdooConnect odooConnect;
    private Boolean isCheckDB = false;
    public String url = "", user = "", db = "", pass = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        //handle thread
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //view mapping
        mapping();
        //check server
        eventEdittextUrl();
        //check login
        eventButtonSignIn();
    }

    private void eventButtonSignIn() {
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                db = spinDB.getSelectedItem().toString();
//                user = edtUser.getText().toString();
//                pass = edtPassword.getText().toString();
                db = "bitnami_odoo";
                user = "vunpt@t4tek.co";
                pass = "12062001";
                url = "https://android.t4erp.cf";
                String path = "common";
                try {
                    odooConnect = new OdooConnect(url, path);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                if (url.equals("")) {
                    edtUrl.setError("Enter self-hosted URL!");
                } else if (user.equals("")) {
                    edtUser.setError("Enter your username or email!");
                } else if (pass.equals("")) {
                    edtPassword.setError("Enter your password!");
                } else {
                    try {
                        int uid = (int) odooConnect.signIn(db, user, pass);
                        if (uid > 0) {
                            getInformationAccount(url, db, pass, uid, user);
                        } else {
                            edtUrl.setError("The username could not be found!");
                        }
                    } catch (XmlRpcException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void getInformationAccount(String url, String db, String password, int id, String user) {
        String path = "object";
        String name = "", email = "", image = "";
        try {
            odooConnect = new OdooConnect(url, path);
            Object[] object = (Object[]) odooConnect.getProfile(db, password, id);
            for (Object i : object
            ) {
                name = OdooUtils.getString((Map<String, Object>) i, "name");
                email = OdooUtils.getString((Map<String, Object>) i, "email");
                image = OdooUtils.getString((Map<String, Object>) i, "image_128");
            }
            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
            intent.putExtra("name", name);
            intent.putExtra("email", email);
            intent.putExtra("image", image);
            intent.putExtra("db", db);
            intent.putExtra("url", url);
            intent.putExtra("user", user);
            intent.putExtra("pass", password);
            intent.putExtra("uid", id);
            startActivity(intent);
            finish();
        } catch (MalformedURLException | XmlRpcException e) {
            e.printStackTrace();
        }
    }

    private void eventEdittextUrl() {
        listDB = new ArrayList<>();
        edtUrl.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus && listDB != null) {
                pbLoading.setVisibility(View.VISIBLE);
                imgCheck.setVisibility(View.INVISIBLE);
                checkServer();
            }
        });
        edtUrl.setOnEditorActionListener((v, actionId, event) -> {
            //key enter and key next
            if (event != null && listDB != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                    || actionId == EditorInfo.IME_ACTION_NEXT) {
                pbLoading.setVisibility(View.VISIBLE);
                imgCheck.setVisibility(View.INVISIBLE);
                checkServer();
            }

            return false;
        });
    }

    private void checkServer() {
        String path = "db";
        String server_url = edtUrl.getText().toString();
        //Check format url
        StringBuilder serverURL = new StringBuilder();
        if (!server_url.contains("http://") && !server_url.contains("https://")) {
            serverURL.append("https://");
        }
        serverURL.append(server_url);
        //Check server
        try {
            odooConnect = new OdooConnect(serverURL.toString(), path);
            Object[] objects = (Object[]) odooConnect.checkServer();
            if (objects.length > 0) {
                for (Object i : objects) {
                    listDB.add(i.toString());
                }
            }
            pbLoading.setVisibility(View.INVISIBLE);
            if (listDB == null) {
                edtUrl.setError("The server could not be found!");
            } else if (listDB.size() > 1) {
                spinDB.setVisibility(View.VISIBLE);
                //dump data spinner
                dataSpinner(listDB);
                imgCheck.setVisibility(View.VISIBLE);
                imgCheck.setImageResource(R.drawable.ic_baseline_check_25);
                isCheckDB = true;
            } else if (listDB.size() == 1) {
                db = listDB.get(0);
                imgCheck.setVisibility(View.VISIBLE);
                imgCheck.setImageResource(R.drawable.ic_baseline_check_25);
                isCheckDB = true;
            } else {
                edtUrl.setError("The server could not be found!");
            }
            url = serverURL.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
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