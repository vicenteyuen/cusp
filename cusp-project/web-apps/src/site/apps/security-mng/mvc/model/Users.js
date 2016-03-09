/**
 * Created by ruanweibiao on 2016-03-02.
 * @parameter userinfo
 */
define('Users',['fw/context' , 'User'], function(_ctx , User  ) {
    var _g = _ctx.getGlobal(),
        restApiCtx = _g.getFullRestApiContext(), mvcmanager = _ctx.getMvcManager(),backbone = mvcmanager.getEngineCore();


    var Users = backbone.Collection.extend({
        url: restApiCtx + '/system/users',
        parse:function(data) {

        }
    });
    mvcmanager.addModelClass(Users,'Users');

    return Users;


});

