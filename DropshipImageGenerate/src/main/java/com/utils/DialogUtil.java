/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils;

import com.view.TradeMarkCheckItem;
import com.config.Configs;
import com.models.aliex.store.inputdata.BaseStoreOrderInfo;
import com.models.trademark.MarkerTradeMarkObj;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 *
 * @author duyuno
 */
public class DialogUtil {

    public static void showInfoMessage(JFrame frame, String message) {
        JOptionPane.showMessageDialog(frame, message);
    }

    public static void showWarningMessage(JFrame frame, String title, String message) {
        JOptionPane.showMessageDialog(frame, message, title, JOptionPane.WARNING_MESSAGE);
    }

    public static void showErrorMessage(JFrame frame, String title, String message) {
        JOptionPane.showMessageDialog(frame, message, title, JOptionPane.ERROR_MESSAGE);
    }

    public static int showOptionsQuestionDialog(JFrame frame, String title, String message, String... options) {

        if (options == null || options.length == 0) {
            return -1;
        }

//        if(options.length == 1) {
//            return 0;
//        }
//        ImageIcon icon = new ImageIcon("Images\\appIcon.jpg");
//        ImageIcon icon = null;
//        java.net.URL imageURL = DialogUtil.class.getResource("Images\\appIcon.jpg");
//        if (imageURL != null) {
//            icon = new ImageIcon(imageURL);
//        }
//        ImageIcon icon = new ImageIcon("Images\\appIcon.png");
//        Image scaled = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        int n = JOptionPane.showOptionDialog(frame,
                message,
                title,
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        return n;
    }

    public static int showOptionsMessageDialog(JFrame frame, String title, String message, String... options) {

        if (options == null || options.length == 0) {
            return -1;
        }

        JLabel label = new JLabel(message);

        label.setSize(500, 300);

//        ImageIcon icon = new ImageIcon("Images\\appIcon.png");
//        Image scaled = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        int n = JOptionPane.showOptionDialog(frame,
                label,
                title,
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]);

//        System.out.println("" + n);
        return n;
    }

    public static void showInforMessageWithScroll(JFrame frame, String tile, String message) {
//        JEditorPane editorPane = new JEditorPane("text/html", "");
//        editorPane.setEditable(false);
//        editorPane.setPage(tile);
//
//        JScrollPane editorScrollPane = new JScrollPane(editorPane);
//        editorScrollPane.setVerticalScrollBarPolicy(
//                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        editorScrollPane.setPreferredSize(new Dimension(250, 145));
//        editorScrollPane.setMinimumSize(new Dimension(10, 10));
//        
//        JOptionPane.showMessageDialog(frame, new JScrollPane(editorPane), tile,
//                JOptionPane.INFORMATION_MESSAGE);

        JTextArea textArea = new JTextArea(message);
        textArea.setColumns(60);

        textArea.setRows(40);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JOptionPane.showMessageDialog(frame, new JScrollPane(textArea), tile,
                JOptionPane.INFORMATION_MESSAGE);

    }

    public static void showPannelDialog(JFrame frame, JPanel jpannel, String tile) {
//        JOptionPane.showMessageDialog(frame, jpannel, tile,
//                JOptionPane.NO_OPTION);

        JDialog dialog = new JDialog(frame, tile, true);
        
        dialog.setResizable(false);
        dialog.getContentPane().add(jpannel);
        dialog.pack();
        Dimension Size = Toolkit.getDefaultToolkit().getScreenSize();
        dialog.setLocation(new Double((Size.getWidth() / 2) - (dialog.getWidth() / 2)).intValue(), new Double((Size.getHeight() / 2) - (dialog.getHeight() / 2)).intValue());
        dialog.setVisible(true);
        
        
        
//        return dialog;
    }

    public static HashMap<String, Boolean> showListTradeMark(ArrayList<MarkerTradeMarkObj> listTradeMark) {

        JPanel controls = new JPanel();
        controls.setLayout(new BoxLayout(controls, BoxLayout.Y_AXIS));

        ArrayList<TradeMarkCheckItem> listTradeMarkItems = new ArrayList<>();

        for (MarkerTradeMarkObj markerTradeMarkObj : listTradeMark) {
//            TradeMarkCheckItem tradeMarkCheckItem = new TradeMarkCheckItem(markerTradeMarkObj.getWordmark(), markerTradeMarkObj.getDescription());
////            tradeMarkCheckItem.setTitle("" + markerTradeMarkObj.getWordmark());
////            tradeMarkCheckItem.setDescription("" + markerTradeMarkObj.getDescription());
//            
//            listTradeMarkItems.add(tradeMarkCheckItem);
//            
//            controls.add(tradeMarkCheckItem);
//            controls.add(new JSeparator(), BorderLayout.CENTER);

            JPanel newPanel = new JPanel();
//            newPanel.setLayout(new BoxLayout(controls, BoxLayout.X_AXIS));
//            newPanel.setBackground(Color.yellow);

//            JCheckBox jCheckBox = new JCheckBox("Title " + i);
            JPanel checkBoxPanel = new JPanel();
            checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS));
            JCheckBox jCheckBoxSoftRemove = createCheckBox("Soft Remove", null);
            JCheckBox jCheckBoxHardRemove = createCheckBox("Hard Remove", null);

            JTextArea jTextArea = createArea(markerTradeMarkObj.getInfos(), true, 50, null);

