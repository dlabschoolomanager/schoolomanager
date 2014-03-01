package com.dlabs.mis.dao;


//import com.dlabs.config.Property;
//import com.dlabs.dao.DAO;
//import com.dlabs.dao.DaoHelper;
//import com.dlabs.dao.MySqlQuery;
//import com.dlabs.permission.PermissionDAO;
//import com.dlabs.session.AuthHandler;
import com.dlabs.mis.model.GenerateString;
import com.dlabs.mis.model.User;
import com.dlabs.mis.model.UserLogin;
import com.dlabs.mis.model.UserProperty;
import com.dlabs.mis.model.UserRole;
import com.dlabs.session.UserSessionBean;
import com.dlabs.util.FileHandler;
import com.dlabs.util.JsonResult;
import com.dlabs.util.Paging;
import com.kjava.base.ReadableException;
import com.kjava.base.db.DaoUtil;
import com.kjava.base.db.DbPool;
import com.kjava.base.util.ConfigReader;
import com.kjava.base.util.ExtJsonUtil;
import com.kjava.base.util.JSONUtil;
import com.kjava.util.DateHelper;
import java.io.Console;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Kamlesh Kumar Sah
 */
@Repository
public class UserDAO  {
    int count = 0;
   JSONUtil jsonUtil = new ExtJsonUtil();
   @Autowired UserSessionBean userSessionBean;
  Logger LOG = Logger.getLogger(UserDAO.class);
    public UserDAO() {
    }
    
   
    public List getAll(Connection conn) throws ReadableException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

   
    public JSONObject getAllAsJson(Connection conn, Paging page) throws ReadableException {
       
        JSONObject job = null;
        String query[] =MySqlQuery.getAllUserQuery(page.getStart(),page.getLimit(),"",page.getSearchString());
        ResultSet rs = null;
        try {
            rs = DaoUtil.executeQuery(conn, query[0]);
            if (rs.next()) {
                count = rs.getInt("count");
            }
            rs = DaoUtil.executeQuery(conn, query[1]);
            job = jsonUtil.getJsonObject(rs, count, page.getStart(),page.getLimit(), false);
        } catch (SQLException ex) {
           LOG.error(ex);
        }
        return job;
    }

//    public Object get(Connection conn, Object obj) throws ReadableException {
//        User user = (User) obj;
//       // ResultSet rs = DaoUtil.executeQuery(conn, MySqlQuery.getUserById(), user.getUserID());
//        try{
//        if(rs.next()){
//            user = //DaoHelper.getUserObject(rs);
//        }
//        }
//        catch(SQLException ex){
//            throw new ReadableException("Userodao", "get");
//        }
//        return user;
//    }

    
    public User addOrEditUser(Connection conn, User user) throws ReadableException {
        String uId = user.getUserId();
        String query[] = MySqlQuery.getUserInsertQuery();
         ResultSet rs = null;
         try {
         if(uId == null)
        	{
        		rs = DaoUtil.executeQuery(conn, query[2], new Object[]{user.getUserLogin().getUserName()});

                if(rs.next()){
                        return user;
                }
            String uid = UUID.randomUUID().toString();
            DaoUtil.executeUpdate(conn,query[0],new Object[]
            {uid,user.getName(),user.getDob(),user.getGender(),user.getAddress(),user.getCity(),user.getEmailId(),user.getContactNo(),user.getRoleId()});
            UserLogin ul = user.getUserLogin();
            DaoUtil.executeUpdate(conn,query[1],new Object[]
            {uid,ul.getUserName(),new GenerateString().getString(),"",""});
            user.setUserId(uid);
            if(user.getRoleId()==3){
                 insertIntoTeacher(conn,user);
            }
            }
        	else
        	{
        		String updateQuery[] = MySqlQuery.getUserUpdateQuery();
        		System.out.println(updateQuery[0]);
        		DaoUtil.executeUpdate(conn,updateQuery[0],new Object[]
        	    {user.getName(),user.getDob(),user.getGender(),user.getAddress(),user.getCity(),user.getEmailId(),user.getContactNo(),user.getRoleId(),user.getUserId()});
        		UserLogin ul = user.getUserLogin();
        		System.out.println(updateQuery[1]);
        		System.out.println(ul.getUserName()+user.getUserId());
        		DaoUtil.executeUpdate(conn,updateQuery[1],new Object[]
        		{ul.getUserName(),user.getUserId()});
        		
        	}
         }catch(SQLException ex){
             LOG.error(ex);
         } 
        return user;
    }

//    public int edit(Connection conn, Object obj) throws ReadableException {
//        User u=(User)obj;
//        int x=0,y=0;
//        String query[]=MySqlQuery.getUserUpdateQuery();
//        try{
//           x= DaoUtil.executeUpdate(conn,query[0] ,new Object[]{u.getSalutation(),u.getFirstName(),u.getLastName(),u.getDob(),u.getGender(),u.getAddress(),u.getCity(),u.getEmailID(),u.getContactno(),u.getRoleid(),u.getDesignation(),u.getHeadQuarter(),u.getDoj(),u.getManagerid(),u.getUserID()});
//           if (x > 0)
//               y=DaoUtil.executeUpdate(conn,query[1] ,new Object[]{u.getUsername(),u.getUserID()});
//
//        }
//        catch(SQLException ex){
//            throw new ReadableException("Userdao", "edit");
//        }
//         return y;
//    }

    
    public int delete(Connection conn, String userId) throws ReadableException {
        
        int x=0,y=0;
        String query[]=new String[2];
        query= MySqlQuery.getUserDeleteQuery();
        
            x=DaoUtil.executeUpdate(conn,query[1],new Object[]{userId});
            if(x > 0)
                y=DaoUtil.executeUpdate(conn,query[0],new Object[]{userId});

        
       return y;
    }

