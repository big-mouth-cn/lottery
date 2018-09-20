(function($) {
    
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
    });

    function load(date) {
        $.get(ctx + '/lottery_history', {
            date : date
        }, function(json) {
            if (json.success) {
                $('#lottery_history_table tbody').empty();
                if (json.results > 0) {
                    var crt = new Date();
                    var crttime = crt.getTime();
                    for (var i = 0; i < json.rows.length; i++) {
                        var row = json.rows[i];
                        if (row.time >= crttime) {
                            json.rows.splice(i, i+1);
                        }
                    }
                    var body = template('lottery-history-template', { rows : json.rows });
                    $('#lottery_history_table tbody').append(body);
                }
            } else {
                Toast.danger(json.msg);
            }
        });
    }

})(jQuery);