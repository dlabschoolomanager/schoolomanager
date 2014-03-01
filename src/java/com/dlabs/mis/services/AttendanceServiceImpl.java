package com.dlabs.mis.services;

import com.dlabs.mis.dao.AttendanceDAO;
import com.dlabs.mis.model.Attendance;
import com.dlabs.mis.model.AttendanceSheet;
import com.dlabs.mis.model.AttendanceUpdateModel;
import com.kjava.base.db.DbPool;
import java.sql.Connection;
import java.util.Calendar;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Kamlesh the admin
 */
@Component
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    AttendanceDAO attendanceDAO;
    Connection conn = null;
    @Autowired MailService mailService;

    public AttendanceDAO getAttendanceDAO() {
        return attendanceDAO;
    }

    public void setAttendanceDAO(AttendanceDAO attendanceDAO) {
        this.attendanceDAO = attendanceDAO;
    }

    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    
    @Override
    public JSONObject getAttendanceSheets(AttendanceSheet defaultSheet) {
         JSONObject job =null;
        try {
            conn = DbPool.getConnection();
            job = attendanceDAO.getAttendanceSheets(conn,defaultSheet);         
            JSONObject monthSheet = attendanceDAO.getAttendanceSheet(conn, defaultSheet.getMonth(), defaultSheet.getBatchId());
            JSONArray rows = (JSONArray)job.get("rows");
            for(int i=0; i<rows.size();i++){
                JSONObject obj = (JSONObject)rows.get(i);
                if(obj.get("month").equals(defaultSheet.getMonth()) && obj.get("batchId").equals(defaultSheet.getBatchId())){
                     obj.put("attendance", monthSheet.get("rows"));
                     break;
                }
            }
        } catch (Exception ex) {
           job = null;
        } finally {
            DbPool.close(conn);
        }
        return job;
    }
    @Override
    public JSONObject getAttendanceSheet(int id) {
        JSONObject obj = new JSONObject();
        try {
            conn = DbPool.getConnection();
            JSONObject jobj = attendanceDAO.getAttendanceSheet(conn, id);
            obj.put("sheetId", id);
            obj.put("attendance", jobj.get("rows"));
            conn.commit();
        } catch (Exception ex) {
            DbPool.rollback(conn);
        } finally {
            DbPool.close(conn);
        }
        return obj;
    }
    @Override
    public AttendanceSheet addAttendanceSheet(AttendanceSheet sheet) {
        try {
            conn = DbPool.getConnection();
            sheet = attendanceDAO.addAttendanceSheet(conn,sheet);
            conn.commit();
        } catch (Exception ex) {
            DbPool.rollback(conn);
        } finally {
            DbPool.close(conn);
        }
        return sheet;
    }

    @Override
    public String deleteAttendanceSheet(int sheetId) {
        String result = "{sucess:true}";
        try {
            conn = DbPool.getConnection();
            attendanceDAO.deleteAttendanceSheet(conn,sheetId);
            conn.commit();
        } catch (Exception ex) {
            result = "{sucess:false}";
        } finally {
            DbPool.close(conn);
        }
        return result;
    }

    @Override
    public String updateAttendanceSheet(int sheetId,AttendanceUpdateModel[] sheets) {
       String result = "{success:true}";
        try {
            conn = DbPool.getConnection();
            attendanceDAO.updateAttendanceSheet(conn,sheetId,sheets);
            conn.commit();
        } catch (Exception ex) {
            DbPool.rollback(conn);
            result = "{success:false}";
        } finally {
            DbPool.close(conn);
        }
        return result;
    }

    public String getAttnedanceOfMonth(String studentId, String month) {
        studentId = "06bd64f3-c532-41ab-9032-3535e49c93be";
        month = "Aug-2013";
        String result ="";
        try {
            conn = DbPool.getConnection();
            Attendance att = this.attendanceDAO.getAttendanceOfStudent(conn, studentId, month);
            conn.commit();
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DATE, 1);
            //cal.get(Calendar.);
        } catch (Exception ex) {
            DbPool.rollback(conn);
            result = "{success:false}";
        } finally {
            DbPool.close(conn);
        }
        return result;
    }

   
}
