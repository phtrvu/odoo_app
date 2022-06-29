package com.example.connect_odoo_mobile.handle;

import java.util.Map;

public class Many2One {
    private int id;
    private String name;

    public Many2One() {
    }

    public static Many2One getMany2One(Map<String, Object> stringObjectMap, String fieldName) {
        Integer fieldId = 0;
        String fieldValue = "";

        Many2One res = new Many2One();
        if (stringObjectMap.get(fieldName) instanceof Object[]) {
            Object[] field = (Object[]) stringObjectMap.get(fieldName);

            if (field.length > 0) {
                fieldId = (Integer) field[0];
                fieldValue = (String) field[1];
            }
        }

        res.id = fieldId;
        res.name = fieldValue;

        return res;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
