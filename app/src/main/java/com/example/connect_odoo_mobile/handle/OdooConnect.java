package com.example.connect_odoo_mobile.handle;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

import android.util.Log;

import com.example.connect_odoo_mobile.contact.Contact;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Objects;

public class OdooConnect {
    private static final String TAG = "RUN";
    private final String url;
    private final XmlRpcClient client;

    public OdooConnect(String serverAddress, String path) throws MalformedURLException {
        url = serverAddress + "/xmlrpc/2/" + path;

        client = new XmlRpcClient();
        client.setConfig(new XmlRpcClientConfigImpl() {{
            setServerURL(new URL(url));
        }});
    }

    public Object getContact(String db, int id, String password) {
        Object object = null;
        try {
            object = client.execute("execute_kw", asList(
                    db, id, password,
                    "res.partner", "search_read",
                    emptyList(),
                    new HashMap() {{
                        put("fields",
                                asList("name", "email", "company_name", "image_128"));
                    }}
            ));
        } catch (XmlRpcException e) {
            e.printStackTrace();
        }
        return object;
    }

    public Object getContactDetail(String db, int uid, int id, String password) {
        Object object = null;
        try {
            object = client.execute("execute_kw", asList(
                    db, uid, password,
                    "res.partner", "search_read",
                    asList(asList(
                            asList("id", "=", id))),
                    new HashMap() {{
                        put("fields",
                                asList("street","street2","company_name","email","zip",
                                        "name","phone", "mobile", "image_1920", "company_type",
                                        "country_id", "website", "comment"));
                    }}
            ));
        } catch (XmlRpcException e) {
            e.printStackTrace();
        }
        return object;
    }
    public Object getContactEdit(String db, int uid, int id, String password) {
        Object object = null;
        try {
            object = client.execute("execute_kw", asList(
                    db, uid, password,
                    "res.partner", "search_read",
                    asList(asList(
                            asList("id", "=", id))),
                    new HashMap() {{
                        put("fields",
                                asList("street","street2","company_name","email","zip",
                                        "name","phone", "mobile", "image_1920", "company_type",
                                        "country_id", "website", "comment"));
                    }}
            ));
        } catch (XmlRpcException e) {
            e.printStackTrace();
        }
        return object;
    }

    public int addContact(String db, int uid, String password, Contact contact) {
        int id = -1;
        try {
            id = (int) client.execute("execute_kw", asList(
                    db, uid, password,
                    "res.partner", "create",
                    asList(new HashMap() {{
                        put("name", contact.getName());
                        put("image_1920", contact.getImage());
                        put("email", contact.getEmail());
                        put("company_name", contact.getCompany_name());
                        put("street", contact.getStreet());
                        put("street2", contact.getStreet2());
                        put("zip", contact.getZip());
                        put("country_id", contact.getCountry_id());
                        put("website", contact.getWebsite());
                        put("phone", contact.getPhone());
                        put("mobile", contact.getMobile());
                        put("comment", contact.getComment());
                        put("company_type", contact.getCompany_type());
                    }})
            ));
        } catch (XmlRpcException e) {
            e.printStackTrace();
        }
        return id;
    }

    public Object signIn(String db, String username, String password) throws XmlRpcException {
        Object object = null;
        object = client.execute("login", asList(db, username, password));
        return object;
    }

    public Object checkServer() {
        Object object = null;
        try {
            object = client.execute("list", emptyList());
        } catch (XmlRpcException e) {
            e.printStackTrace();
        }
        return object;
    }

    public Object getProfile(String db, String password, int id) throws XmlRpcException {
        Object object = null;
        object = client.execute("execute_kw", asList(
                db, id, password,
                "res.users", "search_read",
                asList(asList(
                        asList("id", "=", id))),
                new HashMap() {{
                    put("fields", asList("name", "email", "id", "image_128"));
                }}
        ));
        return object;
    }

    public Object getCompany(String db, int id, String password) {
        Object object = null;
        try {
            object = client.execute("execute_kw", asList(
                    db, id, password,
                    "res.company", "search_read",
                    emptyList(),
                    new HashMap() {{
                        put("fields",
                                asList("id", "name"));
                    }}
            ));
        } catch (XmlRpcException e) {
            e.printStackTrace();
        }
        return object;
    }

    public Object getCountry(String db, int id, String password) {
        Object object = null;
        try {
            object = client.execute("execute_kw", asList(
                    db, id, password,
                    "res.country", "search_read",
                    emptyList(),
                    new HashMap() {{
                        put("fields",
                                asList("id", "name"));
                    }}
            ));
        } catch (XmlRpcException e) {
            e.printStackTrace();
        }
        return object;
    }
}
