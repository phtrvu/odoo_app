package com.example.connect_odoo_mobile.data;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;

import com.example.connect_odoo_mobile.data_models.Contact;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

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

    public List<Object> getContact() throws XmlRpcException {
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
        int uid = (int) client.execute(common_config, "authenticate", asList(db, username, password, emptyMap()));
        List<Object> data = asList((Object[]) models.execute("execute_kw", asList(
                        db, uid, password,
                        "res.partner", "search_read",
                        emptyList(),
                        new HashMap() {{
                            put("fields", asList("name", "email","company_name","id"));
                        }}
                )));
        return data;
    }
}
