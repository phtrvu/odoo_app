package com.example.connect_odoo_mobile.read_json;

import com.example.connect_odoo_mobile.company.Company;
import com.example.connect_odoo_mobile.contact.Contact;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

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
    public static Contact readProfileJSON(String jsonText) throws IOException, JSONException {

        JSONObject jsonRoot = new JSONObject(jsonText);

        int id= jsonRoot.getInt("id");
        String name = jsonRoot.getString("name");
        String email = jsonRoot.getString("email");

        Contact contact = new Contact();
        contact.setId(id);
        contact.setName(name);
        contact.setEmail(email);
        return contact;
    }

    public static Company readCompanyJSON(String jsonText) throws IOException, JSONException {

        JSONObject jsonRoot = new JSONObject(jsonText);

        int id= jsonRoot.getInt("id");
        String name = jsonRoot.getString("name");
        String city = jsonRoot.getString("city");
        String email = jsonRoot.getString("email");
        String street = jsonRoot.getString("street");
        String street2 = jsonRoot.getString("street2");
        String country_code = jsonRoot.getString("country_code");
        String phone = jsonRoot.getString("phone");
        String mobile = jsonRoot.getString("mobile");

        Company company = new Company();
        company.setId(id);
        company.setName(name);
        company.setEmail(email);
        company.setCity(city);
        company.setStreet(street);
        company.setStreet2(street2);
        company.setCountry_code(country_code);
        company.setPhone(phone);
        company.setMobile(mobile);
        return company;
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
