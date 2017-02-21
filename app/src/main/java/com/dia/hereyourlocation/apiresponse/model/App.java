package com.dia.hereyourlocation.apiresponse.model;

public class App {
    private String appName;

    private String appId;

    private String code;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "ApiResponse [appName = " + appName + ", appId = " + appId + ", code = " + code + "]";
    }
}