/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.config;

import com.utils.StringUtils;

/**
 *
 * @author PhanDuy
 */
public class SystemInfo {

    public static SystemInfo systemInfo;

    public static SystemInfo getInstance() {
        if (systemInfo == null) {
            systemInfo = new SystemInfo();
        }

        return systemInfo;
    }

    public boolean isValidVersion(String version) {
        
        if(StringUtils.isEmpty(version)) {
            return true;
        }
        try {
            float ver = Float.parseFloat(version);
            if(ver < forceVersionValue) {
                return false;
            } else {
                return true;
            }
        } catch (NumberFormatException ex) {
            return false;
        }
    }
    
    public boolean isLastestVersion(String version) {
        try {
            float ver = Float.parseFloat(version);
            if(ver < lastestVersionValue) {
                return false;
            } else {
                return true;
            }
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public String forceVersion;
    public String lastestVersion;
    public float lastestVersionValue;
    public float forceVersionValue;

    public String getLastestVersion() {
        return lastestVersion;
    }

    public void setLastestVersion(String lastestVersion) {
        this.lastestVersion = lastestVersion;
    }

    public String getForceVersion() {
        return forceVersion;
    }

    public void setForceVersion(String forceVersion) {
        this.forceVersion = forceVersion;
    }

}
