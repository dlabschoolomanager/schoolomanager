/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

( function(){
    QuickLinksController = can.Control({
        defaults:{
            view : BASE_URL+"/app/ejs/quicklink.ejs",
            timeTableView: BASE_URL+"/app/ejs/timetable.ejs",
            $content:null
        }
    },{
        init: function(){
            var self = this;
            $.ajax({
                url:'dashboard/get.do?id=Widget_QUICKLINKS',
                dataType:'json',
                success : function(response){
                      var data = new can.Observe(response);
                      console.log(data);
                      $(self.element).html(can.view(self.options.view,data));
                }
            })
            this.options.$content=$("#content-body");
        },
        '.quick-links click': function(el, event){
            var callback = $(el).attr('callback');
            console.log(callback);
            this[callback]();
        },
        createTimeTable: function(){      
             var self =this;
             $.ajax({
                url:'timetable/get.do?classid=d6f54930-7210-4bf3-ab90-3976752d4d9a&sessionid=00a24b9a-5bb2-4466-b629-f9d91de9e551&teacherid=',
                dataType:'json',
                success : function(response){
                    console.log(self.options.$content);
                      self.options.$content.html(can.view(self.options.timeTableView,
                         response.rows));
                }
            })
        },
        showHomeWork: function(){
           
        var self =this;
             $.ajax({
                url:'homework/get.do?sessionid=00a24b9a-5bb2-4466-b629-f9d91de9e551&classid=d6f54930-7210-4bf3-ab90-3976752d4d9a&createdby=19e2e30f-9ada-41fa-95bd-c9d499b88fe2',
                dataType:'json',
                success : function(response){
                     self.options.$content.html(response.rows);
                }
            }) 
    },
        showDigitalDairy: function(){
             var self =this;
             $.ajax({
                url:'digitaldairy/get.do?classid=d6f54930-7210-4bf3-ab90-3976752d4d9a&studentid=aed0623d-8309-47b3-8b94-af6756a1a16e&createdby=19e2e30f-9ada-41fa-95bd-c9d499b88fe2&sessionid=00a24b9a-5bb2-4466-b629-f9d91de9e551',
                dataType:'json',
                success : function(response){
                      console.log(response);
                     // $(self.element).html(can.view(self.options.view,data));
                }
            })
        },
        showReportCardToParent: function(){
             var self =this;
             var data = '{"classid":"d6f54930-7210-4bf3-ab90-3976752d4d9a","sessionid":"00a24b9a-5bb2-4466-b629-f9d91de9e551","examtypeidid":"94c7380d-cf7f-4c40-8c77-a17307b7d237","studentid":"486375a5-26ea-4826-9a55-65e609eeb3e4","subjectid":"null"}'
             $.ajax({
                url:'reportcard/get.do',
                data:data,
                method:'POST',
                headers:{
                    'Content-Type':'application/json'  
                },
                dataType:'json',
                success : function(response){
                       self.options.$content.html(response.rows);
                }
            })   
        },
        showFeeDetailsParent:function(){
            var self =this;
             $.ajax({
                url:'studentmonthlyfeeparent/get.do?userid=0e041a43-2930-4364-89aa-37058858a9c2&sessionid=00a24b9a-5bb2-4466-b629-f9d91de9e551',
                dataType:'json',
                success : function(response){
                     self.options.$content.html(response.rows);
                }
            })   
        },
        showTutorial:function(){
             $.ajax({
                url:'tutorial/get.do?_dc=1392546698282&sessionid=00a24b9a-5bb2-4466-b629-f9d91de9e551&isparent=0&batchid=1c00279b-5fde-4586-93a1-46618e73aaf3',
                dataType:'json',
                success : function(response){
                     
                }
            }) 
        },
         showPaymentHistory: function(){
             $.ajax({
                url:'timetable/get.do?classid=d6f54930-7210-4bf3-ab90-3976752d4d9a&sessionid=00a24b9a-5bb2-4466-b629-f9d91de9e551',
                dataType:'json',
                success : function(response){
                      console.log(data);
                     // $(self.element).html(can.view(self.options.view,data));
                }
            })  
        }
    })     
})();