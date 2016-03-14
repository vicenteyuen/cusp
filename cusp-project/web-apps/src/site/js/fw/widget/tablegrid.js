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

                for (var i in _conf) {

                    var col = {
                        data:_conf[i].field,
                        bSortable:false,
                        render : _conf[i].render ? true:false
                    };


                    // ---- define render event ---
                    col.render = function(value , type , record, rowModel) {

                        if (type == 'display') {

                            var rowIndex = rowModel.row;
                            var colIndex = rowModel.col;
                            var render = _conf[i].render;


                            if (colIndex == i) {

                                console.log(_conf[i]);

                                var result = _conf[i].render(value, record, rowIndex, colIndex);
                                return result;
                            }
                        }
                        return value;


                    }

                    _columns[i] = col;


                }
                console.log(_columns);


                delete wconf["cols"];
                wconf['columns'] = _columns;
            }



            var elem = renderElem.DataTable(wconf);
            return elem;
        }


    }

    return builder;
});