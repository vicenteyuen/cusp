/**
 * Created by ruanweibiao on 2016-03-03.
 */
define('fw/mvcmanager',['backbone','backbone-localstorage'],function(_backbone, _backlocal) {
    var _this = this;


    var MvcManager = function() {

        var _modelMap = {};


        this.getModelClass = function(clsName) {
            return _modelMap[clsName];

        };

        /**
         * defind class name
         * @param clsObj
         * @param clsName
         */
        this.addModelClass = function(clsObj , clsName) {

            _modelMap[clsName] = clsObj;

        }


        this.getEngineCore = function() {
            return _backbone;
        }







    };

    var inst = new MvcManager();
    return inst;
})