/**
 * Created by ruanweibiao on 2016-02-19.
 * define and build dot handle
 */
define('doT',['template/doT'],function(dotEngine) {
    var _this = this;

    var _loadFileWithXHR = function(srcUrl , passdata , callback) {
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

                    var orgTpl = xhrReq.responseText;

                    // ---- parse content ---
                    var tpl = dotEngine.template( orgTpl );
                    var tplContent = tpl(passdata);

                    if (callback) {
                        callback(tplContent);
                    }
                }
            }
        }


        // ---- send request ---
        xhrReq.open("GET", srcUrl, true);
        xhrReq.send({});

    };



    var dotTplEngine = new function() {

        this.render = function(args) {

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
                            _loadFileWithXHR(srcUrl,args['data'] , renderCallback);



                        } else {
                            // --- parse url content ---
                            orgTpl = tplDom.innerHTML;

                            // _me.engine.template( args['template'] );
                            var tpl = dotEngine.template( orgTpl );
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

        }
    };

    return dotTplEngine;
})