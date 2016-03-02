/**
 * Created by ruanweibiao on 2015-11-03.
 */
var AppMod = AppMod || {};

AppMod = {


    _initConfig:{},

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

            // --- import template engine handle ---
            if (config.impTplEngines) {
                this._initConfig['impTplEngines'] = config.impTplEngines;
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
            supportMVC:true,
        }, appMe = this;

        // --- check config ---
        if (config) {

            var mods = [];
            // --- create tpl engines mapping ---

            // --- set the template engine load javascript
            for (var i in this._initConfig['impTplEngines']) {
                var mod = this._initConfig['impTplEngines'][i];
                mods.push(mod);
            }

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

            // ---- define mods ---
            mods.push('bootstrap');

            // --- defined global handle --
            requirejs(mods , function(_global , tplEngine){
                var args = arguments;

                var agentEngine = null, leftPos = 1;

                // --- load defined framework ---
                var observer = appMe._getObserver();







                var agentArgs = [];

                var ctx = require('fw/context');
                ctx.initCtx();
                agentArgs.push(ctx);

                require(['fw/uimanager'],function(uimanager) {
                    agentArgs.push(uimanager);
                });


                // --- all define mapping ---
                for (var i = leftPos ; i < args.length ; i++) {
                    if (args[i]) {
                        agentArgs.push( args[i] )
                    }
                }


                if (typeof config.launch == 'function') {
                    // --- call method
                    config.launch.apply(this,agentArgs);
                }

            });

        }
    },

    /**
     * return observer mode
     * @private
     */
    _getObserver : function() {


        var Observer = function() {

        };

        return Observer;


    },



    _globalLibsConf : function() {
        var baseUrl = '../..';

        var _global = {

            baseUrl:baseUrl,

            paths:{
                // --- base lib ---
                "underscore": "/js/plugins/underscore/1.8.3/underscore",
                "backbone": "/js/plugins/backbone/1.2.3/backbone",
                "backbone-localstorage": "/js/plugins/backbone/backbone.localStorage",

                // --- global ---
                '_g':'/js/global',
                'fw/uimanager': '/js/fw/uimanager',
                'fw/context':'/js/fw/context',
                'bootstrap':'/js/bootstrap/bootstrap',

                // --- jquery load ---
                jquery: '/js/plugins/jquery/jquery-2.1.4',
                'template/doT':'/js/plugins/doT/doT',
                doT:'/js/fw/template/dotEngine',
                jsPlumb: '/js/plugins/jsPlumb/jsPlumb-2.0.5',
                jsPlumbToolkit: '/js/plugins/jsPlumb/jsPlumbToolkit-1.0.17',
                'bs-slider': '/js/plugins/bootstrap-slider/bootstrap-slider',
                'jstree':'/js/plugins/jstree/jstree',
                artTemplate:'/js/plugins/artTemplate/template-debug',


                'jquery-datatables': '/js/plugins/datatables/jquery.dataTables',
                'dataTables-bootstrap': '/js/plugins/datatables/dataTables.bootstrap',
                'iCheck':'/js/plugins/iCheck/icheck',

                'theme-AdminLTE': '/js/themes/AdminLTE/app'
            },
            shim: {
                'backbone-localstorage': ['underscore', 'backbone'],
                jsPlumb:['jquery', 'css!'+baseUrl+'/js/plugins/jsPlumb/jsPlumbToolkit-default.css'],
                jsPlumbToolkit:['jsPlumb', 'css!'+baseUrl+'/js/plugins/jsPlumb/jsPlumbToolkit-default.css'],
                'bs-slider':['jquery', 'css!'+baseUrl+'/js/plugins/bootstrap-slider/slider.css'],
                'bootstrap':['jquery'],

                'dataTables-bootstrap':['jquery-datatables', 'css!'+baseUrl+'/js/plugins/datatables/dataTables.bootstrap.css'],
                'iCheck':['css!'+baseUrl+'/js/plugins/iCheck/flat/blue.css'],
                'jstree':['jquery','css!'+baseUrl+'/js/plugins/jstree/themes/default/style.css'],


                'theme-AdminLTE':['jquery']
            }

        }


        return _global

    }




}

