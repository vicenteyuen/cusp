/**
 * Created by ruanweibiao on 2016-03-02.
 */
define('fw/uimanager',[],function() {
    var _this = this;

    var UIManager = new function() {

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


    };



    return UIManager;
})