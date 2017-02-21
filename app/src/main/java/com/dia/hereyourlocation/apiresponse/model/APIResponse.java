package com.dia.hereyourlocation.apiresponse.model;

public class APIResponse {
    private String responseMessage;

    private App app;

    private String responseCode;

    private String droidVersion;

    private String errors;

    private String accessToken;

    private String vehicle;

    private User user;

    private String isValid;

    private String clientId;

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getDroidVersion() {
        return droidVersion;
    }

    public void setDroidVersion(String droidVersion) {
        this.droidVersion = droidVersion;
    }

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return "ApiResponse [responseMessage = " + responseMessage + ", app = " + app + ", responseCode = " + responseCode + ", droidVersion = " + droidVersion + ", errors = " + errors + ", accessToken = " + accessToken + ", vehicle = " + vehicle + ", user = " + user + ", isValid = " + isValid + ", clientId = " + clientId + "]";
    }
}