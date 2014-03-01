var cellEditing = Ext.create('Ext.grid.plugin.CellEditing', {
    clicksToEdit: 1,
     ptype: 'datatip',
     tpl: 'Click to edit'
});

Ext.define('MyApp.view.class.ClassExam' ,{
    extend: 'Ext.grid.Panel',
    alias: 'widget.classexamlist',
    closable:true,
    title: 'Create Exam For Class',
    id:'classexamgrid',
    layout:'fit',
    store:'ClassExam',    
    selModel: {
        selType: 'cellmodel'
    },
    plugins: [cellEditing],
    tbar :[{
       xtype: 'combo',
       emptyText: 'Select Session',
       text   : 'Session',
       id:'sessioncombo',
       store:Ext.create('MyApp.store.Combo').load({
                                      params:{propertyId:-1}}),//For Session
       typeAhead: true,
       queryMode: 'local',
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
           }
       }
    },{
       xtype: 'combo',
       emptyText: 'Select Exam Type',
       text   : 'Exam Type',
       id:'examtypecombo',
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
    },{
        iconCls: 'icon-add',
        text: '<b>View Exam Details</b>',
        listeners:{
          render: function(component){
                component.getEl().on('click', function(){
                    
                var classid     =Ext.getCmp('classcombo').getValue();                
                var sessionid   =Ext.getCmp('sessioncombo').getValue();                
                var examtypeidid=Ext.getCmp('examtypecombo').getValue();                
                var data={'classid':classid,'sessionid':sessionid,'examtypeidid':examtypeidid};
                Ext.getCmp('classexamgrid').getStore().reload({
                                      params:{'classid':classid,'sessionid':sessionid,'examtypeidid':examtypeidid}}
                );
            });
         }       
        }   
    },{
        iconCls: 'icon-add',
        text: '<b>Create Exam</b>',
        listeners:{
             render: function(component){
                component.getEl().on('click', function(){                
                var classid     =Ext.getCmp('classcombo').getValue();                
                var sessionid   =Ext.getCmp('sessioncombo').getValue();                
                var examtypeidid=Ext.getCmp('examtypecombo').getValue();                
                var data={'classid':classid,'sessionid':sessionid,'examtypeidid':examtypeidid};
                Ext.Ajax.request({
                url:'classexam/add.do',
                type:'json',
                scope:this,
                headers:{
                    'Content-Type':'application/json'  
                },
                params:Ext.JSON.encode(data),
                success: function(res){
                   var res1 = eval('('+res.responseText+')');
                   if(res1=='1'){
                   Ext.Msg.alert('Warning','Exam Already created for this Class');
                   Ext.getCmp('classexamgrid').getStore().reload({
                                      params:{'classid':classid,'sessionid':sessionid,'examtypeidid':examtypeidid}});                   
                   }
                   if(res1=='2')
                   Ext.Msg.alert('Failure','Subject not created for this class,Please create Subject');    
                   if(res1=='0') {
                   Ext.Msg.alert('Success','Exam Create Successfully');                                        
                   Ext.getCmp('classexamgrid').getStore().reload({
                                      params:{'classid':classid,'sessionid':sessionid,'examtypeidid':examtypeidid}});                                      
                   }
                }  
               });
          });
        }
      } 
    },
    {
        iconCls: 'icon-add',
        text: '<b>Save Exam</b>',       
        listeners:{
             render: function(component){
           component.getEl().on('click', function(){
           var grid =  Ext.getCmp('classexamgrid');
           var store=  Ext.getStore('Timetable');
           var records = Ext.StoreManager.lookup('ClassExam').getModifiedRecords();
           var data = []; 
            Ext.each(records, function(rec1){                
       
                rec1.data.starttime=new Date(rec1.data.starttime).getTime();            
                rec1.data.endtime  =new Date(rec1.data.endtime).getTime();            
                rec1.data.examdate =new Date(rec1.data.examdate).getTime();                            
                data.push(rec1.data);                
            });
   
            Ext.Ajax.request({
                url:'classexam/save.do',
                type:'json',
                scope:this,
                headers:{
                    'Content-Type':'application/json'  
                },
                params:Ext.JSON.encode(data),
                success: function(res){

                   if(res.responseText==='1')
                   Ext.Msg.alert('Success','Exam Saved Seccessfully');
                   else
                   Ext.Msg.alert('Failure','Error Occured, Please Contact Admin');    
                }
                });
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
    }]
    }),
    initComponent: function() {
        
        var me=this;
        
        this.teacherStore=Ext.create('MyApp.store.Combo');
        this.teacherStore.load({
            params:{propertyId:5}
        });

        this.examtypeStore=Ext.create('MyApp.store.Master');
        this.examtypeStore.load({
               params:{propertyId:5}
        }),//For Exam Type
                  
        this.SessionStore=Ext.create('MyApp.store.Master');
        this.SessionStore.load({
               params:{propertyId:6},callback :function(){
                   Ext.getCmp('classexamgrid').getStore().reload();
               }
        }),
        this.columns=[
        Ext.create('Ext.grid.RowNumberer'),
        {
            header: 'Class Name',
            dataIndex:'classname',
            style :'color:#17385B;font-weight:bold'
        },
        {
          
            header    : 'Subject',
            dataIndex :'subjectname',
            style :'color:#17385B;font-weight:bold'
        },{
          
            header    : 'Exam Type',
            dataIndex :'examtypeid',
            style :'color:#17385B;font-weight:bold',
            
            editor: {
                store :this.examtypeStore,
                xtype : 'combo',
                allowBlank : false,                    //xtype: 'numberfield'
                triggerAction : 'all',
                queryMode :'local',
                valueField :'id',
                displayField :'value'
                
            },
            renderer : function(value){
                var record=me.examtypeStore.findRecord('id',value);
                return record?record.data.value:'';
            }
        },{
            header: 'Exam Name',
            dataIndex:'examname',
            style :'color:#17385B;font-weight:bold',
            editor :{
                    xtype:'textfield'
                }
        },{
          
            header    : 'Current Session',
            dataIndex :'sessionid',
            style :'color:#17385B;font-weight:bold',
           
            editor: {
                store :this.SessionStore,
                xtype : 'combo',
                allowBlank : false,                    //xtype: 'numberfield'
                triggerAction : 'all',
                queryMode :'local',
                valueField :'id',
                displayField :'value'
                
            },
            renderer  : function(value){
                var record=me.SessionStore.findRecord('id',value);
                return record?record.data.value:'';
            }
        },{
                width: 125,
                sortable: true,      
                editor :{
                xtype: 'datefield'},
                name : 'examdate',
                header:'Exam Date',
                dataIndex:'examdate', 
                
                //renderer: Ext.util.Format.dateRenderer('d:m:Y'),                
                /*renderer : function(value){
            		return Ext.Date.format(new Date(value),DEFAULT_DATE_FORMAT);
                },*/
                id:'examdate',
                format: 'm d Y',
                altFormats: 'm-d-Y|m.d.Y',
                style :'color:#17385B;font-weight:bold'
        },{
            header : 'Start Time',
            dataIndex:'starttime',           
            style :'color:#17385B;font-weight:bold',
           // renderer: Ext.util.Format.dateRenderer('g:i A'),
           // summaryRenderer: Ext.util.Format.dateRenderer('g:i A'),            
            editor :{
                    xtype: 'timefield',
                    minValue: '6:00 AM',
                    maxValue: '8:00 PM',
                    format: 'H:i'
                }            
        },{
            header : 'End Time',
            dataIndex:'endtime',
            style :'color:#17385B;font-weight:bold',
           // renderer: Ext.util.Format.dateRenderer('g:i A'),
            //summaryRenderer: Ext.util.Format.dateRenderer('g:i A'),                        
            editor :{
            xtype: 'timefield',
            minValue: '6:00 AM',
            maxValue: '8:00 PM',
            increment: 30,
            anchor: '100%',
            style :'color:#17385B;font-weight:bold'
            }
        },{
            header :'Invigilotor Name',
            dataIndex:'teacherid',
            style :'color:#17385B;font-weight:bold',
            editor: {
                store :this.teacherStore,
                xtype : 'combo',
                allowBlank : false,                    
                triggerAction : 'all',
                queryMode :'local',
                valueField :'id',
                displayField :'value'                
            },
            renderer : function(value){
                var record=me.teacherStore.findRecord('id',value);
                return record?record.data.value:'';
            }
        },{
            header:'Max Mark',
            dataIndex:'maxmark',
            style :'color:#17385B;font-weight:bold',
            editor :{
                    xtype: 'numberfield',
                    anchor: '100%',
                    maxValue: 100,
                    minValue: 0
            }
        },
        {
            header:'Pass Mark',
            dataIndex:'passmark',
            style :'color:#17385B;font-weight:bold',
            editor :{
                    xtype: 'numberfield',
                    anchor: '100%',
                    maxValue: 100,
                    minValue: 0

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





