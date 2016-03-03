/**
 * Created by ruanweibiao on 2016-03-02.
 */
define('fw/uimanager',['_g','doT'],function(_g , tplEngine) {
    var _this = this;

    var UIManager = new function() {

        var restApiCtx = _g.getFullRestApiContext();

        this.openDialog = function(viewConf) {
            var diaElems = $('div[role="dialog"].modal');
            if (diaElems.length > 1) {
                throw Error('Found more than 1 dialog under html');
            }

            var modalBodyElems = diaElems.find('div.modal-content');
            var containElems = modalBodyElems.children();
            if (containElems.length > 0) {
                // --- clear contain first ---
                containElems.remove();
            }


            // --- append html ---
            var htmlContent = viewConf['html'];
            if (!htmlContent) {
                htmlContent = '';
            }
            modalBodyElems.html(htmlContent);


            var _defHandlers = {
                'ui:rendered' : function(){}
            }

            if (viewConf['handlers']) {
                // ---reset handlers --
                if (viewConf['handlers']['ui:rendered']) {
                    _defHandlers['ui:rendered'] = viewConf['handlers']['ui:rendered'];
                }
            }


            // --- open dialog ---
            diaElems.modal({
                keyboard: true
            }).on('shown.bs.modal', function (e) {
                // --- proxy delegate --
                _defHandlers['ui:rendered'](e);


            });
        }



        this.renderContentHeader = function(conf) {
            // --- init content base tools ---
            var contHeaderElems = $('section.content-header');
            tplEngine.render({
                templateId:'content-nav-header-tpl',
                data:{
                    "title":conf['title']?conf['title']:''
                },
                renderCallback: function(renderResult) {
                    contHeaderElems.html(renderResult);
                }
            });
        }


        this.renderNavMenu = function(conf) {
            var menuElems = $('section.sidebar ul.sidebar-menu');

            $.get(restApiCtx + '/system/nav-menus',function(data,status) {
                if (status == 'success') {
                    // --- parse data ---
                    var navMenus = data["data"];
                    // --- parse template ---
                    tplEngine.render({
                        templateId:'sidebar-menu-tpl',
                        data:navMenus,
                        renderCallback: function(renderResult) {
                            menuElems.html(renderResult);
                        }
                    });
                }
            });
        }


    };



    return UIManager;
});