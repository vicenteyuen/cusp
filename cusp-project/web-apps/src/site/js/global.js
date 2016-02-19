/**
 * Created by vison ruan on 2016-02-19.
 * define global handle
 */
define(function() {

    var _global = new function() {

        var _this = this;

        var _restServer = 'localhost:8080';
        var _protocol = 'http://';
        var _restapi = '/restapi';



        _this.getRestServer = function() {
            return _restServer;
        };

        /**
         * defined full rest api context handle
         */
        _this.getFullRestApiContext = function() {
            return _protocol + _restServer + _restapi;
        }

    };



    return _global;
})