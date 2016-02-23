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


    _tplEngines:[],

    _tplEnginesMap:{},


    _initTplEngineMap: function() {
        var me = this;

        // --- create template engine service ---
        var dotTplEngine = new function() {
            var _me = this;


            _me.setEngine = function(engine) {
                _me.engine = engine;
            }


            _me.render = function(args) {

                // --- check args ---
                var tpl = _me.engine.template( args['template'] );
                var result = tpl(args['data']);
                var renderCallback = args['renderCallback'];
                renderCallback(result);
            }
        };
        me._tplEnginesMap['doT'] = dotTplEngine;


    },



    application:function(config) {
        var _cnf = {
            supportMVC:true,
        }, appMe = this;

        // --- check config ---
        if (config) {

            var mods = [];
            mods.push('_g'); // first


            // --- create tpl engines mapping ---
            appMe._initTplEngineMap();


            // --- set the template engine load javascript
            for (var i in config.tplEngines) {
                var tplEngine = config.tplEngines[i];

                if (tplEngine == 'doT') {
                    mods.push(tplEngine);
                }
            }

            // --- load mvc frameowrk --

            if (config.supportMVC != 'undefined') {
                _cnf.supportMVC = config.supportMVC;
            }
            // --- load mvc ---
            if (_cnf.supportMVC) {
                mods.push('backbone');
                mods.push('backbone-localstorage');
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

            // --- defined global handle --
            requirejs(mods , function(_global , tplEngine){
                var args = arguments;

                var agentEngine = null, leftPos = 1;
                for (var i = 0 ; i < config.tplEngines.length ; i++) {
                    var engineName = config.tplEngines[i];

                    agentEngine = appMe._tplEnginesMap[engineName];
                    var currentIndex = leftPos + i ;
                    agentEngine.setEngine(args[currentIndex]);
                }

                // --- get backbone and backbone local storage ---
                leftPos = leftPos + config.tplEngines.length;
                var backbone = null;
                var backboneLocalStorage = null;
                if (_cnf.supportMVC) {
                    backbone = args[leftPos++];
                    // skip one variable for 'backbone-localstorage'
                    leftPos++;
                }







                // --- create context ---
                var ctx = new function() {
                    var _this = this;

                    // --- define global ---
                    _this.getGlobal = function () {
                        return _global;
                    }

                    /**
                     *
                     * @param engineName  --- defined the template engine for defined
                     */
                    _this.getClientTplEngine = function(engineName) {
                        return appMe._tplEnginesMap[engineName];
                    }

                    /**
                     * define mvc context handle
                     */
                    _this.getMvcContext = function() {
                        if (!_cnf.supportMVC ) {
                            if (window.cosole) {
                                window.cosole.log('Application config not set support mvc');
                            }
                            return {};
                        }

                        if (backbone != null) {
                            return backbone;
                        }
                        return null;
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
                // --- base lib ---
                "underscore": "/js/plugins/underscore/1.8.3/underscore",
                "backbone": "/js/plugins/backbone/1.2.3/backbone",
                "backbone-localstorage": "/js/plugins/backbone/backbone.localStorage",

                // --- global ---
                '_g':'/js/global',

                // --- jquery load ---
                jquery: '/js/plugins/jquery/jquery-2.1.4',
                doT:'/js/plugins/doT/doT',
                jsPlumb: '/js/plugins/jsPlumb/jsPlumb-2.0.5',
                jsPlumbToolkit: '/js/plugins/jsPlumb/jsPlumbToolkit-1.0.17',
                'bs-slider': '/js/plugins/bootstrap-slider/bootstrap-slider',
                'jstree':'/js/plugins/jstree/jstree',
                artTemplate:'/js/plugins/artTemplate/template-debug',


                'theme-AdminLTE': '/js/themes/AdminLTE/app'
            },
            shim: {
                'backbone-localstorage': ['underscore', 'backbone'],
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

