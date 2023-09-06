package com.linkly.pos.demoPos;

public class SimplifiedResponse {

    private String requestType;
    private String responseType;
    private Object response;
    private String uuid;

    public SimplifiedResponse() {
        super();
    }

    public SimplifiedResponse(String requestType, String responseType, Object response,
        String uuid) {
        super();
        this.requestType = requestType;
        this.responseType = responseType;
        this.response = response;
        this.uuid = uuid;
    }

    public String getResponseType() {
        return responseType;
    }

    public Object getResponse() {
        return response;
    }

    public String getUuid() {
        return uuid;
    }

    public String getRequestType() {
        return requestType;
    }
}
