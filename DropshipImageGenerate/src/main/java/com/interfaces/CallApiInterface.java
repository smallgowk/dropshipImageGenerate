/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interfaces;

/**
 *
 * @author Admin
 */
public interface CallApiInterface {
    public void onSuccess(String response);
    public void onFailure(Exception ex);
}
