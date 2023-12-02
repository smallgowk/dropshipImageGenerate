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
public interface ProgressInterface {
    public void startProcess(int maxValue);
    public void onUpdateProcess(String status, int value);
    public void onFinishProcess();
}
