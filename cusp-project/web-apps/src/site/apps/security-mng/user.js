/**
 * Created by ruanweibiao on 2015-11-03.
 */

AppMod.setConfig({

    impTplEngines:['doT'],

    paths:{
        'Users':'mvc/Users'
    }

})

/**
 * running application
 */
AppMod.application({


    // --- module , plugin , component defined
    mods: [
        'dataTables-bootstrap','iCheck',
        'theme-AdminLTE','Users'],

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


        // ---- render every event ---
        var tablegrid = null;
        _uiManager.renderWidget('widget/tablegrid' , {
            type:'datatable',
            renderElem:$("#user-list"),
            wconf:{
                "paging": true,
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
                                {iconCls:'fa-edit'},{iconCls:'fa-trash-o'}
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
                ],
                "ajax":{
                    "url":restApiCtx + "/system/users"
                }
            }
        } , function(widget) {
            tablegrid = widget;

            tablegrid.on('init.dt' , function(comp) {
                $('.list-table input[type="checkbox"]').iCheck({
                    checkboxClass: 'icheckbox_minimal-blue',
                    radioClass: 'iradio_minimal-blue'
                });
            });



            // --- render event for ui ----
            /*
            $('.list-table input[type="checkbox"]').iCheck({
                checkboxClass: 'icheckbox_minima-blue',
                radioClass: 'iradio_minima-blue'
            });
            */
            /*
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
            */
        });



        // --- init data ---




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

        var listeners = {};


        // --- defind all event ---
        listeners['btn:add-user'] = {
            'deps':[],
            'event':function(comp,e) {

                tplEngine.render({
                    templateId:'user-info-tpl',
                    data:{},
                    renderCallback: function(renderResult) {
                        _uiManager.openDialog({
                            html:renderResult,
                            title:"新增用户",
                            handlers: {
                                // --- render handle ---
                                'ui:rendered' : function(comp , e) {

                                    // --- get reference object --
                                    $(".btn-save").on('click' , appMod.delegateEvent(_me.eventDef['btn:add-user:do']) );
                                }
                            }

                        });

                    }
                });
            }
        };


        // --- define layout event ---
        listeners['btn:add-user:do'] = {
            'deps':[],
            'event':function(comp,e) {
                var data = {
                    loginAccount: $("#loginAccount").val(),
                    chineseName : $("#chineseName").val(),
                    staffNo : $("#staffNo").val()
                };

                var newUser = new User();
                newUser.set(data);

                newUser.save();
            }
        };



        return listeners;

    }

})
