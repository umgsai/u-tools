var treeSetting = {
    check: {
        enable: true,
        nocheckInherit: true
    },
    view: {
        dblClickExpand: false
    },
    data: {
        simpleData: {
            enable: true
        }
    },
    callback: {
        // onCheck: onCheck,
        onClick: onclickTree,
        onCollapse: onCollapse,
        onExpand: onExpand
    },
    async: {
        enable: true,
        url: "/getChildren.json",
        autoParam: ["id", "name", "level"],
        // otherParam:{"otherParam":"zTreeAsyncTest"},
        dataFilter: filter
    }
};

var treeNodes = [
    {
        name: "请选择数据库",
        open: true,
        nocheck: true,
        children: []
    }
];

var tree;
setTimeout(function () {
    $.fn.zTree.init($("#tableTree"), treeSetting, treeNodes);
}, 200);

function onCollapse(event, treeId, treeNode) {
    console.log(treeNode.tId + ", " + treeNode.name);
};

function onExpand(event, treeId, treeNode) {
    console.log("展开：" + treeNode.tId + ", " + treeNode.name);
    if (treeNode.children.length > 0) {
        return;
    }
    console.log("加载子节点....");
    onclickTree(event, treeId, treeNode);
};

function onclickTree(event, treeId, treeNode) {
    console.log(event);
    console.log(treeNode);
    if (treeNode.open) {
        treeNode.open = false;
    } else {
        treeNode.open = true;
    }
    if (treeNode.type != "table") {
        return;
    }
    let tableName = treeNode.name;
    if (treeNode.children.length > 0) {
        let tableNodes = treeNodes[0].children;
        for (let i = 0; i < tableNodes.length; i++) {
            if (tableNodes[i].name == tableName) {
                tableNodes[i].open = treeNode.open;
                tree = $.fn.zTree.init($("#tableTree"), treeSetting, treeNodes);
                break;
            }
        }
        return;
    }
    let dbName = treeNode.getParentNode().name;
    let map = {};
    map = dbForm._data;
    map.dbName = dbName;
    map.tableName = tableName;
    $.ajax({
        type: "post",
        url: "/db/getTableColumnList",
        contentType: "application/json",
        data: JSON.stringify(dbForm._data),
        success: function (dataResult) {
            dbConsole._data.message = dataResult.message;
            dbConsole._data.success = dataResult.success;
            if (!dataResult.success) {
                layui.layer.alert(dataResult.message);
                return;
            }
            if (dataResult.data.length == 0) {
                toastr.error("未查询到字段");
                return;
            }
            treeNode.children = [];
            let data = dataResult.data;
            for (let i = 0; i < data.length; i++) {
                treeNode.children.push({
                    name: data[i].columnName + " " + "[" + data[i].columnType + "]",
                    nocheck: true,
                    type: "column",
                    open: true
                });
            }
            let tableNodes = treeNodes[0].children;
            for (let i = 0; i < tableNodes.length; i++) {
                if (tableNodes[i].name == tableName) {
                    tableNodes[i].children = treeNode.children;
                    tableNodes[i].open = true;
                    // tree.reAsyncChildNodes(tableNodes[i], "refresh");
                    tree = $.fn.zTree.init($("#tableTree"), treeSetting, treeNodes);
                    break;
                }
            }

        }
    });
}

function filter(treeId, parentNode, childNodes) {
    if (!childNodes) return null;
    for (var i = 0, l = childNodes.length; i < l; i++) {
        childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
    }
    return childNodes;
}

function connectServer() {
    console.log(dbForm._data);
    $.ajax({
        type: "post",
        url: "/db/getDbNameList",
        contentType: "application/json",
        data: JSON.stringify(dbForm._data),
        success: function (dataResult) {
            dbConsole._data.message = dataResult.message;
            dbConsole._data.success = dataResult.success;
            if (!dataResult.success) {
                layui.layer.alert(dataResult.message);
                return;
            }
            if (dataResult.length == 0) {
                toastr.error("未查询到数据库");
                return;
            }
            treeNodes = [];
            let data = dataResult.data;
            dbForm._data.dbNameList = data;
            setTimeout(function () {
                layui.form.render('select');
            }, 200);
            toastr.success("连接服务器成功！");
        }
    });
};

function getTableNameList(dbName) {
    $.ajax({
        type: "post",
        url: "/db/getTableNameList",
        contentType: "application/json",
        data: JSON.stringify(dbForm._data),
        success: function (dataResult) {
            dbConsole._data.message = dataResult.message;
            dbConsole._data.success = dataResult.success;
            if (!dataResult.success) {
                layui.layer.alert(dataResult.message);
                return;
            }
            if (dataResult.data.length == 0) {
                toastr.error("未查询到表！");
                return;
            }
            var rootNode = {};
            rootNode.name = dbName;
            rootNode.open = true;
            rootNode.children = [];
            rootNode.icon = "/static/css/zTreeStyle/img/database.png";
            let data = dataResult.data;
            for (let i = 0; i < data.length; i++) {
                rootNode.children.push({
                    name: data[i],
                    icon: "/static/css/zTreeStyle/img/table.png",
                    open: false,
                    type: "table",
                    children: []
                });

            }
            treeNodes = [];
            treeNodes.push(rootNode);
            // currentDbNode.children = tablesNodes;
            tree = $.fn.zTree.init($("#tableTree"), treeSetting, treeNodes);
        }
    });
}

