package com.yigit.web;

import com.yigit.web.utilities.RestUtil;
import org.springframework.beans.factory.annotation.Value;

/**
 * This test base class is used to define the test "configuration" as adjusting the connection of the endpoint
 */
public abstract class TestBase {

    @Value("${local.server.port}")
    int port;

    protected static String getUrl(int port) {
        return "http://localhost:"+port;
    }

    protected RestUtil createEndpointConnection() {
        return new RestUtil(TestBase.getUrl(port));
    }
}