//            newPanel.setPreferredSize(new Dimension(size, heightText));
            checkBoxPanel.add(jCheckBoxSoftRemove);
            checkBoxPanel.add(jCheckBoxHardRemove);
            newPanel.add(checkBoxPanel, BorderLayout.CENTER);
            newPanel.add(jTextArea, BorderLayout.CENTER);
            controls.add(newPanel);
            controls.add(new JSeparator(), BorderLayout.CENTER);

            TradeMarkCheckItem tradeMarkCheckItem = new TradeMarkCheckItem(markerTradeMarkObj, jCheckBoxSoftRemove, jCheckBoxHardRemove);
            listTradeMarkItems.add(tradeMarkCheckItem);
        }

        JScrollPane sp = new JScrollPane(controls);
        Dimension d = sp.getPreferredSize();
        sp.setPreferredSize(new Dimension(d.width + 30, 600));

        sp.getVerticalScrollBar().setUnitIncrement(16);

        String[] options = new String[]{"OK", "Cancel"};
        int n = JOptionPane.showOptionDialog(null,
                sp,
                "Chose the wordmarks which you want to remove in your content",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]);

        if (n == 0) {
            HashMap<String, Boolean> hashMap = new HashMap<>();
            for (TradeMarkCheckItem tradeMarkCheckItem : listTradeMarkItems) {
                if (tradeMarkCheckItem.isCheckedSoftRemove()) {

                    if (hashMap == null) {
                        hashMap = new HashMap<>();
                        hashMap.put(tradeMarkCheckItem.getTitle(), tradeMarkCheckItem.isCheckedHardRemove());
                    } else {
                        if (!hashMap.containsKey(tradeMarkCheckItem.getTitle())) {
                            hashMap.put(tradeMarkCheckItem.getTitle(), tradeMarkCheckItem.isCheckedHardRemove());
                        }
                    }
                }

            }

            return hashMap;
        } else {
            return null;
        }
    }

    public static String showListTradeMarkWords(String input) {
        ArrayList<TradeMarkCheckItem> listTradeMarkItems = new ArrayList<>();
        ArrayList<JCheckBox> listCheckBoxAll = new ArrayList<>();
        String[] parts = input.split(" ");

        int columns = 7;

        JPanel controls = new JPanel();
        controls.setLayout(new BoxLayout(controls, BoxLayout.Y_AXIS));

        JPanel rowWords = null;

        JCheckBox checkBoxAll = createCheckBox("All", null);
//        checkBoxAll.setSelected(true);
        checkBoxAll.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getID() == ActionEvent.ACTION_PERFORMED) {
                    for (TradeMarkCheckItem checkItem : listTradeMarkItems) {
                        checkItem.setSoftRemove(checkBoxAll.isSelected());
                    }

                    for (JCheckBox jCheckBox : listCheckBoxAll) {
                        jCheckBox.setSelected(checkBoxAll.isSelected());
                    }
                }
            }
        });

        rowWords = new JPanel();
        rowWords.add(checkBoxAll);
        rowWords.add(new JSeparator(), BorderLayout.CENTER);
        for (int i = 0; i < columns; i++) {

            rowWords.add(createLabel("", null));
            rowWords.add(new JSeparator(), BorderLayout.CENTER);
        }

        controls.add(rowWords);
        controls.add(new JSeparator(), BorderLayout.CENTER);

        rowWords = null;

        for (int i = 0, length = parts.length; i < length; i++) {
            if (i % columns == 0) {
                final int row = i / columns + 1;
                rowWords = new JPanel();
                JCheckBox jCheckBoxRowAll = createCheckBox("All Row", null);
                listCheckBoxAll.add(jCheckBoxRowAll);

                jCheckBoxRowAll.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (e.getID() == ActionEvent.ACTION_PERFORMED) {
                            for (TradeMarkCheckItem checkItem : listTradeMarkItems) {
                                if (checkItem.getRowNumber() == row) {
                                    checkItem.setSoftRemove(jCheckBoxRowAll.isSelected());
                                }

                            }
                        }
                    }
                });
                rowWords.add(jCheckBoxRowAll);
                rowWords.add(new JSeparator(), BorderLayout.CENTER);
            }

            JCheckBox jCheckBoxSoftRemove = createCheckBox(parts[i], null);
