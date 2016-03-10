/**
 * Created by ruanweibiao on 2016-03-02.
 * @parameter userinfo
 */
define('Users',['fw/context' , 'User'], function(_ctx ) {
    var _g = _ctx.getGlobal(),
        restApiCtx = _g.getFullRestApiContext(), mvcmanager = _ctx.getMvcManager(),backbone = mvcmanager.getEngineCore();

    var User = mvcmanager.getModelClass('User');


    var Users = backbone.Collection.extend({
        model: User,
        url: restApiCtx + '/system/users',
        parse:function(response) {
            var users = [];

            for (var i = 0 ; i< response['data'].length ; i++) {
                var user = new User(response['data'][i]);
                users.push(user);
            }

            return users;
        }
    });
    mvcmanager.addModelClass(Users,'Users');

    return Users;


});

