<!DOCTYPE HTML>
<html>
<head>
    <title>PK10</title>
    <#include "/modules/header.ftl"/>
</head>
<body>
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <div class="navbar-form navbar-left" id="last-div">
                <p class="navbar-text">第 <span class="last-issue">-</span> 期开奖结果：</p>
            </div>
            <div class="navbar-form navbar-right">
                <p class="navbar-text"><a href="javascript:;" id="logout">退出</a></p>
            </div>
        </div>
    </nav>

    <div class="container-fluid">
        <div class="row">
            <div class="col-md-2">
                <div class="row userinfo">
                    <ul class="list-group">
                        <li class="list-group-item" style="background-color: #f5f6fa;"><span name="username">-</span></li>
                        <li class="list-group-item">可用积分：<span class="badge" name="balance">0</span></li>
                        <li class="list-group-item">已用积分：<span class="badge text-success" name="used">0</span></li>
                        <li class="list-group-item">已获积分：<span class="badge text-danger" name="gain">0</span></li>
                    </ul>
                </div>
            </div>
            <div class="col-md-10">
                <ul class="nav nav-tabs" role="tablist">
                    <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">投注</a></li>
                    <li role="presentation"><a href="#orders" aria-controls="orders" role="tab" data-toggle="tab">投注记录</a></li>
                    <li role="presentation"><a href="#score_history" aria-controls="score_history" role="tab" data-toggle="tab">积分记录</a></li>
                    <li role="presentation"><a href="#lottery_history" aria-controls="lottery_history" role="tab" data-toggle="tab">开奖记录</a></li>
                </ul>

                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane active" id="home">
                        <div class="row">
                        <#include "/modules/num-table.ftl"/>
                        </div>
                    </div>
                    <div role="tabpanel" class="tab-pane" id="orders">
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
                    <#include "/modules/num-table2.ftl"/>
                    </div>
                    <div role="tabpanel" class="tab-pane" id="score_history">
                        <table class="table" id="score_history_table">
                            <thead>
                            <tr>
                                <th>交易时间</th>
                                <th>交易类型</th>
                                <th>交易积分</th>
                                <th>备注</th>
                            </tr>
                            </thead>
                            <tbody></tbody>
                        </table>
                    </div>
                    <div role="tabpanel" class="tab-pane" id="lottery_history">
                        <#include "/modules/lottery_history.ftl"/>
                    </div>
                </div>
            </div>
        </div>

    </div>

    <#include "/modules/footer.ftl"/>

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

    <script id="score_history_empty_template" type="text/html">
        <tr>
            <td colspan="4"><div class="alert alert-warning">暂时没有找到符合条件的内存</div></td>
        </tr>
    </script>
    <script id="score_history_template" type="text/html">
        {{each rows as row index}}
        <tr>
            <td>{{row.createTime | dateFormat : 'yyyy-MM-dd HH:mm:ss'}}</td>
            <td>{{row.scoreType | format : 'scoreType'}}</td>
            <td>{{row.scoreValue | format : 'toFixed'}}</td>
            <td>{{row.remark}}</td>
        </tr>
        {{/each}}
    </script>
</body>
</html>
<link rel="stylesheet" type="text/css" href="${ctx.contextPath}/css/bootstrap-datetimepicker.min.css">
<script type="text/javascript" src="${ctx.contextPath}/js/datepicker/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="${ctx.contextPath}/js/datepicker/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="${ctx.contextPath}/js/order_loaded.js"></script>
<script src="${ctx.contextPath}/js/score_history.js"></script>
<script src="${ctx.contextPath}/js/lottery_history.js"></script>
<script src="${ctx.contextPath}/js/orders.js"></script>

