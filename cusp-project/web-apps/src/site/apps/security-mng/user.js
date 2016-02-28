/**
 * Created by ruanweibiao on 2015-11-03.
 */

AppMod.setConfig({

})


AppMod.application({

    supportMVC:true,
    /**
     * defeind use template engine
     *
     */
    tplEngines:['doT'],

    // --- module , plugin , component defined
    mods: [
        'dataTables-bootstrap','iCheck',
        'theme-AdminLTE'],

    launch : function(_ctx , _uiManager) {
        var _g = _ctx.getGlobal(),
            restApiCtx = _g.getFullRestApiContext();

        // --- render module ---
        var tplEngine = _ctx.getClientTplEngine('doT');

        var menuElems = $('section.sidebar ul.sidebar-menu');
        var smTmpl = document.getElementById('sidebar-menu-tpl').innerHTML;
        $.get(restApiCtx + '/system/nav-menus',function(data,status) {

            if (status == 'success') {
                // --- parse data ---
                var navMenus = data["data"];
                // --- parse template ---
                tplEngine.render({
                    template:smTmpl,
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


        _uiManager.openDialog({
            html:"",
            title:"新增用户"

        });

        tplEngine.render({
            templateId:'user-info-tpl',
            data:{},
            renderCallback: function(renderResult) {

            }
        });
       // var smTmpl = document.getElementById('sidebar-menu-tpl');


        $(".add-user").on("click",function() {
            $('#myModal').modal({
                keyboard: true
            }).on('shown.bs.modal', function (e) {

                $(".btn-save").on('click' , function() {

                })



            });


        });
    }
})
