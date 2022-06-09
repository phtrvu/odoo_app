package com.example.connect_odoo_mobile;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import java.net.MalformedURLException;
import java.net.URL;

public class SignInActivity extends AppCompatActivity {
    final XmlRpcClient client = new XmlRpcClient();
    final XmlRpcClientConfigImpl common_config = new XmlRpcClientConfigImpl();
    private EditText edtUrl, edtUser, edtPassword;
    private Button btnSignIn;
    public int uid;
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
        //check auto login
        if(uid > 0){
            startActivity(new Intent(SignInActivity.this,MainActivity.class));
        }
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
                    checkSignIn(https,name,pass);
                } catch (XmlRpcException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void checkSignIn(String url,String user, String password) throws XmlRpcException {
        Object version;
        try {
            common_config.setServerURL(new URL(String.format("%s/xmlrpc/2/common", url)));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            version = client.execute(common_config, "version", emptyList());
        } catch (XmlRpcException e) {
            e.printStackTrace();
        }
        uid = (int) client.execute(common_config, "authenticate", asList(db, user, password, emptyMap()));

        if(uid > 0){
            Intent intent = new Intent(SignInActivity.this,MainActivity.class);
            intent.putExtra("uid",uid);
            startActivity(intent);
        }else {
            Toast.makeText(this, "Éo đúng user or pass =))", Toast.LENGTH_SHORT).show();
        }
    }

    private void mapping() {
        edtUrl = findViewById(R.id.edtHttps);
        edtUser = findViewById(R.id.edtUser);
        edtPassword = findViewById(R.id.edtPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
    }
}