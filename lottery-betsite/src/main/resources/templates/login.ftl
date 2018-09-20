<!DOCTYPE HTML>
<html>
<head>
    <title>PK10</title>
    <#include "/modules/header.ftl"/>

    <style type="text/css">
        body {background-color: #5e5e5e;}
        .sign {background-color: rgba(0, 0, 0, 0.5); max-width: 300px;  margin: auto; color: white; }
        .sign .form-group {margin: 0px}
        .sign .form-control { height: 40px; border: 0px; padding: 6px 15px;}
        @media (max-width: 768px) {
            .sign{padding: 25px 25px;}
        }
        .footer {text-shadow: 0px 0px 5px #000000; font-weight: bold; color: #CCC;}
    </style>
</head>
<body>
<div class="container-fluid">
    <div class="form sign" id="sign">
        <div class="form-group">
            <input class="form-control" name="username" placeholder="用户名">
        </div>
        <div class="form-group">
            <input class="form-control" name="password" placeholder="登录密码" type="password">
        </div>
        <div class="form-group">
            <button class="btn btn-primary btn-lg btn-block" id="signIn">登录</button>
        </div>
    </div>
</div>
</body>
</html>
<script type="text/javascript">
    $(function() {
        Tag.medially('#sign');

        $(document).on('click', '#signIn', function() {
            var btn = $(this);
            btn.prop('disabled', true).text('登录中');
            var params = {
                username : $('input[name=username]').val(),
                password : $('input[name=password]').val()
            };

            $.getJSON(ctx + '/auth/login', params, function(res) {
                if (res.success) {
                    location.replace(ctx + res.single);
                } else {
                    Toast.danger(res.msg);
                }
                btn.prop('disabled', false).text('登录');
            });
        });

        $(document).on('keydown', 'input', function(e) {
            if (e.keyCode === 13) {
                $('#signIn').trigger('click');
            }
        });

        $('input:first').focus();
    });
</script>