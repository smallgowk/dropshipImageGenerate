/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view;

import com.models.aliex.store.inputdata.BaseStoreOrderInfo;
import com.models.trademark.MarkerTradeMarkObj;
import javax.swing.JCheckBox;

/**
 *
 * @author Admin
 */
public class TradeMarkCheckItem {

    String title;
    String description;
    private int rowNumber;
    private Object data;
    private String state;

    public JCheckBox checkBoxSoftRemove;
    public JCheckBox checkBoxHardRemove;

    public TradeMarkCheckItem(String title, String description, JCheckBox checkBoxSoftRemove, JCheckBox checkBoxHardRemove) {
        this.title = title;
        this.description = description;
        this.checkBoxSoftRemove = checkBoxSoftRemove;
        this.checkBoxHardRemove = checkBoxHardRemove;
    }

    public TradeMarkCheckItem(Object obj, JCheckBox checkBoxSoftRemove, JCheckBox checkBoxHardRemove) {
        data = obj;
        if (obj instanceof MarkerTradeMarkObj) {
            MarkerTradeMarkObj markerTradeMarkObj = (MarkerTradeMarkObj) obj;
            this.title = markerTradeMarkObj.getWordmark();
            this.description = markerTradeMarkObj.getDescription();
        } else if(obj instanceof String[]) {
            String[] data = (String[]) obj;
            this.title = data[0];
            this.description = data[1];
        }
        this.checkBoxSoftRemove = checkBoxSoftRemove;
        this.checkBoxHardRemove = checkBoxHardRemove;
    }

    public Object getData() {
        return data;
    }
    
    

    public boolean isCheckedHardRemove() {
        return checkBoxHardRemove != null && checkBoxHardRemove.isSelected();
    }

    public boolean isCheckedSoftRemove() {
        return isCheckedHardRemove() || (checkBoxSoftRemove != null && checkBoxSoftRemove.isSelected());
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setSoftRemove(boolean isCheck) {
        if (checkBoxSoftRemove != null) {
            checkBoxSoftRemove.setSelected(isCheck);
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        
        if(description != null) return description;
        
        if(data != null) {
            if(data instanceof BaseStoreOrderInfo) {
                return ((BaseStoreOrderInfo) data).getLink();
            } else if(data instanceof MarkerTradeMarkObj) {
                return ((MarkerTradeMarkObj) data).getDescription();
            }
            
        }
        return null;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getState() {
        if(data != null) {
            if(data instanceof BaseStoreOrderInfo) {
                return ((BaseStoreOrderInfo) data).getStatus();
            } 
        }
        return state;
    }
    
    public String getTotal() {
//        if(data != null) {
//            if(data instanceof BaseStoreOrderInfo) {
//                return "" + (((BaseStoreOrderInfo) data).getTotal() > 0 ? ((BaseStoreInfo) data).getTotal() : "");
//            } 
//        }
        
        return "";
    }

    public void setState(String state) {
        this.state = state;
    }

    
    
}
