package com.yigit.web.utilities;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import io.restassured.specification.RequestSpecification;



public final class RestUtil {

    public RequestSpecification request;

    /**
     * This constructor will create baseURI, ContentType and
     * it will adjust the Request for us.
     * After creating Request we can adjust expectation for our response
     * other methods are helping this constructor
     * Setting of the URL adjust inside Configuration.properties file by
     * Configuration.get(""); method
     */
    public RestUtil() {
        this(ConfigurationReader.get("apiUrl"));
    }

    public RestUtil(String apiUrl) {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri(apiUrl);
        builder.setContentType(ContentType.JSON);
        builder.addHeader("Cache-Control", "no-cache, no-store, max-age=0, must-revalidate");
        RequestSpecification requestSpec = builder.build();
        request = RestAssured.given().spec(requestSpec);
    }

    /**
     * Add Header to requestSpecification with two Parameter
     *
     * @param headerName  key for header
     * @param headerValue value for header
     */
    public void addHeader(String headerName, Object headerValue) {
        request.header(headerName, headerValue);
    }

    /**
     * Get Request with Path Parameter
     *
     * @param url endpoint
     * @return Response
     */
    public ResponseOptions<Response> getReq(String url) {
        request.header("Accept", "application/json");
        return request.get(url);
    }

    /**
     * Get Request with Path Parameter for header application/pdf file
     *
     * @param url   endpoint
     * @param param according to documentation will be given
     * @return Response
     */
    public ResponseOptions<Response> getReqWithPathParamForPDF(String url, String param) {
        request.header("Accept", "application/pdf");
        request.pathParam("id", param);
        return request.get(url + "/{id}").prettyPeek();
    }

    /**
     * POST Request with body
     *
     * @param url  endpoint
     * @param body body must be created in or outside of the method
     * @return Response
     */
    public ResponseOptions<Response> postReqWithBody(String url, Object body) {
        request.body(body);
        return request.post(url);
    }

    /**
     * PUT Request with body
     *
     * @param url  endpoint
     * @param body body must be created in or outside of the method
     * @return Response
     */
    public ResponseOptions<Response> putReqWithBody(String url, Object body) {
        request.body(body);
        return request.put(url);
    }

    /**
     * PATCH Request with body
     *
     * @param url  endpoint
     * @param body body must be created in or outside of the method
     * @return Response
     */
    public ResponseOptions<Response> patchReqWithBody(String url, Object body) {
        request.body(body);
        return request.patch(url);
    }

    /**
     * DELETE Request
     *
     * @param url endpoint
     * @return Response
     */
    public ResponseOptions<Response> deleteReq(String url) {
        return request.delete(url);
    }
}

