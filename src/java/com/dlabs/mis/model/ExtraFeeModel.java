/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlabs.mis.model;

/**
 *
 * @author cd
 */
public class ExtraFeeModel {
    
 String comment;		
 int    feeamount;
 String feename;
 String monhtly_fee_id;
 String feetype;
 String student_id; 
 int month;
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getFeeamount() {
        return feeamount;
    }

    public void setFeeamount(int feeamount) {
        this.feeamount = feeamount;
    }

    public String getFeename() {
        return feename;
    }

    public void setFeename(String feename) {
        this.feename = feename;
    }

    public String getMonhtly_fee_id() {
        return monhtly_fee_id;
    }

    public void setMonhtly_fee_id(String monhtly_fee_id) {
        this.monhtly_fee_id = monhtly_fee_id;
    }

    public String getFeetype() {
        return feetype;
    }

    public void setFeetype(String feetype) {
        this.feetype = feetype;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }
    
    
}
