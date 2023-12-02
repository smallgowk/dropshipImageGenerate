/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view;

import com.models.state.ProcessState;
import com.utils.OSUtil;
import com.utils.StringUtils;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author PhanDuy
 */
public class ProcessPannel extends BasePanel {

    private int width, height;

    ArrayList<ProcessState> listState;
    int marginLeft = 5;
    int marginTop = 15;
    int marginVertical = 30;
    float fontSize = 11f;

    HashMap<String, String> hashMapState = null;

    public ProcessPannel(int width, int height, int size) {
        this.width = width;
        this.height = height;
        System.out.println("" + width + " | " + height);
//        setSize(new Dimension(width, height));
        setTitle("ProcessPannel");
        setMenuActionCommand("ProcessPannel");
        listState = new ArrayList<>();
        hashMapState = new HashMap<>();
        
        setPreferredSize(new Dimension(width, marginTop + marginVertical * size));
    }
    
    public void addAll(ArrayList<ProcessState> listData) {
        if (listState == null) {
            listState = new ArrayList<>();
        }
        
        listState.addAll(listData);
        
        repaint();
    }

    public void addData(ProcessState processState) {
        if (listState == null) {
            listState = new ArrayList<>();
        }
        
        if(StringUtils.isEmpty(processState.getStoreSign())) {
            return;
        }

        if (!hashMapState.containsKey(processState.getStoreSign())) {
            listState.add(0, processState);
            hashMapState.put(processState.getStoreSign(), "");
            repaint();
        }
    }

    public void updateStatus(String storeSign, String status) {
        ProcessState processState = null;

        for (ProcessState processState1 : listState) {
            if (processState1.getStoreSign().equals(storeSign)) {
                processState = processState1;
                break;
            }
        }

        if (processState == null) {
            return;
        }
        processState.setStatus(status);
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (listState == null || listState.isEmpty()) {
            return;
        }
        

        int size = listState.size();

        Font font = OSUtil.findFont("Arial").deriveFont(fontSize);

        g.setFont(font);
        FontMetrics fontMetrics = g.getFontMetrics(font);

        for (int i = 0; i < size; i++) {
            ProcessState processState = listState.get(i);
//            int margin = marginLeft;
//            if (size >= 9) {
//                margin = 20;
//            }

            g.drawString(processState.getAccNo() + " - " + processState.getStoreSign(), marginLeft, marginTop + marginVertical * i);
            g.drawString(processState.getStatus(), width - fontMetrics.stringWidth(processState.getStatus()) - marginLeft, marginTop + marginVertical * i);
        }

//        Rectangle rectangle = new Rectangle
//        Iterator it = hashMapState.entrySet().iterator();
//        int index = 0;
//        while (it.hasNext()) {
//            Map.Entry pair = (Map.Entry) it.next();
//
//            ProcessState processState = (ProcessState) pair.getValue();
////                System.out.println("" + width);
////            g.drawString(processState.getAccNo(), 0, marginTop + 20 * index);
//            int margin = marginLeft;
//            if(size >= 9) {
//                margin = 20;
//            }
//
//            g.drawString(processState.getAccNo() + " - " + processState.getStoreSign(), marginLeft, marginTop + marginVertical * index);
//            g.drawString(processState.getStatus(), width - fontMetrics.stringWidth(processState.getStatus()) - margin, marginTop + marginVertical * index);
//            index ++;
//        }
//        if (listState != null) {
//            for (int i = 0, size = listState.size(); i < size; i++) {
//
//                ProcessState processState = listState.get(i);
////                System.out.println("" + width);
//                g.drawString(processState.getAccNo(), 0, marginTop + 20 * i);
//                g.drawString(processState.getStoreSign(), width / 3, marginTop + 20 * i);
//                String status = processState.getStatus();
//                g.drawString(status, width - fontMetrics.stringWidth(status) - marginLeft, marginTop + 20 * i);
//            }
//        }
//        g.drawRect(0, 0, width, height);
    }
}
