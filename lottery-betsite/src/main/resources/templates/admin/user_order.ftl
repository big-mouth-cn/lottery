<!DOCTYPE HTML>
<html>
<head>
    <title>PK10</title>
<#include "/modules/header.ftl"/>

</head>
<body>
<#include "/admin/modules/menu.ftl"/>

<div class="container">
    <div class="row">
        <h3><span class="user">${realName}</span> 的投注记录</h3>
    </div>
    <div class="row">
        <table class="table" id="orders_table">
            <thead>
            <tr>
                <th>投注时间</th>
                <th>投注期号</th>
                <th>状态</th>
                <th>下注积分</th>
                <th>中奖积分</th>
                <th></th>
            </tr>
            </thead>
            <tbody></tbody>
        </table>
    </div>
</div>

<script id="orders_empty_template" type="text/html">
    <tr>
        <td colspan="5"><div class="alert alert-warning">暂时没有找到符合条件的内存</div></td>
    </tr>
</script>
<script id="orders_template" type="text/html">
    {{each rows as row index}}
    <tr>
        <td>{{row.createTime | dateFormat : 'yyyy-MM-dd HH:mm:ss'}}</td>
        <td>{{row.issueNo}}</td>
        <td>{{row.status | format : 'status'}}</td>
        <td>{{row.totalScore | format : 'toFixed'}}</td>
        <td>{{row.incomeScore | format : 'toFixed'}}</td>
        <td class="text-right">{{row | format:'optFormat', row}}</td>
    </tr>
    {{/each}}
</script>
<#include "/modules/num-table2.ftl"/>

<#include "/modules/footer.ftl"/>
</body>
</html>
<script src="${ctx.contextPath}/js/order_loaded.js"></script>
<script type="text/javascript">
    var userid = '${userId}';

    Nvwa.colFormatter.optFormat = function(row) {
        var btngroup = '<div class="btn-group" role="group">';
        btngroup += '<a class="btn-link details" href="javascript:;" title="明细" data-id="'+row.id+'" data-issueno="'+row.issueNo+'">明细</a>';
        btngroup += '</div>';
        return btngroup;
    };

    function refresh() {
        $.get(ctx + '/admin/user_orders', {
            userId : userid
        }, function(json) {
            if (json.success) {
                if (json.results > 0) {
                    var rows = json.rows;
                    var html = template('orders_template', { rows : rows });
                    $('#orders_table tbody').empty().html(html);
                } else {
                    $('#orders_table tbody').empty().html(template('orders_empty_template'));
                }
            } else {
                Toast.danger(json.msg);
            }
        });
    }

    $(function() {
        refresh();

        $(document).on('click', 'a.details', function() {
            var issueno = $(this).data('issueno');
            var id = $(this).data('id');
            $.get(ctx + '/admin/user_order', {
                issueNo : issueno,
                id : id,
                userId : userid
            }, function(json) {
                if (json.success) {
                    var o = json.single;
                    $(document).trigger('ORDER_LOADED', [o]);
                } else {
                    Toast.danger(json.msg);
                }
            });
        });
    });
</script>