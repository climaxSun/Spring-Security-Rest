package com.swb.security.demo.wiremock;

import com.github.tomakehurst.wiremock.client.WireMock;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

/**
 * @author swb
 * 时间  2020-03-28 00:37
 * 文件  MockServer
 */
public class MockServer {

    public static void main(String[] args) {
        //端口
        configureFor(8062);
        removeAllMappings();
        stubFor(get(urlPathEqualTo("/order/1"))
                .willReturn(aResponse().withBody("{\"id\":1}").withStatus(200)));
    }
}
