/**
 * Created by ruanweibiao on 2015-11-03.
 */
var AppMod = AppMod || {};

AppMod = {

    setConfig: function(config) {
        var me = this;

        var gconf =  me._globalLibsConf().paths;
        var shim = me._globalLibsConf().shim;

        var baseUrl = me._globalLibsConf().baseUrl;


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

            var mods = [];
            mods.push('_g');


            // --- reset modules ---
            for (var i in config.mods) {
                var checkPreMod = config.mods[i];
                var indLoc = checkPreMod.indexOf('jsx!');

                if (indLoc > -1) {
                    var newPath = "../../js/react/requirejs/" + config.mods[i];
                    mods.push( newPath );
                }
                else {
                    mods.push(checkPreMod);
                }
            }

            // --- defined global handle --
            requirejs(mods , function(_global){

                // --- create context ---
                var ctx = new function() {
                    var _this = this;

                    // --- define global ---
                    _this.getGlobal = function () {
                        return _global;
                    }
                };

                config.launch(ctx);
            });

        }
    },



    _globalLibsConf : function() {
        var baseUrl = '../..';

        var _global = {

            baseUrl:baseUrl,

            paths:{
                // --- global ---
                '_g':'/js/global',

                // --- jquery load ---
                jquery: '/js/plugins/jquery/jquery-2.1.4',
                jsPlumb: '/js/plugins/jsPlumb/jsPlumb-2.0.5',
                jsPlumbToolkit: '/js/plugins/jsPlumb/jsPlumbToolkit-1.0.17',
                'bs-slider': '/js/plugins/bootstrap-slider/bootstrap-slider',
                'jstree':'/js/plugins/jstree/jstree',

                'theme-AdminLTE': '/js/themes/AdminLTE/app'
            },
            shim: {
                jsPlumb:['jquery', 'css!'+baseUrl+'/js/plugins/jsPlumb/jsPlumbToolkit-default.css'],
                jsPlumbToolkit:['jsPlumb', 'css!'+baseUrl+'/js/plugins/jsPlumb/jsPlumbToolkit-default.css'],
                'bs-slider':['jquery', 'css!'+baseUrl+'/js/plugins/bootstrap-slider/slider.css'],

                'jstree':['jquery','css!'+baseUrl+'/js/plugins/jstree/themes/default/style.css'],

                'theme-AdminLTE':['jquery']
            }

        }


        return _global

    }




}

