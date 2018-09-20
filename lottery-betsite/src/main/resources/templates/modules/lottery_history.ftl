<div class="row">
    <div class="col-xs-12">
        <div class="form-inline">
            <div class="form-group">
                <input class="form-control" name="date" id="date" data-date-format="yyyy-mm-dd" placeholder="开奖日期" readonly>
            </div>
            <button type="button" class="btn btn-default" id="search-lottery-history">查询</button>
        </div>
    </div>
</div>
<div class="table-responsive">
    <table class="table" id="lottery_history_table">
        <thead>
        <tr>
            <th>期号</th>
            <th>开奖时间</th>
            <th>冠军</th>
            <th>亚军</th>
            <th>第三名</th>
            <th>第四名</th>
            <th>第五名</th>
            <th>第六名</th>
            <th>第七名</th>
            <th>第八名</th>
            <th>第九名</th>
            <th>第十名</th>
            <th></th>
        </tr>
        </thead>
        <tbody></tbody>
    </table>
</div>

<script id="lottery-history-template" type="text/html">
    {{each rows as row index}}
    <tr>
        <td>{{row.issueNo}}</td>
        <td>{{row.time | dateFormat : 'yyyy-MM-dd HH:mm:ss'}}</td>
        <td>{{row.one}}</td>
        <td>{{row.two}}</td>
        <td>{{row.three}}</td>
        <td>{{row.four}}</td>
        <td>{{row.five}}</td>
        <td>{{row.six}}</td>
        <td>{{row.seven}}</td>
        <td>{{row.eight}}</td>
        <td>{{row.nine}}</td>
        <td>{{row.ten}}</td>
        <td class="text-right">{{row | format:'optFormatForLotteryHistory', row}}</td>
    </tr>
    {{/each}}
</script>

<script>
    Nvwa.colFormatter.optFormatForLotteryHistory = function(row) {
        return '';
    };
</script>