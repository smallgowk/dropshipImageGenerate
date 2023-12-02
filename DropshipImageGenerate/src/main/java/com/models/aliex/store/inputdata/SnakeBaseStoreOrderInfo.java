/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models.aliex.store.inputdata;

/**
 *
 * @author duyuno
 */
public class SnakeBaseStoreOrderInfo extends BaseStoreOrderInfo{
    public String main_key;
    public String tip;
    public String reasons;
    public String description;
    
    public static SnakeBaseStoreOrderInfo buildTestData() {
        SnakeBaseStoreOrderInfo store = new SnakeBaseStoreOrderInfo();
        store.setAcc_no("Acc_no");
        store.setCategory("tools");
        store.setProduct_type("tools");
        store.setDescription("Productdescription: </br>{Productdescription}");
        store.setStoreSign("TestStore");
        store.setBullet_points("✅ Bullet Point 01 [{searchterm1}]\n" +
"\n" +
"✅ Bullet Point 02 [{searchterm2}]\n" +
"\n" +
"✅ Bullet Point 03 [{searchterm3}]\n" +
"\n" +
"✅ Bullet Point 04 [{searchterm4}]\n" +
"\n" +
"✅ Bullet Point 05 [{searchterm5}]\"");
        store.setBrand_name("BrandName");
        return store;
    }

    public String getMain_key() {
        return main_key;
    }

    public void setMain_key(String main_key) {
        this.main_key = main_key;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getReasons() {
        return reasons;
    }

    public void setReasons(String reasons) {
        this.reasons = reasons;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
    
}
