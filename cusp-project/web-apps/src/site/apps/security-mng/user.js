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
        'dataTables-bootstrap','iCheck','theme-AdminLTE'],

    launch : function(_ctx ) {
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
        $("#user-list").DataTable();
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


        // --- create model ---
        var mvcCtx =  _ctx.getMvcContext();
        var User = mvcCtx.Collection.extend({
            url: restApiCtx+'/system/user'
        });

        var user = new User();
        user.create({
            id:"123",
            name:"person name"
        });

        Backbone.sync("create", user , {
            success: function() {
                console.log('ok');
            },
            error: function() {
                console.log("error ok");
            }
        })









    }
})
