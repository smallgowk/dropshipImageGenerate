/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author khanhpm2
 */
public class EmailValidator {
    
    public static final String EMAIL_PATTERN = "^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$";

    private static Pattern patternEmail;
    private static Matcher matcherEmail;
    public static boolean validateEmail(final String email) {
        
        if(email == null || email.isEmpty()) {
            return false;
        }
        
        patternEmail = Pattern.compile(EMAIL_PATTERN);
        matcherEmail = patternEmail.matcher(email);      
        return matcherEmail.matches();
    }
}
