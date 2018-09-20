(function($) {

    function refresh() {
        $.get(ctx + '/score_history', {}, function(json) {
            if (json.success) {
                if (json.results > 0) {
                    var rows = json.rows;
                    var html = template('score_history_template', { rows : rows });
                    $('#score_history_table tbody').empty().html(html);
                } else {
                    $('#score_history_table tbody').empty().html(template('score_history_empty_template'));
                }
            } else {
                Toast.danger(json.msg);
            }
        });
    }

    $(function() {
        $('a[data-toggle="tab"]').on('show.bs.tab', function (e) {
            var a = $(e.target);
            if (a.text() === '积分记录') {
                refresh();
            }
        })
    });

})(jQuery);