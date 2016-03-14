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
        var UserForm = mvcManager.getViewClass('UserForm');

        var users = new Users();

        // --- call running ---
        var def1 = $.Deferred();

        var code1 = function() {
            // --- load all users ---
            var tablegrid = null;
            _uiManager.renderWidget('widget/tablegrid' , {
                type:'datatable',
                renderElem:$("#user-list"),
                wconf:{
                    "paging": true,
                    "data":users.toJSON(),
                    "lengthChange": false,
                    "searching": false,
                    "ordering": true,
                    "info": true,
                    "autoWidth": false,
                    "cols":[
                        { "field":"id" , "render":function(value, record, rowIndex, i) {
                            var checkedHtml = _uiManager.getTableCheckSelectedPlugin({
                                name:'id',
                                raw:record
                            });
                            return 5;
                        }},
                        {"title":"用户帐号" , "field":"loginAccount"},
                        {"title":"中文名字" , "field":"chineseName"},
                        {"title":"员工编号" , "field":"staffNo" },
                        {"title":"联系方式" , "field":"contact" },
                        {"title":"状态" ,
                            "field":"status" ,
                            "render":function(value, record, rowIndex, i) {
                                var result = _uiManager.getRenderedRowTools([
                                    {iconCls:'fa-edit', value:record.id},{iconCls:'fa-trash-o' , value:record.id},{iconCls:'fa-user-secret' , value:record.id}
                                ]);
                                var statsHtml = 0;
                                if (value == 0) {
                                     statsHtml = '<span class="label label-success">启用</span>';
                                } else if ( value == 1 ) {
                                     statsHtml = '<span class="label label-warning">禁用</span>';
                                 }
                                var html = statsHtml + result;
                            return html;
                        }}
                    ]
                  }
            } , function(widget) {
                def1.resolve();
            });
            return def1.promise();

        };


        var code2 = function() {

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

            $("#user-list .tools .fa-trash-o").on("click",appMod.delegateEvent({
                'deps':[],
                'event':function(comp,e) {

                    var id = $(comp).attr('data-value');
                    var existedUser = users.get('' +id);
                    if (existedUser) {
                        var form = new UserForm({
                            el: comp,
                            model:existedUser
                        });
                        form.delUser();
                    }





                }
            }));

            $("#user-list .tools .fa-user-secret").on("click",appMod.delegateEvent(_me.eventDef['btn:change-pwd']) );

        };


        $.when( users.fetch() ).then(code1).done( code2 );

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
        var UserForm = mvcManager.getViewClass("UserForm");

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
