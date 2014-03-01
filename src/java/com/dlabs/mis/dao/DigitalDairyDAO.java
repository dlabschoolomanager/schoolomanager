/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlabs.mis.dao;

import com.dlabs.mis.model.Classes;
import com.dlabs.mis.model.DigitalDairy;
import com.dlabs.mis.model.MonthlyProgress;
import com.kjava.base.ReadableException;
import com.kjava.base.db.DaoUtil;
import com.kjava.base.util.ExtJsonUtil;
import com.kjava.base.util.JSONUtil;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;

/**
 *
 * @author cd
 */
public class DigitalDairyDAO {

    JSONUtil jsonUtil = new ExtJsonUtil();
    
    public DigitalDairy addOrEditDDParent(Connection conn, DigitalDairy obj) throws ReadableException {

        
        String batchid=new GetBatch(obj.getClassid(),obj.getSessionid()).BatchId(conn);
        String pid=null;
        try {
            String query = "";
               query="INSERT INTO studentdairy (pid, 	classid,classteacherid, studentid,subjectid, requesttype, matter, priority, " +
                     "description,createdby ,createdon)" +
                     "VALUES (?,?,?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP)"; 
               pid = java.util.UUID.randomUUID().toString();

           if( DaoUtil.executeUpdate(conn, query, new Object[]{pid,batchid,
                                                               obj.getCreatedby(),
                                                               obj.getStudentid(),
                                                               obj.getSubject(),
                                                               obj.getType(),
                                                               obj.getTitle(),
                                                               obj.getPriority(),
                                                               obj.getDescription(),
                                                               obj.getCreatedby()
                                                              })==1)
           {
               obj.setPid(pid);
               conn.commit();
           }
        } catch (SQLException ex) {
            throw new ReadableException(ex.getCause(),ex.getMessage(),"Digital Dairy DAO", "addoredit");
        }
        return obj;

    }

