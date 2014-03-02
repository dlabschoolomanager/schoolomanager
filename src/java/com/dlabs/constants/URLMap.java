/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlabs.constants;

/**
 *
 * @author Kamlesh the admin
 */
public interface URLMap {
    String CALENDARS = "/calendar";
    String CALENDAR = "/calendar/{id}";
    String ADD_CALENDAR = "/calendar";
    String EVENTS = "/event";
    String EVENTS_ID = "/event/{eventId}";
    String ADD_MASTER = "/master/add.do";
    String GET_MASTER = "/master/get.do";
    String GET_PROPRTIES = "/master/properties.do";
    String LOG_BUG = "/master/addbug.do";
    
    String GET_TEMPLATES ="/payment/getTemplates.do";
    String GET_TEMPLATE ="/payment/getTemplate.do";
    String ADD_TEMPLATES = "/payment/addTemplates.do";
    String DEL_TEMPLATE = "/payment/delTemplates.do";
    String ADD_TEMPLATE_DETAILS = "/payment/templateDetails.do";
    String ADD_FEE_STRUCTURE = "/payment/addFee.do";
    String DEL_FEE_STRUCTURE = "/payment/delFee.do";
    String GET_ALL_FEESTRUCTURE = "/payment/getAllFees.do";
    
    String ATTENDANC_SHEETS = "/attendancesheet";
    String ATTENDANC_SHEET = "/attendancesheet/{id}";
    
