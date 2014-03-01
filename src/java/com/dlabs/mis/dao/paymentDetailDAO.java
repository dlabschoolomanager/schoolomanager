/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dlabs.mis.dao;


import com.dlabs.mis.model.GenerateFee;
import com.dlabs.mis.model.GenerateFeePostData;
import com.dlabs.mis.model.PaymentFeeStructure;
import com.dlabs.mis.model.Timetable;
import com.kjava.base.ReadableException;
import com.kjava.base.db.DaoUtil;
import com.kjava.base.util.ExtJsonUtil;
import com.kjava.base.util.JSONUtil;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;



public class paymentDetailDAO {

      JSONUtil jsonUtil = new ExtJsonUtil();
     
        public Object getAllPaymentDetailsAsJson(Connection conn, int page, int rows) throws ReadableException {

        JSONObject job = new JSONObject();        
        Collection<JSONObject> items = new ArrayList<JSONObject>();
        ResultSet rs = null;
        int count =0;
        int x=0,i=0;
        String schoolid="1000";
        String studentname= null;;
        String classname= null;;
        String duedate= null;;
        int amount=0;
        int totamount=0;
        String fineamount= null;;
        String frommonth= null;;
        String tomonth= null;;
        

        try{
            //rs = DaoUtil.executeQuery(conn, "SELECT count(1) as count from timetable where schoolid=? and classid=?");
            rs = DaoUtil.executeQuery(conn, "SELECT count(1) as count from timetable");
            if(rs.next()) {
                count = rs.getInt("count");

            }
            rs = DaoUtil.executeQuery(conn,"SELECT S.name NAME, C.name ClASS, T.name Template ,"
                                           +"FS.fee_structure_id id , FS.fee_name feename, FS.fee_amount amount "
                                           + "FROM student S ,class C , "
                                           + "     templates T ,feestructure FS , "
                                           + "     template_structure_mapping M "
                                           + "WHERE S.classid=C.classid AND C.feetemplate=T.id "
                                           + "AND T.id=M.template_id AND FS.fee_structure_id=M.fee_structure_id "
                                           + "AND S.studentid ='208aac44-92cb-4296-b61e-cbd09d27e4fb'");

            while(rs.next()){

               studentname=rs.getString("NAME");
               classname  =rs.getString("CLASS");               
               JSONObject obj =new JSONObject();
               obj.put("fee_structure_id",rs.getString("id"));
               obj.put("fee_name",rs.getString("feename"));
               amount=rs.getInt("amount");
               obj.put("fee_amount",amount);
               totamount=totamount+amount;
               items.add(obj);
               x++;
            }

            job.put("studentname",studentname);
            job.put("classname",classname);
            job.put("duedate","2013-05-31");
            job.put("totamount",totamount);
            job.put("fineamount",0);
            job.put("frommonth","May");
            job.put("tomonth","May");
            job.put("feestructure",items);
        }
        catch (SQLException ex) {
            Logger.getLogger(MasterDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return job;
    }

  

    public Object generateFee(Connection conn, String sessionid, String classid, int month , long duedate) throws ReadableException, SQLException {

        JSONObject job = new JSONObject();        
        Collection<JSONObject> items = new ArrayList<JSONObject>();
        ResultSet rs = null;
        ResultSet rsfee = null;
        ResultSet rsstud = null;
        ResultSet fsrs=null;
        String createdby="ADMIN";//Need to set createdby 
        int amount=0;
        String templateid="";
        String feeid="";
        String class_id="";
        String schoolid="";
        int flag=0;
        //if user has selected session , 
        //and month then generate fee for all class
        if(sessionid!=null && month!=0 && classid==null){
         
         rs= DaoUtil.executeQuery(conn, "SELECT batch_id ,template_id as feetemplate FROM sessions WHERE session_id=?",new Object[]{sessionid});               
    
         while(rs.next()){

            if(rs.getObject("batch_id")!=null)
              class_id=rs.getString("batch_id");//get Batchid
            if(rs.getObject("feetemplate")!=null)
              templateid=rs.getString("feetemplate");
           
           String query="SELECT SUM(F.fee_amount) amount "
                      + " FROM feestructure F, templates T ,template_Structure_mapping TM "
                      + " WHERE F.fee_structure_id=TM.fee_structure_id    "
                      + " AND TM.template_id=T.id   "
                      + " AND T.id=?";   
           
           rsfee = DaoUtil.executeQuery(conn,query,new Object[]{templateid});               
           if(rsfee.next()){
           if(rsfee.getObject("amount")!=null)
               amount=rsfee.getInt("amount");
           }
           
           String studquery="SELECT student_id as studentid FROM student_class_map scm WHERE scm.batch_id=?";
           rsstud=DaoUtil.executeQuery(conn,studquery,new Object[]{class_id});               
           String studid="";
           while(rsstud.next()){
               
            if(rsstud.getObject("studentid")!=null)
             studid=rsstud.getString("studentid");
               
               String insetquery="INSERT INTO generatemonthlyfee "
                               + " (monthly_fee_id,class_id,student_id,for_month,"
                               + " for_year,amount,templateid,paid_status,createdby,due_date)	"
                               + " VALUES(?,?,?,?,?,?,?,?,?,?)";
               feeid= java.util.UUID.randomUUID().toString();
                               
               if(DaoUtil.executeUpdate(conn, insetquery, new Object[]{feeid,class_id,studid,month,sessionid,amount,templateid,0,createdby,duedate})==1){
                  flag=1;                                    

                      String feestructure="SELECT f.fee_structure_id , f.fee_amount FROM feestructure f , template_structure_mapping t WHERE t.template_id=?   AND f.fee_structure_id =t.fee_structure_id";
                      fsrs =DaoUtil.executeQuery(conn,feestructure,new Object[]{templateid});               

                      String insertquery="INSERT INTO monthlyfeedetails (monthly_fee_id,student_id ,fee_structure_id, amount) VALUES(?,?,?,?)";

                      while(fsrs.next()){
                      
                          String feestru=null;
                          int    amount1=0;
                          
                          if(fsrs.getObject("fee_structure_id")!=null)
                              feestru=fsrs.getString("fee_structure_id");
                          if(fsrs.getObject("fee_amount")!=null)
                              amount1=fsrs.getInt("fee_amount");
                                                    
                          DaoUtil.executeUpdate(conn, insertquery, new Object[]{feeid,studid,feestru,amount1});                                                  
                      }
               }
           } 
                      
         }
         
        }
        else//if user has selected session , class and month all details then generate fee for that selected class
        {
         if(sessionid!=null && month!=0 && classid!=null)
         {
           rs= DaoUtil.executeQuery(conn, "SELECT batch_id ,template_id as feetemplate FROM sessions WHERE session_id=? and class_id=?",new Object[]{sessionid,classid});               
    
           while(rs.next()){

            if(rs.getObject("batch_id")!=null)
              class_id=rs.getString("batch_id");
            if(rs.getObject("feetemplate")!=null)
              templateid=rs.getString("feetemplate");
           
           String query="SELECT SUM(F.fee_amount) amount "
                      + " FROM feestructure F, templates T ,template_Structure_mapping TM "
                      + " WHERE F.fee_structure_id=TM.fee_structure_id    "
                      + " AND TM.template_id=T.id   "
                      + " AND T.id=?";   
           
           rsfee = DaoUtil.executeQuery(conn,query,new Object[]{templateid});               
           if(rsfee.next()){
           if(rsfee.getObject("amount")!=null)
               amount=rsfee.getInt("amount");
           }
           
           String studquery="SELECT student_id as studentid FROM student_class_map scm WHERE scm.batch_id=?";
           rsstud=DaoUtil.executeQuery(conn,studquery,new Object[]{class_id});               
           String studid="";
           while(rsstud.next()){
               
            if(rsstud.getObject("studentid")!=null)
             studid=rsstud.getString("studentid");
               
               String insetquery="INSERT INTO generatemonthlyfee "
                               + " (monthly_fee_id,class_id,student_id,for_month,"
                               + " for_year,amount,templateid,paid_status,createdby,due_date)	"
                               + " VALUES(?,?,?,?,?,?,?,?,?,?)";
               feeid= java.util.UUID.randomUUID().toString();
                               
               if(DaoUtil.executeUpdate(conn, insetquery, new Object[]{feeid,class_id,studid,month,sessionid,amount,templateid,0,createdby,duedate})==1){
                  flag=1;                                    

                      String feestructure="SELECT f.fee_structure_id , f.fee_amount FROM feestructure f , template_structure_mapping t WHERE t.template_id=?   AND f.fee_structure_id =t.fee_structure_id";
                      fsrs =DaoUtil.executeQuery(conn,feestructure,new Object[]{templateid});               

                      String insertquery="INSERT INTO monthlyfeedetails (monthly_fee_id,student_id ,fee_structure_id, amount) VALUES(?,?,?,?)";

                      while(fsrs.next()){
                      
                          String feestru=null;
                          int    amount1=0;
                          
                          if(fsrs.getObject("fee_structure_id")!=null)
                              feestru=fsrs.getString("fee_structure_id");
                          if(fsrs.getObject("fee_amount")!=null)
                              amount1=fsrs.getInt("fee_amount");
                                                    
                          DaoUtil.executeUpdate(conn, insertquery, new Object[]{feeid,studid,feestru,amount1});                                                  
                      }
               }
           } 
                      
         }

       }
     }
        if(flag==1)
            conn.commit();
            return null;
        
    }

    public Object getAllAsJson1(Connection conn,GenerateFeePostData obj,int page, int rows) throws ReadableException {
        JSONObject job = null;
        ResultSet rs = null;
        int count =0;
        String batchid="";
        
        batchid=new GetBatch(obj.getClassid(),obj.getSessionid()).BatchId(conn);
        
        String countQuery="SELECT COUNT(1) AS count " +
                        "  FROM class c, templates t , student s ,generatemonthlyfee g ,sessions ss " +
                        " WHERE ss.batch_id=g.class_id " +
                        "   AND ss.class_id = c.classid  " +
                        "   AND ss.template_id = t.id   " +
                        "   AND g.student_id = s.studentid " +
                        "   AND g.class_id   = ? " +
                        "   AND g.for_month  = ? ";
        
        String dataquery="SELECT g.monthly_fee_id,g.school_id,g.class_id,c.name classname,g.student_id,CONCAT(CONCAT(CONCAT(CONCAT(s.fname,' '),case when s.mname is null then '' else s.mname end),' '),s.lname) AS studentname, " +
                        "       CASE g.for_month WHEN 1 THEN 'January' WHEN 2 THEN 'Febrary' WHEN 3 THEN 'March' WHEN 4 THEN 'April' WHEN 5 THEN 'May' WHEN 6 THEN 'June' WHEN 7 THEN 'July' WHEN 8 THEN 'August' WHEN 9 THEN 'September' WHEN 10 THEN 'October' WHEN 11 THEN 'November' WHEN 12 THEN 'December' END AS for_month," +
                        "       g.for_year,g.amount,FROM_UNIXTIME(g.due_date/1000,'%d-%m-%Y') as due_date,g.templateid,t.name AS template,CASE g.paid_status WHEN 0 THEN NULL WHEN 1 THEN TRUE END AS markpaid,CASE g.paid_status WHEN 0 THEN 'Payment Pending' WHEN 1 THEN 'Payment Received' END AS paid_status,g.paid_on,g.paid_by,g.paid_amount,g.comment,g.for_month as month " +
                        "  FROM class c, templates t , student s ,generatemonthlyfee g ,sessions ss " +
                        " WHERE ss.batch_id=g.class_id " +
                        "   AND ss.class_id = c.classid  " +
                        "   AND ss.template_id = t.id   " +
                        "   AND g.student_id = s.studentid " +
                        "   AND g.class_id   = ? " +
                        "   AND g.for_month  = ? " +
                        "   LIMIT ? OFFSET ?";

        try{
            rs = DaoUtil.executeQuery(conn, countQuery,new Object[]{batchid,obj.getMonthid()});
            if(rs.next()) {
                count = rs.getInt("count");

            }
            rs = DaoUtil.executeQuery(conn,dataquery,new Object[]{batchid,obj.getMonthid(),15,0});
            job = jsonUtil.getJsonObject(rs, count, page,rows, false);
        }
        catch (SQLException ex) {
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return job;
    }

    public Object getGeneratedFeeList(Connection conn,GenerateFeePostData obj,int page, int rows) throws ReadableException {
        JSONObject job = null;
        ResultSet rs = null;
        int count =0;
        int flag=0;
        String schoolid="1000";
        String countquery="";
        String dataquery="";
        String classid="";
        String sessionid="";
        String monthid  ="";
        String batchid="";
        if(obj.getClassid().equals(""))classid=null;
        if(obj.getSessionid().equals(""))sessionid=null;        

        if(obj.getSessionid()!=null && obj.getClassid()!=null)
           batchid=new GetBatch(obj.getClassid(),obj.getSessionid()).BatchId(conn);
        
        if(flag==0 && classid==null && sessionid==null && obj.getMonthid()==-1 ){
        
            countquery="SELECT COUNT(1) as count FROM generatemonthlyfee g " +
                                            "       INNER JOIN class   c ON   c.classid=g.class_id " +
                                            "       INNER JOIN templates t ON t.id     =g.templateid " +
                                            " ORDER BY for_month DESC";
            
            dataquery ="SELECT g.class_id,m.value as for_year,FROM_UNIXTIME(g.due_date/1000,'%d-%m-%Y') as  due_date ,g.templateid ,SUM(g.amount) AS amount,sum(paid_amount) AS tot_received " +
                                            "      ,g.createdby , g.createdon,c.name AS classname , t.name AS template , " +
                                            "      CASE g.for_month WHEN 1 THEN 'January' WHEN 2 THEN 'Febrary' WHEN 3 THEN 'March' WHEN 4 THEN 'April' WHEN 5 THEN 'May' WHEN 6 THEN 'June' WHEN 7 THEN 'July' WHEN 8 THEN 'August' WHEN 9 THEN 'September' WHEN 10 THEN 'October' WHEN 11 THEN 'November' WHEN 12 THEN 'December' END AS for_month " +
                                            "  FROM generatemonthlyfee g " +
                                            "       INNER JOIN class   c ON   c.classid=g.class_id " +
                                            "       INNER JOIN templates t ON t.id     =g.templateid " +
                                            "       INNER JOIN MASTER m  ON m.id=g.for_year  "+
                                            " GROUP BY class_id ,for_month " +
                                            " ORDER BY class_id limit ? offset ?";
            flag=1;
        }
        if(flag==0 &&  classid==null && obj.getMonthid()==0 && sessionid!=null){        
           //list out current year generated fees            
            countquery="SELECT COUNT(1) as count " +
                        "  FROM generatemonthlyfee g " +
                        "       INNER JOIN sessions s ON s.batch_id=g.class_id " +
                        "       INNER JOIN class   c ON   c.classid=s.class_id " +
                        "       INNER JOIN templates t ON t.id     =s.template_id " +
                        " WHERE g.for_year= '" +obj.getSessionid() +"' " +
                        " GROUP BY g.class_id,for_month " +
                        " ORDER BY for_month DESC";
            dataquery ="SELECT g.class_id,g.for_year,FROM_UNIXTIME(g.due_date/1000,'%d-%m-%Y') as  due_date ,g.templateid ,SUM(g.amount) AS amount,sum(paid_amount) AS tot_received " +
                        "      ,g.createdby , g.createdon,c.name AS classname , t.name AS template ," +
                        "      CASE g.for_month WHEN 1 THEN 'January' WHEN 2 THEN 'Febrary' WHEN 3 THEN 'March' WHEN 4 THEN 'April' WHEN 5 THEN 'May' WHEN 6 THEN 'June' WHEN 7 THEN 'July' WHEN 8 THEN 'August' WHEN 9 THEN 'September' WHEN 10 THEN 'October' WHEN 11 THEN 'November' WHEN 12 THEN 'December' END AS for_month" +
                        "    FROM generatemonthlyfee g " +
                        "       INNER JOIN sessions s ON s.batch_id=g.class_id " +
                        "       INNER JOIN class   c ON   c.classid=s.class_id " +
                        "       INNER JOIN templates t ON t.id     =s.template_id " +
                        " WHERE g.for_year= '" +obj.getSessionid() +"' " +
                        " GROUP BY g.class_id,for_month " +
                        " ORDER BY for_month DESC limit ? offset ?";
            flag=1;
        }
        if(flag==0 && classid!=null && (obj.getMonthid()==0 || obj.getMonthid()==13) && sessionid!=null){        
           
            countquery="SELECT COUNT(1) AS count FROM (SELECT COUNT(1)" +
                        "  FROM generatemonthlyfee g " +
                        "       INNER JOIN sessions  s ON   s.batch_id=g.class_id " +
                        "       INNER JOIN class     c ON   c.classid =s.class_id " +
                        "       INNER JOIN templates t ON   t.id      =s.template_id " +
                        "       INNER JOIN MASTER    m ON   m.id      =g.for_year     " +
                        " WHERE g.for_year ='" + obj.getSessionid()  +"'" +
                        "   AND g.class_id ='" + batchid +"' " +
                        " GROUP BY for_month" +
                        ")  tab";
        
            dataquery ="SELECT g.class_id,m.value AS for_year,FROM_UNIXTIME(g.due_date/1000,'%d-%m-%Y') as  due_date ,g.templateid ,SUM(g.amount) AS amount,SUM(g.paid_amount) AS tot_received " +
                        "      ,g.createdby , g.createdon,c.name AS classname , t.name AS template ," +
                        "      CASE g.for_month WHEN 1 THEN 'January' WHEN 2 THEN 'Febrary' WHEN 3 THEN 'March' WHEN 4 THEN 'April' WHEN 5 THEN 'May' WHEN 6 THEN 'June' WHEN 7 THEN 'July' WHEN 8 THEN 'August' WHEN 9 THEN 'September' WHEN 10 THEN 'October' WHEN 11 THEN 'November' WHEN 12 THEN 'December' END AS for_month" +
                        " FROM generatemonthlyfee g " +
                        "       INNER JOIN sessions  s ON   s.batch_id=g.class_id " +
                        "       INNER JOIN class     c ON   c.classid =s.class_id " +
                        "       INNER JOIN templates t ON   t.id      =s.template_id " +
                        "       INNER JOIN MASTER    m ON   m.id      =g.for_year     " +
                        " WHERE g.for_year ='" + obj.getSessionid()  +"' " +
                        "   AND g.class_id ='" + batchid +"' " +
                        " GROUP BY for_month  limit ? offset ?";
            flag=1;
        }
        if(flag==0 && classid==null && (obj.getMonthid()!=0 || obj.getMonthid()!=13 || obj.getMonthid()!=-1) && sessionid!=null){
        
            countquery=" SELECT COUNT(1) AS count FROM (SELECT COUNT(1)  " +
                        "  FROM generatemonthlyfee g " +
                        "       INNER JOIN sessions     s   ON s.batch_id=g.class_id " +
                        "       INNER JOIN class   c   ON c.classid =s.class_id  " +
                        "       INNER JOIN templates t ON t.id      =s.template_id " +
                        "       INNER JOIN MASTER   m  ON m.id      =g.for_year    " +
                        " WHERE g.for_year = '" + obj.getSessionid()  +"' " +
                        "   AND g.for_month= '" + obj.getMonthid()    +"' " +
                        " GROUP BY g.class_id " +
                        ") tab";
            dataquery ="SELECT g.class_id,m.value AS for_year,FROM_UNIXTIME(g.due_date/1000,'%d-%m-%Y') as  due_date ,g.templateid ,SUM(g.amount) AS amount,sum(paid_amount) AS tot_received " +
                        "      ,g.createdby , g.createdon,c.name AS classname , t.name AS template ," +
                        "      CASE g.for_month WHEN 1 THEN 'January' WHEN 2 THEN 'Febrary' WHEN 3 THEN 'March' WHEN 4 THEN 'April' WHEN 5 THEN 'May' WHEN 6 THEN 'June' WHEN 7 THEN 'July' WHEN 8 THEN 'August' WHEN 9 THEN 'September' WHEN 10 THEN 'October' WHEN 11 THEN 'November' WHEN 12 THEN 'December' END AS for_month" +
                        "    FROM generatemonthlyfee g  " +
                        "       INNER JOIN sessions     s   ON s.batch_id=g.class_id " +
                        "       INNER JOIN class   c   ON c.classid =s.class_id " +
                        "       INNER JOIN templates t ON t.id      =s.template_id " +
                        "       INNER JOIN MASTER   m  ON m.id      =g.for_year     " +
                        " WHERE g.for_year ='" + obj.getSessionid()  +"'" +
                        "   AND g.for_month='" + obj.getMonthid()    +"'" +
                        " GROUP BY g.class_id  limit ? offset ?";
           flag=1; 
        }        
        if(flag==0 &&  classid!=null && (obj.getMonthid()>=1 && obj.getMonthid()<=12) && sessionid!=null){
        
            countquery="SELECT COUNT(1) AS count " +
                        "  FROM (SELECT COUNT(1)   FROM generatemonthlyfee g " +
                        "  INNER JOIN sessions s  ON s.batch_id=g.class_id " +
                        "  INNER JOIN class   c   ON   c.classid=s.class_id " +
                        "  INNER JOIN templates t ON t.id     =s.template_id " +
                        "  INNER JOIN MASTER   m  ON m.id=g.for_year   " +
                        "  WHERE g.for_month='"+obj.getMonthid()+"' " +
                        "  AND g.class_id ='"+batchid+"' " +
                        ") tab  ";
            
            dataquery ="SELECT g.class_id,m.value AS for_year,FROM_UNIXTIME(g.due_date/1000,'%d-%m-%Y') as  due_date ,g.templateid ,SUM(g.amount) AS amount,sum(g.paid_amount) AS tot_received " +
                        "      ,g.createdby , g.createdon,c.name AS classname , t.name AS template ," +
                        "      CASE g.for_month WHEN 1 THEN 'January' WHEN 2 THEN 'Febrary' WHEN 3 THEN 'March' WHEN 4 THEN 'April' WHEN 5 THEN 'May' WHEN 6 THEN 'June' WHEN 7 THEN 'July' WHEN 8 THEN 'August' WHEN 9 THEN 'September' WHEN 10 THEN 'October' WHEN 11 THEN 'November' WHEN 12 THEN 'December' END AS for_month " +
                        " FROM generatemonthlyfee g " +
                        " INNER JOIN sessions s  ON s.batch_id=g.class_id   " +
                        " INNER JOIN class   c   ON   c.classid=s.class_id  " +
                        " INNER JOIN templates t ON t.id     =s.template_id " +
                        " INNER JOIN MASTER   m  ON m.id=g.for_year " +
                        " WHERE g.for_month= '"+obj.getMonthid()+"'" +
                        "   AND g.class_id ='"+batchid+"' limit ? offset ?";
            flag=1;
        }     
        try{
            rs = DaoUtil.executeQuery(conn, countquery);
            if(rs.next()) {
                count = rs.getInt("count");

            }
            rs = DaoUtil.executeQuery(conn,dataquery,new Object[]{15,0});
            job = jsonUtil.getJsonObject(rs, count, page,rows, false);
        }
        catch (SQLException ex) {
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return job;
        
    }

    public Object getAllAsJsonStudentWise(Connection conn, GenerateFeePostData obj, String studentid, int page, int rows) throws ReadableException {
     
        JSONObject jsonobj = new JSONObject();        
        ResultSet rs = null;
        String countquery="";
        String dataquery="";
        int count=0;
        String batchid="";
        
        if(obj.getSessionid()!=null && obj.getClassid()!=null)
          batchid=new GetBatch(obj.getClassid(),obj.getSessionid()).BatchId(conn);

        if((obj.getMonthid() > 0 && obj.getMonthid() < 12) && studentid!=null){
           countquery=  "SELECT COUNT(1) AS count " +
                    "  FROM class c, templates t , student s ,generatemonthlyfee g , sessions ss " +
                    " WHERE ss.batch_id=g.class_id " +
                    "   AND ss.class_id   = c.classid " +
                    "   AND ss.template_id = t.id   " +
                    "   AND g.student_id = s.studentid " +
                    "   AND g.student_id = '"+studentid+"' " +
                    "   AND g.class_id   = '"+batchid+"' " +
                    "   AND g.for_month  = '"+obj.getMonthid()+"' " ;
           
          dataquery="SELECT g.monthly_fee_id,g.school_id,g.class_id,c.name classname,g.student_id,CONCAT(CONCAT(CONCAT(CONCAT(s.fname,' '),case when s.mname is null then '' else s.mname end),' '),s.lname) AS studentname, " +
                    "       CASE g.for_month WHEN 1 THEN 'January' WHEN 2 THEN 'Febrary' WHEN 3 THEN 'March' WHEN 4 THEN 'April' WHEN 5 THEN 'May' WHEN 6 THEN 'June' WHEN 7 THEN 'July' WHEN 8 THEN 'August' WHEN 9 THEN 'September' WHEN 10 THEN 'October' WHEN 11 THEN 'November' WHEN 12 THEN 'December' END AS for_month," +
                    "       g.for_year,g.amount,FROM_UNIXTIME(g.due_date/1000,'%d-%m-%Y') as  due_date,g.templateid,t.name AS template,CASE g.paid_status WHEN 0 THEN NULL WHEN 1 THEN TRUE END AS markpaid,CASE g.paid_status WHEN 0 THEN 'Payment Pending' WHEN 1 THEN 'Payment Received' END AS paid_status,g.paid_on,g.paid_by,g.paid_amount,g.comment,g.for_month AS MONTH " +
                    "  FROM class c, templates t , student s ,generatemonthlyfee g , sessions ss " +
                    " WHERE ss.batch_id=g.class_id " +
                    "   AND ss.class_id   = c.classid " +
                    "   AND ss.template_id = t.id   " +
                    "   AND g.student_id = s.studentid " +
                    "   AND g.student_id = '"+studentid+"' " +
                    "   AND g.class_id   = '"+batchid+"' " +
                    "   AND g.for_month  = '"+obj.getMonthid()+"' " +
                    "   LIMIT ? OFFSET ?";

        }
        if(studentid!=null && (obj.getMonthid()<=0 ||obj.getMonthid() > 12 ) ){
           countquery=  "SELECT count(1) as count " +
                    "  FROM class c, templates t , student s ,generatemonthlyfee g  , sessions ss " +
                    " WHERE ss.batch_id=g.class_id " +
                    "   AND ss.class_id = c.classid  " +
                    "   AND ss.template_id = t.id    " +
                    "   AND g.student_id = s.studentid " +
                    "   AND g.student_id = '"+studentid+"'  " +
                    "   AND g.class_id   = '"+batchid+"'";


                        
          dataquery="SELECT g.monthly_fee_id,g.school_id,g.class_id,c.name classname,g.student_id,CONCAT(CONCAT(CONCAT(CONCAT(s.fname,' '),case when s.mname is null then '' else s.mname end),' '),s.lname) AS studentname, " +
                    "       CASE g.for_month WHEN 1 THEN 'January' WHEN 2 THEN 'Febrary' WHEN 3 THEN 'March' WHEN 4 THEN 'April' WHEN 5 THEN 'May' WHEN 6 THEN 'June' WHEN 7 THEN 'July' WHEN 8 THEN 'August' WHEN 9 THEN 'September' WHEN 10 THEN 'October' WHEN 11 THEN 'November' WHEN 12 THEN 'December' END AS for_month, " +
                    "       g.for_year,g.amount,FROM_UNIXTIME(g.due_date/1000,'%d-%m-%Y') as  due_date,g.templateid,t.name AS template,CASE g.paid_status WHEN 0 THEN NULL WHEN 1 THEN TRUE END AS markpaid,CASE g.paid_status WHEN 0 THEN 'Payment Pending' WHEN 1 THEN 'Payment Received' END AS paid_status,g.paid_on,g.paid_by,g.paid_amount,g.comment,g.for_month AS MONTH " +
                    "  FROM class c, templates t , student s ,generatemonthlyfee g  , sessions ss " +
                    " WHERE ss.batch_id=g.class_id " +
                    "   AND ss.class_id = c.classid  " +
                    "   AND ss.template_id = t.id    " +
                    "   AND g.student_id = s.studentid " +
                    "   AND g.student_id = '"+studentid+"'  " +
                    "   AND g.class_id   = '"+batchid+"'" +
                    "   LIMIT ? OFFSET ?";
        
        }  
        try{
            rs = DaoUtil.executeQuery(conn, countquery);
            if(rs.next()) {
                count = rs.getInt("count");

            }
            rs = DaoUtil.executeQuery(conn,dataquery,new Object[]{15,0});
            jsonobj = jsonUtil.getJsonObject(rs, count, page,rows, false);
        }
        catch (SQLException ex) {
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return jsonobj;
    }
}
