var table;
var options = {
  "serverSide": true,
  "sAjaxSource": "/security/platUser/ajaxData",
  "aoColumns": [
    {data: 'id'},
    {data: 'name'},
    {data: 'plat'},
    {
      data: null, "bSortable": false, "mRender": function (data, type, full) {

      var btn = ''
          + '<a href="javascript:void(0)" onclick="edit(' + data.id
          + ')" > 配置角色信息 </a>'
      return btn;
    }
    }
  ],
  "fnServerParams": function (aoData) {  //查询条件
    aoData.push(
        {
          "name": "query", "value": {
          "userName": $("input[name='userName']").val()

        }
        }
    );
  }

};

jQuery(function ($) {

  table = dataTables('#table_id_example', options);
  $('table th input:checkbox').on('click', function () {
    var that = this;
    $(this).closest('table').find('tr > td:first-child input:checkbox')
    .each(function () {
      this.checked = that.checked;
      $(this).closest('tr').toggleClass('selected');
    });

  });

});

function doSearch() {
  table.ajax.reload();
}

function edit(id) {
  $("#myModal").modal("show");
  $("#myModalLabel").text("配置用户角色信息");
  $("#userId").val(id);
  $("#tree").removeData("tree");
  $("#tree").unbind('click');
  initTree(id);

}

function modalClose() {

  $("#myModal").modal("hide");
}

$(function () {
  $('#tree').on('selected', function (evt, data) {
    roles = data;
    console.log(data);
  });
})

function initTree(userId) {
  var tree_data;
  $.ajax({
    url: "/security/role/list",
    type: "GET",
    data: {userId: userId},
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
        layer.alert("角色信息加载失败");
      }
    }
  })

}

var roles = [];
function save() {
  var userId = $("#userId").val();
  var length = roles.info.length;
  if (length == 0) {
    return
  }
  var roleIds = "";
  for (var i = 0; i < length; i++) {
    role = roles.info[i];
    roleIds = roleIds + role.id + ","
  }
  roleIds = roleIds.replace(/^,|,$/, "");
  $.ajax({
    url: "/security/userRole/save",
    data: {userId: userId, roles: roleIds},
    type: "POST",
    success: function (res) {
      layer.alert(res.desc);
      if (res.code == 200) {
        modalClose();
      }
    },
    error: function () {
      layer.alert("系统异常")
    }

  })
}