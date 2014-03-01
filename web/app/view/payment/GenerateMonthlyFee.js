var color;

function AutomateFeeGeneration(){
    
    var win;
    if(!win){
        win = Ext.create('Ext.app.view.component.AppWindow', {
            title:'',
            id: 'automate_win',
            width:400,
            closeAction:'destroy',
            top:{
                image:BASE_URL+'resources/images/createuser.png',
                formTitle:'Automate Fee Generation'
            },
            defaults:{
                xtype:'textfield',
                value:'',
                width:300
            },
            url:'ppppp',
            formItems :[
            {
                xtype:'datefield',
                name : 'doa',
                fieldLabel: 'Date of Fee Generation',
                id:'doa',
                format: 'd',
                altFormats: 'd|d' 
            },
            {
                xtype:'textfield',
                name : 'time',
                fieldLabel: 'Time',
                id:'time'                
            },        
            {
                xtype:'combobox',
                fieldLabel :'Select',
                id:'select',
                emptyText: 'Select',       
                store:
                Ext.create('Ext.data.Store', {
                    fields: ['abbr', 'name'],
                    data : [
                    {
                        "abbr":"AM",
                        "name":"AM"
                    },{
                        "abbr":"PM",
                        "name":"PM"
                    }]
                }),
                Autoload:true,
                queryMode: 'local',
                displayField: 'name',
                valueField: 'abbr',
                name:'type'

           } 
            ],
            buttons :[
            {
                text: 'Add',
                action: 'save',
                scope:this,
                handler:saveData                
            },
            {xtype:'btncancel'}
            ]
        });
    }
    
    win.show();
}

function generateFee(btn){
 
      var form = btn.up('window').down('form').getForm();
      if(form.isValid()){
            var obj = form.getValues();
                obj.duedate=new Date(obj.duedate).getTime();
                Ext.Ajax.request({
                url:'generatefee/add.do',
                type:'json',
                headers:{
                     'Content-Type':'application/json'
                },         
                params:Ext.JSON.encode(obj),
                success: function(res){
                    Ext.Msg.alert('Success','Monthly Fee Generated successfully');
                }
            });
        }
    
}