    public Object getAllDDAsJson(Connection conn, String classid, String studentid,String createdby, String sessionid, int page, int rows) throws ReadableException {
        JSONObject job = null;
        int count =0;
        String countquery="";
        String selectquery="";
        String userid="10006";//Need to change with current userid
        String batchid=new GetBatch(classid,sessionid).BatchId(conn);
        if(studentid.equals(""))
            studentid=null;
        
        if(classid!=null && studentid==null){
        
            selectquery="SELECT sd.pid,sd.classid, c.name AS classname, sd.classteacherid,u.name AS username,sd.studentid,CONCAT(CONCAT(CONCAT(CONCAT(s.fname,' '),s.mname),' '),s.lname) AS studentname," +
                        "       sd.subjectid,m2.value AS subjectname,sd.requesttype,m1.value AS type,sd.priority,m.value AS priorityname," +
                        "       sd.matter,sd.description,sd.createdby , sd.createdon "+
                        "  FROM  studentdairy  sd " +
                        " INNER  JOIN MASTER  m  ON m.id=sd.priority INNER  JOIN MASTER  m1 ON m1.id=sd.requesttype  INNER  JOIN MASTER  m2 ON m2.id=sd.subjectid INNER  JOIN student s  ON s.studentid=sd.studentid  INNER  JOIN sessions ss ON ss.batch_id=sd.classid INNER  JOIN class c ON c.classid=ss.class_id INNER  JOIN users u ON u.userid=sd.createdby " +
                        " WHERE  sd.classid='"+batchid+"'  AND  sd.createdby='"+createdby+"' limit ? offset ?";
            
            countquery ="SELECT count(1) as count " +
                        "  FROM  studentdairy  sd " +
                        " INNER  JOIN MASTER  m  ON m.id=sd.priority INNER  JOIN MASTER  m1 ON m1.id=sd.requesttype  INNER  JOIN MASTER  m2 ON m2.id=sd.subjectid INNER  JOIN student s  ON s.studentid=sd.studentid  INNER  JOIN sessions ss ON ss.batch_id=sd.classid INNER  JOIN class c ON c.classid=ss.class_id INNER  JOIN users u ON u.userid=sd.createdby\n" +
                        " WHERE  sd.classid='"+batchid+"'  AND  sd.createdby='"+createdby+"'";            
        }
        
        if(classid!=null && studentid!=null){
            selectquery="SELECT sd.pid,sd.classid, c.name AS classname, sd.classteacherid,u.name AS username,sd.studentid,CONCAT(CONCAT(CONCAT(CONCAT(s.fname,' '),s.mname),' '),s.lname) AS studentname," +
                        "       sd.subjectid,m2.value AS subjectname,sd.requesttype,m1.value AS type,sd.priority,m.value AS priorityname," +
                        "       sd.matter,sd.description,sd.createdby , sd.createdon "+
                        "  FROM  studentdairy  sd " +
                        " INNER  JOIN MASTER  m  ON m.id=sd.priority INNER  JOIN MASTER  m1 ON m1.id=sd.requesttype  INNER  JOIN MASTER  m2 ON m2.id=sd.subjectid INNER  JOIN student s  ON s.studentid=sd.studentid  INNER  JOIN sessions ss ON ss.batch_id=sd.classid INNER  JOIN class c ON c.classid=ss.class_id INNER  JOIN users u ON u.userid=sd.createdby " +
                        " WHERE  sd.classid='"+batchid+"'  AND  sd.createdby='"+createdby+"' AND  sd.studentid='"+studentid+"' limit ? offset ?";
            
            countquery ="SELECT count(1) as count " +
                        "  FROM  studentdairy  sd " +
                        " INNER  JOIN MASTER  m  ON m.id=sd.priority INNER  JOIN MASTER  m1 ON m1.id=sd.requesttype  INNER  JOIN MASTER  m2 ON m2.id=sd.subjectid INNER  JOIN student s  ON s.studentid=sd.studentid  INNER  JOIN sessions ss ON ss.batch_id=sd.classid INNER  JOIN class c ON c.classid=ss.class_id INNER  JOIN users u ON u.userid=sd.createdby\n" +
                        " WHERE  sd.classid='"+batchid+"'  AND  sd.createdby='"+createdby+"' AND  sd.studentid='"+studentid+"' "; 
        
        }
         try{
            ResultSet rs = DaoUtil.executeQuery(conn,countquery);
            if(rs.next()) {
                count = rs.getInt("count");
            }
            rs = DaoUtil.executeQuery(conn,selectquery,new Object[]{15,0});
            job = jsonUtil.getJsonObject(rs, count, page,rows, false);
        }
        catch (SQLException ex) {
            Logger.getLogger(MasterDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return job;

    }

    public String addSMP(Connection conn, MonthlyProgress obj) throws ReadableException {
        ResultSet rs=null;
        String subjectid=null;
        String insertquery="INSERT INTO studentmonthlyprogress (pid,classid,studentid,monthid,subjectid,createdby, createddate) VALUES(?,?,?,?,?,?,CURRENT_TIMESTAMP)";
        String pid=null;
        int flag=0;
        String createdby="10006";
        String batchid=new GetBatch(obj.getClassid(),obj.getSessionid()).BatchId(conn);
        String selectquery="SELECT subjectid FROM classsubject WHERE classid=?";
        String countquery="SELECT count(1) as count FROM classsubject WHERE classid=?";
        String checkquery="SELECT COUNT(1) as count  FROM studentmonthlyprogress WHERE classid   = ?   AND studentid = ?  AND monthid   = ?";
        try {
        if(obj.getClassid()!=null && (obj.getMonth() >0 && obj.getMonth() <=12) && obj.getSessionid()!=null && obj.getStudentid()!=null)
        {
          
            rs=DaoUtil.executeQuery(conn,checkquery,new Object[]{batchid,obj.getStudentid(),obj.getMonth()});      
            rs.next();
            if(rs.getInt("count")==0) {
            rs= DaoUtil.executeQuery(conn,countquery,new Object[]{batchid});    
            rs.next();
                if(rs.getInt("count") > 0){

                   rs= DaoUtil.executeQuery(conn,selectquery,new Object[]{batchid});    
                   while(rs.next()){
                      subjectid=rs.getString("subjectid");
                      pid = java.util.UUID.randomUUID().toString();
                      if(DaoUtil.executeUpdate(conn,insertquery, new Object[]{pid,batchid,obj.getStudentid(),obj.getMonth(),subjectid,obj.getCreatedby()})==1){
                         flag=1;  
                      }
                   }
                }
                if(flag==1)
                    conn.commit();
                else
                    return "1";
           }
            else{
                return getAllSMPAsJson(conn,obj,15,0).toString();                
            } 
          }        
       }
        catch(SQLException ex) {
            Logger.getLogger(MasterDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";        
    }

    public Object getAllSMPAsJson(Connection conn, MonthlyProgress obj, int page, int rows) throws ReadableException {
                  JSONObject job = null;
        int count =0;
        String batchid=new GetBatch(obj.getClassid(),obj.getSessionid()).BatchId(conn);
        String selectquery="SELECT smp.pid, smp.classid, smp.studentid, smp.sessionid, smp.monthid, " +
                            "       smp.subjectid,smp.progressdescription, m1.value as progressstatus, " +
                            "       smp.suggestion, smp.createddate, smp.createdby , m.value as subjectname" +
                            "  FROM studentmonthlyprogress smp INNER JOIN MASTER m ON m.id=smp.subjectid left outer JOIN MASTER m1 ON m1.id=smp.progressstatus " +                
                            " WHERE smp.classid   = ?   " +
                            "   AND smp.studentid = ?   " +
                            "   AND smp.monthid   = ? and smp.createdby=? limit ? offset ?";
        
        String countquery ="SELECT COUNT(1) as count  FROM studentmonthlyprogress smp WHERE classid=? AND studentid=? AND monthid=? and smp.createdby=?";
        String userid="10006";//Need to change with current userid
        
         try{
            ResultSet rs = DaoUtil.executeQuery(conn,countquery,new Object[]{batchid,obj.getStudentid(),obj.getMonth(),obj.getCreatedby()});
            if(rs.next()) {
                count = rs.getInt("count");
            }
            rs = DaoUtil.executeQuery(conn,selectquery,new Object[]{batchid,obj.getStudentid(),obj.getMonth(),obj.getCreatedby(),15,0});
            job = jsonUtil.getJsonObject(rs, count, page,rows, false);
        }
        catch (SQLException ex) {
            Logger.getLogger(MasterDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return job;

    }

    public int saveSMP(Connection conn, MonthlyProgress obj) throws ReadableException {
        
        String updatequery="UPDATE studentmonthlyprogress SET progressdescription=? , progressstatus=? ,suggestion=? ,modifiedby=? WHERE pid=?";
        String modifiedby="10006";
        int flag=0;
        if(DaoUtil.executeUpdate(conn, updatequery,new Object[]{obj.getProgressdescription(),obj.getStatus(),obj.getSuggestion(),modifiedby,obj.getPid()})==1)
        {
            try {
                conn.commit();
                flag=1;
            } catch (SQLException ex) {
                Logger.getLogger(DigitalDairyDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }          
        return flag;
    }
    
}
