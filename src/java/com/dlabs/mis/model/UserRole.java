/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlabs.mis.model;

/**
 *
 * @author Kamlesh the admin
 */
public class UserRole {
    private int id;
    private String name;
    private int permValue;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPermValue() {
        return permValue;
    }

    public void setPermValue(int permValue) {
        this.permValue = permValue;
    }
    
}
