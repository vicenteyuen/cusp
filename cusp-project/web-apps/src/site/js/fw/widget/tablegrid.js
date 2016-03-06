/**
 * Created by vison on 2016/3/5.
 */
define('widget/tablegrid',['_g','doT'],function() {
    var _this = this;

    var builder = new function() {
        var wconf = null;
        var renderElem = null;


        this.init = function(conf) {

            if (!conf['renderElem']) {
                throw Error('Property "renderElem" could not set.');
            }

            renderElem = conf['renderElem'];

            if (conf['wconf']) {
                wconf = conf['wconf'];
            }
        }


        this.build = function() {
            return renderElem.DataTable(wconf);
        }


    }

    return builder;
});