    public int disableuser(Connection conn, String userId) throws ReadableException {
       return DaoUtil.executeUpdate(conn,"update userlogin set status=0 where userid=?",new Object[]{userId});
    }
    
//    public List<Doctor> getAllBirthDays(Connection conn) throws ReadableException, SQLException {
//        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.DATE, -7);
//        Date d1 = new Date(cal.getTimeInMillis());
//        cal.add(Calendar.DATE, 21);
//        Date d2 = new Date(cal.getTimeInMillis());
//        List<Doctor> doctors = new ArrayList<Doctor>();
//        String query = "select fname,dob from doctor where dob>? and dob<?";
//        ResultSet rs = DaoUtil.executeQuery(conn, query, new Object[]{d1,d2});
//        Doctor d = null;
//        while(rs.next()){
//            d = new Doctor();
//            d.setDob(rs.getDate("dob"));
//            d.setFirstName(rs.getString("fname"));
//            doctors.add(d);
//        }
//        return doctors;
//    }

    public User validateUser(Connection conn,String uid, String pass) throws ReadableException{
        User u = null;
        String sessionid="";
        ResultSet rs =null;
        String obj[]={"SELECT schoolid , schoolname , websiteurl FROM schooladmin",            
                      "SELECT DISTINCT session_id ,year FROM sessions WHERE currentsession=1",
                      "SELECT class_id, 1 AS isclassteacher FROM sessions WHERE class_teacher=? AND session_id=?"};   
        try{
            rs = DaoUtil.executeQuery(conn, MySqlQuery.verifyUser(), new Object[]{uid,pass});
            if(rs.next()){
                u=getUserObject(rs);
                Map<String, Object> p = new HashMap<String, Object>();
                if(u.getRoleId()==4){ // parent       
                     ResultSet rs1 =  DaoUtil.executeQuery(conn,"SELECT studentid FROM student WHERE userid = ?" , new Object[]{u.getUserId()});
                     if(rs1.next()){
                        p.put("studentid",rs1.getString("studentid"));
                     }
                }
            rs = DaoUtil.executeQuery(conn,obj[0]);                     
            if(rs.next()){
                p.put("schoolid",rs.getString("schoolid"));
                p.put("schoolname",rs.getString("schoolname"));   
                p.put("websiteurl",rs.getString("websiteurl"));   
            }
            rs = DaoUtil.executeQuery(conn,obj[1]);                     
            if(rs.next()){
                sessionid=rs.getString("session_id");
                p.put("session_id",sessionid);
                p.put("year",rs.getString("year"));                
            }
            rs = DaoUtil.executeQuery(conn,obj[2],new Object[]{u.getUserId(),sessionid});                     
            if(rs.next()){
                String classid=rs.getString("class_id");
                p.put("class_id",classid);
                p.put("isclassteacher",rs.getInt("isclassteacher")); 
                String batchid=new GetBatch(classid, sessionid).BatchId(conn);
                p.put("batchid",batchid); 
            }else{
                p.put("class_id",null);
                p.put("isclassteacher",null);                
                p.put("batchid",null); 
            }
                       
            u.setProperties(p);
                
                //PermissionDAO pdao = new PermissionDAO();
                //u.setRolesPermission(pdao.getPermissionsByRole(conn, u.getRoleid()));
            }
        }
        catch (SQLException ex) {
            throw new ReadableException(ex,ex.getMessage(),"UserDAO", "validateUser");
        }
        
        return u;
    }