//            jCheckBoxSoftRemove.setSelected(true);
            rowWords.add(jCheckBoxSoftRemove);
            rowWords.add(new JSeparator(), BorderLayout.CENTER);

            TradeMarkCheckItem tradeMarkCheckItem = new TradeMarkCheckItem(parts[i], null, jCheckBoxSoftRemove, null);
            tradeMarkCheckItem.setRowNumber(i / columns + 1);
            listTradeMarkItems.add(tradeMarkCheckItem);

            if (i % columns == columns - 1 || i == length - 1) {
                if (i == length - 1 && i % columns < columns - 1) {
                    for (int j = i % columns; j < columns - 1; j++) {
//                        JCheckBox jCheckBox = createCheckBox("", null);
                        rowWords.add(createLabel("", null));
                        rowWords.add(new JSeparator(), BorderLayout.CENTER);
                    }
                }
//                rowWords.setBackground(Color.yellow);
                controls.add(rowWords);
//                rowWords.setAlignmentX(Component.LEFT_ALIGNMENT);
                controls.add(new JSeparator(), BorderLayout.AFTER_LAST_LINE);
            }
        }

        JScrollPane sp = new JScrollPane(controls);
        Dimension d = sp.getPreferredSize();
        sp.setPreferredSize(new Dimension(d.width + 30, d.height < 600 ? d.height : 600));

        sp.getVerticalScrollBar().setUnitIncrement(16);

        String[] options = new String[]{"OK", "Cancel"};
        int n = JOptionPane.showOptionDialog(null,
                sp,
                "Chose the words to check trade mark...",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]);

        if (n == 0) {
            StringBuilder sb = new StringBuilder();
            for (TradeMarkCheckItem tradeMarkCheckItem : listTradeMarkItems) {
                if (tradeMarkCheckItem.isCheckedSoftRemove()) {
                    sb.append(tradeMarkCheckItem.getTitle()).append(" ");
                }
            }

            return sb.toString().trim().toUpperCase();
        } else {
            return null;
        }
    }

    private static JCheckBox createCheckBox(
            String text,
            Dimension minimumSize) {
        JCheckBox area = new JCheckBox(text == null ? "" : text);
        area.setBorder(new CompoundBorder(
                new LineBorder(Color.GRAY),
                new EmptyBorder(1, 3, 1, 1)));

        Font font = OSUtil.findFont("Tahoma").deriveFont(13f);

        area.setFont(font);

//        if (text != null && !text.isEmpty()) {
//
//            Canvas c = new Canvas();
//            FontMetrics fm = c.getFontMetrics(font);
//            int charWidth = fm.charWidth('c');
//            int textWidth = charWidth * text.length();
//
//            area.setPreferredSize(new Dimension(32 + textWidth, 32));
//        } else {
//            area.setPreferredSize(new Dimension(32, 32));
//        }
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

    private static JLabel createLabel(
            String text,
            Dimension minimumSize) {
        JLabel area = new JLabel(text);
//        area.setBorder(new CompoundBorder(
//                new LineBorder(Color.WHITE),
//                new EmptyBorder(1, 3, 1, 1)));
        area.setBackground(Color.red);
        area.setBorder(null);
        area.setPreferredSize(new Dimension(120, 32));
        if (minimumSize != null) {
            area.setMinimumSize(new Dimension(40, 32));
        }
        return area;
    }

    public static JLabel createLabel(
            String text,
            int width,
            Dimension minimumSize) {
        JLabel area = new JLabel(text);
        area.setBackground(Color.red);
//        area.setBorder(new CompoundBorder(
//                new LineBorder(Color.WHITE),
//                new EmptyBorder(1, 3, 1, 1)));
        area.setBorder(null);
        area.setPreferredSize(new Dimension(width, 32));
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

//        Canvas c = new Canvas();
//        FontMetrics fm = c.getFontMetrics(font);
//        int charWidth = fm.charWidth('c');
//        int textWidth = charWidth * text.length();
//        area.setFont(font);
        area.setFont(font);

//        if (text.length() < columns) {
//            area.setColumns(text.length());
//            area.setRows(2);
//        } else {
//            area.setColumns(columns);
//        }
        area.setColumns(columns);

        if (minimumSize != null) {
            area.setMinimumSize(new Dimension(350, 32));
        }
        return area;
    }

    public static ArrayList<BaseStoreOrderInfo> showListAliexStore(ArrayList<BaseStoreOrderInfo> listStores) {
        if (listStores == null || listStores.isEmpty()) {
            return null;
        }

        ArrayList<TradeMarkCheckItem> listTradeMarkItems = new ArrayList<>();
        for (int i = 0, size = listStores.size(); i < size; i++) {
            BaseStoreOrderInfo storePageInfo = listStores.get(i);
            JCheckBox jCheckBoxSoftRemove = createCheckBox(storePageInfo.getAcc_no(), 80, null);
            JTextArea jTextArea = createArea(storePageInfo.getLink(), true, 40, null);
            jTextArea.setEditable(false);
            jTextArea.setBackground(null);
//            final int j = i;
//            jCheckBoxSoftRemove.addActionListener(new ActionListener() {
//
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    if (e.getID() == ActionEvent.ACTION_PERFORMED) {
//
//                        if (!jCheckBoxSoftRemove.isSelected()) {
//                            boolean isHasSelected = false;
//                            for (TradeMarkCheckItem checkItem : listTradeMarkItems) {
//                                if (checkItem.isCheckedSoftRemove()) {
//                                    isHasSelected = true;
//                                    break;
//                                }
//
//                            }
//
//                            if (!isHasSelected) {
//                                jCheckBoxSoftRemove.setSelected(true);
//                            }
//                            return;
//                        }
//
//                        for (TradeMarkCheckItem checkItem : listTradeMarkItems) {
//
//                            if (checkItem.getRowNumber() != j) {
//                                checkItem.setSoftRemove(false);
//                            }
//
//                        }
//                    }
//                }
//            });

            TradeMarkCheckItem tradeMarkCheckItem = new TradeMarkCheckItem(storePageInfo, jCheckBoxSoftRemove, null);
            tradeMarkCheckItem.setState(storePageInfo.getStatus());
            tradeMarkCheckItem.setRowNumber(i);
            listTradeMarkItems.add(tradeMarkCheckItem);
        }
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        JPanel dataPanel = new JPanel();

//        ArrayList<TradeMarkCheckItem> listTradeMarkItems = displayData(dataPanel, listStores, "All");
        JScrollPane sp = displayData(listTradeMarkItems, "All");
        dataPanel.add(sp);

        JPanel filterPanel = new JPanel();
        JCheckBox allCheckBox = createCheckBox("All", null);
        JCheckBox newCheckBox = createCheckBox("New", null);
        JCheckBox doneCheckBox = createCheckBox("Done", null);
        filterPanel.add(allCheckBox);
        filterPanel.add(new JSeparator(), BorderLayout.CENTER);
        filterPanel.add(newCheckBox);
        filterPanel.add(new JSeparator(), BorderLayout.CENTER);
        filterPanel.add(doneCheckBox);

        allCheckBox.setSelected(true);

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                System.out.println("" + e.getActionCommand());
                switch (e.getActionCommand()) {
                    case "All":
                        allCheckBox.setSelected(true);
                        newCheckBox.setSelected(false);
                        doneCheckBox.setSelected(false);

//                        displayData(dataPanel, listStores, "All");
                        javax.swing.SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                dataPanel.removeAll();
                                dataPanel.add(displayData(listTradeMarkItems, "All"));
                                dataPanel.revalidate();
                                container.revalidate();
                            }
                        });

                        break;
                    case "New":
                        allCheckBox.setSelected(false);
                        newCheckBox.setSelected(true);
                        doneCheckBox.setSelected(false);
//                        dataPanel.removeAll();
//                        dataPanel.add(displayData(listTradeMarkItems, "New"));
//                        dataPanel.invalidate();

                        javax.swing.SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                dataPanel.removeAll();
                                dataPanel.add(displayData(listTradeMarkItems, "New"));
                                dataPanel.revalidate();
                                container.revalidate();
                            }
                        });
                        break;
                    case "Done":
                        allCheckBox.setSelected(false);
                        newCheckBox.setSelected(false);
                        doneCheckBox.setSelected(true);
