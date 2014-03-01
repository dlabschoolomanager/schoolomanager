function displayReport(rec){

   if(rec.data.id==4 && rec.data.name=='Attendence Report'){ 
    var app1=app.getController('Dashboard')
    var Tab = Ext.create('MyApp.view.misreport.AttendenceReport');
    app1.getDashboard().add(Tab);
    app1.getDashboard().setActiveTab(Tab);  
   }
   else if(rec.data.id==2 && rec.data.name=='Payment Report'){
    var app1=app.getController('Dashboard')
    var Tab = Ext.create('MyApp.view.misreport.PaymentReports');
    app1.getDashboard().add(Tab);
    app1.getDashboard().setActiveTab(Tab);  
   }
   else if(rec.data.id==22 && rec.data.name=='Monthly Class Fee'){
    var app1=app.getController('Dashboard')
    var Tab = Ext.create('MyApp.view.misreport.PaymentMonthlyReport');
    app1.getDashboard().add(Tab);
    app1.getDashboard().setActiveTab(Tab);  
   }
   else if(rec.data.id==25 && rec.data.name=='Monthly Student Attendence Reports'){
    var app1=app.getController('Dashboard')
    var Tab = Ext.create('MyApp.view.misreport.ClassAttendenceForMonthReport');
    app1.getDashboard().add(Tab);
    app1.getDashboard().setActiveTab(Tab);  
   }

   else{
       Ext.Msg.alert("This Report is in development phase");     
   }
}


Ext.define('MyApp.view.misreport.MisReport' ,{
    extend: 'Ext.grid.Panel',
    closable:true,
    title: 'School Report Management',
    id:'reportgrid',
    layout:'fit',
    alias: 'widget.reportgrid',
    viewConfig:{
        forceFit:true,
        emptyText:'<div class="no-results">No Results To display</div>'
    },    
    store:'MisReport',
    
    initComponent: function() {

    this.columns=[
    Ext.create('Ext.grid.RowNumberer'),
    {
        header: 'id',
        dataIndex:'id',
        hidden   :true
    },
    {
        header: '<font color=#17385B><b>Report Name</b></font>',
        dataIndex:'name',
        width    :'27%',
        renderer : function(value){
                  return '<b>'+value+'</b>';
        }       
        
    },
    {
        header:'<font color=#17385B><b>Description</b></font>',
        dataIndex:'description',
        width    :'27%'
    },
    {
        header:'<font color=#17385B><b>Status</b></font>',
        dataIndex:'status',
        width    :'10%',
        renderer : function(value){
            	  if(value==='Active') 
                  return '<font color=green><b>'+value+'</font></b>';
                  else
                  return '<font color=red><b>'+value+'</font></b>';    
        }       
    },{
        header:'<font color=#17385B><b>Created By</b></font>',
        dataIndex:'createdby',
        width    :'15%'
        
    },{
        header:'<font color=#17385B><b>Created On</b></font>',
        dataIndex:'createdon',
        width    :'15%'
    }
    ];
    this.selModel=Ext.create('Ext.selection.CheckboxModel',{
        singleSelect:true,
        listeners:{
                selectionchange:function(){

                /*   var  button = Ext.getCmp('noticeEdit');
                   button.setDisabled(false);
                   var  delbutton = Ext.getCmp('noticeDelete');
                   delbutton.setDisabled(false);*/
                }
            }
    });
    this.tbar =[{
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
    },{
       xtype: 'combobox',
       emptyText: 'Select Report Type',
       id:'reporttype',
       store:Ext.create('MyApp.store.Master').load({
                                      params:{propertyId:35}}),//For Session,
       typeAhead: true,
       queryMode: 'local',
       displayField: 'value',
       valueField: 'id',
       name:'reporttype',
        listeners:{
            select: function(component){
                var reporttype=Ext.getCmp('reporttype').getValue();                
                if(reporttype!=null)    
                {
                     Ext.getCmp('reportgrid').getStore().load({
                     params:{                             
                         'reporttypeid':reporttype
                     }
                     });
                }    

            }
       }
    },{
        iconCls: 'icon-add',
        text: '<font color:"#17385B"><b>Run Report</b></font>',
        listeners:{
            render: function(component){
                component.getEl().on('click', function(){                    
                    //addNotice(null);
                });

            }
        }
    },{
        iconCls: 'icon-add',
        text: '<font color:"#17385B"><b>Report on New Tab</b></font>',
        listeners:{
            render: function(component){
                component.getEl().on('click', function(){                    
                   var rec=Ext.getCmp('reportgrid').getSelectionModel().getSelection()[0];
                   displayReport(rec);
                });

            }
        }
    },{
        xtype:'splitbutton',
        text:'<font color:"#17385B"><b>Export Data</b></font>',
        arrowAlign:'right',
        menu: [{text: '<font color:"#17385B"><b>PDF</b></font>'},{text: '<font color:"#17385B"><b>Excelsheet</b></font>'}],
        listeners:{
            render: function(component){
                component.getEl().on('click', function(){
                    //addFeeTemplate();
                    })

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






