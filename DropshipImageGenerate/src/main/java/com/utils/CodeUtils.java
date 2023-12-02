/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 *
 * @author duyuno
 */
public class CodeUtils {

    static final long MAX_POSSIBLE_UPC_CODE = 999999999999L;
    static final long MIN_POSSIBLE_UPC_CODE = 100000000000L;

    static final long MAX_POSSIBLE_EAN_CODE = 9999999999999L;
    static final long MIN_POSSIBLE_EAN_CODE = 1000000000000L;

    private static HashMap<String, int[]> hashMapPrefixBanned = new HashMap<>();

    static {
        hashMapPrefixBanned.put("38", new int[]{1, 2, 4, 6, 8});
        hashMapPrefixBanned.put("39", new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
        hashMapPrefixBanned.put("44", new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
        hashMapPrefixBanned.put("51", new int[]{-1});
        hashMapPrefixBanned.put("55", new int[]{-1});
        hashMapPrefixBanned.put("58", new int[]{-1});
        hashMapPrefixBanned.put("63", new int[]{-1});
        hashMapPrefixBanned.put("52", new int[]{2, 3, 4, 5, 6, 7});
        hashMapPrefixBanned.put("53", new int[]{2, 3, 4, 6, 7, 8});
        hashMapPrefixBanned.put("60", new int[]{2, 5, 6, 7});
        hashMapPrefixBanned.put("61", new int[]{0, 2, 4, 5, 6, 7});
        hashMapPrefixBanned.put("56", new int[]{1, 2, 3, 4, 6, 7, 8});
        hashMapPrefixBanned.put("59", new int[]{1, 2, 3, 5, 6, 7, 8});
        hashMapPrefixBanned.put("47", new int[]{2, 3});
        hashMapPrefixBanned.put("48", new int[]{3});
        hashMapPrefixBanned.put("65", new int[]{-1});
        hashMapPrefixBanned.put("66", new int[]{-1});
        hashMapPrefixBanned.put("67", new int[]{-1});
        hashMapPrefixBanned.put("68", new int[]{-1});
        hashMapPrefixBanned.put("71", new int[]{-1});
        hashMapPrefixBanned.put("72", new int[]{0 , 1, 2, 3,4, 5, 6, 7, 8});
        hashMapPrefixBanned.put("74", new int[]{7, 8, 9});
        hashMapPrefixBanned.put("75", new int[]{1, 2, 3, 6, 7, 8});
        hashMapPrefixBanned.put("77", new int[]{2, 4, 5, 6});
        hashMapPrefixBanned.put("78", new int[]{1, 2, 3, 5, 7, 8});
        hashMapPrefixBanned.put("79", new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
        hashMapPrefixBanned.put("85", new int[]{1, 2, 3, 4, 5, 6, 7});
        hashMapPrefixBanned.put("86", new int[]{1, 2, 3, 4, 6});
        hashMapPrefixBanned.put("88", new int[]{1, 2, 3, 6, 7, 9});
        hashMapPrefixBanned.put("89", new int[]{1, 2, 4, 5, 7, 8});
        hashMapPrefixBanned.put("97", new int[]{0, 1, 2, 4, 5, 6});
        hashMapPrefixBanned.put("95", new int[]{2, 3, 4, 6, 7, 9});
        hashMapPrefixBanned.put("98", new int[]{5, 6, 7, 8, 9});
        hashMapPrefixBanned.put("92", new int[]{-1});
        hashMapPrefixBanned.put("3", new int[]{8, 9});
        hashMapPrefixBanned.put("4", new int[]{4, 7, 8});
        hashMapPrefixBanned.put("5", new int[]{1, 2, 3, 5, 6, 8, 9});
        hashMapPrefixBanned.put("6", new int[]{0, 1, 3, 5,6,7,8});
        hashMapPrefixBanned.put("7", new int[]{1,2,4,5,7,8,9});
        hashMapPrefixBanned.put("8", new int[]{5,6,8,9});
        hashMapPrefixBanned.put("9", new int[]{2,5,7,8});
    }

    public static String genRandomProductId() {
        return genEAN();
    }

    public static int genRandomNumberWithPrefix(String prefix, Random random) {
        if (hashMapPrefixBanned.containsKey(prefix)) {
            int[] banned = hashMapPrefixBanned.get(prefix);
            if (banned.length == 1 && banned[0] == -1) {
                return -1;
            } else {
                
                List<Integer> listBanned = new ArrayList<>();
                for (int i = 0, length = banned.length; i < length; i++) {
                    listBanned.add(banned[i]);
                }
                
                List<Integer> listValid = new ArrayList<>();
                
                for(int i = 0; i < 10; i++) {
                    if(!listBanned.contains(Integer.valueOf(i))) {
                        listValid.add(i);
                    }
                }
                
                Collections.shuffle(listValid);
                
                return listValid.get(0);
            }
        } else {
            return random.nextInt(9);
        }
    }

    public static String genEAN() {
        StringBuilder sbEAN = new StringBuilder();
        Random random = new Random();

        int[] d = new int[12];
        
        d[0] = random.nextInt(8) + 1;//1
        d[1] = random.nextInt(9);//2
        
        int thirdDigit = genRandomNumberWithPrefix(d[0] + "" + d[1], random);
        
        if(thirdDigit == -1) {
            d[1] = genRandomNumberWithPrefix("" + d[0], random);
            thirdDigit = genRandomNumberWithPrefix(d[0] + "" + d[1], random);
        }
        d[2] = thirdDigit;
        d[3] = random.nextInt(9);//2
        d[4] = random.nextInt(9);//2
        d[5] = random.nextInt(9);//2
        d[6] = random.nextInt(9);//2
        d[7] = random.nextInt(9);//2
        d[8] = random.nextInt(9);//2
        d[9] = random.nextInt(9);//2
        d[10] = random.nextInt(9);//2
        d[11] = random.nextInt(9);//2
        
        
//        int[] d = new int[]{
//            random.nextInt(8) + 1,//1
//            random.nextInt(9),//2
//            random.nextInt(9),//3
//            random.nextInt(9),//4
//            random.nextInt(9),//5
//            random.nextInt(9),//6
//            random.nextInt(9),//7
//            random.nextInt(9),//8
//            random.nextInt(9),//9
//            random.nextInt(9),//10
//            random.nextInt(9),//11
//            random.nextInt(9)//12
//        };

        int le = 0, chan = 0;
//        int m = d2 + d4 + d6 + d8 + d10;

        for (int i = 0, length = d.length; i < length; i++) {
            if (i % 2 == 0) {
                le += d[i];
            } else {
                chan += d[i];
            }
        }
        int mod = (chan * 3 + le) % 10;
        int lastDigit = mod == 0 ? 0 : 10 - mod;

        for (int i = 0, length = d.length; i < length; i++) {
            sbEAN.append(d[i]);
        }

        sbEAN.append(lastDigit);
        return sbEAN.toString();
    }

    public static String genUPC() {
        StringBuilder sbUpc = new StringBuilder();
        Random random = new Random();

        int[] d = new int[]{
            random.nextInt(8) + 1,//1
            random.nextInt(9),//2
            random.nextInt(9),//3
            random.nextInt(9),//4
            random.nextInt(9),//5
            random.nextInt(9),//6
            random.nextInt(9),//7
            random.nextInt(9),//8
            random.nextInt(9),//9
            random.nextInt(9),//10
            random.nextInt(9)//11
        };

        int m = 0, n = 0;
//        int m = d2 + d4 + d6 + d8 + d10;

        for (int i = 0, length = d.length; i < length; i++) {
            if (i % 2 == 0) {
                n += d[i];
            } else {
                m += d[i];
            }
        }

        int mod = (m + 3 * n) % 10;
        int lastDigit = mod == 0 ? 0 : 10 - mod;

        for (int i = 0, length = d.length; i < length; i++) {
            sbUpc.append(d[i]);
        }

        sbUpc.append(lastDigit);

//        sbEAN.append(d1).append(d2).append(d3).append(d4).append(d5).append(d6).append(d7).append(d8).append(d9).append(d10).append(d11).append(d12);
        return sbUpc.toString();
    }

    public static boolean checkIsUPC(long input) {

        long number = input;

        // determine whether number is a possible upc code	
        if ((input < MIN_POSSIBLE_UPC_CODE) || (input > MAX_POSSIBLE_UPC_CODE)) {
            // not a upc code	
//            System.out.println(input + " is an invalid UPC code");
            return false;
        } else {
            // might be a upc code	

            // determine individual digits	
            int d12 = (int) (number % 10);
            number /= 10;
            int d11 = (int) (number % 10);
            number /= 10;
            int d10 = (int) (number % 10);
            number /= 10;
            int d9 = (int) (number % 10);
            number /= 10;
            int d8 = (int) (number % 10);
            number /= 10;
            int d7 = (int) (number % 10);
            number /= 10;
            int d6 = (int) (number % 10);
            number /= 10;
            int d5 = (int) (number % 10);
            number /= 10;
            int d4 = (int) (number % 10);
            number /= 10;
            int d3 = (int) (number % 10);
            number /= 10;
            int d2 = (int) (number % 10);
            number /= 10;
            int d1 = (int) (number % 10);
            number /= 10;

            // compute sums of first 5 even digits and the odd digits	
            int m = d2 + d4 + d6 + d8 + d10;
            int n = d1 + d3 + d5 + d7 + d9 + d11;

            // use UPC formula to determine required value for d12	
//            int r = 10 - ((m + 3 * n) % 10);
            int r = (10 - ((m + 3 * n) % 10)) % 10;

            // based on r, can test whether number is a UPC code	
            if (r == d12) {
                // is a upc code	
//                System.out.println(input + " is a feasible UPC code");

                return true;
            } else {
                // not a upc code	
//                System.out.println(input + " is an invalid UPC code");

                return false;
            }
        }
    }

    public static boolean checkIsEAN(long input) {

        long number = input;

        // determine whether number is a possible upc code	
        if ((input < MIN_POSSIBLE_EAN_CODE) || (input > MAX_POSSIBLE_EAN_CODE)) {
            // not a upc code	
//            System.out.println(input + " is an invalid UPC code");
            return false;
        } else {
            // might be a upc code	

            // determine individual digits
            int d13 = (int) (number % 10);
            number /= 10;
            int d12 = (int) (number % 10);
            number /= 10;
            int d11 = (int) (number % 10);
            number /= 10;
            int d10 = (int) (number % 10);
            number /= 10;
            int d9 = (int) (number % 10);
            number /= 10;
            int d8 = (int) (number % 10);
            number /= 10;
            int d7 = (int) (number % 10);
            number /= 10;
            int d6 = (int) (number % 10);
            number /= 10;
            int d5 = (int) (number % 10);
            number /= 10;
            int d4 = (int) (number % 10);
            number /= 10;
            int d3 = (int) (number % 10);
            number /= 10;
            int d2 = (int) (number % 10);
            number /= 10;
            int d1 = (int) (number % 10);
            number /= 10;

            // compute sums of first 5 even digits and the odd digits	
            int m = d2 + d4 + d6 + d8 + d10 + d12;
            int n = d1 + d3 + d5 + d7 + d9 + d11;

            int mode = (m * 3 + n) % 10;

            int r = mode == 0 ? 0 : 10 - mode;

            // use UPC formula to determine required value for d12	
//            int r = 10 - ((m + 3 * n) % 10);
//            int r = (10 - ((m + 3 * n) % 10)) % 10;
            // based on r, can test whether number is a UPC code	
            if (r == d13) {
                // is a upc code	
//                System.out.println(input + " is a feasible UPC code");

                return true;
            } else {
                // not a upc code	
//                System.out.println(input + " is an invalid UPC code");

                return false;
            }
        }
    }
}
