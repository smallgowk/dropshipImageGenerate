/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view;

/**
 *
 * @author Admin
 */
public class BasePanel extends javax.swing.JPanel {
    public String title;
    public String menuActionCommand;
    
    public void reloadData() {
        
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMenuActionCommand() {
        return menuActionCommand;
    }

    public void setMenuActionCommand(String menuActionCommand) {
        this.menuActionCommand = menuActionCommand;
    }
    
    
}
