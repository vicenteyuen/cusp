/**
 * Created by ruanweibiao on 2016-03-02.
 * @parameter userinfo
 */
define('User',['fw/context'], function(_ctx ) {
    var _g = _ctx.getGlobal(),
        restApiCtx = _g.getFullRestApiContext(), mvcmanager = _ctx.getMvcManager(),backbone = mvcmanager.getEngineCore();

    // --- define module and event define ---
    var User = backbone.Model.extend({
        idAttribute: "id",
        defaults:{
            id:'',
            chineseName:"",
            loginAccount:"",
            staffNo:"",
            contact:"",
            status:""
        },
        parse:function(res) {
            return res.data;
        },
        urlRoot : restApiCtx + '/system/user'
    });

    mvcmanager.addModelClass(User,'User');

    return User;


});

