package com.github.lottery.drawing.impls;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.lottery.common.model.LotteryDrawing;
import com.github.lottery.common.model.LotteryDrawings;
import com.github.lottery.common.service.LotteryDrawingService;
import com.github.lottery.drawing.AbstractDarwingHandler;
import com.google.common.collect.Lists;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.bigmouth.nvwa.network.http.utils.HttpRequest;
import org.bigmouth.nvwa.network.http.utils.HttpResponse;
import org.bigmouth.nvwa.network.http.utils.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class Ss1300DrawingHandler extends AbstractDarwingHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(Ss1300DrawingHandler.class);

    public Ss1300DrawingHandler(LotteryDrawingService lotteryDrawingService) {
        super(lotteryDrawingService);
    }

    @Override
    protected LotteryDrawing get() {
        String url = "http://www.ss1300.com/api/newest?code=pk10&t=" + System.currentTimeMillis();

        List<Header> headers = Lists.newArrayList();
        headers.add(new BasicHeader("Accept", "*/*"));
        headers.add(new BasicHeader("Accept-Encoding", "gzip, deflate"));
        headers.add(new BasicHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8"));
        headers.add(new BasicHeader("Cache-Control", "no-cache"));
        headers.add(new BasicHeader("Connection", "keep-alive"));
        headers.add(new BasicHeader("Host", "www.ss1300.com"));
        headers.add(new BasicHeader("Pragma", "no-cache"));
        headers.add(new BasicHeader("Referer", "https://www.ss1300.com/draw-pk10-today.html"));
        headers.add(new BasicHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36"));
        headers.add(new BasicHeader("X-Requested-With", "XMLHttpRequest"));

        HttpRequest request = new HttpRequest();
        request.setUrl(url);
        request.setHeaders(headers);
        request.setExpectStatusCode(200);
        request.setThrowUnexpectStatusCode(true);
        request.setTimeout(5000);
        try {
            HttpResponse response = HttpUtils.get(request);
            String entityString = response.getEntityString();

            JSONObject json = JSONObject.parseObject(entityString);

            JSONObject data = json.getJSONObject("data");
            JSONObject newest = data.getJSONObject("newest");

            String issueNo = newest.getString("issue");
            String numbers = newest.getString("code");
            String time = newest.getString("time");

            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("[{}] {} - {}", issueNo, numbers, time);
            }

            return LotteryDrawings.of(issueNo, numbers, time);
        } catch (Exception e) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("get: ", e);
            }
        }
        return null;
    }
}
