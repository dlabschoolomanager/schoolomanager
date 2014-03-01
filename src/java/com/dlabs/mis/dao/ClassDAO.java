/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlabs.mis.dao;

import com.dlabs.mis.model.ClassExam;
import com.dlabs.mis.model.ClassSubject;
import com.dlabs.mis.model.ClassSubjectTeacher;
import com.dlabs.mis.model.Classes;
import com.dlabs.mis.model.CreateExamPostData;
import com.kjava.base.ReadableException;

import com.kjava.base.db.DaoUtil;
import com.kjava.base.util.ExtJsonUtil;
import com.kjava.base.util.JSONUtil;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.util.HashSet;
//import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
/**
 *
 * @author Kamlesh the admin
 */
public class ClassDAO {
    
   JSONUtil jsonUtil = new ExtJsonUtil();
    public Classes addOrEditClass(Connection conn, Classes obj) throws ReadableException {
        int flag=0;
        try {            
            String query = "insert into class(schoolid,classid,name,classteacher,feetemplate,comment,sessionid) values(?,?,?,?,?,?,?)";
            String classid = "";
            obj.setSchoolid("1000");
            classid = java.util.UUID.randomUUID().toString();
           if(obj.getBatch_id()==null && obj.getClassid()==null)
           {
               if(DaoUtil.executeUpdate(conn, query, new Object[]{obj.getSchoolid(),classid,obj.getName(),obj.getClassteacher(),obj.getFeetemplate(),obj.getComment(),obj.getSessionid()})==1){
               obj.setClassid(classid);
               flag=1;
               String batchid = java.util.UUID.randomUUID().toString();                
               if(obj.getSessionid()!=null){
                  String sessionquery="INSERT INTO sessions (batch_id,session_id,class_id,class_teacher,template_id,year) VALUES(?,?,?,?,?,?)" ;
                  if(DaoUtil.executeUpdate(conn,sessionquery,new Object[]{batchid,obj.getSessionid(),obj.getClassid(),obj.getClassteacher(),obj.getFeetemplate(),obj.getYear()})==1){
                    // conn.commit();
                  }
               }
                conn.commit();
             }  
           }else
           {
           if(obj.getBatch_id()!=null && obj.getClassid()!=null)
           {
               String updatequery="UPDATE sessions SET class_teacher = ? , template_id = ? , comment =? WHERE	batch_id = ?";
               if(DaoUtil.executeUpdate(conn,updatequery,new Object[]{obj.getClassteacher(),obj.getFeetemplateid(),obj.getComment(),obj.getBatch_id()})==1)
                     conn.commit();
           }   
         }
        }
        catch (SQLException ex) {
            throw new ReadableException(ex.getCause(),ex.getMessage(),"ClassDAO", "addoredit");
        }
        return obj;
    }
   
    
    public Object getAllClassesAsJson(Connection conn,String sessionid ,int page,int rows) throws ReadableException {
        JSONObject job = null;
        String selectquery= " SELECT s.batch_id as batch_id ,s.session_id,s.class_id,c.name,u.name AS teachername," +
                            "       s.template_id AS feetemplateid,t.name AS feetemplate,s.comment, s.year AS sessionname " +
                            "  FROM sessions  s " +
                            "  LEFT JOIN class     c ON s.class_id=c.classid " +
                            "  LEFT JOIN users     u ON s.class_teacher=u.userid " +
                            "  LEFT JOIN templates t ON s.template_id=t.id " +
                            " WHERE s.session_id=? LIMIT ? OFFSET ?";
        int count =0;        
        try{
            ResultSet rs = DaoUtil.executeQuery(conn, "SELECT count(1) as count from sessions where session_id=?",new Object[]{sessionid});
            if(rs.next()) {
                count = rs.getInt("count");
            }
            rs = DaoUtil.executeQuery(conn,selectquery,new Object[]{sessionid,25,0});
            job = jsonUtil.getJsonObject(rs, count, page,rows, false);
        }
        catch (SQLException ex) {
            Logger.getLogger(MasterDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return job;
    }    
    
       public Object getAllClassSubjectAsJson(Connection conn,String classid ,int page,int rows) throws ReadableException {
        JSONObject job = null;
        int count =0;
        String schoolid="1000";
        String countquery=  "SELECT count(1) as count FROM (SELECT csobj.classid ,csobj.subjectid , csobj.comment ,m.id ,m.value  FROM (SELECT * FROM MASTER WHERE propertyid=2) m LEFT OUTER JOIN (SELECT * 	  FROM classsubject cs 	 WHERE cs.classid=?) csobj  ON m.id=csobj.subjectid ) dat  ORDER BY dat.classid DESC ";
      
        String selectquery="SELECT dat.classid AS classid , dat.id ,dat.comment ,dat.value value ,CASE  WHEN dat.classid IS NULL THEN NULL ELSE TRUE END AS added  FROM (SELECT csobj.classid ,csobj.subjectid , csobj.comment ,m.id ,m.value  FROM (SELECT * FROM MASTER WHERE propertyid=2) m LEFT OUTER JOIN (SELECT * 	  FROM classsubject cs 	 WHERE cs.classid=?) csobj  ON m.id=csobj.subjectid ) dat  ORDER BY dat.classid DESC";
        try{
            ResultSet rs = DaoUtil.executeQuery(conn,countquery,new Object[]{classid});
            
            if(rs.next()) {
                count = rs.getInt("count");
            }
            rs = DaoUtil.executeQuery(conn,selectquery,new Object[]{classid});
            job = jsonUtil.getJsonObject(rs, count, page,rows, false);
        }
        catch (SQLException ex) {
            Logger.getLogger(MasterDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return job;
    }    

    public void addOrEditClassSubject(Connection conn, ClassSubject[] obj) throws ReadableException {
            for (int i = 0; i < obj.length; i++) {
            if(obj[i].getAdded()==0){
            String query = "UPDATE classsubject SET subjectid=? WHERE classid=?";
            DaoUtil.executeUpdate(conn, query, new Object[]{obj[i].getId(),obj[i].getClassid()});
            }
            else{
               if(obj[i].getAdded()==1){
                   String query = "INSERT INTO classsubject(classid,subjectid,COMMENT) VALUES(?,?,?)";
                   DaoUtil.executeUpdate(conn, query, new Object[]{obj[i].getClassid(),obj[i].getId(),obj[i].getComment()});
            }
                
            }
            }
        }

    public Object getAllClassExamAsJson(Connection conn,CreateExamPostData obj , int page, int rows) throws ReadableException {
        JSONObject job = null;
        int count =0;

        String schoolid="1000";
        String countquery=  "SELECT COUNT(1) as count FROM  classexam ce  RIGHT OUTER JOIN classsubject cs ON cs.subjectid=ce.subjectid  LEFT  OUTER JOIN class        c  ON c.classid   =ce.classid  LEFT  OUTER JOIN MASTER        m  ON m.id        =ce.sessionid LEFT  OUTER JOIN MASTER        m1 ON m1.id       =ce.examtypeid LEFT  OUTER JOIN MASTER        m2 ON m2.id       =ce.subjectid WHERE ce.classid=?";      
        String selectquery="SELECT  ce.id,ce.classid,ce.examtypeid,ce.examname,	ce.sessionid, 	ce.subjectid, 	ce.maxmark, 	ce.passmark, 	ce.examdate, 	ce.teacherid, 	ce.starttime, 	ce.endtime,	m.value  AS sessionname,	m1.value AS examtype,	m2.value AS subjectname ,c.name as classname FROM  classexam ce  RIGHT OUTER JOIN classsubject cs ON cs.subjectid=ce.subjectid  LEFT  OUTER JOIN class        c  ON c.classid   =ce.classid  LEFT  OUTER JOIN MASTER        m  ON m.id        =ce.sessionid LEFT  OUTER JOIN MASTER        m1 ON m1.id       =ce.examtypeid LEFT  OUTER JOIN MASTER        m2 ON m2.id       =ce.subjectid WHERE ce.classid=?";
        String batch_id=new GetBatch(obj.getClassid(),obj.getSessionid()).BatchId(conn);
        if(obj.getClass()!=null && obj.getExamtypeidid()!=null && obj.getSessionid()!=null)            
           selectquery="SELECT   ce.id AS pid ,ce.classid AS classid , ce.examtypeid AS examtypeid ,ce.examname AS examname," +
"                                    ce.sessionid AS sessionid , 	ce.subjectid AS subjectid , 	ce.maxmark AS maxmark, " +
"                                    ce.passmark AS passmark ,FROM_UNIXTIME(ce.examdate/1000,'%d-%m-%Y')  AS examdate, 	ce.teacherid AS teacherid, " +
"                                    FROM_UNIXTIME(ce.starttime/1000,'%h-%i-%s') AS starttime,FROM_UNIXTIME(ce.endtime/1000,'%h-%i-%s') AS endtime,	m.value  AS sessionname," +
"                                    m1.value AS examtype,	m2.value AS subjectname," +
"                                    c.name AS classname   " +
"                              FROM  classexam ce " +
"                              LEFT JOIN        sessions      s  ON s.batch_id    =ce.batch_id " +
"                              LEFT  OUTER JOIN  class        c  ON c.classid     =s.class_id " +
"                              LEFT  OUTER JOIN MASTER        m  ON m.id          =s.session_id " +
"                              LEFT  OUTER JOIN MASTER        m1 ON m1.id         =ce.examtypeid " +
"                              LEFT  OUTER JOIN MASTER        m2 ON m2.id         =ce.subjectid " +
"                              LEFT  OUTER JOIN users         u  ON u.userid      =ce.teacherid AND u.roleid=3 " +
"			WHERE  ce.batch_id= ?" +
"				AND  ce.examtypeid=?";
            
        try{
            ResultSet rs = DaoUtil.executeQuery(conn,countquery,new Object[]{obj.getClass()});
            
            if(rs.next()) {
                count = rs.getInt("count");
            }
            if(batch_id!=null && obj.getExamtypeidid()!=null)                        
            rs = DaoUtil.executeQuery(conn,selectquery,new Object[]{batch_id,obj.getExamtypeidid()});
            
            job = jsonUtil.getJsonObject(rs, count, page,rows, false);
        }
        catch (SQLException ex) {
            Logger.getLogger(MasterDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return job;
    }

    
    public int addClassExam(Connection conn, CreateExamPostData obj) throws ReadableException {
        
        String countquery="SELECT count(1) as count FROM classexam WHERE batch_id =? AND examtypeid=?";
        String insertquery="INSERT INTO classexam (id,batch_id,examtypeid,subjectid,createby,modifiedby,createdon,modifiedon) VALUES(?,?,?,?,?,?,CURRENT_DATE,CURRENT_DATE)";
        String createby  ="1";
        String modifiedby="1";
        String response="";
        int count=-1;
        String batch_id=new GetBatch(obj.getClassid(),obj.getSessionid()).BatchId(conn);
        try{
            ResultSet rs = DaoUtil.executeQuery(conn,countquery,new Object[]{batch_id,obj.getExamtypeidid()});
            
            if(rs.next()) {
                count = rs.getInt("count");
            }
            if(count > 0 ){
               response="Exam Already created for this Class"; 
               return 1;
            }
            else{
               ResultSet rs1 = DaoUtil.executeQuery(conn,"SELECT subjectid FROM classsubject WHERE classid=?",new Object[]{batch_id});                                     
               int flag=0;
               while(rs1.next()){                   
                 String id = java.util.UUID.randomUUID().toString();  
                 String subjectid="";
                 if(rs1.getObject("subjectid")!=null)
                     subjectid=rs1.getString("subjectid");
                     DaoUtil.executeUpdate(conn,insertquery, new Object[]{id,batch_id,obj.getExamtypeidid(),subjectid,createby,modifiedby});                 
                     flag=1;
               }
             if(flag==0)  
             return 2;
            }                
        }
        catch (SQLException ex) {
            Logger.getLogger(MasterDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public Object getAllCreatedClassExamAsJson(Connection conn, CreateExamPostData obj, int page, int rows) throws ReadableException {
        JSONObject job = null;
        int count =0;

        String schoolid="1000";
        String batch_id=new GetBatch(obj.getClassid(),obj.getSessionid()).BatchId(conn);
        
        String selectquery="SELECT   ce.id AS pid ,ce.classid AS classid , ce.examtypeid AS examtypeid ,ce.examname AS examname," +
                            "            ce.sessionid AS sessionid , 	ce.subjectid AS subjectid , 	ce.maxmark AS maxmark, " +
                            "            ce.passmark AS passmark , 	FROM_UNIXTIME(ce.examdate/1000,'%d-%m-%Y') AS examdate, 	ce.teacherid AS teacherid, " +
                            "            FROM_UNIXTIME(ce.starttime/1000,'%h-%i-%s')  AS starttime,FROM_UNIXTIME(ce.endtime/1000,'%h-%i-%s') 	 AS endtime,	m.value  AS sessionname," +
                            "            m1.value AS examtype,	m2.value AS subjectname,c.name AS classname              " +
                            "      FROM classexam ce " +
                            "      LEFT JOIN sessions      s  ON s.batch_id    =ce.batch_id " +
                            "      LEFT JOIN class         c  ON c.classid     =s.class_id       " +
                            "      LEFT JOIN MASTER        m  ON m.id          =s.session_id " +
                            "      LEFT JOIN MASTER        m1 ON m1.id         =ce.examtypeid " +
                            "      LEFT JOIN MASTER        m2 ON m2.id         =ce.subjectid  " +
                            "      LEFT JOIN users         u  ON u.userid      =ce.teacherid AND u.roleid=3 " +
                            "     WHERE  ce.batch_id= ?" +
                            "       AND  ce.examtypeid=?";
        
        
            ResultSet rs =DaoUtil.executeQuery(conn,selectquery,new Object[]{batch_id,obj.getExamtypeidid()});
            job = jsonUtil.getJsonObject(rs, count, page,rows, false);
        
        return job;
    }
    
    public int saveClassExam(Connection conn, ClassExam[] obj) throws ReadableException {
        
        String updatequery="UPDATE classexam SET examname=? , maxmark  = ?, passmark = ?, examdate = ?, teacherid= ?, starttime= ?, endtime  = ?, modifiedby=? WHERE id = ?";
        int i=0;
        for(i=0;i<obj.length;i++){
          DaoUtil.executeUpdate(conn,
                               updatequery,
                               new Object[]{
                                   obj[i].getExamname(),
                                   obj[i].getMaxmark(),
                                   obj[i].getPassmark(),
                                   obj[i].getExamdate(),
                                   obj[i].getTeacherid(),
                                   obj[i].getStarttime(),
                                   obj[i].getEndtime(),
                                   "100000"  ,
                                   obj[i].getPid()
          });  
          
        }
        if(i > 0)
            return 1;
        else
        return 0;     
    }

    public Object getAllClassSubjectTeacherAsJson(Connection conn, String classid, int page, int rows) throws ReadableException {
          
        JSONObject job = null;
        int count =0;
        String schoolid="1000";
        String countquery=  "SELECT COUNT(1) as count FROM MASTER m, ( " +
                            "       SELECT cs.classid AS batchid,cs.subjectid," +
                            "              cst.teacherid,u.name AS teachername,cst.comment " +
                            "	 FROM classsubjectteacher cst INNER  JOIN  MASTER m ON m.id=cst.subjectid" +
                            "	INNER JOIN  users u ON u.userid=cst.teacherid AND u.roleid=3 " +
                            "	RIGHT OUTER JOIN classsubject cs ON  cs.classid=cst.batchid  AND m.id=cs.subjectid " +
                            "	WHERE cs.classid=?) tab " +
                            "    WHERE m.id=tab.subjectid";
      
        String selectquery= "SELECT tab.pid,tab.batchid AS batchid,tab.subjectid AS subjectid,m.value AS subjectname,tab.teacherid AS teacherid,tab.teachername AS teachername,tab.comment AS comment FROM MASTER m, (" +
                            "       SELECT cst.pid AS pid,cs.classid AS batchid,cs.subjectid," +
                            "              cst.teacherid,u.name AS teachername,cst.comment " +
                            "	 FROM classsubjectteacher cst INNER  JOIN  MASTER m ON m.id=cst.subjectid " +
                            "	INNER JOIN  users u ON u.userid=cst.teacherid AND u.roleid=3 " +
                            "	RIGHT OUTER JOIN classsubject cs ON  cs.classid=cst.batchid  AND m.id=cs.subjectid " +
                            "	WHERE cs.classid=?) tab" +
                            "    WHERE m.id=tab.subjectid ";

        
        try{
            ResultSet rs = DaoUtil.executeQuery(conn,countquery,new Object[]{classid});
            
            if(rs.next()) {
                count = rs.getInt("count");
            }
            rs = DaoUtil.executeQuery(conn,selectquery,new Object[]{classid});
            job = jsonUtil.getJsonObject(rs, count, page,rows, false);
        }
        catch (SQLException ex) {
            Logger.getLogger(MasterDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return job;
    }
    
    public int addClassSubjectTeacher(Connection conn, ClassSubjectTeacher[] obj) throws ReadableException {
            int flag=0;  
            for (int i = 0; i < obj.length; i++) {
              
            if(obj[i].getPid().equals(""))
                   obj[i].setPid(null);
            if(obj[i].getPid()!=null){
            String query = "UPDATE classsubjectteacher SET teacherid  =  ? ,modifiedby =  ? ,modifiedon =  CURRENT_DATE,COMMENT    =  ?	WHERE	pid = ?";
            DaoUtil.executeUpdate(conn, query, new Object[]{obj[i].getTeacherid(),obj[i].getModifiedby(),obj[i].getComment(),obj[i].getPid()});
            flag++;            
            }
            else{
               if(obj[i].getPid()==null){
                   String query = "INSERT INTO classsubjectteacher (pid,batchid,subjectid,teacherid,createdby,modifiedby,modifiedon,COMMENT)VALUES (?,?,?,?,?,?,CURRENT_DATE,?)";
                   String pid = java.util.UUID.randomUUID().toString();  
                   DaoUtil.executeUpdate(conn, query, new Object[]{pid,obj[i].getBatchid(),obj[i].getSubjectid(),obj[i].getTeacherid(),obj[i].getCreatedby(),obj[i].getModifiedby(),obj[i].getComment()});
                       obj[i].setPid(pid);
                       flag++;
            }
                
            }
            }

        return flag;    
    }

    public int deleteClass(Connection conn, Classes clas) throws ReadableException {

        if(clas.getBatch_id()!=null){
        
           String checkquery="Select batch_id from sessions where batch_id=?"; 
           String deletequery="delete from sessions where batch_id=?";            
           ResultSet rs;
          
           
            try {
                rs = DaoUtil.executeQuery(conn,checkquery,new Object[]{clas.getBatch_id()});
                
                if(rs.next()){
                    if(DaoUtil.executeUpdate(conn,deletequery,new Object[]{clas.getBatch_id()})==1)
                        return 1;
                }
                else
                {
                    return 0;
                }    
            } catch (SQLException ex) {
                Logger.getLogger(ClassDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
       return 0;  
    }


   
}


 
