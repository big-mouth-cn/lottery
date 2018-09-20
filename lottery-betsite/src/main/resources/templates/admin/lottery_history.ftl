<!DOCTYPE HTML>
<html>
<head>
    <title>PK10</title>
    <#include "/modules/header.ftl"/>

</head>
<body>
    <#include "/admin/modules/menu.ftl"/>

    <div class="container">
    <#include "/modules/lottery_history.ftl"/>
    </div>
    <#include "/modules/footer.ftl"/>
    <#include "/modules/num-table2.ftl"/>
</body>
</html>
<link rel="stylesheet" type="text/css" href="${ctx.contextPath}/css/bootstrap-datetimepicker.min.css">
<script type="text/javascript" src="${ctx.contextPath}/js/datepicker/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="${ctx.contextPath}/js/datepicker/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript">
    Nvwa.colFormatter.optFormatForLotteryHistory = function(row) {
        var btngroup = '<div class="btn-group" role="group">';
        btngroup += '<a class="btn-link total" href="javascript:;" title="投注统计" data-id="'+row.id+'" data-issueno="' + row.issueNo + '">投注统计</a>';
        btngroup += '</div>';
        return btngroup;
    };

    $(function () {
        $('#date').val(DateUtil.format(new Date(), 'yyyy-MM-dd')).datetimepicker({
            autoclose : true,
            language : 'zh-CN',
            minView : 2
        });

        $('#search-lottery-history').on('click', function() {
            load($('#date').val());
        });

        $('#search-lottery-history').trigger('click');

        $(document).on('click', 'a.total', function() {
            var issueNo = $(this).data('issueno');
            get(issueNo);
        });
    });

    function load(date) {
        $.get(ctx + '/lottery_history', {
            date : date
        }, function(json) {
            if (json.success) {
                $('#lottery_history_table tbody').empty();
                if (json.results > 0) {
                    var body = template('lottery-history-template', { rows : json.rows });
                    $('#lottery_history_table tbody').append(body);
                }
            } else {
                Toast.danger(json.msg);
            }
        });
    }

    function get(issueNo) {
        $.get(ctx + '/admin/orders', {
            issueNo : issueNo
        }, function(json) {
            if (json.success) {
                var o = json.single;
                var draw = o.draw;
                var body = $('<div>');
                body.html(template('table2-template'));
                $.modal.open({
                    title : '第 ' + o.issueNo + ' 期',
                    body : body,
                    size : 'lg',
                    okBtn : 'hide',
                    shown : function() {
                        for (var k in o) {
                            var o2 = o[k];
                            for (var k1 in o2) {
                                var v = o[k][k1];
                                var v1 = v > 0 ? Nvwa.colFormatter.toFixed(v) : '-';
                                body.find('td[name=' + (k + '_' + k1) + ']').text(v1);
                            }
                        }
                        body.find('p[name=income]').html('中奖积分：' + Nvwa.colFormatter.toFixed(o.incomeScore));
                        if (draw) {
                            for (var k in draw) {
                                var n = k;
                                var v = draw[k];
                                body.find('td[name="' + (n + '_no' + v) + '"]').addClass('hit');
                            }
                        }
                    }
                });
            } else {
                Toast.danger(json.msg);
            }
        });
    }
</script>