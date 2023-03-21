package com.tistory.jaimemin.multidatasourcemybatis.multitenancy.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.sql.DataSource;

@Getter
public class DataSourceDto {

    private String tenantId;

    private String driverClassName;

    private String url;

    private String username;

    @Setter
    private String password;

    private String isDefault;

    public DataSource buildDataSource() {
        return DataSourceBuilder.create()
                .driverClassName(driverClassName)
                .url(url)
                .username(username)
                .password(password)
                .build();
    }

    public boolean isDefault() {
        return "Y".equals(isDefault);
    }
}
