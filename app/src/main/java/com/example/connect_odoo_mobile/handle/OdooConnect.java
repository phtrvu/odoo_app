package com.example.connect_odoo_mobile.handle;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

import android.util.Log;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class OdooConnect {
    private static final String TAG = "RUN";
    private String url;
    private XmlRpcClient client;

    public OdooConnect(String serverAddress, String path) throws MalformedURLException {
        url = serverAddress + "/xmlrpc/2/" + path;

        client = new XmlRpcClient();
        client.setConfig(new XmlRpcClientConfigImpl() {{
            setServerURL(new URL(url));
        }});
    }

    public Object GetContact(String db, int id, String password) {
        Object object = null;
        try {
            object = client.execute("execute_kw", asList(
                    db, id, password,
                    "res.partner", "search_read",
                    emptyList(),
                    new HashMap() {{
                        put("fields",
                                asList("name", "email", "phone", "mobile",
                                        "country_id", "website", "comment",
                                        "company_name", "id", "image_128"));
                    }}
            ));
        } catch (XmlRpcException e) {
            e.printStackTrace();
        }
        return object;
    }

    public Object Login(String db, String username, String password) throws XmlRpcException {
        Object object;
        object = client.execute("login", asList(db, username, password));
        return object;
    }

    public Object CheckServer() {
        Object object = null;
        try {
            object = client.execute("list", emptyList());
        } catch (XmlRpcException e) {
            e.printStackTrace();
        }
        return object;
    }
}
