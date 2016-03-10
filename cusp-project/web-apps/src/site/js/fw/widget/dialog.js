/**
 * Created by vison on 2016/3/9.
 */
define('widget/dialog',['_g','doT'],function(_g , tplEngine) {
    var _this = this;

    var builder = new function() {
        // --- dialog define config ---
        var _conf = null;
        var wconf = null;
        var renderElem = null;


        this.init = function(conf) {
            _conf = conf;

        }

        this.openCommonDialog = function(viewConf) {
            // --- create dialog html ---
            var dialogInitConf = {
                id:'myModal'
            };
            var dialogHtml = '<div class="modal fade" tabindex="-1" role="dialog" id="'+dialogInitConf.id+'" aria-labelledby="myModalLabel" aria-hidden="true"><div class="modal-dialog"><div class="modal-content"></div></div></div>';
            $('body').append(dialogHtml);


            var diaElems = $('div[role="dialog"].modal');
            if (diaElems.length > 1) {
                throw Error('Found more than 1 dialog under html');
            }

            var modalBodyElems = diaElems.find('div.modal-content');
            var containElems = modalBodyElems.children();
            if (containElems.length > 0) {
                // --- clear contain first ---
                containElems.remove();
            }


            // --- append html ---
            var htmlContent = viewConf['html'];
            if (!htmlContent) {
                htmlContent = '';
            }
            modalBodyElems.html(htmlContent);


            var _defHandlers = {
                'ui:rendered' : function(){}
            }

            if (viewConf['handlers']) {
                // ---reset handlers --
                if (viewConf['handlers']['ui:rendered']) {
                    _defHandlers['ui:rendered'] = viewConf['handlers']['ui:rendered'];
                }
            }


            // --- open dialog ---
            diaElems.modal({
                keyboard: true
            }).on('shown.bs.modal', function (modal_eve) {

                // --- proxy delegate --
                _defHandlers['ui:rendered'](this , modal_eve);
            }).on('hidden.bs.modal' , function(modal_eve) {

                // --- remove mapping element  ---
                $(this).remove();
            });
        };

        /**
         *  set default dialog handle
          * @param viewConf
         */
        this.openDefaultDialog = function(viewConf) {

        };





        this.closeDialog = function(dialogRef) {
            if (!dialogRef) {
                return;
            }

            $(dialogRef).modal('toggle');

        }


        this.showMsgDialog = function(dialogRef) {

            var dialogTpl = '<div class="modal fade {{=it.type}}" role="msgbox" >';
            dialogTpl = dialogTpl+'<div class="modal-dialog">';
            dialogTpl = dialogTpl+'<div class="modal-content">';

            // --- header ---
            dialogTpl = dialogTpl+'<div class="modal-header">';
            dialogTpl = dialogTpl+'<button type="button" class="close" aria-label="Close">';
            dialogTpl = dialogTpl+'<span aria-hidden="true">&times;</span>';
            dialogTpl = dialogTpl+'</button>';
            dialogTpl = dialogTpl+'<h4 class="modal-title">{{=it.modalTitle}}</h4>';
            dialogTpl = dialogTpl + '</div>';

            // --- body ---
            dialogTpl = dialogTpl+'<div class="modal-body">';
            dialogTpl = dialogTpl + '{{=it.content}}';
            dialogTpl = dialogTpl + '</div>';


            // --- footer ---
            dialogTpl = dialogTpl+'<div class="modal-footer">';
            dialogTpl = dialogTpl + '{{=it.btn["yes"]}}';
            dialogTpl = dialogTpl + '{{=it.btn["no"]}}';
            dialogTpl = dialogTpl + '{{=it.btn["cancel"]}}';
            dialogTpl = dialogTpl + '</div>';


            dialogTpl = dialogTpl + '</div>';
            dialogTpl = dialogTpl + '</div>';
            dialogTpl = dialogTpl + '</div>';


            var btnConfirm = '<button type="button" class="btn btn-outline btn-yes">确认</button>';
            var btnNo = '<button type="button" class="btn btn-outline btn-no">否定</button>';
            var btnCancel = '<button type="button" class="btn btn-outline btn-cancel">取消</button>';


            var _data = {
                type:dialogRef['type'],
                modalTitle: '',
                msgtype:'alert',
                content:dialogRef['content'],
                btn:{
                    'yes':'',
                    'no':'',
                    'cancel':''
                }
            }

            if (dialogRef['buttons']) {
                for (var i = 0 ; i < dialogRef['buttons'].length ; i++) {
                    if (dialogRef['buttons'][i] == 'yes') {
                        _data.btn['yes'] = btnConfirm;
                    }
                    else if (dialogRef['buttons'][i] == 'no') {
                        _data.btn['no'] = btnNo;
                    }
                    else if (dialogRef['buttons'][i] == 'cancel') {
                        _data.btn['cancel'] = btnCancel;
                    }
                }
            } else {
                _data.btn['yes'] = btnConfirm;
            }





            if (dialogRef['type'] == 'info') {
                _data.type = 'modal-info';
                _data.modalTitle = '信息';
            }
            else if (dialogRef['type'] == 'warning') {
                _data.type = 'modal-warning';
                _data.modalTitle = '警告';
            }
            else if (dialogRef['type'] == 'primary') {
                _data.type = 'modal-primary';
                _data.modalTitle = '主要';
            }
            else if (dialogRef['type'] == 'success') {
                _data.type = 'modal-success';
                _data.modalTitle = '成功';
            }
            else if (dialogRef['type'] == 'error') {
                _data.type= 'modal-danger';
                _data.modalTitle = '出错';
            }

            // --- render html result ---
            var htmlResult = "";
            tplEngine.render({
                tplHtml:dialogTpl ,
                data:_data,
                renderCallback: function(renderResult) {
                    htmlResult = renderResult;
                }
            });

            // --- create html dom --
            $('body').append(htmlResult);
            var modalElems = $('div[role="msgbox"].modal');

            // --- open dialog ---
            modalElems.modal({
                keyboard: true
            }).on('shown.bs.modal', function (modal_eve) {
                // --- proxy delegate ---
                var $thisModal = $(this);

                // --- define buttom event ---
                $thisModal.find('button.btn-yes').on('click' , function(e) {

                    if (dialogRef['btnCallback']) {
                        var func = dialogRef['btnCallback']['yes'];
                        if (func) {func(this,e)};
                    }
                    $thisModal.modal('toggle');
                });

                $thisModal.find('button.btn-no').on('click' , function(e) {

                    if (dialogRef['btnCallback']) {
                        var func = dialogRef['btnCallback']['no'];
                        if (func) {func(this,e)};
                    }
                    $thisModal.modal('toggle');
                });
                $thisModal.find('button.btn-cancel').on('click' , function(e) {

                    if (dialogRef['btnCallback']) {
                        var func = dialogRef['btnCallback']['cancel'];
                        if (func) {func(this,e)};
                    }
                    $thisModal.modal('toggle');
                });


                $thisModal.find('button.close').on('click' , function() {
                    $thisModal.modal('toggle');
                });


            }).on('hidden.bs.modal' , function(modal_eve) {

                // --- remove mapping element  ---
                $(this).remove();
            });


            return modalElems;
        }



        this.build = function() {
            return this;
        }


    }

    return builder;
});