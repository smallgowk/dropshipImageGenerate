/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models.tcp;

import java.util.Date;

/**
 *
 * @author taipa
 */
public class MessageObject {

    private String version;
    private String email;

    public MessageObject() {
    }
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}
