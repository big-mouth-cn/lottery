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
            <div class="col-md-12">
                <div class="form form-inline">
                    <button class="btn btn-primary" type="button" id="btn-add">新建</button>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <table class="table" id="users-table">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th></th>
                        <th>用户</th>
                        <th>余额</th>
                        <th>已用</th>
                        <th>获利</th>
                        <th>状态</th>
                        <th>注册时间</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody></tbody>
                </table>
            </div>
        </div>
    </div>

    <script id="users" type="text/html">
        {{each rows as row index}}
        <tr>
            <td>{{row.id}}</td>
            <td>
                {{if row.isAdmin == 1}}
                <i class="glyphicon glyphicon-user"></i>
                {{/if}}
            </td>
            <td>{{row.realName}}（{{row.username}}）</td>
            <td>{{row.balance | format : 'toFixed'}}</td>
            <td>{{row.used | format : 'toFixed'}}</td>
            <td>{{row.gain | format : 'toFixed'}}</td>
            <td>{{row.status | format : 'formatStatus'}}</td>
            <td>{{row.createTime | dateFormat : 'yyyy-MM-dd'}}</td>
            <td class="text-right">{{row | format:'optFormat', row}}</td>
        </tr>
        {{/each}}
    </script>

    <script id="charge" type="text/html">
        <div class="form-horizontal">
            <div class="form-group">
                <label class="col-sm-2 control-label">用户名</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" name="username" readonly>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">充值积分</label>
                <div class="col-sm-10">
                    <input type="number" class="form-control" name="value">
                </div>
            </div>
        </div>
    </script>

    <script id="extract" type="text/html">
        <div class="form-horizontal">
            <div class="form-group">
                <label class="col-sm-2 control-label">用户名</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" name="username" readonly>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">提取积分</label>
                <div class="col-sm-10">
                    <input type="number" class="form-control" name="value">
                </div>
            </div>
        </div>
    </script>

    <script id="add" type="text/html">
        <div class="form-horizontal">
            <div class="form-group">
                <label class="col-sm-2 control-label">用户名</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" name="username">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">密码</label>
                <div class="col-sm-10">
                    <input type="password" class="form-control" name="password">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">真实姓名</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" name="realName">
                </div>
            </div>
        </div>
    </script>

    <#include "/modules/footer.ftl"/>
</body>
</html>
<script type="text/javascript">
    Nvwa.colFormatter.formatStatus = function(v) {
        return v === 0 ? '<span class="text-danger">禁用</span>' : v === 1 ? '启用' : v;
    };

    Nvwa.colFormatter.optFormat = function(row) {

        var stxt = (row.status === 1) ? '禁用' : '启用';
        var atxt = (row.isAdmin === 1) ? '设置为玩家' : '设置为管理员';

        var btngroup = '<div class="btn-group" role="group">';
        btngroup += '<a class="btn-link switch_admin" href="javascript:;" title="管理员设置" data-id="'+row.id+'" data-name="' + row.username + '">' + atxt + '</a>';
        btngroup += '<em>|</em>';
        btngroup += '<a class="btn-link switch_status" href="javascript:;" title="启用/禁用" data-id="'+row.id+'" data-name="' + row.username + '">' + stxt + '</a>';
        btngroup += '<em>|</em>';
        btngroup += '<a class="btn-link charge" href="javascript:;" title="充值" data-id="'+row.id+'" data-name="' + row.username + '">充值</a>';
        btngroup += '<em>|</em>';
        btngroup += '<a class="btn-link extract" href="javascript:;" title="提取" data-id="'+row.id+'" data-name="' + row.username + '">提取</a>';
        btngroup += '<em>|</em>';
        btngroup += '<a class="btn-link order" href="' + ctx + '/admin/user_order_page?userId=' + row.id + '&realName=' + row.realName + '" title="投注记录" data-id="'+row.id+'" data-name="' + row.username + '">投注记录</a>';
        btngroup += '</div>';
        return btngroup;
    };

    function refresh() {
        $.get(ctx + '/user/list', {}, function(json) {
            var html = template('users', { rows : json.rows });
            $('#users-table tbody').empty().append(html);
        });
    }

    $(function() {
        refresh();

        $('#btn-add').click(function() {

            var body = $('<div>');
            body.append(template('add'));

            $.modal.open({
                title: '充值',
                body: body,
                shown: function () {
                    body.find('[name=value]').focus();
                },
                okHandler: function (e) {
                    var username = body.find('[name=username]').val();
                    var password = body.find('[name=password]').val();
                    var realName = body.find('[name=realName]').val();

                    $.post(ctx + '/add_user', {
                        username : username,
                        password : password,
                        realName : realName
                    }, function(json) {
                        if (json.success) {
                            $.modal.close();
                            Toast.success("用户新增成功");
                            refresh();
                        } else {
                            Toast.danger(json.msg);
                        }
                    });
                }
            });
        });

        $(document).on('click', 'a.switch_status', function() {
            var id = $(this).data('id');
            $.getJSON(ctx + '/user/switch_status', {
                userId : id
            }, function(json) {
                if (json.success) {
                    Toast.success("用户状态更新成功");
                    refresh();
                } else {
                    Toast.danger(json.msg);
                }
            });
        });

        $(document).on('click', 'a.switch_admin', function() {
            var id = $(this).data('id');
            $.getJSON(ctx + '/user/switch_admin', {
                userId : id
            }, function(json) {
                if (json.success) {
                    Toast.success("设置成功");
                    refresh();
                } else {
                    Toast.danger(json.msg);
                }
            });
        });

        $(document).on('click', 'a.charge', function() {
            var id = $(this).data('id');
            var name = $(this).data('name');
            var body = $('<div>');
            body.append(template('charge'));

            body.find('[name=username]').val(name);

            $.modal.open({
                title : '充值',
                body : body,
                shown : function() {
                    body.find('[name=value]').focus();
                },
                okHandler : function(e) {
                    var btn = $(e.target);

                    var value = body.find('[name=value]').val();

                    btn.attr('disabled', true);
                    $.post(ctx + '/user/charge', {
                        userId : id,
                        value : value * 100
                    }, function(json) {
                        if (json.success) {
                            $.modal.close();
                            Toast.success("积分充值成功");
                            refresh();
                        } else {
                            Toast.danger(json.msg);
                        }
                        btn.attr('disabled', false);
                    });
                }
            });
        });

        $(document).on('click', 'a.extract', function() {
            var id = $(this).data('id');
            var name = $(this).data('name');
            var body = $('<div>');
            body.append(template('extract'));

            body.find('[name=username]').val(name);

            $.modal.open({
                title : '提取',
                body : body,
                shown : function() {
                    body.find('[name=value]').focus();
                },
                okHandler : function(e) {
                    var btn = $(e.target);

                    var value = body.find('[name=value]').val();

                    btn.attr('disabled', true);
                    $.post(ctx + '/user/extract', {
                        userId : id,
                        value : value * 100
                    }, function(json) {
                        if (json.success) {
                            $.modal.close();
                            Toast.success("积分提取成功");
                            refresh();
                        } else {
                            Toast.danger(json.msg);
                        }
                        btn.attr('disabled', false);
                    });
                }
            });
        });
    });
</script>