//                        dataPanel.removeAll();
//                        dataPanel.add(displayData(listTradeMarkItems, "Done"));
//                        dataPanel.invalidate();

                        javax.swing.SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                dataPanel.removeAll();
                                dataPanel.add(displayData(listTradeMarkItems, "Done"));
                                dataPanel.revalidate();
                                container.revalidate();
                            }
                        });
                        break;
                }
            }
        };

        allCheckBox.addActionListener(actionListener);
        newCheckBox.addActionListener(actionListener);
        doneCheckBox.addActionListener(actionListener);

        container.add(filterPanel);
        container.add(dataPanel);

        String[] options = new String[]{"OK", "Cancel"};
        int n = JOptionPane.showOptionDialog(null,
                container,
                "Chose the store to crawl...",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]);

        if (n == 0) {
            ArrayList<BaseStoreOrderInfo> listBaseStoreOrderInfos = null;
            for (TradeMarkCheckItem tradeMarkCheckItem : listTradeMarkItems) {
                if (tradeMarkCheckItem.isCheckedSoftRemove()) {
                    if (listBaseStoreOrderInfos == null) {
                        listBaseStoreOrderInfos = new ArrayList<>();
                    }
                    listBaseStoreOrderInfos.add((BaseStoreOrderInfo) tradeMarkCheckItem.getData());
                }
            }

            return listBaseStoreOrderInfos;
        } else {
            return null;
        }
    }

    public static JScrollPane displayData(ArrayList<TradeMarkCheckItem> listTradeMarkItems, String command) {
//        ArrayList<TradeMarkCheckItem> listTradeMarkItems = new ArrayList<>();

        JPanel controls = new JPanel();
        controls.setLayout(new BoxLayout(controls, BoxLayout.Y_AXIS));

        JPanel selectAllPanel = new JPanel();
        JCheckBox jCheckBox = createCheckBox("Select All", null);
        selectAllPanel.add(jCheckBox);

        selectAllPanel.add(new JSeparator(), BorderLayout.CENTER);

        selectAllPanel.add(createLabel("", 80, null));
        selectAllPanel.add(new JSeparator(), BorderLayout.CENTER);
        JTextArea jTextAreaFake = createArea("", true, 40, null);
        jTextAreaFake.setEditable(false);
        jTextAreaFake.setBackground(null);
        selectAllPanel.add(jTextAreaFake, BorderLayout.CENTER);

        controls.add(selectAllPanel);
        controls.add(new JSeparator(), BorderLayout.AFTER_LAST_LINE);

        jCheckBox.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getID() == ActionEvent.ACTION_PERFORMED) {

//                    if (!jCheckBox.isSelected()) {
//                        boolean isHasSelected = false;
//
//                    }
                    for (TradeMarkCheckItem checkItem : listTradeMarkItems) {
                        checkItem.checkBoxSoftRemove.setSelected(jCheckBox.isSelected());
                    }
                }
            }
        });

        boolean isFirst = true;

        for (int i = 0, size = listTradeMarkItems.size(); i < size; i++) {

            TradeMarkCheckItem tradeMarkItem = listTradeMarkItems.get(i);

            if (!command.equals("All")) {
                if (command.equals("New")) {
                    if (tradeMarkItem.getState() != null && !tradeMarkItem.getState().isEmpty()) {
                        tradeMarkItem.setSoftRemove(false);
                        continue;
                    }
                } else if (command.equals("Done")) {
                    if (tradeMarkItem.getState() == null || !tradeMarkItem.getState().toLowerCase().equals("done")) {
                        tradeMarkItem.setSoftRemove(false);
                        continue;
                    }
                }
            }

            JPanel rowWords = new JPanel();
//            JCheckBox jCheckBoxSoftRemove = createCheckBox(tradeMarkItem.getACC_NO(), null);

            if (isFirst) {
                tradeMarkItem.setSoftRemove(true);
                for (int j = i + 1; j < size; j++) {
                    listTradeMarkItems.get(j).setSoftRemove(false);
                }
                isFirst = false;
            }

            rowWords.add(tradeMarkItem.checkBoxSoftRemove);
            rowWords.add(new JSeparator(), BorderLayout.CENTER);

            rowWords.add(createLabel(tradeMarkItem.getState() + " " + tradeMarkItem.getTotal(), 80, null));
            rowWords.add(new JSeparator(), BorderLayout.CENTER);
//            JTextArea jTextAreaBrand = createArea(tradeMarkItem.getBrandName(), true, tradeMarkItem.getBrandName().length(), null);
//            jTextAreaBrand.setEditable(false);
//            jTextAreaBrand.setBorder(null);
//            jTextAreaBrand.setBackground(null);
//            rowWords.add(jTextAreaBrand, BorderLayout.CENTER);
            JTextArea jTextArea = createArea(tradeMarkItem.getDescription(), true, 40, null);
            jTextArea.setEditable(false);
            jTextArea.setBackground(null);
            rowWords.add(jTextArea, BorderLayout.CENTER);

//            TradeMarkCheckItem tradeMarkCheckItem = new TradeMarkCheckItem(tradeMarkItem, jCheckBoxSoftRemove, null);
//            tradeMarkCheckItem.setRowNumber(i);
//            listTradeMarkItems.add(tradeMarkCheckItem);
            controls.add(rowWords);

            if (i < size - 1) {
                controls.add(new JSeparator(), BorderLayout.AFTER_LAST_LINE);
            }
        }

        JScrollPane sp = new JScrollPane(controls);
        Dimension d = sp.getPreferredSize();
        sp.setPreferredSize(new Dimension(d.width + 30, d.height < 400 ? d.height + 30 : 400));

        sp.getVerticalScrollBar().setUnitIncrement(16);

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                sp.getVerticalScrollBar().setValue(0);
            }
        });