    String ATTENDANC_MONTHS= "/monthlyattendance";
    String ATTENDANC_MONTH= "/monthlyattendance/{id}";
    String NORMAL_SEARCH ="/search/getAll.do";
    String ADVANCE_SEARCH ="/search/getAdvance.do";
    String ADD_Class = "/classes/add.do";
    String GET_Classes ="/classes/get.do";
    String DELETE_CLASS ="/classes/del.do";
    String ADD_STUDENT ="/student/add.do"; 
    String GET_STUDENTS_RECENT_UPDATES ="/student/get-recent-updates.do";
    String GET_PARENT_RECENT_UPDATES ="/parent/get-recent-updates.do";
    String GET_STUDENTS ="/student/get.do";
    String GET_STUDENT ="/student/{id}";
    String ADD_USER = "/user/addUser.do";
    String DELETE_USER = "/user/deleteUser.do";
    String GET_USERS = "/user/getUsers.do";
    String GET_USER = "/user/getUser.do";
    String VERIFY_USERS = "/user/verifyUser.do";
    String GET_AUDITTRAIL = "/user/auditTrail.do";
    String GET_ROLES = "/user/getRoles.do";
    String SIGN_OUT = "/user/signOut.do";
    String GET_ROLES_GROUP = "/permission/getRolesGroup.do";
    String GET_GROUP_PERMISSION="/permission/getPermissions.do";
    String UPDATE_PERMISSION ="/permission/updatePermission.do";
    String GET_EMAIL ="/mail/get.do";
    String ADD_EMAIL ="/mail/add.do";
    String ADD_PERIOD="/period/add.do";
    String GET_PERIOD="/period/get.do";
    String ADD_PERIOD_DATA="period/addTT.do";
    String GET_TIMETABLE="/timetable/get.do";
    String ADD_TIMETABLE="/timetable/add.do";
    String GET_COMBO="/combo/get.do";
    String GET_PAYMENTDETAILS="paymentDetail/get.do";  
    String GET_DASHBOARD_LINK = "/dashboard/get.do";
    String GET_STUDENT_LIST="classcombo/get.do";
    String GET_STUDENT_Fee_List="AdminStudentFee/get.do";
    String GENERATE_FEE="generatefee/add.do";
    String GET_GENERATED_FEE="generatefee/get.do";
    String GET_GENERATED_FEE_CLASS="generatefee/getFee.do";
    String GET_STUDENT_MONTHLY_FEE="/studentmonthlyfee/get.do";
    String ADD_EDIT_STUDENT_MONTHLY_FEE="/addextrafee/add.do";
    String MARK_FEE_PAID="/markpaidfee/pay.do";
    String DELETE_MONTHLY_FEE="deletemonthlyfee/del.do";
    String ADD_CLASS_SUBJECT="classsubject/add.do";
    String GET_CLASS_SUBJECT="classsubject/get.do";
    String ADD_CLASS_SUBJECT_TEACHER="classsubjectteacher/add.do";
    String GET_CLASS_SUBJECT_TEACHER="classsubjectteacher/get.do";    
    String ADD_CLASS_FACILITY="classfacility/get.do";
    String ADD_CLASS_TEACHER="classtecher/get.do";
    String GET_CLASS_EXAM="classexam/get.do";
    String ADD_CLASS_EXAM="classexam/add.do";
    String SAVE_CLASS_EXAM="/classexam/save.do";
    String ADD_STUD_MARK="studentmark/add.do";
    String GET_STUDENTS_MARKS="studentmark/get.do";
    String SAVE_STUDENTS_MARKS="savestudentmark/save.do";
    String SAVE_ROUTE="/transportroute/add.do";
    String GET_ROUTE ="transportroute/get.do";
    String GET_TRAN_PLACE="transportroute/getPlace.do";
    String SAVE_VEHICLE  ="transportroute/addbus.do";
    String GET_VEHICLE   ="transportroute/getVehicle.do";
    String SAVE_STUDENT_TRANSPORT="studenttransport/add.do";
    String GET_ROUTE_VEHICLE="vehiclecombo/get.do";
    String ADD_NOTIFICATION="notification/add.do";
    String GET_NOTIFICATION="notification/get.do";
    String GET_STUDENT_FEE_PARENT="studentmonthlyfeeparent/get.do";
    String GET_STUDENT_FEE_NAME="paymentfeename/get.do";
    String ADD_DIGITALDAIRY_PARENT="/digitaldairy/addparent.do";
    String GET_DIGITALDAIRY_PARENT="/digitaldairy/get.do";
    String ADD_STUDENT_MONTHLY_PROGRESS="smp/addsmp.do";
    String GET_STUDENT_MONTHLY_PROGRESS="smp/getsmp.do";
    String SAVE_STUDENT_MONTHLY_PROGRESS="smp/savesmp.do";
    String ADD_HOMEWORK="homework/add.do";
    String GET_HOMEWORK="homework/get.do";
    String DOWNLOAD_FILE="homework/download.do";
    String STUDENTHWSTATUS="workstatus/smwsget.do";
    String MARK_COMPLETED="homework/markcompleted.do";
    String SAVE_SCHOOL_DETAILS="schooladmin/save.do";
    String GET_SCHOOL_DETAILS="schooladmin/get.do";
    String GET_PSM="/psm/get.do";
    String GET_PSMSTUD_LIST="/psm/getpsmslist.do";
    String PROMOTE_STUDENT="/psm/promotestudent.do";
    String SAVE_SESSION_DETAILS="session/add.do";
    String WEB_SERVICE="wb/mis.do";
    String INITIATE_ADMISSION_PROCESS="studentadmission/init.do";
    String GET_ADMISSION_LIST="studentadmission/get.do";
    String GET_PERSONAL_DATA="studentadmission/getpd.do";
    String ADD_NEW_STUDENT="studentadmission/addns.do";
    String GET_OFFLINE_STUDENT="studentadmission/getoffline.do";
    String GET_EXISTING_STUDENT="studentadmission/getexisting.do";
    String UPDATE_NEW_STUDENT ="studentadmission/edtonln.do";
    String GET_PERSONAL_DATA_WEB="studentadmission/getonweb.do";
    String SEND_INTERVIW_EXAM_DATE="studentadmission/sendintvwexams.do";
    /*Mobile URL*/
    String VALIDATE_MOBILE_USER="wb/mlogin.do";
    String DISABLE_USER="user/disableUser.do";
    String STUDENT_REPORTCARD="reportcard/get.do";
    String CREATE_TIMETABLE="timetable/create.do";
    String CHECK_TEACHER_AVAILBILITY="timetable/check.do";
    
