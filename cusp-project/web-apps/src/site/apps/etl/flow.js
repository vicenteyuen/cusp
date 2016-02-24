/**
 * Created by ruanweibiao on 2015-11-03.
 */

AppMod.setConfig({

    /*
    paths:{
        'comps/treeview': '../../js/react-plugin/treeview/tv'
    }
    */

})

AppMod.application({
    supportMVC:true,
    /**
     * defeind use template engine
     *
     */
    tplEngines:['doT'],

    // --- module , plugin , component defined
    mods: ['jstree','jsPlumbToolkit' , 'theme-AdminLTE'],

    /**
     * get launch ctx
     * @param _ctx
     */
    launch : function(_ctx) {
        var _g = _ctx.getGlobal(),
            restApiCtx = _g.getFullRestApiContext();

        // --- render module ---
        var tplEngine = _ctx.getClientTplEngine('doT');

        var menuElems = $('section.sidebar ul.sidebar-menu');
        var smTmpl = document.getElementById('sidebar-menu-tpl').innerHTML;
        $.get(restApiCtx + '/system/nav-menus',function(data,status) {

            if (status == 'success') {
                // --- parse data ---
                var navMenus = data["data"];

                // --- parse template ---
                tplEngine.render({
                    template:smTmpl,
                    data:navMenus,
                    renderCallback: function(renderResult) {
                        menuElems.html(renderResult);
                    }
                });
            }

        });



        /*
        $.ajax({
            url:restApiCtx + '/security/nav-menus',
            success:function() {
                alert("Success");
            },
            error:function() {
                alert("Error");
            },
            dataType:"json"
        }
        */


            /*

        tplEngine.render({
            template:"<h1>{{=it.name}}</h1>",
            data:{name:'hello vison'},
            renderCallback: function(renderResult) {


            }
        });*/



       // var doTmp = doT.template("<h1>{{=it.name}}</h1>");
       // var res = doTmp({name:'hemo'});







        $("#oper-treeview").jstree({
            'plugins':[],
            'core' : {
                'data':dataOperTree
            }
        });
        /*    .on('select_node.jstree',function(node,selectedNodes,event) {
            selectedNodes.selected;
            console.log(selected.selected);
        })*/


        /*
        $("#oper-treeview").jstree({
            'plugins':[],
            'core' : {
                'data':{
                    'url':restApiCtx '/user/12',
                    'data' : function(node) {
                        return {'id' : node.id};
                    }
                }
            }

        });
        */

        /*

        try {

            // --- load tree view component ---
            TreeView = React.createFactory(TreeView);
            React.render(TreeView({
                data : dataOperTree
            }), document.getElementById('oper-treeview'));

        } catch (e) {
            if (window.console) {
                // --- show error ---
                console.log(e);
            }
        }
        */










        // --- binding event ---
        $("#import-from-csv").bind('click',function() {
            // --- build instance ---

        })


        jsPlumbToolkit.ready(function() {


            // --- toolkit setup ---

            // prepare some data
            var data = {
                nodes:[
                    { id:"1", label:"jsPlumb" },
                    { id:"2", label:"Toolkit" },
                    { id:"3", label:"Hello" },
                    { id:"4", label:"World" }
                ],
                edges:[
                    { source:"1", target:"2" },
                    { source:"2", target:"3" },
                    { source:"3", target:"4" },
                    { source:"4", target:"1" }
                ]
            };



            // --- declare tookit ---
            var toolkit = jsPlumbToolkit.newInstance();



            var mainElement = document.querySelector("#jtk-main"),
                canvasElement = mainElement.querySelector(".jtk-canvas");
                miniviewElement = mainElement.querySelector(".miniview");


            var renderer = toolkit.render({
                container:canvasElement,
                miniview:{
                    container:miniviewElement
                },
                layout:{
                    type:"Spring"
                },
                zoomToFit:true,
                view:{
                    nodes:{
                        "default":{
                            template:"tmplNode"
                        }
                    }
                },
                jsPlumb:{
                    Connector:"Bezier",
                    Anchor:"Continuous",
                    Endpoint:["Dot", { radius:3 }],
                    PaintStyle: { lineWidth: 1, strokeStyle: '#89bcde' },
                    EndpointStyle: { fillStyle:"#89bcde" }
                }

            });

            // load the data.
            toolkit.load({
                data:data
            });

        });










        //$.AdminLTE.tree('.step-container');






        //


    }
})


/* define the flow menu object handle  */
var dataOperTree = [
    {
        id: '01',
        text: "输入",
        children: [
            {
                id:'01.01',
                text: "Access输入"
            },
            {
                id:'01.02',
                text: "CSV文件输入"
            },
            {
                id:'01.03',
                text: "Excel文件输入"
            }
        ]
    },
    {
        id:'02',
        text: "输出",
        parent:'01',
        children:[
            {
                id:'02.01',
                text: "Access输出"
            },
            {
                id:'02.02',
                text: "Json输出"
            },
            {
                id:'02.03',
                text: "Excel输出"
            }
        ]
    },
    {
        id:'03',
        text: "转换"
    },
    {
        id:'04',
        text: "流程"
    },
    {
        id:'05',
        text: "查询"
    }
];