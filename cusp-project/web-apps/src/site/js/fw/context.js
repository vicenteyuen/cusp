/**
 * Created by ruanweibiao on 2016-02-19.
 * define and build framework
 */
define('fw/context',['_g','fw/mvcmanager'],function(_g , _mvcmanager) {
    var _this = this;



    var context = new function() {

        // --- init all ctx handle ---
        this.initCtx = function() {

        }


        // --- define global ---
        this.getGlobal = function () {
            return _g;
        }

        this.getMvcManager = function() {
            return _mvcmanager;
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
            var tplEngCore = require(engineName);

            return tplEngCore;
        }


    };



    return context;
})