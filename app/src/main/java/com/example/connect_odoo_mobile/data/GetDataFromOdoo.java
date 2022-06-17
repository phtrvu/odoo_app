package com.example.connect_odoo_mobile.data;

import static android.content.ContentValues.TAG;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;

import android.util.Log;

import com.example.connect_odoo_mobile.adapter.ContactAdapter;
import com.example.connect_odoo_mobile.data_models.Contact;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class GetDataFromOdoo {
    final XmlRpcClient client = new XmlRpcClient();
    final String url = "https://android.t4erp.cf",
            db = "bitnami_odoo",
            username = "vunpt@t4tek.co",
            password = "12062001";
    final XmlRpcClientConfigImpl common_config = new XmlRpcClientConfigImpl();

    final XmlRpcClient models = new XmlRpcClient();
    int uid = 0;
    private JsonAdapter<List<Contact>> jsonAdapter;

    public void common() throws XmlRpcException {
        try {
            common_config.setServerURL(new URL(String.format("%s/xmlrpc/2/common", url)));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        models.setConfig(new XmlRpcClientConfigImpl() {{
            try {
                setServerURL(new URL(String.format("%s/xmlrpc/2/object", url)));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }});
        uid = (int) client.execute(common_config, "authenticate", asList(db, username, password, emptyMap()));

    }

    public List<Contact> getContact() throws XmlRpcException {
       common();
        Moshi moshi = new Moshi.Builder().build();
        Type usersType = Types.newParameterizedType(List.class, Contact.class);
        jsonAdapter = moshi.adapter(usersType);
        Object data = asList((Object[]) models.execute("execute_kw", asList(
                        db, uid, password,
                        "res.partner", "search_read",
                        emptyList(),
                        new HashMap() {{
                            put("fields", asList("name", "email","company_name","id"));
                        }}
                )));
        final List<Contact> contacts = jsonAdapter.fromJsonValue(data);
        Log.d(TAG, "getContact: "+ contacts);
        return contacts;
    }
    public List<Object> getCompany() throws XmlRpcException {
       common();
        List<Object> data = asList((Object[]) models.execute("execute_kw", asList(
                db, uid, password,
                "res.company", "search_read",
                emptyList(),
                new HashMap() {{
                    put("fields", asList("name","phone","mobile","street","street2","email","id","city","country_code"));
                }}
        )));
        return data;
    }
    public List<Object> getProfile() throws XmlRpcException {
        common();
        List<Object> profile = asList((Object[]) models.execute("execute_kw", asList(
                db, uid, password,
                "res.users", "search_read",
                asList(asList(
                        asList("id", "=", uid))),
                new HashMap() {{
                    put("fields", asList("name","email"));
                }}
        )));
        return profile;
    }
}
