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

            if (wconf["cols"] && wconf["cols"] instanceof Array) {
                var _conf = wconf["cols"];

                var _columns = [];
                var _columnsRef = [];

                for (var i in _conf) {

                    var col = {
                        data:_conf[i].field,
                        bSortable:false
                    };

                    if (_conf[i].render) {
                        var render = _conf[i].render;

                        col.render = function(value , type , record, rowModel) {

                            /*
                            if (type == 'type') {

                                var rowIndex = rowModel.row;
                                var colIndex = rowModel.col;

                                var result = render(value, record, rowIndex, colIndex);
                                return result;
                            }
                            else {
                                return value;
                            }*/

                            return value;


                        }

                    }
                    _columns.push(col);

                }




                delete wconf["cols"];
                wconf['columns'] = _columns;
            }



            var elem = renderElem.DataTable(wconf);
            return elem;
        }


    }

    return builder;
});