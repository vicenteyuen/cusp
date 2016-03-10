/**
 * Created by ruanweibiao on 2015-11-03.
 */

AppMod.setConfig({

    impTplEngines:['doT'],

    paths:{
        'User':'mvc/model/User',
        'Users':'mvc/model/Users',
        'UserForm':'mvc/view/UserForm'
    }

})

/**
 * running application
 */
AppMod.application({


    // --- module , plugin , component defined
    mods: [
        'dataTables-bootstrap','iCheck',
        'theme-AdminLTE','Users', 'UserForm'],

    eventDef : {},
    init: function(appMod, _ctx , _uiManager) {
        this.eventDef = this.events(appMod, _ctx , _uiManager);
    },

    /**
     * launch application
     * @param appMod
     * @param _ctx
     * @param _uiManager
     */
    launch : function(appMod, _ctx , _uiManager) {

        var _me = this, _g = _ctx.getGlobal(),
            restApiCtx = _g.getFullRestApiContext(),
            mvcManager = _ctx.getMvcManager();

        // --- render module ---
        var tplEngine = _ctx.getClientTplEngine('doT');

        // --- render navigertor menu handle ---
        _uiManager.renderNavMenu();

        // -- render content header element
        _uiManager.renderContentHeader({
            title:'用户管理'
        });

        // --- render table dal --
        var Users = mvcManager.getModelClass('Users');
        var users = new Users();

        // --- load all users ---
        users.fetch({
            success:function(collection , response , options) {
                // --- render data  grid ---
                var tablegrid = null;
                _uiManager.renderWidget('widget/tablegrid' , {
                    type:'datatable',
                    renderElem:$("#user-list"),
                    wconf:{
                        "paging": true,
                        data:collection.toJSON(),
                        "lengthChange": false,
                        "searching": false,
                        "ordering": true,
                        "info": true,
                        "autoWidth": false,
                        "columns":[
                            {"data":"id" , "bSortable":false},
                            {"data":"loginAccount"},
                            {"data":"chineseName"},
                            {"data":"staffNo"},
                            {"data":"contact"},
                            {"data":"status" , "bSortable":false}
                        ],
                        "columnDefs":[
                            {
                                "targets":[0],
                                "data":"id",
                                "render": function(data , type , full) {

                                    var checkedHtml = _uiManager.getTableCheckSelectedPlugin({
                                        name:'id',
                                        raw:data
                                    });
                                    return checkedHtml;
                                }
                            },
                            {
                                "targets":[5],
                                "data":"status",
                                "render": function(data , type , full) {

                                    // --- load render item tools ---
                                    var result = _uiManager.getRenderedRowTools([
                                        {iconCls:'fa-edit', value:data},{iconCls:'fa-trash-o' , value:data},{iconCls:'fa-user-secret' , value:data}
                                    ]);

                                    var statsHtml = 0;
                                    if (data == 0) {
                                        statsHtml = '<span class="label label-success">启用</span>';
                                    } else if ( data == 1 ) {
                                        statsHtml = '<span class="label label-warning">禁用</span>';
                                    }

                                    var html = statsHtml + result;
                                    return html
                                }
                            }
                        ]
                    }
                } , function(widget) {
                    tablegrid = widget;

                    tablegrid.on('init.dt' , function(comp) {
                        $('.list-table input[type="checkbox"]').iCheck({
                            checkboxClass: 'icheckbox_minimal-blue',
                            radioClass: 'iradio_minimal-blue'
                        });
                        $(".checkbox-toggle").click(function () {
                            var clicks = $(this).data('clicks');
                            if (clicks) {
                                //Uncheck all checkboxes
                                $(".list-table input[type='checkbox']").iCheck("uncheck");
                                $(".fa", this).removeClass("fa-check-square-o").addClass('fa-square-o');
                            } else {
                                //Check all checkboxes
                                $(".list-table input[type='checkbox']").iCheck("check");
                                $(".fa", this).removeClass("fa-square-o").addClass('fa-check-square-o');
                            }
                            $(this).data("clicks", !clicks);
                        });



                        $("#user-list .tools .fa-edit").on("click",appMod.delegateEvent(_me.eventDef['btn:edit-user']) );
                        $("#user-list .tools .fa-trash-o").on("click",appMod.delegateEvent(_me.eventDef['btn:remove-user']) );
                        $("#user-list .tools .fa-user-secret").on("click",appMod.delegateEvent(_me.eventDef['btn:change-pwd']) );

                    });
                });


            }
        });




        // --- render event handle ---
        $(".add-user").on("click",appMod.delegateEvent(_me.eventDef['btn:add-user']) );

    },



    // --- event define ---
    events: function(appMod ,_ctx , _uiManager) {

        var _me = this;

        // --- render module ---
        var tplEngine = _ctx.getClientTplEngine('doT'), mvcManager = _ctx.getMvcManager();

        // --- load class type ---
        var User = mvcManager.getModelClass('User');
        var Users = mvcManager.getModelClass('Users');
        var UserForm = mvcManager.getViewlClass("UserForm");

        var listeners = {};


        // --- defind all event ---
        listeners['btn:add-user'] = {
            'deps':[],
            'event':function(comp,e) {
                var form = new UserForm({
                    el: comp,
                    model:new User()
                });
                form.addUser();
            }
        };

        listeners['btn:edit-user'] = {
            'deps':[],
            'event':function(comp,e) {
                var id = $(comp).attr('data-value');
                var form = new UserForm({
                    el: comp,
                    model:new User({
                        id:id
                    })
                });
                form.editUser();

            }
        };


        // --- defind all event ---
        listeners['btn:remove-user'] = {
            'deps':[],
            'event':function(comp,e) {

                var id = $(comp).attr('data-value');
                var form = new UserForm({
                    el: comp,
                    model:new User({
                        id:id
                    })
                });
                form.delUser();

            }
        };


        // --- defind all event ---
        listeners['btn:change-pwd'] = {
            'deps':[],
            'event':function(comp,e) {

                var id = $(comp).attr('data-value');

                tplEngine.render({
                    templateId:'user-info-tpl',
                    data:{},
                    renderCallback: function(renderResult) {
                        _uiManager.openDialog({
                            html:renderResult,
                            title:"更新用户",
                            handlers: {
                                // --- render handle ---
                                'ui:rendered' : function(comp , e) {
                                    // --- load data ---
                                    var existedUser = new User({
                                        id:id
                                    });

                                    existedUser.fetch();



                                    // --- get reference object --
                                    $(".btn-save").on('click' , appMod.delegateEvent(_me.eventDef['btn:edit-user:do']) );
                                }
                            }

                        });

                    }
                });
            }
        };









        return listeners;

    }

})
