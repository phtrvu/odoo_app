package com.example.connect_odoo_mobile;

import static android.content.ContentValues.TAG;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.connect_odoo_mobile.data.GetDataFromOdoo;
import com.example.connect_odoo_mobile.data_models.Contact;
import com.example.connect_odoo_mobile.read_json.ReadJSON;
import com.google.gson.Gson;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class SignInActivity extends AppCompatActivity {
    final XmlRpcClient client = new XmlRpcClient();
    final XmlRpcClientConfigImpl common_config = new XmlRpcClientConfigImpl();
    private GetDataFromOdoo getDataFromOdoo = new GetDataFromOdoo();
    private EditText edtUrl, edtUser, edtPassword;
    private Button btnSignIn;
    public static int uid;
    private Gson gson = new Gson();
    private String https = "https://android.t4erp.cf",
            name = "vunpt@t4tek.co",
            pass = "12062001",
            db = "bitnami_odoo";
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
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String url = edtUrl.getText().toString();
//                String user = edtUser.getText().toString();
//                String password = edtPassword.getText().toString();
//                if(url.equals("")){
//                    edtUrl.setError("NUll");
//                }else if (user.equals("")){
//                    edtUser.setError("NULL");
//                }else if(password.equals("")){
//                    edtPassword.setError("NULL");
//                }else {
//                    try {
//                       checkSignIn(url,user,pass);
//                    } catch (XmlRpcException e) {
//                        e.printStackTrace();
//                    }
//                }
                try {
                    //checkSignIn(url,user,pass);
                    int uid = getDataFromOdoo.checkSignIn(https,name,pass);
                    if (uid >0){
                        Contact contact = getProfile(uid);
                        Intent intent = new Intent(SignInActivity.this,MainActivity.class);
                        intent.putExtra("name", (String) contact.getName());
                        intent.putExtra("email", (String) contact.getEmail());
                        startActivity(intent);
                    }
                } catch (XmlRpcException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private Contact getProfile(int uid) throws XmlRpcException {
        String json = null;
        Contact contact = null;
        try {
            List<Object> data = getDataFromOdoo.getProfile(uid);
            Log.d(TAG, "getProfile: " + json);
            for (Object i: data
            ) {
                json = gson.toJson(i);
                try {
                    contact = ReadJSON.readProfileJSON(json);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (XmlRpcException e) {
            e.printStackTrace();
        }
        return contact;
    }

    private void mapping() {
        edtUrl = findViewById(R.id.edtHttps);
        edtUser = findViewById(R.id.edtUser);
        edtPassword = findViewById(R.id.edtPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
    }
}