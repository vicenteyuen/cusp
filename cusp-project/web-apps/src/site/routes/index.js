/**
 * Created by ruanweibiao on 2016-02-18.
 */
exports.index = function(req, res){
    res.render('index', { name: 'John' });
};