$(document).bind('ORDER_LOADED', function(e, o) {
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
});