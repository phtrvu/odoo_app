package com.example.connect_odoo_mobile.read_json;

import android.content.Context;
import android.util.Log;

import com.example.connect_odoo_mobile.R;
import com.example.connect_odoo_mobile.data_models.Contact;
import com.example.connect_odoo_mobile.fragment.CustomerFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ReadJSON {
    private static final String TAG = "Run";

    // Read the company.json file and convert it to a java object.
    public static Contact readContactJSON(String jsonText) throws IOException, JSONException {

        JSONObject jsonRoot = new JSONObject(jsonText);


        int id= jsonRoot.getInt("id");
        String name = jsonRoot.getString("name");
        String  company = jsonRoot.getString("company_name");
        String email = jsonRoot.getString("email");

        Contact contact = new Contact();
        contact.setId(id);
        contact.setName(name);
        contact.setEmail(email);
        contact.setCompany(company);
        return contact;
    }

//    private static String readText(Context context, int resId) throws IOException {
//        InputStream is = context.getResources().openRawResource(resId);
//        BufferedReader br= new BufferedReader(new InputStreamReader(is));
//        StringBuilder sb= new StringBuilder();
//        String s= null;
//        while((  s = br.readLine())!=null) {
//            sb.append(s);
//            sb.append("\n");
//        }
//        return sb.toString();
//    }
}
