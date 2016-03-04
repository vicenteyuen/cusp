/**
 * Created by ruanweibiao on 2016-03-02.
 * @parameter userinfo
 */
define('Users',['fw/context'], function(_ctx) {
    var _g = _ctx.getGlobal(),
        restApiCtx = _g.getFullRestApiContext(), mvcmanager = _ctx.getMvcManager(),backbone = mvcmanager.getEngineCore();


    // --- define module and event define ---
    var User = backbone.Model.extend({
        urlRoot : restApiCtx + '/user'
    });

    mvcmanager.addModelClass(User,'User');

    var Users = backbone.Collection.extend({
        url: restApiCtx + '/users',
        parse:function(data) {

        }
    });
    mvcmanager.addModelClass(Users,'Users');




    return mvcmanager;


});

