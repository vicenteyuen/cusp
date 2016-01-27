/**
 * Created by ruanweibiao on 2015-11-03.
 */
var AppMod = AppMod || {};

AppMod = {

    setConfig: function(config) {
        var me = this;

        var gconf =  me._globalLibsConf().paths;
        var shim = me._globalLibsConf().shim;
        if (config) {

            if (config.paths) {
                // --- import and load config

                for (var i in config.paths) {

                    var refVal = gconf[i];

                    if (!refVal) {
                        gconf[i] =  config.paths[i];
                    }

                }
            }

        }

        require.config({
            paths:gconf,
            shim:shim,
            map: {
                '*':{
                    'css':'../../js/css'
                }
            }
        })


    },

    application:function(config) {
        var _cnf = {

        }

        // --- check config ---
        if (config) {

            requirejs(config.mods , config.launch);

        }
    },



    _globalLibsConf : function() {
        var _global = {
            paths:{
                jquery: '../../js/plugins/jquery/jquery-2.1.4',
                app: '../../js/app',
                jsPlumb: '../../js/plugins/jsPlumb/jsPlumb-2.0.5'
            },
            shim: {
                jsPlumb:['jquery', 'css!../../js/plugins/jsPlumb/jsPlumbToolkit-default.css']
            }

        }


        return _global

    }




}

