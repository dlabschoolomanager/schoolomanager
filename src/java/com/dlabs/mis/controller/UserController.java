package com.dlabs.mis.controller;

//import com.dlabs.audittrail.AuditManager;
//import com.dlabs.config.Property;
//import com.dlabs.dao.DAO;
import com.dlabs.constants.MISConstant;
import com.dlabs.constants.URLMap;
import com.dlabs.mis.dao.UserDAO;
import com.dlabs.mis.model.User;
import com.dlabs.mis.model.UserRole;
import com.dlabs.mis.services.AuditTrailService;
import com.dlabs.mis.services.MailService;
import com.dlabs.mis.services.MailServiceImpl;
import com.dlabs.session.AuthHandler;
import com.dlabs.session.UserSessionBean;
import com.dlabs.util.JsonResult;
import com.dlabs.util.Paging;
//import com.dlabs.session.RequestHandler;
import com.kjava.base.ReadableException;
import com.kjava.base.db.DbPool;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author Kamlesh Kumar Sah
 */
@Controller
public class UserController extends AbstractUserController {
    
    Connection conn=null;
    
    @Autowired
    private UserDAO userDAO;
    @Autowired MailService mailService;
    @Autowired UserSessionBean userSessionBean;
    @Autowired AuditTrailService auditTrailService;

//    public void setUserDAO(UserDAO userDAO) {
//        this.userDAO = userDAO;
//    }

    @RequestMapping(value=URLMap.GET_ROLES, method= RequestMethod.GET)
    @ResponseBody
    public JsonResult<UserRole> getRoles(){
        JsonResult<UserRole> result = new JsonResult<UserRole>();
        try{
            conn = DbPool.getConnection();
            result.setRows(userDAO.getAllRoles(conn));
            result.setRecords(userDAO.getCount());
        }
       
        catch(Exception ex){
            DbPool.rollback(conn);
        }finally{
            DbPool.close(conn);
        }
        return result; 
     }
    @RequestMapping(value=URLMap.ADD_USER, method=RequestMethod.POST)
    @ResponseBody
    public User addUser(@RequestBody User user) throws ReadableException {
         try{
           conn = DbPool.getConnection();
           //mailService.sendCreateUserNotification(conn,user);
           user = userDAO.addOrEditUser(conn,user);
           mailService.sendCreateUserNotification(conn,user);
           conn.commit();
        }
        catch(Exception ex){
            DbPool.rollback(conn);
        }finally{
            DbPool.close(conn);
        }
        return user;   
    }
    @RequestMapping(value=URLMap.DELETE_USER, method=RequestMethod.POST)
    @ResponseBody
    public String deleteUser(@RequestParam("userId") String userId) throws ReadableException {
       String res = "{Success:false}";
       try{
           conn = DbPool.getConnection();
           if (userDAO.delete(conn, userId) > 0) {
        	   res = "{Success:true}";
           }
          conn.commit();
       }
       catch(Exception ex){
           DbPool.rollback(conn);
       }finally{
           DbPool.close(conn);
       }
       return res; 
   }
   
   @RequestMapping(value=URLMap.DISABLE_USER, method=RequestMethod.POST)
   @ResponseBody
    public String disableUser(@RequestParam("userId") String userId) throws ReadableException {
       String res = "{Success:false}";
       try{
           conn = DbPool.getConnection();
           if (userDAO.disableuser(conn, userId) > 0) {
        	   res = "{Success:true}";
           }
          conn.commit();
       }
       catch(Exception ex){
           DbPool.rollback(conn);
       }finally{
           DbPool.close(conn);
       }
       return res; 
   } 
    
    @RequestMapping(value=URLMap.GET_USER, method= RequestMethod.GET)
    @ResponseBody
    public User getUser(HttpServletRequest req) throws ReadableException {
        String res = "{}";
        return AuthHandler.getUser(req);
    }
    @RequestMapping(value=URLMap.GET_USERS, method= RequestMethod.GET)
    @ResponseBody
    public String getAllUser(HttpServletRequest req) throws ReadableException {
        String res = "{}";
         try {
           conn = DbPool.getConnection();
           Paging page =Paging.getInstance(req);
//        if (request.getParameter("page") != null) {
//            page = Integer.parseInt(request.getParameter("page"));
//        }
//        if (request.getParameter("rows") != null) {
//            rows = Integer.parseInt(request.getParameter("rows"));
//        }
        //String ss = RequestHandler.getSearchString(request);
        //String orderBy = RequestHandler.getOrderBy(request);
        res = userDAO.getAllAsJson(conn, page).toString();
        }
       catch (Exception ex) {
        throw new ReadableException(ex.getCause(),ex.getMessage(),"UserController", "getAllUser");
        }
        return res;
    }