function saveData(btn){
   
      var form = btn.up('window').down('form').getForm();
      var grid =  Ext.getCmp('periodgrid');
      //var recs =grid.getSelectionModel().getSelection();
        if(form.isValid()){
            var obj = form.getValues();

            Ext.Ajax.request({
                url:'period/add.do',
                type:'json',
                headers:{
                    'Content-Type':'application/json'
                },
                params:Ext.JSON.encode(obj),
                success: function(res){
                    Ext.Msg.alert('Success','Class added successfully');
                 //   var rec = eval('('+res.responseText+')');
                 //   app.getController('Class').getClassStore().add(rec);
                }
            });
        }
}
 
 
 Ext.define('MyApp.view.payment.GenerateMonthlyFee' ,{
    extend: 'Ext.grid.Panel',
    closable:true,
    title: 'Generate Monthly Fee Grid',
    id:'GenerateMonthlyFee',
    layout:'fit',
    alias: 'widget.GenerateMonthlyFee',
    viewConfig:{
        forceFit:true,
        emptyText:'<div class="no-results">No Results To display</div>'
    },    
    store:'CreatedFeeForClass',
    
    initComponent: function() {

    this.columns=[
    Ext.create('Ext.grid.RowNumberer'),

        {id:'srv', header: 'Class', sortable: true, dataIndex: 'classname',
         style :'color:#17385B;font-weight:bold',
         width:'10%',
         renderer: function(value){
             
             if(color==null)color=value;
             
             if(color==value){              
             return '<font color=black><b>'+value+'</b></font>'        
             
             }
             else{
                color=value;
                return '<font color=green><b>'+value+'</b></font>'
             }
         }
        },
        {header: 'Year',  sortable: true, dataIndex: 'for_year',
        style :'color:#17385B;font-weight:bold',
        width:'10%'
        },
        {header: 'Month',  sortable: true, dataIndex: 'for_month',
        style :'color:#17385B;font-weight:bold',
        width:'20%'
        },        
    
        {header: 'Template', sortable: true,  dataIndex: 'template',
        style :'color:#17385B;font-weight:bold',
        width:'10%'
        },        
        {header: 'Due Date', sortable: true,  dataIndex: 'due_date',
        style :'color:#17385B;font-weight:bold',
        width:'10%'},
        {header: 'Total Calculated',  sortable: true,  dataIndex: 'amount',
        style :'color:#17385B;font-weight:bold',
        width:'10%'},
        {header: 'Total Received', sortable: true,  dataIndex: 'tot_received',
        style :'color:#17385B;font-weight:bold',
        width:'10%'},
        {header: 'Generated By',  dataIndex: 'createdby',
        style :'color:#17385B;font-weight:bold',
        width:'10%'},
        {header: 'Generated Date',  dataIndex: 'createdon',width    :200,
        style :'color:#17385B;font-weight:bold',
        width:'10%'}
    ];
    this.selModel=Ext.create('Ext.selection.CheckboxModel',{
        singleSelect:true
    });
    this.tbar =[/*{
        xtype: 'searchfield',
        store: Ext.create('Ext.data.Store', {
            autoLoad: false,
            fields:['id','name'],
            proxy: {
                type: 'ajax',
                url: 'users.json',
                reader: {
                    type: 'json',
                    root: 'users'
                }
            }
        })
    },*/{
        xtype:'combobox',
        id:'session',
        emptyText: 'Select Session',       
        store:Ext.create('MyApp.store.Combo').load({
                                      params:{propertyId:-1}}),//For Session
        Autoload:true,
        queryMode: 'local',
        displayField: 'value',
        valueField: 'id',
        name:'type',
        value:SETTING.Users.properties.session_id,        
        listeners:{
            select: function(component){
                var sessionid=Ext.getCmp('session').getValue();                                                
                Ext.getCmp('classcombo').getStore().load({
                     params:{
                             propertyId:2,///Class List
                             classid   :sessionid,
                             teacherid :SETTING.Users.userId
                     }
               });
                  

            }
       }

    },
    {
       xtype: 'combo',
       emptyText: 'Select Class',
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
                var id=Ext.getCmp('classcombo').getValue();
                
            }
       }
    },
    {
       xtype: 'combobox',
       emptyText: 'Select Month',
       id:'month',
       store:Ext.create('Ext.data.Store', {
            fields: ['id', 'value'],
            data : [
            {"id":"1","value":"January"},
            {"id":"2","value":"February"},
            {"id":"3","value":"March"},
            {"id":"4","value":"April"},
            {"id":"5","value":"May"},
            {"id":"6","value":"June"},
            {"id":"7","value":"July"},
            {"id":"8","value":"August"},            
            {"id":"9","value":"September"},
            {"id":"10","value":"October"},
            {"id":"11","value":"November"},
            {"id":"12","value":"December"},
            {"id":"13","value":"All Month"}
        ]
        }),
       typeAhead: true,
        queryMode: 'local',
        displayField: 'value',
        valueField: 'id',
        name:'type',
        listeners:{
            select: function(component){
                
            }
       }
    },{
        xtype:'button',
        text:'<b>View Fee</b>',        
        iconCls: 'icon-add',
        tooltip:'Click to View Fee detail by selecting class and student',
        listeners:{
            render: function(component){
                component.getEl().on('click', function(){                
                var sessionid=Ext.getCmp('session').getValue();
                var classid  =Ext.getCmp('classcombo').getValue();
                var month=Ext.getCmp('month').getValue();
                if(sessionid==null){
                    Ext.Msg.alert('Warning','Please Select Session');
                }
                else{
                    if(month==null)
                          month=0;                      
                        Ext.getCmp('GenerateMonthlyFee').getStore().reload({
                                    params:{'classid':classid,
                                            'sessionid':sessionid,
                                            'month':month
                            }});
               }
            });

           }
        }
    },{
        xtype:'button',
        text:'<b>Generate Fee</b>',
        iconCls: 'icon-add',
        tooltip:'Generate monthly fee for select month',
        listeners:{
            render: function(component){
                component.getEl().on('click', function(){                
                
                var sessionid=Ext.getCmp('session').getValue();
                var classid  =Ext.getCmp('classcombo').getValue();
                var month=Ext.getCmp('month').getValue();
                if(classid!=null && month!=null) {                
                    var win;
                    if(!win){
                        win = Ext.create('Ext.app.view.component.AppWindow', {
                            title:'',
                            id: 'generate_win',
                            width:400,
                            closeAction:'destroy',
                            top:{
                                image:BASE_URL+'resources/images/createuser.png',
                                formTitle:'Fee Generation for Class :'+Ext.getCmp('classcombo').getRawValue()+ ', For Month :'+Ext.getCmp('month').getRawValue()
                            },
                            defaults:{
                                xtype:'textfield',
                                value:'',
                                width:300
                            },
                            url:'ppppp',
                            formItems :[
                            {
                                xtype:'datefield',
                                name : 'duedate',
                                fieldLabel: 'Due Date of Fee Payment',
                                id:'duedate',
                                format: 'm d Y',
                                altFormats: 'm-d-Y|m.d.Y'
                            },
                            {
                                xtype:'textfield',
                                name : 'classid',
                                value :classid,
                                id:'classid',
                                hidden:true
                            },        
                            {
                                xtype:'textfield',
                                name : 'sessionid',
                                value :sessionid,
                                id:'sessionid',
                                hidden:true
                            },        
                            {
                                xtype:'textfield',
                                name : 'monthid',
                                value :month,
                                id:'monthid',
                                hidden:true
                            }        
                            ],
                            buttons :[
                            {
                                text: 'Add',
                                action: 'save',
                                scope:this,
                                handler:generateFee                
                            },
                            {xtype:'btncancel'}
                            ]
                        });
                    }
                    win.show();

            /*    var sessionid=Ext.getCmp('session').getValue();
                var classid  =Ext.getCmp('classcombo').getValue();
                var month=Ext.getCmp('month').getValue();
                var data={'classid':classid,'sessionid':sessionid,monthid:month};
                Ext.Ajax.request({
                url:'generatefee/add.do',
                type:'json',
                headers:{
                     'Content-Type':'application/json'
                },         
                params:Ext.JSON.encode(data),
                success: function(res){
                    Ext.Msg.alert('Success','Monthly Fee Generated successfully');
                }
            });*/
             }
             else{
                    Ext.Msg.alert('Warning','Please select Class and Month to generate Fee');
             }
            });
           
           }
        }
    },
    {
        xtype:'button',
        text:'<b>Add to Monthly Fee</b>',
        iconCls: 'icon-add',
        tooltip:'Add More fee to monthly fee',
        listeners:{
            render: function(component){
                component.getEl().on('click', function(){
                    var rec=Ext.getCmp('notificationgrid').getSelectionModel().getSelection()[0];    
                    AddtoMonthlyFee(rec);
                    });

            }
        }
    },        
    {
        xtype:'button',
        text:'<b>Automate Generate Fee</b>',
        iconCls: 'icon-add',
        tooltip:'Automate Fee Generation on particular date on month',
        listeners:{
            render: function(component){
                component.getEl().on('click', function(){
                       AutomateFeeGeneration();
                    });

            }
        }
    }        
    ];
    this.bbar = Ext.create('Ext.PagingToolbar', {
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
                       
                    });

            }
        }
    }]
    });
    this.callParent(arguments);
    },
    onRender : function(){
        //this.selModel.on('selectionchange', this.onSelectChange);
        this.callParent(arguments);
    },
    selectionchange : function(sm, selected,eOpts){
        if(sm.getCount()){
              
    }
    }
});
