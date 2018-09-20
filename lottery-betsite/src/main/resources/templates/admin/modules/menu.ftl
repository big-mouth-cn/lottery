<nav class="navbar navbar-default">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">
            </a>
        </div>

        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li><a href="${ctx.contextPath}/admin/user">用户管理</a></li>
                <li><a href="${ctx.contextPath}/admin/lottery_history">开奖记录</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <p class="navbar-text"><a href="javascript:;" id="logout">退出</a></p>
                </li>
            </ul>
        </div>
    </div>
</nav>

<script type="text/javascript">
    $(function () {
        $('#logout').click(function() {
            $.get(ctx + '/auth/logout', {}, function() {
                location.reload();
            });
        });
    });
</script>