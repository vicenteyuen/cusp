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


    /**
     *
     * @private method
     */
    _loadFileWithXHR: function(srcUrl , callback) {
        var xhrReq = null;
        if (window.XMLHttpRequest) {
            xhrReq = new XMLHttpRequest();
        } else if (window.ActiveXObject) {
            xhrReq = new ActiveXObject("Microsoft.XMLHTTP");
        } else {
            if (window.console) {
                console.log("XMLHttpRequest not supported!");
            }
            return ;
        }

        xhrReq.onreadystatechange = function() {
            if ( xhrReq.readyState != 4 ) {
                return;
            }
            if (xhrReq.status != 200) {
                if (window.console) {
                    console.log('Failed to retrive the template! Error : '+xhrReq.status);
                }
            }
            if ( xhrReq.readyState == 4 ) {
                if (xhrReq.status == 200) {

                    var tplContent = xhrReq.responseText;
                    if (callback) {
                        callback(tplContent);
                    }
                }
            }
        }


        // ---- send request ---
        xhrReq.open("GET", srcUrl, true);
        xhrReq.send({});




    },

    _initTplEngineMap: function() {
        var me = this;

        // --- create template engine service ---
        var dotTplEngine = new function() {
            var _me = this;


            _me.setEngine = function(engine) {
                _me.engine = engine;
            }


            _me.render = function(args) {

                var orgTpl = null;
                if (args['template'] || args['templateId']) {

                    if (args['templateId']) {

                        // --- parse id ---
                        var tplDom = document.getElementById(args['templateId']);
                        var tplType = tplDom.getAttribute('type');

                        if (tplType == 'text/x-dot-template') {

                            var renderCallback = args['renderCallback'];


                            var srcUrl = tplDom.getAttribute('src');
                            if (srcUrl) {
                                me._loadFileWithXHR(srcUrl,renderCallback);
                            } else {
                                // --- parse url content ---
                                orgTpl = tplDom.innerHTML;

                               // _me.engine.template( args['template'] );
                                var tpl = _me.engine.template( orgTpl );
                                var result = tpl(args['data']);

                                renderCallback(result);
                            }


                            // --- check content under dom element
                            //

                        }


                    }

                    else {
                        tpl = _me.engine.template( args['template'] );
                    }



                } else {
                    throw new Error('"template or templateId " variable is not defined.');
                }
                /*
                if (!args['template']) {
                    throw new Error('"template" variable is not defined.');
                }
                */

                if (!args['data']) {
                    throw new Error('"data" variable is not defined.');
                }

                if (!args['renderCallback']) {
                    throw new Error('"renderCallback" variable is not defined.');
                }

                if (tpl) {


                }


                // --- check args ---
                /*
                var tpl = _me.engine.template( args['template'] );
                var result = tpl(args['data']);
                var renderCallback = args['renderCallback'];
                renderCallback(result);
                */
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

            // ---- define mods ---
            mods.push('bootstrap');

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



                var agentArgs = [];
                agentArgs.push(ctx);
                agentArgs.push(appMe.getUIManager());


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
     * bind view ui
     */
    getUIManager:function() {

        /**
         * define ui manager
         */
        var UIManager = function() {
            var _this = this;


            // --- defind unit change view ---
            _this.openDialog = function(viewConf) {




            }



        };



        var currentManage = new UIManager();


        return currentManage;


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
                'bootstrap':'/js/bootstrap/bootstrap',

                // --- jquery load ---
                jquery: '/js/plugins/jquery/jquery-2.1.4',
                doT:'/js/plugins/doT/doT',
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

