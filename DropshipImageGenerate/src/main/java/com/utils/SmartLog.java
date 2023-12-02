/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils;

import com.utils.DialogUtil;
import javax.swing.JFrame;
import javax.swing.JTextArea;

/**
 *
 * @author duyuno
 */
public class SmartLog {

    public static void logPrint(String message) {
        System.out.print("" + message);
    }

    public static void logPrintln(String message) {
        System.out.println("" + message);
    }

    public static void logPrintln(String message, JTextArea jTextArea) {
        System.out.println("" + message);

        if (jTextArea != null) {
            jTextArea.append(message);
            jTextArea.append("\n");
        }

    }
    
    public static void logErrorPrintln(String message, JFrame frame) {
        System.out.println("" + message);

        if (frame != null) {
            DialogUtil.showErrorMessage(frame, "Error", message);
        }

    }

    public static void logPrintln(String tag, String message) {
        System.out.println(tag + ": " + message);
    }
}
