/**
 * Created by ruanweibiao on 2016-03-02.
 * @parameter userinfo
 */
define('UserForm',['fw/context','fw/uimanager',
    'Users',
    'doT'], function(_ctx , _uiManager, Users) {
    var _g = _ctx.getGlobal(),
        restApiCtx = _g.getFullRestApiContext(), mvcmanager = _ctx.getMvcManager(),backbone = mvcmanager.getEngineCore();

    var tplEngine = _ctx.getClientTplEngine('doT');

    var models = new Users();


    // --- define view class
    var UserForm = backbone.View.extend({

        models: models,

        initialize:function() {
            var _thisView = this;
        },

        dialogRef : null,

        render:function() {
            var _thisView = this;
            // -- change data view ---
            var data = this.model.attributes;
            var title = '新增用户';

            if (this.model) {
                title = "更新用户";
            }
            tplEngine.render({
                templateId:'user-info-tpl',
                data:data,
                renderCallback: function(renderResult) {
                    var dialogRef = _uiManager.openDialog({
                        html:renderResult,
                        title:title,
                        handlers: {
                            // --- render handle ---
                            'ui:rendered' : function(comp , e) {
                                _thisView.dialogRef = comp;


                                $(".btn-close").on('click' , function(evt) {
                                    _uiManager.closeDialog(_thisView.dialogRef);
                                });
                                // --- get reference object --
                                if (_thisView.actionType == 0) {

                                    $(".btn-save").on('click' , function(evt) {
                                        _thisView.doAddUser.call(_thisView, this , evt);
                                    });

                                }
                                else if (_thisView.actionType == 1) {
                                    $(".btn-save").on('click' , function(evt) {
                                        _thisView.doEditUser.call(_thisView, this , evt);
                                    });

                                }

                            }
                        }
                    });



                }
            });


            return this;

        },

        actionType:0,

        addUser : function() {
            this.actionType = 0;
            // --- call render ---
            this.render();
        },

        editUser:function() {
            var _thisView = this;
            _thisView.actionType = 1;

            // --- fetch model first ---
            $.when(this.model.fetch()).done(function() {
                // --- call render ---
                _thisView.render();
            })


        },


        delUser: function() {
            var _thisView = this;
            _thisView.actionType = 2;


            var confirmMsg = "是否删除数据 [" + _thisView.model.get('loginAccount') + "]? ";;

            _uiManager.showMsgDialog({
                type:'info',
                buttons:['yes','no'],
                content:confirmMsg,
                btnCallback: {
                    'yes':function(comp , evt) {
                        // --- fetch model first ---
                        _thisView.model.destroy({
                            success:function(model , response) {

                            },
                            error : function(model , response) {

                            }
                        });
                    }
                }

            });
            /*
            $.when(this.model.fetch()).done(function() {
                // --- call render ---

            })
            */
        },


        /**
         * check value --
         * @param comp
         * @param e
         */
        doAddUser : function(comp ,e) {
            var ref = this;
            var model = this.model;
            var models = this.models;

            var data = {
                loginAccount: $("#loginAccount").val(),
                chineseName : $("#chineseName").val(),
                staffNo : $("#staffNo").val()
            };

            models.create(data , {
                success:function(_model , response) {
                    var result = response['success'];
                    if (result) {
                        var msg = result['msg'];
                        _uiManager.closeDialog(ref.dialogRef);
                    }
                },
                error : function() {

                }
            });
        },


        doEditUser : function(comp ,e) {
            var ref = this;
            var model = this.model;
            var models = this.models;

            var data = {
                loginAccount: $("#loginAccount").val(),
                chineseName : $("#chineseName").val(),
                staffNo : $("#staffNo").val()
            };

            model.save(data , {
                success:function(_model , response) {
                    var result = response['success'];
                    if (result) {
                        var msg = result['msg'];
                        _uiManager.closeDialog(ref.dialogRef);
                    }
                },
                error : function() {

                }
            });
        }

    });





    mvcmanager.addViewClass(UserForm, 'UserForm');
    return UserForm;


});

