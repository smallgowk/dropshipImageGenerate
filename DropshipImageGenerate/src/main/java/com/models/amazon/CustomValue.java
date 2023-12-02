/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models.amazon;

/**
 *
 * @author PhanDuy
 */
public class CustomValue {
    public String fieldName;
    public String value;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public CustomValue(String fieldName, String value) {
        this.fieldName = fieldName;
        this.value = value;
    }
}