    String ADD_DOCUMENT = "tutorial/add.do";
    String GET_DOCUMENT= "tutorial/document.get";
    String GET_FOLDERS ="tutorial/get.do";
    String GET_ATRA="arta/get.do";
    String ADD_ATRA="arta/add.do";
    String ADMIN_EDIT_ATRA="arta/adminedit.do";
    String STUDENT_ROLE_NUMBER="student/setrollno.do";
    String SESSION_ADD="sessions/add.do";
    String SESSION_GET="sessions/get.do";
    String SESSION_CLASS_ADD="sessions/addclass.do";
    String SESSION_CLASS_GET="sessions/getclass.do";
    String SESSION_MARK_CURRENT="sessions/markcur.do";
    String SESSION_CLASS_MAP="sessions/addsessclassmap.do";
    String ADD_BOOK="/library/addbk.do";
    String GET_BOOKLIST="/library/getlst.do"; 
    String GET_BOOKDETAIL="/library/getdetl.do";
    String ISSUE_BOOK="/library/issbk.do";
    String GET_ISSUE_BOOK="/library/getbktrsn.do";
    String RETURN_ISSUE_BOOK="/library/returnbk.do";
    String RENEWAL_BOOK="/library/renewbk.do";
    String GET_MISREPORT="misreport/get.do";
    String GET_TEACHER_PROF="teacher/getTcherPrf.do";
    String UPDATE_TEACHER="teacher/updte.do";
    String ADD_TEACHER_QUALIF="teacher/addqualif.do";
    String ADD_TEACHER_SKIIL="teacher/addskill.do";
    String ADD_TEACHER_EXP="teacher/addsexp.do";
    String GET_TEACHER_QUALIF="teacher/getqualif.do";
    String GET_TEACHER_EXP="teacher/gettchrexp.do";
    String DELETE="teacher/delt.do";
    String UPLOAD_RPOF="user/addprofpic.do";
    String CLASSSUBJECTCOMBO="classsubjectcombo/get.do";
    String ATTENDENCE_MISREPORT="misreport/getattdnc.do";
    String GET_ALERT="alert/get.do";    
    String GET_PAYMENT_REPORT="misreport/getpayment.do";
    String GET_MONTHLY_PAYMENT_REPORT="misreport/getmonpay.do";
    String ATTENDENCE_MONTHLY_MISREPORT="misreport/getmonattdnc.do";
    String GET_CLASS_REPORT="misreport/getclsrpt.do";
    String ADD_HOSTEL="hostel/addhstl.do";
    String GET_HOSTEL="hostel/gethstl.do";
    String ADD_HOSTEL_ROOM="hostel/addhstlrm.do";
    String GET_HOSTEL_ROOM="hostel/gethstlrm.do";
    String ALLOCATE_HOSTEL_ROOM="hostel/allocatehstlrm.do";
    String GET_HOSTEL_ROOM_ALLOCATION="hostel/getallocatehstlrm.do";
    String MARK_HOSTEL_FEE_PAID="hostel/makepayhstlrmfee.do";
    String SAVE_ADDMISION_STUDENT_DET="studentadmission/saveaddns.do";
    String CONFIRM_ADDMISION_STUDENT_DET="studentadmission/cofmadmsn.do";
    
    String CONTACT_ADMIN="master/cntadmn.do";
    
    String SET_PARENT_RECENT_COMMENT="/parent/set-recent-updates-comment.do";
    String SET_PARENT_RECENT_LIKE   ="/parent/set-recent-updates-like.do";
    
}

