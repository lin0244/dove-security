var table;
var options = {
  "serverSide": true,
  "sAjaxSource": "/security/role/ajaxData",
  "aoColumns": [{ data: 'name' },
    { data: 'code' },
    { data: 'plat' },
    { data: 'typeName' },
    { data: null,"bSortable": false, "mRender":function(data,type,full){
      if(data.type == 0){
          return '<a href="javascript:void(0)" onclick="openRes('+data.id+',1)" > 查看资源信息</a>';
      }else{
      var btn = ''
          +'<a href="javascript:void(0)" onclick="edit('+data.id+',\''+data.name+'\',\''+data.code+'\',\''+data.plat+'\')" > 修改 </a>'
          +'<a href="javascript:void(0)" onclick="deleteById('+data.id+')" > 删除 </a>'
          +'<a href="javascript:void(0)" onclick="openRes('+data.id+',0)" > 配置资源信息</a>'

        return btn;
      }
    }
    }

  ],
  "fnServerParams": function (aoData) {  //查询条件
    aoData.push(
        { "name": "query", "value": {"name":$("input[name='name']").val()} }
    );
  }

};


jQuery(function($) {
  table = dataTables('#table_id_example',options);
  $('table th input:checkbox').on('click' , function(){
    var that = this;
    $(this).closest('table').find('tr > td:first-child input:checkbox')
    .each(function(){
      this.checked = that.checked;
      $(this).closest('tr').toggleClass('selected');
    });

  });

});

function  doSearch() {
  table.ajax.reload();
}

function  add() {
  $("#myModal").modal("show");
}


function  edit(id,name,code,palt) {
  $("#myModal").modal("show");
  $("#myModalLabel").text("修改");
  $("#id").val(id);
  $("#name").val(name);
  $("#code").val(code);


}


function save() {
  var obj = fromData();
  ajax(obj);
}



function  fromData() {
  var obj = {};
  obj.id = $("#id").val();
  obj.name = $("#name").val();
  obj.code = $("#code").val();
  obj.plat = $("#plat").val();

  return obj;

}
function  clear() {
  $("#id").val("");
  $("#name").val("");
  $("#code").val("");

}
function modalClose() {
  clear();

  $("#myModal").modal("hide");
}



function deleteById(id) {
  $.ajax({
    url:"/security/role/delete/"+id,
    type:"DELETE",
    success:function (data) {
      table.ajax.reload();
      layer.alert(data.desc);
    }
  })

}
function ajax(obj) {
  var url ="/security/role/save" ;

  $.ajax({
    url:url ,
    type:"POST",
    data: obj,
    success: function (data) {
      layer.alert(data.desc);
      if(data.code === 200) {
        table.ajax.reload();
        $("#myModal").modal("hide");
        $("#myModalLabel").text("新增");
        clear();
      }

      console.log("结果" + data);
    }
  });
}

function  refresh(url) {
  $.post({url:url});
}



/****************************************配置资源信息 **********************************/

function openRes(id,type) {
  $("#myModa2").modal("show");
  $("#myModalLabel2").text("配置角色资源");
  $("#roleId").val(id);
  if(type ===1){
    $("#save1").hide();
  }else{
    $("#save1").show();

  }
  $("#tree").removeData("tree");
  $("#tree").unbind('click');
  initTree(id);

}
$(function () {
  $('#tree').on('selected', function (evt, data) {
    ress = data;
    console.log(data);
  });
});

function initTree(roleId) {
  var tree_data;
  $.ajax({
    url: "/security/resource/list",
    type: "GET",
    data: {roleId: roleId},
    async: false,
    success: function (res) {
      if (res.code == 200) {
        tree_data = JSON.parse(res.data);
        console.log(tree_data);
        $('#tree').ace_tree({
          dataSource: new DataSourceTree({data: tree_data}),
          multiSelect: true,
          cacheItems: false,
          loadingHTML: '<div class="tree-loading"><i class="icon-refresh icon-spin blue"></i></div>',
          'open-icon': 'icon-minus',
          'close-icon': 'icon-plus',
          'selectable': true,
          'selected-icon': 'icon-ok',
          'unselected-icon': 'icon-remove'
        });

      } else {
        layer.alert("资源信息加载失败");
      }
    }
  })

}

var ress = [];
function saveRes() {
  var roleId = $("#roleId").val();
  var length = ress.info.length;
  if (length == 0) {
    return
  }
  var resIds = "";
  for (var i = 0; i < length; i++) {
    res = ress.info[i];
    resIds = resIds + res.id + ","
  }
  resIds = resIds.replace(/^,|,$/, "");
  $.ajax({
    url: "/security/roleResource/save",
    data: {roleId: roleId, resIds: resIds},
    type: "POST",
    success: function (res) {
      layer.alert(res.desc);
      if (res.code == 200) {
          $("#myModal1").modal("hide");

      }
    },
    error: function () {
      layer.alert("系统异常")
    }

  })
}