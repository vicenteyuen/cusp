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

    supportMVC:true,

    // --- module , plugin , component defined
    mods: [
        'dataTables-bootstrap','iCheck',
        'theme-AdminLTE','UserInfo'],

    launch : function(_ctx , _uiManager) {

        console.log(_ctx );

        var _g = _ctx.getGlobal(),
            restApiCtx = _g.getFullRestApiContext();

        // --- render module ---
        var tplEngine = _ctx.getClientTplEngine('doT');

        var menuElems = $('section.sidebar ul.sidebar-menu');
        //var smTmpl = document.getElementById('sidebar-menu-tpl').innerHTML;
        $.get(restApiCtx + '/system/nav-menus',function(data,status) {

            if (status == 'success') {
                // --- parse data ---
                var navMenus = data["data"];
                // --- parse template ---
                tplEngine.render({
                    templateId:'sidebar-menu-tpl',
                    //template:smTmpl,
                    data:navMenus,
                    renderCallback: function(renderResult) {
                        menuElems.html(renderResult);
                    }
                });
            }
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






        // --- defind application event ----
        $(".add-user").on("click",function() {
            // --- call application event --

            // --- render api ---
            tplEngine.render({
                templateId:'user-info-tpl',
                data:{},
                renderCallback: function(renderResult) {

                    /*
                     * define dialog
                     */
                    _uiManager.openDialog({
                        html:renderResult,
                        title:"新增用户",
                        handlers: {
                            'ui:rendered' : function(e) {

                                // --- get reference object --
                                $(".btn-save").on('click' , function() {
                                    var currentUser = new User();


                                })



                            }
                        }

                    });

                }
            });

        });


    }
})
