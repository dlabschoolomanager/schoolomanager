Ext.define('MyApp.view.AuditTrail' ,{
    extend: 'Ext.grid.Panel',
    closable:true,
    title: 'Audit Trail',
    layout:'fit',
    viewConfig:{
        forceFit:true
    },
    constructor: function(config){
        this.store = Ext.create('Ext.data.Store',{
            fields: ['details', 'ts','ipaddr','actionby'],
            proxy: {
                type: 'ajax',
                url: 'user/auditTrail.do',
                reader: {
                    type: 'json',
                    root: 'rows'
                }
            },
            autoLoad:true
        }),
        this.callParent(Ext.apply([{
            columns:[
            Ext.create('Ext.grid.RowNumberer'),
            {
                header: 'Action',  
                dataIndex: 'details',
                flex:2
            },{
                header: 'Action By',  
                dataIndex: 'actionby',
                flex:1
            },{
                header: 'Time',  
                dataIndex: 'ts',
                flex:1,
                renderer: function(v){
                    
                }
            },{
                header: 'IP Address',  
                dataIndex: 'ipaddr',
                flex:1
            }
            ],
            
            tbar :[{
                xtype: 'searchfield',
                width:200,
                emptyText:'Search by action, action by..',
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
            },'|', {
                xtype:'datefield',
                emptyText:'Start date'
            },{
                xtype:'datefield',
                emptyText:'End Date'
            }]
         
        },config])); 
         
    },
    initComponent: function() {
        //        
        //        this.selModel = Ext.create('Ext.selection.CheckboxModel',{
        //            singleSelect:true
        //        });
        //        this.bbar = Ext.create('Ext.PagingToolbar', {
        //            store: this.store,
        //            displayInfo: true,
        //            displayMsg: 'Displaying users {0} - {1} of {2}',
        //            emptyMsg: "No user to display"
        //        }),
        //app.getController('Master').init();
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