//        jPanel.removeAll();
//        jPanel.add(sp);
        return sp;
    }

    public static String[] showListAccount() {
        ArrayList<TradeMarkCheckItem> listTradeMarkItems = new ArrayList<>();
        for (int i = 0, size = Configs.listAccount.size(); i < size; i++) {
            String[] accountData = Configs.listAccount.get(i);
            JCheckBox jCheckBoxSoftRemove = createCheckBox(accountData[0], 300, null);
            final int j = i;
            jCheckBoxSoftRemove.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getID() == ActionEvent.ACTION_PERFORMED) {

                        if (!jCheckBoxSoftRemove.isSelected()) {
                            boolean isHasSelected = false;
                            for (TradeMarkCheckItem checkItem : listTradeMarkItems) {
                                if (checkItem.isCheckedSoftRemove()) {
                                    isHasSelected = true;
                                    break;
                                }

                            }

                            if (!isHasSelected) {
                                jCheckBoxSoftRemove.setSelected(true);
                            }
                            return;
                        }

                        for (TradeMarkCheckItem checkItem : listTradeMarkItems) {

                            if (checkItem.getRowNumber() != j) {
                                checkItem.setSoftRemove(false);
                            }

                        }
                    }
                }
            });

            TradeMarkCheckItem tradeMarkCheckItem = new TradeMarkCheckItem(accountData, jCheckBoxSoftRemove, null);
            tradeMarkCheckItem.setRowNumber(i);
            listTradeMarkItems.add(tradeMarkCheckItem);
        }
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        JPanel dataPanel = new JPanel();

        JScrollPane sp = displayAccountData(listTradeMarkItems);
        dataPanel.add(sp);

        container.add(dataPanel);

        String[] options = new String[]{"OK", "Cancel"};
        int n = JOptionPane.showOptionDialog(null,
                container,
                "Chose the account to login...",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]);

        if (n == 0) {
            for (TradeMarkCheckItem tradeMarkCheckItem : listTradeMarkItems) {
                if (tradeMarkCheckItem.isCheckedSoftRemove()) {
                    return (String[]) tradeMarkCheckItem.getData();
                }
            }

            return null;
        } else {
            return null;
        }
    }

    public static JScrollPane displayAccountData(ArrayList<TradeMarkCheckItem> listTradeMarkItems) {
//        ArrayList<TradeMarkCheckItem> listTradeMarkItems = new ArrayList<>();

        JPanel controls = new JPanel();
        controls.setLayout(new BoxLayout(controls, BoxLayout.Y_AXIS));

        boolean isFirst = true;

        for (int i = 0, size = listTradeMarkItems.size(); i < size; i++) {

            TradeMarkCheckItem tradeMarkItem = listTradeMarkItems.get(i);

            JPanel rowWords = new JPanel();
//            JCheckBox jCheckBoxSoftRemove = createCheckBox(tradeMarkItem.getACC_NO(), null);

            if (isFirst) {
                tradeMarkItem.setSoftRemove(true);
                for (int j = i + 1; j < size; j++) {
                    listTradeMarkItems.get(j).setSoftRemove(false);
                }
                isFirst = false;
            }

            rowWords.add(tradeMarkItem.checkBoxSoftRemove);
            rowWords.add(new JSeparator(), BorderLayout.CENTER);
            controls.add(rowWords);

            if (i < size - 1) {
                controls.add(new JSeparator(), BorderLayout.AFTER_LAST_LINE);
            }
        }

        JScrollPane sp = new JScrollPane(controls);
        Dimension d = sp.getPreferredSize();
        sp.setPreferredSize(new Dimension(d.width + 30, d.height < 400 ? d.height + 30 : 400));

        sp.getVerticalScrollBar().setUnitIncrement(16);

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                sp.getVerticalScrollBar().setValue(0);
            }
        });

//        jPanel.removeAll();
//        jPanel.add(sp);
        return sp;
    }

}
