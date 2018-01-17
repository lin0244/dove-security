var table;
var options = {
  "serverSide": true,
  "sAjaxSource": "/security/platUser/ajaxData",
  "aoColumns": [
    { data: 'userId' },
    { data: 'userName' },
    { data: 'plat' }
  ],
  "fnServerParams": function (aoData) {  //查询条件
    aoData.push(
        { "name": "query", "value": {"userName":$("input[name='userName']").val()

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

