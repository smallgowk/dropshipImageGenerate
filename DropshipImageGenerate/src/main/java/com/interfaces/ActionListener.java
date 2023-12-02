/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interfaces;

import com.controller.MainController.STATE;

/**
 *
 * @author duyuno
 */
public interface ActionListener {
    public void onFinish(STATE state);
    public void onNotAuthen();
    public void onLicenseInvalid();
}
