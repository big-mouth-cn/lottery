var Utils = {};

var m_client = false;
if (/(iPhone|iPad|iPod|iOS|Android)/i.test(navigator.userAgent)) { //移动端
    m_client = true;
}

var Tag = {
    medially: function(o) {
        var height = $(o).outerHeight();
        var wh = $(window).height();
        $(o).css('margin-top', (wh - height)/2);
    }
};

var Toast = {
    info: function(t, delay) {
        Toast.open(t, 'info', delay);
    },
    success: function(t, delay) {
        Toast.open(t, 'success', delay);
    },
    danger: function(t, delay) {
        Toast.open(t, 'danger', delay);
    },
    open: function(t, type, delay) {
        $.bootstrapGrowl(t, {
            type: type,
            align: 'center',
            width: 'auto',
            delay: delay || 3000,
            offset: {
                from: "top",
                amount: ($(window).height()-50)/2
            },
            allow_dismiss: false
        });
    }
};

var Nvwa = {
    colFormatter: {},
    ReExp: {
        time: /^\d{4}-\d{1,2}-\d{1,2} \d{1,2}:\d{1,2}:\d{1,2}$/,         // yyyy-MM-dd HH:mm:ss
        number: /^\d+$/              // 正整数
    }
};

Nvwa.colFormatter.toFixed = function(v) {
    return (parseFloat(v / 100)).toFixed(2);
};
Nvwa.colFormatter.scoreType = function(v) {
    if (v === 1) {
        return '充值';
    } else if (v === 2) {
        return '<span class="text-success">支出</span>';
    } else if (v === 3) {
        return '<span class="text-danger">收入</span>';
    } else if (v === 4) {
        return '提取';
    } else {
        return v;
    }
};
Nvwa.colFormatter.status = function(v) {
    if (v === 0) {
        return '待处理';
    } else if (v === 1) {
        return '已兑奖';
    } else {
        return v;
    }
};

template.defaults.escape = false;

template.helper('dateFormat', function (date, format) {
    if (!date) {
        return "";
    }
    return DateUtil.format(date, format);
});

template.helper('format', function (v, colFormatter, params) {
    var formatterFn = Nvwa.colFormatter[colFormatter];
    return formatterFn ? formatterFn(v, params) : obj;
});

function diffTime(startDate,endDate) {
    var diff=endDate.getTime() - startDate.getTime();//时间差的毫秒数

    //计算出相差天数
    var days=Math.floor(diff/(24*3600*1000));

    //计算出小时数
    var leave1=diff%(24*3600*1000);    //计算天数后剩余的毫秒数
    var hours=Math.floor(leave1/(3600*1000));
    //计算相差分钟数
    var leave2=leave1%(3600*1000);        //计算小时数后剩余的毫秒数
    var minutes=Math.floor(leave2/(60*1000));

    //计算相差秒数
    var leave3=leave2%(60*1000);      //计算分钟数后剩余的毫秒数
    var seconds=Math.round(leave3/1000);

    var returnStr = seconds + " 秒";
    if(minutes>0) {
        returnStr = minutes + " 分 " + returnStr;
    }
    if(hours>0) {
        returnStr = hours + " 小时 " + returnStr;
    }
    if(days>0) {
        returnStr = days + " 天 " + returnStr;
    }
    return returnStr;
}