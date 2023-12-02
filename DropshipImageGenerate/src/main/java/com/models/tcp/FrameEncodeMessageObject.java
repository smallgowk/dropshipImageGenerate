/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models.tcp;


/**
 *
 * @author taipa
 */
public class FrameEncodeMessageObject extends MessageObject {

    private byte[] message;

    public FrameEncodeMessageObject() {
    }

    public byte[] getMessage() {
        return message;
    }

    public void setMessage(byte[] message) {
        this.message = message;
    }

}
