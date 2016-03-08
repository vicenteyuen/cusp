/**
 * Created by ruanweibiao on 2016-03-03.
 */
define('fw/mvcmanager',['backbone'],function(_backbone, _backlocal) {
    var _this = this;


    var MvcManager = function() {

        var _modelMap = {};
        var _viewMap = {};


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


        this.getViewlClass = function(clsName) {
            return _viewMap[clsName];

        };

        this.addViewClass = function(clsObj , clsName) {
            _viewMap[clsName] = clsObj;
        }


        this.getEngineCore = function() {
            return _backbone;
        }





        /**
         * render view and show element
         * @param _backbone
         */
        this.renderView = function(_backbone) {

        }







    };

    var inst = new MvcManager();
    return inst;
})