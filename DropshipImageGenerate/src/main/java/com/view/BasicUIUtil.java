/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view;

import com.utils.OSUtil;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 *
 * @author duyuno
 */
public class BasicUIUtil {

    static Font font;
    
    static {
        font = OSUtil.findFont("Tahoma").deriveFont(13f);
    }
    
    public static JTextField createTextField(String text,
            Dimension minimumSize, int width, int height) {
        JTextField jTextField = new JTextField(text);
        jTextField.setBorder(new CompoundBorder(
                new LineBorder(Color.GRAY),
                new EmptyBorder(1, 3, 1, 1)));

        jTextField.setFont(font);

        jTextField.setPreferredSize(new Dimension(width, height));

        if (minimumSize != null) {
            jTextField.setMinimumSize(new Dimension(40, 32));
        }
        
        return jTextField;
    }
    
    public static JLabel createLabel(
            String text,
            Dimension minimumSize, int width, int height) {
        JLabel jLabel = new JLabel(text);
//        area.setBorder(new CompoundBorder(
//                new LineBorder(Color.WHITE),
//                new EmptyBorder(1, 3, 1, 1)));
        jLabel.setFont(font);
        jLabel.setBorder(null);
        jLabel.setPreferredSize(new Dimension(width, height));
        if (minimumSize != null) {
            jLabel.setMinimumSize(new Dimension(40, 32));
        }
        return jLabel;
    }
    
    public static JCheckBox createCheckBox(
            String text,
            Dimension minimumSize) {
        JCheckBox area = new JCheckBox(text == null ? "" : text);
        area.setBorder(new CompoundBorder(
                new LineBorder(Color.GRAY),
                new EmptyBorder(1, 3, 1, 1)));

        Font font = OSUtil.findFont("Tahoma").deriveFont(13f);

        area.setFont(font);

        area.setPreferredSize(new Dimension(120, 32));

        if (minimumSize != null) {
            area.setMinimumSize(new Dimension(40, 32));
        }
        return area;
    }

    public static JCheckBox createCheckBox(
            String text, int width,
            Dimension minimumSize) {
        JCheckBox area = new JCheckBox(text);
        area.setBorder(new CompoundBorder(
                new LineBorder(Color.GRAY),
                new EmptyBorder(1, 3, 1, 1)));

        area.setPreferredSize(new Dimension(width, 32));
        if (minimumSize != null) {
            area.setMinimumSize(new Dimension(40, 32));
        }

        return area;
    }

    public static JLabel createLabel(
            String text,
            Dimension minimumSize) {
        JLabel area = new JLabel(text);
//        area.setBorder(new CompoundBorder(
//                new LineBorder(Color.WHITE),
//                new EmptyBorder(1, 3, 1, 1)));
        area.setBorder(null);
        area.setPreferredSize(new Dimension(120, 32));
        if (minimumSize != null) {
            area.setMinimumSize(new Dimension(40, 32));
        }
        return area;
    }

    public static JTextArea createArea(
            String text,
            boolean lineWrap,
            int columns,
            Dimension minimumSize) {
        JTextArea area = new JTextArea(text);
        area.setBorder(new CompoundBorder(
                new LineBorder(Color.GRAY),
                new EmptyBorder(1, 3, 1, 1)));
        area.setLineWrap(lineWrap);
        area.setWrapStyleWord(true);
        Font font = OSUtil.findFont("Tahoma").deriveFont(13f);

        area.setFont(font);

        area.setColumns(columns);

        if (minimumSize != null) {
            area.setMinimumSize(new Dimension(350, 32));
        }
        return area;
    }
}
