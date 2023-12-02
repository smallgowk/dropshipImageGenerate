/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.utils;

import io.netty.handler.codec.CorruptedFrameException;

/**
 *
 * @author duypn4
 */
public class PackageUtil {
    public static void packageMarkCheck(byte[] data, int dataIndex, byte[] markToCheck) throws CorruptedFrameException {
        for (int i = 0; i < markToCheck.length; i++) {
            if (markToCheck[i] != data[i + dataIndex]) {
                System.out.println("Char Expect: " + (char)(markToCheck[i]));
                System.out.println("Char Received: " + (char)(data[i]));
                String frameErrorMsg = String.format(
                        "Invalid package mark, expected 0x%x but received 0x%x.",
                        markToCheck[i],
                        data[i]);
                throw new CorruptedFrameException(frameErrorMsg);
            }
        }
    }
}