    @RequestMapping(value=URLMap.VERIFY_USERS, method= RequestMethod.POST)
    @ResponseBody
    public String verifyUser(HttpServletRequest request) throws ReadableException, SQLException {
        String res = MISConstant.SUCCESS_FALSE;
         try {
        conn = DbPool.getConnection();
        String uid = request.getParameter("u");
        String pass = request.getParameter("p");
        userDAO = new UserDAO();
        User u = userDAO.validateUser(conn, uid, pass);
        if (u != null) {
            AuthHandler.createSession(request, u);
            userSessionBean.setIpAddress(request.getRemoteAddr());
            userSessionBean.setUserId(u.getUserId());
            userSessionBean.setUserName(u.getName());
            auditTrailService.insertLog(conn, 101, null);
                JSONObject j = new JSONObject();
                j.put("valid", true);
                j.put("zpv", u.getPermValue());
                j.put("userrole", u.getRoleId());                
                res = j.toString();
                conn.commit();
        }
         }catch(Exception ex){
             DbPool.rollback(conn);
         }finally{
             DbPool.close(conn);
         }
        
        return res;
    }

    @Override
    public String changePassword(Connection conn, HttpServletRequest request) throws ReadableException {
        String res = "{Success:false}";
        UserDAO dao = new UserDAO();
        int uid = 0;//AuthHandler.getUserId(request);
        String old = request.getParameter("old");
        String newp = request.getParameter("new");
        if (dao.changePassword(conn, uid, old, newp)) {
            res = "{Success:true}";
        }
        return res;
    }

    @RequestMapping(value=URLMap.SIGN_OUT, method= RequestMethod.POST)
    @ResponseBody
    public String signOut(HttpServletRequest request) throws ReadableException {
        String uid = AuthHandler.getUserId(request);
        AuthHandler.destroySession(request);
        //AuditManager.insertLog(conn, request, 101, null);
        return "{Success:true}";
    }

    @Override
    public boolean isFormSubmit() {
        return formSubmit;
    }

    private User getUserObject(HttpServletRequest request) {//throws ReadableException {
        User a=new User();
//        if (request.getParameter("oper").equals("del"))
//            a.setUserID(RequestHandler.getInt(request,RequestHandler.USER_ID));
//        else
//        {
//           if (request.getParameter("oper").equals("edit"))
//            a.setUserID(RequestHandler.getInt(request,RequestHandler.USER_ID));
//        
//        a.setAddress(RequestHandler.getString(request,RequestHandler.ADDRESS));
//        a.setUsername(RequestHandler.getString(request,RequestHandler.LOGINID));
//        a.setSalutation(RequestHandler.getString(request,RequestHandler.SAL));
//        a.setFirstName(RequestHandler.getString(request,RequestHandler.FNAME));
//        a.setLastName(RequestHandler.getString(request,RequestHandler.LNAME));
//        a.setCity(RequestHandler.getString(request,RequestHandler.CITY));
//        a.setContactno(RequestHandler.getString(request,RequestHandler.CONTACT_NO));
//        a.setEmailID(RequestHandler.getString(request,RequestHandler.EMAILID));
//        a.setDob(RequestHandler.getDate(request,RequestHandler.DOB,"yyyy-MM-dd"));
//        a.setPassword("poisehr");
//        a.setRoleid(1);
//        a.setGender(RequestHandler.getInt(request,RequestHandler.GENDER));
//        a.setRoleid(RequestHandler.getInt(request,RequestHandler.ROLEID));
//        a.setDesignation(RequestHandler.getString(request,RequestHandler.DESIGNATION));
//        a.setManagerid(RequestHandler.getInt(request,RequestHandler.MANAGERID));
//        a.setDoj(RequestHandler.getDate(request,RequestHandler.DOJ,"yyyy-MM-dd"));
//        a.setHeadQuarter(RequestHandler.getString(request,RequestHandler.HEADQUARTER));
      //  }
        return a;
    }
    
    @RequestMapping(value=URLMap.UPLOAD_RPOF, method=RequestMethod.POST)
    @ResponseBody
    public String changeProfilePic(HttpServletRequest request,HttpServletResponse response) throws ReadableException {
        String res = "{failure:true}";
        UserDAO dao = new UserDAO();
        Connection conn = null;
        try {
            conn = DbPool.getConnection();
            HashMap arrmap=dao.changeProfilePic(conn,request, response);
            if(arrmap!=null && arrmap.size()>0)
            res = "{success:true}";
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
    
    @RequestMapping(value=URLMap.GET_AUDITTRAIL, method= RequestMethod.GET)
    @ResponseBody
    public String getAuditLog(HttpServletRequest req) throws ReadableException {
        JSONObject res = null;
        Connection conn = null;
        try {
            conn = DbPool.getConnection();
            Paging page =Paging.getInstance(req);
            res = this.auditTrailService.getAuditTrail(conn,page);
           
        } catch (SQLException ex) {
            DbPool.rollback(conn);
             throw new ReadableException(ex, ex.getMessage(), UserController.class, "");
        } finally {
            DbPool.close(conn);
        }
        return res.toString();
    }
      
    private boolean formSubmit;
}
