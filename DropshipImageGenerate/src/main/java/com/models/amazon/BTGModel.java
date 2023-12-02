/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models.amazon;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author duyuno
 */
public class BTGModel {

    public String name;
    public String type;
    public String branch;
    public String template;
    public String keyword;
    public int currentLevel;
    public int lastLevel;

    public BTGModel child;
    public List<String> fullTree;

    public BTGModel(String type, String template, List<String> fullCats, List<String> cats) {

        if (fullTree == null) {
            fullTree = fullCats;
        }

        currentLevel = fullCats.size() - cats.size() + 1;
        lastLevel = fullCats.size();
        name = cats.get(0);
        this.type = type;
        this.template = template;

        if (cats.size() > 1) {
            child = new BTGModel(type, template, fullCats, cats.subList(1, cats.size()));
        }
    }

    public BTGModel(String type, String template) {
        this.type = type;
        this.template = template;
    }

    public BTGModel(String root, String type, String template) {
        currentLevel = 1;
        lastLevel = 1;
        fullTree = new ArrayList<>();
        fullTree.add(root);
        name = root;
        this.type = type;
        this.template = template;
    }

    public void addNail(String cat) {
        if (fullTree == null) {
            fullTree = new ArrayList<>();
            fullTree.add(cat);
            name = cat;
            currentLevel = 1;
            lastLevel = 1;
        } else {
            if (isRoot()) {
                fullTree.add(cat);
            }

            if (child != null) {
                child.addNail(cat);
            } else {
                List<String> listSub = new ArrayList<>();
//            listSub.add(name);
                listSub.add(cat);
                child = new BTGModel(type, template, fullTree, listSub);
            }
        }

    }

    public boolean isRoot() {
        return currentLevel == 1;
    }

    public boolean isLast() {
        return currentLevel == lastLevel;
    }

    public BTGModel getLastModel() {
        if (child == null) {
            return this;
        } else {
            return child.getLastModel();
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
    
    

    public void getBTG(StringBuilder sb) {

        if (sb.length() == 0) {
            sb.append(name);
        } else {
            sb.append("-").append(name);
        }

        if (child != null) {
            child.getBTG(sb);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public int getLastLevel() {
        return lastLevel;
    }

    public void setLastLevel(int lastLevel) {
        this.lastLevel = lastLevel;
    }

}
