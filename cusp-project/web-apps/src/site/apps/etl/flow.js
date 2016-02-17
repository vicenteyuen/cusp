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
    // --- module , plugin , component defined
    mods: ['jsPlumbToolkit' , 'theme-AdminLTE'],

    launch : function() {

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










        $.AdminLTE.tree('.step-container');






        //


    }
})


/* define the flow menu object handle  */
var dataOperTree = [
    {
        id: '5',
        text: "Parent 1",
        icon: "btn-folder",
        nodes: [
            {
                text: "Child 1",
                nodes: [
                    {
                        text: "Grandchild 1"
                    },
                    {
                        text: "Grandchild 2"
                    }
                ]
            },
            {
                text: "Child 2"
            }
        ]
    },
    {
        text: "Parent 2"
    },
    {
        text: "Parent 3"
    },
    {
        text: "Parent 4"
    },
    {
        text: "Parent 5"
    }
];