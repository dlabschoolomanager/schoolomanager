function reportcard(alldata){

    Ext.create('MyApp.view.parent.ReportCardWindow',{
          title:'Student Report Card',
          reportCard:alldata
      }).show();
}


Ext.define('MyApp.view.parent.ReportCardToParent' ,{
    extend: 'Ext.grid.Panel',
    alias: 'widget.reportcardtoparent',
    closable:true,
    title: 'Exam & Report Card',
    id:'reportcardtoparent',
    layout:'fit',
    store:'StudentMarkEntry',    
    selModel: {
        selType: 'cellmodel'
    },
    features: [{
        ftype: 'summary'
    }],
    tbar :[{
       xtype: 'combobox',
       emptyText: 'Select Session',
       id:'sessioncombo',
       store:Ext.create('MyApp.store.Combo').load({
                                      params:{propertyId:-1}}),//For Session
       queryMode: 'local',
       width : 100,        
       Autoload:true,
       valueField :'id',
       displayField :'value',  
       value:SETTING.Users.properties.session_id,
       listeners:{
            select: function(component){
                var sessionid=Ext.getCmp('sessioncombo').getValue();


               Ext.getCmp('classcombo').getStore().load({
                     params:{
                             propertyId:2,///Class List
                             classid   :sessionid,
                             teacherid :SETTING.Users.userId
                     }
               });
            }
       }
    },{
       xtype: 'combo',
       emptyText: 'Select Class',
       text   : 'Class',
       id:'classcombo',
       width : 100,        
       store:Ext.create('MyApp.store.ClassCombo1').load({
                                      params:{propertyId:2,
                                              classid:SETTING.Users.properties.session_id,
                                              teacherid :SETTING.Users.userId
                                    }}),
       typeAhead: true,
       queryMode: 'local',
       Autoload:true,
       valueField :'id',
       displayField :'value',  
       listeners:{
            select: function(component){
                var id=Ext.getCmp('classcombo').getValue();
                var sessionid=Ext.getCmp('sessioncombo').getValue();
                
                Ext.getCmp('studentcombo').getStore().load({
                     params:{
                             propertyId:7,///Student List
                             classid   :id+'&'+sessionid,
                             teacherid:SETTING.Users.userId
                     }
               });
            }
       }
    },{
       xtype: 'combo',
       emptyText: 'Select Exam Type',
       text   : 'Exam Type',
       id:'examtypecombo',
       width : 100,        
       store:Ext.create('MyApp.store.Master').load({
                                      params:{propertyId:5}}),//For Teacher
       typeAhead: true,
       queryMode: 'local',
       Autoload:true,
       valueField :'id',
       displayField :'value',  
       listeners:{
            select: function(component){
            }
       }
    },
    {
       xtype: 'combobox',
       emptyText: 'Select Student',
       text   : 'Student',
       id:'studentcombo',
       store:'ClassCombo',
       typeAhead: true,
       queryMode: 'local',
       Autoload:true,
       valueField :'id',
       displayField :'value',  
       listeners:{
            select: function(component){
                
            }
       }
    },{
        iconCls: 'icon-add',
        text: 'Exam Detail', 
        tooltip:'Examination date Timing Detail ',
        listeners:{
            render: function(component){
                component.getEl().on('click', function(){
                    
                var classid     =Ext.getCmp('classcombo').getValue();                
                var sessionid   =Ext.getCmp('sessioncombo').getValue();                
                var examtypeid=Ext.getCmp('examtypecombo').getValue();                
                var studentid   =Ext.getCmp('studentcombo').getValue();    
                var subjectid =null;
              });

            }
        }     
    },{
        iconCls: 'icon-add',
        text: 'View Result', 
        tooltip:'View Student Result',
        listeners:{
          render: function(component){
                component.getEl().on('click', function(){
                    
                var classid     =Ext.getCmp('classcombo').getValue();                
                var sessionid   =Ext.getCmp('sessioncombo').getValue();                
                var examtypeidid=Ext.getCmp('examtypecombo').getValue();                
                var studentid   =Ext.getCmp('studentcombo').getValue();    
                var subjectid =null;
                
                Ext.getCmp('reportcardtoparent').getStore().load({
                            params:{'classid':classid,
                                    'sessionid':sessionid,
                                    'examtypeidid':examtypeidid,
                                    'studentid':studentid,
                                    'subjectid':subjectid
                }});                
              });

            }
        }    
    },{
        iconCls: 'icon-add',
        text: 'View Report Card', 
        tooltip:'View Student Report Card',
        listeners:{
          render: function(component){
                component.getEl().on('click', function(){
                    
                var classid     =Ext.getCmp('classcombo').getValue();                
                var sessionid   =Ext.getCmp('sessioncombo').getValue();                
                var examtypeid=Ext.getCmp('examtypecombo').getValue();                
                var studentid   =Ext.getCmp('studentcombo').getValue();    
                var data={
                    'classid':classid,   
                    'sessionid':sessionid,
                    'examtypeidid':examtypeid,
                    'studentid':studentid,
                    'subjectid':null
                };
              
                Ext.Ajax.request({
                    url:'reportcard/get.do',
                    type:'json',
                    headers:{
                        'Content-Type':'application/json'
                    },
                    params:Ext.JSON.encode(data),
                    success: function(res){
                        var alldata = eval('('+res.responseText+')');
                        reportcard(alldata);
                    }
                });    
              });
              
              
              
       /*       var store=  Ext.getStore('StudentMarkEntry');
                
              Ext.create('MyApp.view.parent.ReportCardWindow',{
                    title:'Student Report Card',
                    reportCard:null
                }).show();*/
            }
        }      
    },{
        xtype:'button',
        text:'<b>Previous Result Comparison</b>',
        iconCls: 'icon-add',
        listeners:{
            render: function(component){
                component.getEl().on('click', function(){
                    //addFeeTemplate();
                });

            }
        }
    }    
    ],
     bbar : Ext.create('Ext.PagingToolbar', {
        store: this.store,
        displayInfo: true,
        displayMsg: 'Displaying users {0} - {1} of {2}',
        emptyMsg: "No user to display",
        items:[{
        xtype:'button',
        text:'Print',
        listeners:{
            render: function(component){
                component.getEl().on('click', function(){
                    //addFeeTemplate();
                    });

            }
        }
    },{
        xtype:'splitbutton',
        text:'Export Data',
        arrowAlign:'right',
        menu: [{text: 'PDF'},{text: 'Excelsheet'}],
        listeners:{
            render: function(component){
                component.getEl().on('click', function(){
                    //addFeeTemplate();
                    });

            }
        }
    },]
    }),
    initComponent: function() {
        
        var me=this;
        
        this.teacherStore=Ext.create('MyApp.store.Combo');
        this.teacherStore.load({
            params:{propertyId:1}
        });

        this.examtypeStore=Ext.create('MyApp.store.Master');
        this.examtypeStore.load({
               params:{propertyId:5}
        }),//For Exam Type
                  
        this.SessionStore=Ext.create('MyApp.store.Master');
        this.SessionStore.load({
               params:{propertyId:6}
        });
      /*  this.appearedStore=Ext.create('MyApp.store.Master');
        this.appearedStore.load({
               params:{propertyId:7},callback :function(){
                   Ext.getCmp('studentmarkentry').getStore().reload();
               } 
        });*/
        this.columns=[
        Ext.create('Ext.grid.RowNumberer'),
        {
          
            header    : 'Current Session',
            dataIndex :'sessionname',
            style :'color:#17385B;font-weight:bold'
        },
         {
          
            header    : 'Exam Type',
            dataIndex :'examtype' ,
            style :'color:#17385B;font-weight:bold'
        },{
            header: 'Exam Name',
            dataIndex:'examname',
            style :'color:#17385B;font-weight:bold'
        },
        {
            header: 'Class Name',
            dataIndex:'classname',
            style :'color:#17385B;font-weight:bold',
            hidden:true
        },
        {
          
            header    : 'Subject',
            dataIndex :'subjectname',
            style :'color:#17385B;font-weight:bold',
            renderer : function(value){
                  return '<font color=green><b>'+value+'</font></b>';
            }       

        },{
            header:'Max Mark',
            dataIndex:'maxmark',
            style :'color:#17385B;font-weight:bold',
            summaryType:'sum',
            renderer : function(value){
                  return '<font color=green><b>'+value+'</font></b>';
            }       
            
        },
        {
            header:'Pass Mark',
            dataIndex:'passmark',
            style :'color:#17385B;font-weight:bold',
            summaryType:'sum',
            renderer : function(value){
                  return '<font color=green><b>'+value+'</font></b>';
            }       
            
        },{
          
            header    : 'Appeared',
            dataIndex :'appeared',
            style :'color:#17385B;font-weight:bold',
            renderer : function(value){
                if(value=='1')
                return '<b>Present</b>';
                else
                return '<font color=red><b>Absent</b></font>';    
            }
        },
        {
            header:'Mark Obtained',
            dataIndex:'markobtained',
            style :'color:#17385B;font-weight:bold',
            summaryType:'sum',
            renderer : function(value,metadata,record){
            	  if(record.data.markobtained >= record.data.passmark) 
                  return '<font color=green><b>'+value+'</font></b>';
                  else{
                  return '<font color=red><b>'+value+'</font></b>';    
                  }
            }
            
        },
        {
            header:'Grade',
            dataIndex:'grade',
            style :'color:#17385B;font-weight:bold',
            renderer : function(value,metadata,record){
            }
        }         
        
        ];
        this.selModel = Ext.create('Ext.selection.CheckboxModel',{
            singleSelect:true
        });
        this.bbar = Ext.create('Ext.PagingToolbar', {
            store: this.store,
            displayInfo: true,
            displayMsg: 'Displaying users {0} - {1} of {2}',
            emptyMsg: "No user to display"
        }),

        this.callParent(arguments);
       // this.store.load();
    },

    onRender : function(){
        //this.selModel.on('selectionchange', this.onSelectChange);
        
        
        this.callParent(arguments);

    },
    selectionChange : function(sm, selected,eOpts){
        alert('fff');
        if(sm.getCount()){
            alert('fff');
        }
    }
});











