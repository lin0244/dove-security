var table;
var options = {
  "serverSide": true,
  "sAjaxSource": "/security/resource/ajaxData",
  "aoColumns": [
    { data: 'name' },
    { data: 'path' },
    { data: 'plat' },
    { data: 'parentName' },
    { data: 'anonymousName' },
    { data: 'requireAuthorityName' }
  ],
  "fnServerParams": function (aoData) {  //查询条件
    aoData.push(
        { "name": "query", "value": {"appName":$("input[name='name']").val(),
          "appProfile":$("input[name='path']").val()

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

