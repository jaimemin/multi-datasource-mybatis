package com.tistory.jaimemin.multidatasourcemybatis.multitenancy.dto;

import lombok.Getter;
import lombok.Setter;

import javax.servlet.http.HttpServletRequest;

@Getter
public class DataSourceSaveDto {

    private String tenantId;

    @Setter
    private String driverClassName;

    private String url;

    private String username;

    private String password;

    public void addDefaultOption() {
        if (!"allowMultiQueries=true".equals(url)) {
            url += url.contains("?") ? "&allowMultiQueries=true" : "?allowMultiQueries=true";
        }
    }
}
