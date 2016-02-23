/**
 * Created by ruanweibiao on 2015-11-03.
 */

AppMod.setConfig({
    /*
    paths:{
        jquery1: '../../js/jquery-2.1.4'
    }
    */
})


AppMod.application({

    supportMVC:true,
    /**
     * defeind use template engine
     *
     */
    tplEngines:['doT'],

    // --- module , plugin , component defined
    mods: ['theme-AdminLTE'],

    launch : function(_ctx) {
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
    }
})