    public boolean changePassword(Connection conn, int uid, String old, String newp) throws ReadableException {
        String query = "UPDATE userlogin set PASSWORD=? where userid=? and PASSWORD=?";
            if (DaoUtil.executeUpdate(conn, query, new Object[]{newp, uid, old}) > 0) {
                return true;
            } else {
                return false;
            }
       

    }

  
    private User getUserObject(ResultSet rs) throws SQLException{
        User u = new User();
        if(rs.getObject("userid")!=null)
            u.setUserId(rs.getString("userid"));
        if(rs.getObject("contactno")!=null)
            u.setContactNo(rs.getString("contactno"));
        if(rs.getObject("roleid")!=null)
            u.setRoleId(rs.getInt("roleid"));
        if(rs.getObject("username")!=null){
            UserLogin ul = new UserLogin();
            ul.setUserName(rs.getString("username"));
            u.setUserLogin(ul);
        }
        if(rs.getObject("sal")!=null)
            u.setSalutation(rs.getString("sal"));
        if(rs.getObject("name")!=null)
            u.setName(rs.getString("name"));
        if(rs.getObject("city")!=null)
            u.setCity(rs.getString("city"));
        if(rs.getObject("address")!=null)
            u.setAddress(rs.getString("address"));
        if(rs.getObject("emailid")!=null)
            u.setEmailId(rs.getString("emailid"));
        if(rs.getObject("gender")!=null)
            u.setGender(rs.getString("gender"));
        if(rs.getObject("dob")!=null)
            u.setDob(rs.getLong("dob"));
         if(rs.getObject("permValue")!=null)
            u.setPermValue(rs.getInt("permValue"));
      
        return u;
    }
        private UserRole getRoleObject(ResultSet rs) throws SQLException{
        UserRole u = new UserRole();
        if(rs.getObject("id")!=null)
            u.setId(rs.getInt("id"));
        if(rs.getObject("name")!=null)
            u.setName(rs.getString("name"));
        if(rs.getObject("permValue")!=null)
            u.setPermValue(rs.getInt("permValue"));
        return u;
    }
    
    public int getCount(){
        return count;
    }

    public List<UserRole> getAllRoles(Connection conn) throws ReadableException, SQLException {
        List<UserRole> result = new ArrayList<UserRole>();
        ResultSet rs =null;
        rs = DaoUtil.executeQuery(conn, "select * from roles");
        while(rs.next()){
            result.add(getRoleObject(rs));
        }
        this.count = result.size();
        return result;
    }

    private void insertIntoTeacher(Connection conn, User user) throws ReadableException {

        String insert="INSERT INTO teacher (teacherid,fullname,designation,dob,address,cityid,department,emailid, " +
                      "	teachertype,jobtype,createdby,modifiedby,createdon) " +
                      "	VALUES(?,?,?,?,?,?,?,?,?,?,?,?,CURRENT_DATE)";
        
        DaoUtil.executeUpdate(conn, insert,new Object[]{user.getUserId(),
                                           user.getName(),
                                           user.getDesignation(),
                                           user.getDob(),
                                           user.getAddress(),
                                           user.getCity(),
                                           user.getDepartment(),
                                           user.getEmailId(),
                                           user.getTeachertype(),
                                           user.getJobtype(),
                                           user.getCreatedby(),
                                           user.getModifiedby()}
         );
        
        
    }

    public HashMap changeProfilePic(Connection conn,HttpServletRequest request, HttpServletResponse response) throws ReadableException {
        String path=new ConfigReader().get("prof_pic_store");
        String userid="", filename="";
        String updatequery="update users set profilepic=? where userid=?";
        HashMap arrParam = new HashMap();
        arrParam = (HashMap) FileHandler.uploadFile(path, request);        
        
        if(arrParam.containsKey("id"))userid=(String)arrParam.get("id");
        if(arrParam.containsKey("modifiedfilename"))filename=(String)arrParam.get("modifiedfilename");
        
        if(DaoUtil.executeUpdate(conn,updatequery,new Object[]{filename,userid})==1){
            try {
                conn.commit();
            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return arrParam;
    }
    
    public int getUserRole(Connection conn, String userid) throws ReadableException{
         
        if(userid!=null){
            try {
                ResultSet rs=DaoUtil.executeQuery(conn,"select roleid from users where userid=?",new Object[]{userid});
                if(rs.next() && rs.getObject("roleid")!=null)  
                  return rs.getInt("roleid");
            } catch (SQLException ex) {
                LOG.debug(ex.getMessage());
            } 
         }        
        return 0;
    
    }

    public int isClassTeacher(Connection conn, String sessionid ,String classid, String userid) throws ReadableException{

        String query="SELECT COUNT(1) as count FROM sessions WHERE session_id=? AND class_id= ?   AND class_teacher= ?";
        
        if(userid!=null){
            try {
                ResultSet rs=DaoUtil.executeQuery(conn,query,new Object[]{sessionid,classid,userid});
                if(rs.next() && rs.getObject("count")!=null)  
                  return rs.getInt("count");
            } catch (SQLException ex) {
               LOG.error(ex);
            }
         }        
        return 0;
    
    }
    
}
