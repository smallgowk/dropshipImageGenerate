/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.config;

import java.util.Random;

/**
 *
 * @author PhanDuy
 */
public class AppConfig {
    public static int port = 89;
    
    public static void randomPort() {
        Random random = new Random();
        
        port = random.nextInt(1000);
    }
}
