package com.dia.hereyourlocation.apiresponse.model;

public class User {
    private String filterParam;

    private String lastName;

    private String phone;

    private String sortBy;

    private String email;

    private String uniqueParam;

    private String sockOnline;

    private String userId;

    private String locality;

    private String login;

    private String firstName;

    private String password;

    private String online;

    private String userType;

    public String getFilterParam() {
        return filterParam;
    }

    public void setFilterParam(String filterParam) {
        this.filterParam = filterParam;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUniqueParam() {
        return uniqueParam;
    }

    public void setUniqueParam(String uniqueParam) {
        this.uniqueParam = uniqueParam;
    }

    public String getSockOnline() {
        return sockOnline;
    }

    public void setSockOnline(String sockOnline) {
        this.sockOnline = sockOnline;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "ApiResponse [filterParam = " + filterParam + ", lastName = " + lastName + ", phone = " + phone + ", sortBy = " + sortBy + ", email = " + email + ", uniqueParam = " + uniqueParam + ", sockOnline = " + sockOnline + ", userId = " + userId + ", locality = " + locality + ", login = " + login + ", firstName = " + firstName + ", password = " + password + ", online = " + online + ", userType = " + userType + "]";
    }
}