<script>
    var hours = [9,10,11,12,13,14,15,16,17,18,19,20,21,22,23];
    var minutes = [2,7,12,17,22,27,32,37,42,47,52,57];
    var banMinutes = [1,6,11,16,21,26,31,36,41,46,51,56];

    var nextDrawTime = new Date();

    var EVENT_BAN = "event.ban";
    var EVENT_UNBAN = "event.unban";
    var EVENT_DRAWING = "event.drawing";
    var EVENT_DRAWED = "event.drawed";

    function serverTime(time) {
        var date = new Date();
        date.setTime(time);

        setInterval(function() {
            var crt_hour = date.getHours();
            var crt_minute = date.getMinutes();

            // 开奖时间
            if ($.inArray(crt_hour, hours) != -1 && $.inArray(crt_minute, minutes) != -1) {
                startDrawing();
            }
            date.setTime(date.getTime() + 1000);
            var time = diffTime(date, nextDrawTime);
            $('.next-diff').text((nextDrawTime.getTime() - date.getTime()) < 1 ? '开奖中' : time);
        }, 1000);

        setInterval(function() {
            var crt_hour = date.getHours();
            var crt_minute = date.getMinutes();
            var crt_second = date.getSeconds();
            // 封盘时间
            if ($.inArray(crt_hour, hours) != -1 && $.inArray(crt_minute, banMinutes) != -1 && crt_second >= 45) {
                $(document).trigger(EVENT_BAN);
            } else {
                $(document).trigger(EVENT_UNBAN);
            }
        }, 1000);
    }

    function refreshSubmitIssueNo() {
        $.get(ctx + '/submitIssueNo', {}, function(submitIssueNo) {
            $('span.submit-issue').text(submitIssueNo);
        });
    }

    function refresh(fn) {
        $.get(ctx + '/last', {}, function(json) {
            if (json.success) {
                var o = json.single;
                $('span.last-issue').text(o.issueNo);
                $('#last-div').find('button.icon').remove();
                $.each(o.numbers, function(i, number) {
                    $('#last-div').append('<button type="button" class="navbar-btn icon icon_' + number + '"></button>');
                });
                $('span.next-issue').text(o.nextIssueNo);
                $('span.submit-issue').text(o.submitIssueNo);
                nextDrawTime = new Date(o.nextDrawTime);
                if (fn) fn(o);
            }
        });
    }

    function startDrawing() {
        $.get(ctx + '/drawing', {}, function(bool) {
            $(document).trigger(bool ? EVENT_DRAWING : EVENT_DRAWED);
        });
    }

    function refreshUserInfo() {
        $.get(ctx + '/info', {}, function(json) {
            $('span[name=username]').text(json.realName + '（' + json.username + '）');
            $('span[name=balance]').text(Nvwa.colFormatter.toFixed(json.balance));
            $('span[name=used]').text(Nvwa.colFormatter.toFixed(json.used));
            $('span[name=gain]').text(Nvwa.colFormatter.toFixed(json.gain));
        });
    }

    $(function() {
        if (m_client) {
            $('td.multiple').remove();
            $('span.tt').empty().append('<span>可下注第 <span class="submit-issue">-</span> 期 <br> 距离下期 <span class="next-issue">-</span> 开奖：</span>');
        }

        refreshUserInfo();

        refresh(function(o) {
            serverTime(o.serverTime);
        });

        $(document).bind(EVENT_BAN, function(e, text) {
            $('input.score').attr('readonly', true);
            $('input.batch-setting').attr('readonly', true);
            $('button.option').attr('disabled', true);
        });
        $(document).bind(EVENT_UNBAN, function(e, text) {
            $('input.score').attr('readonly', false);
            $('input.batch-setting').attr('readonly', false);
            $('button.option').attr('disabled', false);
        });
        $(document).bind(EVENT_DRAWING, function() {
            refreshSubmitIssueNo();
        });
        $(document).bind(EVENT_DRAWED, function() {
            refresh();
            refreshUserInfo();
        });

        $('#logout').click(function() {
            $.get(ctx + '/auth/logout', {}, function() {
                location.reload();
            });
        });

        $('input.score').click(function() {
            var val = $(this).val();
            if (!val) {
                $(this).val($('input.batch-setting').val());
            }
        });
        $('input.score').on('dblclick', function() {
            var val = $(this).val();
            if (val === $('input.batch-setting').val()) {
                $(this).val('');
            }
        });
        $('input.score').on('keyup', function(e) {
            if (e.keyCode === 13) {
                $('#submit').trigger('click');
            }
        });

        $('#submit').click(function() {
            var btn = $(this);
            var sumScore = getSumScore();
            if (sumScore === 0) {
                return;
            }
            var requestBody = getRequestBody();

            var body = $('<div>');
            body.append('你本次投注需要总积分：' + sumScore + '，确定要投注吗？');

            $.modal.open({
                title : '确认投注',
                body : body,
                okHandler : function(e) {
                    var in_btn = $(e.target);
                    in_btn.attr('disabled', true);
                    $.ajax({
                        type : "POST",
                        url : ctx + "/submit",
                        data : JSON.stringify(requestBody),
                        contentType : "application/json",
                        dataType : "json",
                        success : function(json) {
                            if (json.success) {
                                var order = json.single;
                                Toast.success("第 " + order.issueNo + " 期投注成功!");
                                refreshUserInfo();
                                $.modal.close();
                            } else {
                                Toast.danger(json.msg);
                            }
                            in_btn.attr('disabled', false);
                        }
                    });
                },
                shown : function() {
                    btn.attr('disabled', true);
                    $($.modal.tags.modal_footer_ok).focus();
                },
                hiden : function() {
                    btn.attr('disabled', false);
                }
            })
        });

        $('#reset').click(function() {
            $('input.score').val('');
        });
    });

    function getRequestBody() {
        var o = {
            'one' : {
                'no1' : 0,
                'no2' : 0,
                'no3' : 0,
                'no4' : 0,
                'no5' : 0,
                'no6' : 0,
                'no7' : 0,
                'no8' : 0,
                'no9' : 0,
                'no10' : 0
            },
            'two' : {
                'no1' : 0,
                'no2' : 0,
                'no3' : 0,
                'no4' : 0,
                'no5' : 0,
                'no6' : 0,
                'no7' : 0,
                'no8' : 0,
                'no9' : 0,
                'no10' : 0
            },
            'three' : {
                'no1' : 0,
                'no2' : 0,
                'no3' : 0,
                'no4' : 0,
                'no5' : 0,
                'no6' : 0,
                'no7' : 0,
                'no8' : 0,
                'no9' : 0,
                'no10' : 0
            },
            'four' : {
                'no1' : 0,
                'no2' : 0,
                'no3' : 0,
                'no4' : 0,
                'no5' : 0,
                'no6' : 0,
                'no7' : 0,
                'no8' : 0,
                'no9' : 0,
                'no10' : 0
            },
            'five' : {
                'no1' : 0,
                'no2' : 0,
                'no3' : 0,
                'no4' : 0,
                'no5' : 0,
                'no6' : 0,
                'no7' : 0,
                'no8' : 0,
                'no9' : 0,
                'no10' : 0
            },
            'six' : {
                'no1' : 0,
                'no2' : 0,
                'no3' : 0,
                'no4' : 0,
                'no5' : 0,
                'no6' : 0,
                'no7' : 0,
                'no8' : 0,
                'no9' : 0,
                'no10' : 0
            },
            'seven' : {
                'no1' : 0,
                'no2' : 0,
                'no3' : 0,
                'no4' : 0,
                'no5' : 0,
                'no6' : 0,
                'no7' : 0,
                'no8' : 0,
                'no9' : 0,
                'no10' : 0
            },
            'eight' : {
                'no1' : 0,
                'no2' : 0,
                'no3' : 0,
                'no4' : 0,
                'no5' : 0,
                'no6' : 0,
                'no7' : 0,
                'no8' : 0,
                'no9' : 0,
                'no10' : 0
            },
            'nine' : {
                'no1' : 0,
                'no2' : 0,
                'no3' : 0,
                'no4' : 0,
                'no5' : 0,
                'no6' : 0,
                'no7' : 0,
                'no8' : 0,
                'no9' : 0,
                'no10' : 0
            },
            'ten' : {
                'no1' : 0,
                'no2' : 0,
                'no3' : 0,
                'no4' : 0,
                'no5' : 0,
                'no6' : 0,
                'no7' : 0,
                'no8' : 0,
                'no9' : 0,
                'no10' : 0
            }
        };
        $('input.score').each(function() {
            var score = $(this).val();
            if (score) {
                var names = $(this).attr('name').split('_');
                console.log(names);
                var l1 = names[0];
                var l2 = names[1];

                o[l1][l2] = score * 100;
            }
        });

        return o;
    }

    function getSumScore() {
        var sumScore = 0;
        $('input.score').each(function() {
            var score = $(this).val();
            if (score) {
                sumScore += parseFloat(score);
            }
        });
        return sumScore;
    }
</script>