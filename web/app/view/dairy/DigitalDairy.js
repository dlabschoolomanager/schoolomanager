var dairydata=Ext.data.StoreManager.lookup(Ext.create('Ext.data.Store',{
    fields: ['teachername','subject','matter','postdate','type','priority','discription','reply','response'],
    data: [
    {
     teachername:'David Otham',
     subject:'English',
     matter:'XXXXX',
     postdate:new Date(),
     type:'Suggestion',
     priority:'<font color=blue>Normal</font>',
     discription:'Need to concentrate on English subject',
     reply:'<a href="">Click Me</a>',
     response:''
    },
    {
     teachername:'Kash Bonzavaski',
     subject:'Maths',
     matter:'Very week in Maths',
     postdate:new Date(),
     type:'Complain',
     priority:'<font color=red>High</font>',
     discription:'xxxxx',
     reply:'<a href="">Click Me</a>',
     response:''
    },
    {
     teachername:'Kash Bonzavaski',
     subject:'Science',
     matter:'Very week in Science',
     postdate:new Date(),
     type:'Complain',
     priority:'<font color=red>High</font>',
     discription:'xxxxx',
     reply:'<a href="#" onClick="addFeeStructure()">Click Me</a>',
     response:''
    },
     {
     teachername:'Kash Bonzavaski',
     subject:'Science',
     matter:'Very week in Science',
     postdate:new Date(),
     type:'Appriciation',
     priority:'<font color=green>High</font>',
     discription:'Good in All Subject',
     reply:'<a href="">Click Me</a>',
     response:''
    }
    ]/*,
        filters: [{
         property: 'name',
         value: /Ed/
     }]*/
}));

function addFeeStructure(){

    var win = Ext.getCmp('askteacher_win');
    if(!win){
        win = Ext.create('Ext.app.view.component.AppWindow', {
            title:'Add New Fee Structure',
            id:'askteacher_win',
            width:400,
            closeAction:'destroy',
            top:{
                image:BASE_URL+'resources/images/createuser.png',
                formTitle:'Ask Teacher'
            },
            defaults:{
                xtype:'textfield',
                value:'',
                width:300
            },
            url:'ppppp',
            formItems :[
            {
                name : 'title',
                fieldLabel: 'Title',
                id:'title'
            },
            {
                xtype:'combobox',
                fieldLabel :'Teacher',
                id:'teacher',
                store:
                Ext.create('Ext.data.Store', {
                    fields: ['abbr', 'name'],
                    data : [
                    {
                        "abbr":"1",
                        "name":"Rapson Joseph"
                    },
                    {
                        "abbr":"2",
                        "name":"Jilia Robert"
                    }
                    ]
                }),
                Autoload:true,
                queryMode: 'local',
                displayField: 'name',
                valueField: 'abbr'

            },
            {
                xtype:'combobox',
                fieldLabel :'Subject',
                id:'subject',
                store:
                Ext.create('Ext.data.Store', {
                    fields: ['abbr', 'name'],
                    data : [
                    {
                        "abbr":"1",
                        "name":"English"
                    },
                    {
                        "abbr":"2",
                        "name":"Hindi"
                    }
                    ]
                }),
                Autoload:true,
                queryMode: 'local',
                displayField: 'name',
                valueField: 'abbr'

            },{
                xtype:'textarea',
                name : 'comment',
                fieldLabel: 'Discription',
                id:'comment'
            }
            ],
            buttons :[
            {
                text: 'Add',
                action: 'save',
                scope:this,
                handler:function(){
                    var grid = Ext.getCmp('dairygrid');

                var r = Ext.create('MyApp.model.PaymentStructure', {

                        name:Ext.getCmp('name').getValue(),
                        type:Ext.getCmp('type').getValue(),
                        amount:Ext.getCmp('amount').getValue(),
                        frequency:Ext.getCmp('frequency').getValue(),
                        comment:Ext.getCmp('comment').getValue(),
                        session:Ext.getCmp('session').getValue(),
                        status:Ext.getCmp('status').getValue()
                });
                feedata.insert(0, r);
                }
            },
            {xtype:'btncancel'}
            ]
        })
    }
    win.show();
}

Ext.define('MyApp.view.dairy.DigitalDairy' ,{
    extend: 'Ext.grid.Panel',
    closable:true,
    title: 'Student Digital Dairy',    
    id:'dairygrid',
    layout:'fit',
    viewConfig:{
        forceFit:true,
        emptyText:'<div class="no-results">No Results To display</div>'
    },
    columns:[
    Ext.create('Ext.grid.RowNumberer'),
    
    {
        header:'Teacher Name',
        dataIndex:'teachername'
    },
    {
        header:'Subject',
        dataIndex:'subject'
    },
    {
        header:'Matter',
        dataIndex:'matter'
    },
    {
        header:'Posted Date',
        dataIndex:'postdate'
    },

    {
        header:'Type',
        dataIndex:'type'
    },

    {
        header:'Priority',
        dataIndex:'priority'
    },


    {
        header:'Discription',
        dataIndex:'discription'
    },
    
    {
        header:'Reply',
        dataIndex:'reply'
    },{
        header:'Response',
        dataIndex:'response'
    }

    ],
    store:dairydata,
    selModel:Ext.create('Ext.selection.CheckboxModel',{
        singleSelect:true,
        listeners:{
                selectionchange:function(){

                   var  button = Ext.getCmp('DairyEdit');
                   button.setDisabled(false);
                   var  delbutton = Ext.getCmp('Dairydelete');
                   delbutton.setDisabled(false);
                }
            }
    }),
    tbar :[{
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
        iconCls: 'icon-add',
        text: 'Ask Teacher',
        listeners:{
            render: function(component){
                component.getEl().on('click', function(){
                    addFeeStructure();
                })

            }
        }
    },{
        iconCls: 'icon-edit',
        text: 'Edit',
        disabled: true,
        id:'DairyEdit',
        scope:this,
        handler: function(component){
                    var rec=Ext.getCmp('dairygrid').getSelectionModel().getSelection()[0];
                    addFeeStructure(rec);
        }
    }, {
        iconCls: 'icon-delete',
        text: 'Delete',
        disabled: true,
        id: 'Dairydelete',
        handler: function(component){
            Ext.Msg.confirm("Alert","Are you sure want to delete records", function(btn){
            if(btn=='yes'){
                var grid = Ext.getCmp('stucturegrid');
                grid.getStore().remove(grid.getSelectionModel().getSelection());
            }
        });
        }
    },{
        xtype:'button',
        text:'Reply',
        iconCls: 'icon-add',
        listeners:{
            render: function(component){
                component.getEl().on('click', function(){
                    //addFeeTemplate();
                })

            }
        }
    },{
        xtype:'button',
        text:'Student Monthly Progress',
        iconCls: 'icon-add',
        listeners:{
            render: function(component){
                component.getEl().on('click', function(){
                    //addFeeTemplate();
                })

            }
        }
    },{
        xtype:'button',
        text:'Teacher Feedback',
        iconCls: 'icon-add',
        listeners:{
            render: function(component){
                component.getEl().on('click', function(){
                    //addFeeTemplate();
                })

            }
        }
    },{
        xtype:'button',
        text:'Achievment & Involvement',
        iconCls: 'icon-add',
        listeners:{
            render: function(component){
                component.getEl().on('click', function(){
                    //addFeeTemplate();
                })

            }
        }
    }
    ],
    bbar : Ext.create('Ext.PagingToolbar', {
        store: this.store,
        displayInfo: true,
        displayMsg: 'Displaying users {0} - {1} of {2}',
        emptyMsg: "No user to display"
    }),
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




