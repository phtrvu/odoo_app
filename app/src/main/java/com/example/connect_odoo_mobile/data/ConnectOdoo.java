package com.example.connect_odoo_mobile.data;

import static android.content.ContentValues.TAG;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;

import android.util.Log;

import com.example.connect_odoo_mobile.data_models.Company;
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
import java.util.HashMap;
import java.util.List;

public class ConnectOdoo {
    final XmlRpcClient client = new XmlRpcClient();
    final String url = "https://android.t4erp.cf",
            db = "bitnami_odoo",
            username = "vunpt@t4tek.co",
            password = "12062001";
    XmlRpcClientConfigImpl common_config, common_database;
    XmlRpcClient models;
    int uid;
    Moshi moshi = new Moshi.Builder().build();
    ;

    public XmlRpcClientConfigImpl common_Config(String url) {
        common_config = new XmlRpcClientConfigImpl();
        try {
            common_config.setServerURL(new URL(String.format("%s/xmlrpc/2/common", url)));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return common_config;
    }

    public XmlRpcClientConfigImpl common_Database(String url) {
        common_database = new XmlRpcClientConfigImpl();
        try {
            common_database.setServerURL(new URL(String.format("%s/xmlrpc/2/db", url)));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return common_database;
    }

    public XmlRpcClient models(String url) {
        models = new XmlRpcClient();
        models.setConfig(new XmlRpcClientConfigImpl() {{
            try {
                setServerURL(new URL(String.format("%s/xmlrpc/2/object", url)));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }});
        return models;
    }

    public int uid(String db, String username, String password, XmlRpcClientConfigImpl common_config) {
        uid = 0;
        try {
            uid = (int) client.execute(common_config, "authenticate", asList(db, username, password, emptyMap()));
        } catch (XmlRpcException e) {
            e.printStackTrace();
        }
        return uid;
    }

    public List<Contact> getContact(String db, String url, int id, String password) throws XmlRpcException {
        common_Config(url);
        models(url);
        JsonAdapter<List<Contact>> jsonAdapter;
        Type usersType = Types.newParameterizedType(List.class, Contact.class);
        jsonAdapter = moshi.adapter(usersType);
        Object data = asList((Object[]) models.execute("execute_kw", asList(
                db, id, password,
                "res.partner", "search_read",
                emptyList(),
                new HashMap() {{
                    put("fields", asList("name", "email", "company_name", "id", "image_128"));
                }}
        )));
        final List<Contact> contacts = jsonAdapter.fromJsonValue(data);
        return contacts;
    }

    public List<Company> getCompany(String db, String url, int id, String password) throws XmlRpcException {
        common_Config(url);
        models(url);
        JsonAdapter<List<Company>> jsonAdapter;
        Type usersType = Types.newParameterizedType(List.class, Company.class);
        jsonAdapter = moshi.adapter(usersType);
        Object data = asList((Object[]) models.execute("execute_kw", asList(
                db, id, password,
                "res.company", "search_read",
                emptyList(),
                new HashMap() {{
                    put("fields", asList("logo","name", "phone", "mobile", "street", "street2", "email", "id", "city", "country_code"));
                }}
        )));
        final List<Company> companies = jsonAdapter.fromJsonValue(data);
        return companies;
    }

    public List<Contact> getProfile(String db, String url, String password, int id) throws XmlRpcException {
        common_Config(url);
        models(url);
        JsonAdapter<List<Contact>> jsonAdapter;
        Type usersType = Types.newParameterizedType(List.class, Contact.class);
        jsonAdapter = moshi.adapter(usersType);
        Object data = asList((Object[]) models.execute("execute_kw", asList(
                db, id, password,
                "res.users", "search_read",
                asList(asList(
                        asList("id", "=", id))),
                new HashMap() {{
                    put("fields", asList("name", "email", "company_name", "id", "image_128"));
                }}
        )));
        final List<Contact> contacts = jsonAdapter.fromJsonValue(data);
        return contacts;
    }

    public int checkSignIn(String db, String url, String user, String password) throws XmlRpcException {
        common_Config(url);
        uid = uid(db, user, password, common_config);
        return uid;
    }

    public List<String> getDB(String url) {
        List<String> database = new ArrayList<>();
        common_Database(url);
        Object[] data;
        try {
            data = (Object[]) client.execute(common_database, "list", emptyList());
            for (Object i : data) {
                database.add(i.toString());
            }
        } catch (XmlRpcException e) {
            e.printStackTrace();
        }
        return database;
    }
}
