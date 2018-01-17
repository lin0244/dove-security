var table;
var options = {
  "serverSide": true,
  "sAjaxSource": "/security/roleResource/ajaxData",
  "aoColumns": [{ data: 'roleName' },
    { data: 'roleCode' },
    { data: 'resName' },
    { data: 'resPath' },
    { data: 'plat' },
    { data: 'typeName' },
    { data: null,"bSortable": false, "mRender":function(data,type,full){
      var btn = ''
          +'<a href="javascript:void(0)" onclick="deleteById('+data.id+')" > 删除 </a>'
      return btn; }
    }

  ],
  "fnServerParams": function (aoData) {  //查询条件
    aoData.push(
        { "name": "query", "value": {"roleName":$("input[name='roleName']").val(),
          "resName":$("input[name='resName']").val(),
          "resPath":$("input[name='resPath']").val()
        } }
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


function  refresh(url) {
  $.post({url:url});
}