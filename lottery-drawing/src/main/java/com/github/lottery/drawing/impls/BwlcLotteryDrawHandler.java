package com.github.lottery.drawing.impls;

import com.github.lottery.common.model.LotteryDrawing;
import com.github.lottery.common.model.LotteryDrawings;
import com.github.lottery.common.service.LotteryDrawingService;
import com.github.lottery.drawing.AbstractDarwingHandler;
import com.google.common.collect.Lists;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.bigmouth.nvwa.network.http.utils.HttpException;
import org.bigmouth.nvwa.network.http.utils.HttpRequest;
import org.bigmouth.nvwa.network.http.utils.HttpResponse;
import org.bigmouth.nvwa.network.http.utils.HttpUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class BwlcLotteryDrawHandler extends AbstractDarwingHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(BwlcLotteryDrawHandler.class);

    public BwlcLotteryDrawHandler(LotteryDrawingService lotteryDrawingService) {
        super(lotteryDrawingService);
    }

    @Override
    protected LotteryDrawing get() {
        String url = "http://www.bwlc.net/bulletin/prevtrax.html";

        List<Header> headers = Lists.newArrayList();
        headers.add(new BasicHeader("Accept", "*/*"));
        headers.add(new BasicHeader("Accept-Encoding", "gzip, deflate"));
        headers.add(new BasicHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8"));
        headers.add(new BasicHeader("Cache-Control", "no-cache"));
        headers.add(new BasicHeader("Connection", "keep-alive"));
        headers.add(new BasicHeader("Host", "www.bwlc.net"));
        headers.add(new BasicHeader("Pragma", "no-cache"));
        headers.add(new BasicHeader("Referer", "http://www.bwlc.net/bulletin/prevtrax.html?num="));
        headers.add(new BasicHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36"));

        HttpRequest request = new HttpRequest();
        request.setUrl(url);
        request.setHeaders(headers);
        request.setExpectStatusCode(200);
        request.setThrowUnexpectStatusCode(true);
        request.setTimeout(5000);
        try {
            HttpResponse response = HttpUtils.get(request);
            String entityString = response.getEntityString();

            Document doc = Jsoup.parse(entityString);
            Element tbody = doc.select("#lottery_tabs div table tbody").first();
            Element element = tbody.child(1);
            Elements tds = element.children();

            String issueNo = tds.get(0).text();
            String numbers = tds.get(1).text();
            String time = tds.get(2).text();

            return LotteryDrawings.of(issueNo, numbers, time);
        } catch (HttpException e) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("get: {}", e.getMessage());
            }
        }
        return null;
    }
}
