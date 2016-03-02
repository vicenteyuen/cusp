/**
 * Created by ruanweibiao on 2016-02-19.
 * define and build framework
 */
define('fw/context',['_g','backbone','backbone-localstorage'],function(_g , _backbone) {
    var _this = this;

    var context = new function() {

        // --- init all ctx handle ---
        this.initCtx = function() {

        }


        // --- define global ---
        this.getGlobal = function () {
            return _g;
        }

        this.getMvcContext = function() {
            return _backbone;
        }


        // --- define use template ---
        this.getClientTplEngine = function(engineName) {

            // --- check support ---
            if (engineName == null) {
                return null;
            } else if (engineName != 'doT') {
                throw Error('Engine : ' + engineName + 'could not supported. ');
            }

            // --- load by demand ---
            var tplEngCore = requirejs(engineName);

            return tplEngCore;
        }


    };



    return context;
})