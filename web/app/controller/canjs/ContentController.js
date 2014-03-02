/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
var gid ; 
( function(){
    var activityid;
    
    ContentController = can.Control({
        defaults:{
            recentUpdates : BASE_URL+"/app/ejs/recent-updates.ejs"
        }
        
        },{
            init: function(){
                var self = this;
                Models.RecentUpdates.findAll({},function(data){
                    $(self.element).html(can.view(self.options.recentUpdates,data));
                  
                    $("#reply").click(function() {
                        
                        activityid=$(this).attr("name");
                        var txt='#'+activityid+'_ta';
                        var btn='#'+activityid+'_btn';
                        gid=btn;
                        alert(gid);
                        $(txt).show();  $(btn).show();  
                     });
                   $(gid).click(function() {
                        var txt='#'+activityid+'_ta';
                        alert($(txt).val());
                     });  
                });
            }
        });
        
            
})();