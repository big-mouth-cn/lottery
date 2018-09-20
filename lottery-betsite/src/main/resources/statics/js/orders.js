(function($) {
    Nvwa.colFormatter.optFormat = function(row) {
        var btngroup = '<div class="btn-group" role="group">';
        btngroup += '<a class="btn-link details" href="javascript:;" title="明细" data-id="'+row.id+'" data-issueno="'+row.issueNo+'">明细</a>';
        btngroup += '</div>';
        return btngroup;
    };

    function refresh() {
        $.get(ctx + '/orders', {}, function(json) {
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
        $('a[data-toggle="tab"]').on('show.bs.tab', function (e) {
            var a = $(e.target);
            if (a.text() === '投注记录') {
                refresh();
            }
        });
        $(document).on('click', 'a.details', function() {
            var issueno = $(this).data('issueno');
            var id = $(this).data('id');
            $.get(ctx + '/order', {
                issueNo : issueno,
                id : id
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

})(jQuery);