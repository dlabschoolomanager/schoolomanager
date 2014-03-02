 SETTING.Users = new Models.Users({
        userId:'',
        name:''
 });
 SETTING.student = new Models.Student({
     id:''
 });
 
 SETTING.teacher = new Models.Teacher({
     studentid:'',
     sessionid:''
 });
 
 
 (function(){
   $.ajax({
       url:'user/getUser.do',
       dataType:'json',
        success : function(response){
            loadStudentProfile(response.properties.studentid);
            //loadTeacherDetails(response.properties.studentid,response.properties.sessionid);
            var u = SETTING.Users;
            u.attr('userId',response.userId);
            u.attr('name',response.name);
            u.attr('dob',response.dob);
            u.attr('roleId',response.roleId);
            u.attr('permValue',response.permValue);
            u.attr('salutation',response.salutation);
            u.attr('gender',response.gender);
            u.attr('emailId',response.emailId);
            u.attr('address',response.address);
            u.attr('designation',response.designation);
            u.attr('teachertype',response.teachertype);
            u.attr('jobtype',response.jobtype);
            u.attr('contactNo',response.contactNo);
            u.attr('city',response.city);
            u.attr('userName',response.userLogin.userName);
            u.attr('schoolid',response.properties.schoolid);
            u.attr('session_id',response.properties.session_id);
            u.attr('schoolname',response.properties.schoolname);
            u.attr('year',response.properties.year);
            u.attr('studentid',response.properties.studentid);         
        }
   });
})();

function loadStudentProfile(studentId){
    $.ajax({
       url:'mis/student/'+studentId,
       dataType:'json',
        success : function(response){
          var s = SETTING.student;
          var res = response.rows[0];
          console.log("result_set",res);
          s.attr('fname',res.fname);
          s.attr('lname',res.lname);
          s.attr('roll',res.addmission_no);
          s.attr('gender',res.gender);              
          s.attr('dob',res.dob);              
          s.attr('nationality',res.nationality);              
          s.attr('religion',res.religiontxt);              
          s.attr('age',res.age);              
          s.attr('street',res.address);              
          s.attr('city',res.city);              
          s.attr('district',res.city);              
          s.attr('state',res.state);              
          s.attr('zipCode',res.postalcode);              
          s.attr('fathername',res.fathername);              
          s.attr('mothername',res.mothername);              
          s.attr('contact',res.parentmobile);              
         //initializeContentController(res.rows[0].userid);
        }
   });
}

function loadTeacherDetails(studentId,sessionid){
    $.ajax({
       url:'mis/getstudclassteachers.do?studentId='+studentId+'&sessionid='+sessionid,
       dataType:'json',
        success : function(response){
          var s = SETTING.teacher;
          var res = response.rows[0];
          console.log("result_set",res);
          s.attr('fname',res.fname);
          s.attr('lname',res.lname);
          s.attr('roll',res.addmission_no);
          s.attr('gender',res.gender);              
          s.attr('dob',res.dob);              
          s.attr('nationality',res.nationality);              
          s.attr('religion',res.religiontxt);              
          s.attr('age',res.age);              
          s.attr('street',res.address);              
          s.attr('city',res.city);              
          s.attr('district',res.city);              
          s.attr('state',res.state);              
          s.attr('zipCode',res.postalcode);              
          s.attr('fathername',res.fathername);              
          s.attr('mothername',res.mothername);              
          s.attr('contact',res.parentmobile);              
         //initializeContentController(res.rows[0].userid);
        }
   })
}

$(function(){
   
   new TopController('#app-header',{
       user:SETTING.Users
   });
   new BradcrumbController('#app-header',{
       
   });
   new QuickLinksController('#quick-links',{
       
   });
   new ProfileController('#student-body',{
       student:SETTING.student
   });
   new WidgetController('#widget-list',{
       
   });
   new ContentController('#content-body',{
       
   })
   $('#student-content-body').layout({ 
      applyDefaultStyles: true,
      east__size:350
   });
});