var dbForm = new Vue({
    el: '#db-form',
    data: {
        serverType: "MySQL",
        host: "localhost",
        port: "3306",
        username: "root",
        password: "123456",
        dbName: "",
        dbNameList: []
    }, watch: {
        dbNameList(newValue, oldValue) {
            if (!newValue || newValue.length == 0) {
                return;
            }
            this.dbName = newValue[0];
        },
        dbName(newValue, oldValue) {
            if (!newValue || newValue == "") {
                return;
            }
            getTableNameList(newValue);
        }
    },
    methods: {}
});

function exeSql() {
    // let sql = getSelectText();
    let sql = editor.getSelection();
    if (!sql) {
        let cursor = editor.getCursor()
        if (!cursor) {
            toastr.error("请选中要执行的SQL");
            return;
        }
        let line = cursor.line;
        sql = editor.getLine(line);
        if (!sql) {
            toastr.error("请选中要执行的SQL");
            return;
        }
    }
    // dbForm._data.dbName = "SofaTest";
    dbForm._data.sql = sql;
    $.ajax({
        type: "post",
        url: "/db/exeSql",
        contentType: "application/json",
        data: JSON.stringify(dbForm._data),
        success: function (dataResult) {
            dbConsole._data.message = dataResult.message;
            dbConsole._data.success = dataResult.success;
            if (!dataResult.success) {
                layui.layer.alert(dataResult.message);
            }
            let sqlType = dataResult.data.sqlType;
            if (sqlType == "DML") {
                toastr.success(dataResult.data.message);
                dbConsole._data.message = dataResult.data.message;
                return;
            }
            if (sqlType == "DQL") {
                let columnNameList = dataResult.data.columnNameList;
                let dataList = dataResult.data.data;
                console.log(columnNameList);
                console.log(dataList);
                let cols = [];
                for (let i = 0; i < columnNameList.length; i++) {
                    let colMember = {
                        field: columnNameList[i],
                        title: columnNameList[i]
                    };
                    if (colMember.field != "id") {
                        colMember.edit = 'text';
                    }
                    cols.push(colMember);
                }
                let tableDataList = [];
                for (let i = 0; i < dataList.length; i++) {
                    let dataMember = {};
                    for (let j = 0; j < columnNameList.length; j++) {
                        dataMember[columnNameList[j]] = dataList[i][j];
                    }
                    tableDataList.push(dataMember);
                }
                let columnCount = columnNameList.length;
                let width = columnCount * 110;
                if (width < document.body.clientWidth * 0.8 + 20) {
                    width = document.body.clientWidth * 0.8 + 20;
                }
                let tableSettings = {
                    elem: '#db-result'
                    // , toolbar: true
                    , style: "margin: 0px"
                    , height: 500
                    , width: width
                    , cellMinWidth: 80
                    , cols: []
                    , data: []
                    //,skin: 'line' //表格风格
                    , even: true
                    , page: true //是否显示分页
                    //,limits: [5, 7, 10]
                    , limit: 20 //每页默认显示的数量
                };
                tableSettings.cols.push(cols);
                tableSettings.data = tableDataList;
                layui.table.render(tableSettings);
                return;
            }
            console.log(dataResult);
        }
    });

}

var dbConsole = new Vue({
    el: '#sql-console',
    data: {
        sqlType: "DQL",
        success: true,
        message: "请连接服务器后使用"
    },
    methods: {}
});

layui.use(['element', 'form', 'layer', 'table'], function () {
    var element = layui.element;
    var form = layui.form;
    var table = layui.table;

    form.on('select(dbNameFilter)', function (data) {
        console.log(data.elem); //得到select原始DOM对象
        console.log(data.value); //得到被选中的值
        console.log(data.othis); //得到美化后的DOM对象
        if (data.value == "") {
            return;
        }
        dbForm._data.dbName = data.value;

    });

    table.on('edit(db-result)', function (obj) {
        var value = obj.value //得到修改后的值
            , data = obj.data //得到所在行所有键值
            , field = obj.field; //得到字段
        toastr.info('[ID: ' + data.id + '] ' + field + ' 字段更改为：' + value);
    });

    window.editor = CodeMirror.fromTextArea(document.getElementById('code'), {
        // mode: mime,
        indentWithTabs: true,
        smartIndent: true,
        lineNumbers: true,
        matchBrackets: true,
        autofocus: true,
        // extraKeys: {"Ctrl-Space": "autocomplete"},
        extraKeys: {
            "Ctrl": "autocomplete"
        },
        mode: {name: "text/x-mysql"},
        // theme: "ambiance",
        hintOptions: {
            // http://www.mamicode.com/info-detail-2243638.html
            // tables: result.data,
            tables: {
                "table1": [
                    "col1", "col2"
                ],
                "table2": ["col1", "col2"]
            },
            Tab: function (cm) {
                var spaces = Array(cm.getOption("indentUnit") + 1).join(" ");
                cm.replaceSelection(spaces);
            }
        }
    });

    window.editor.on("keyup", function (cm, event) {
        if (!cm.state.completionActive &&
            ((event.keyCode >= 65 && event.keyCode <= 90) || event.keyCode == 52 || event.keyCode == 219 || event.keyCode == 190 || event.keyCode == 32)) {
            CodeMirror.commands.autocomplete(cm, null, {completeSingle: false});
        }
    });
});