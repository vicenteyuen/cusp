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


    init: function(appMod, _ctx , _uiManager) {
        this.eventDef = this.events(appMod, _ctx , _uiManager);
    },

    eventDef : {},

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



        // --- render event for ui ----
        $("#user-list").DataTable({
            "paging": true,
            "lengthChange": false,
            "searching": false,
            "ordering": true,
            "info": true,
            "autoWidth": false
        });
        $('.list-table input[type="checkbox"]').iCheck({
            checkboxClass: 'icheckbox_flat-blue',
            radioClass: 'iradio_flat-blue'
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
