/**
 * Created by ruanweibiao on 2015-11-03.
 */

AppMod.setConfig({

    impTplEngines:['doT'],

    paths:{
        'UserInfo':'mvc/UserInfo'
    }

})


AppMod.application({


    // --- module , plugin , component defined
    mods: [
        'dataTables-bootstrap','iCheck',
        'theme-AdminLTE','UserInfo'],

    launch : function(_ctx , _uiManager) {

        var _g = _ctx.getGlobal(),
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


        var User = mvcManager.getModelClass('UserInfo');

        var btnAddUser = this.getEventRef('btn:add-user');
        $(".add-user").on("click",btnAddUser);



    },

    events: function(_ctx , _uiManager) {

        // --- render module ---
        var tplEngine = _ctx.getClientTplEngine('doT'), mvcManager = _ctx.getMvcManager();

        var User = mvcManager.getModelClass('UserInfo');


        var listeners = {
            'btn:add-user':{
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
                                    'ui:rendered' : function(e) {

                                        // --- get reference object --
                                        $(".btn-save").on('click' , function(refComp) {
                                            var currentUser = new User();


                                        })



                                    }
                                }

                            });

                        }
                    });
                }
            }



        }

        return listeners;

    }

})
