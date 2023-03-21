package com.tistory.jaimemin.multidatasourcemybatis.multitenancy.service;

import com.tistory.jaimemin.multidatasourcemybatis.multitenancy.dto.DataSourceSaveDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.sql.*;

@Service
public class DBService {

    @Value("classpath:ddl/table_ddl.sql")
    private Resource ddlResource;

    public boolean isValid(String url, String username, String password) {
        String hostUrl = getHostUrl(url);

        try (Connection connection = DriverManager.getConnection(hostUrl, username, password)) {
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void generate(DataSourceSaveDto dataSource) {
        createDatabase(dataSource);
        createObjects(dataSource);
    }

    private void createDatabase(DataSourceSaveDto dataSource) {
        String hostUrl = getHostUrl(dataSource.getUrl());
        String username = dataSource.getUsername();
        String password = dataSource.getPassword();

        try (Connection connection = DriverManager.getConnection(hostUrl, username, password)) {
            Statement statement = connection.createStatement();
            String dbName = getDBName(dataSource.getUrl());

            if (isDuplicateDatabase(statement, dbName)) {
                throw new SQLException("동일한 이름의 데이터베이스가 존재하므로 데이터베이스를 생성할 수 없습니다.");
            }

            String query = "CREATE DATABASE " + dbName;
            statement.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException("데이터베이스를 생성하는데 오류가 발생했습니다.(원인: " + e.getMessage() + ")");
        }
    }

    private boolean isDuplicateDatabase(Statement statement, String dbName) throws SQLException {
        String query = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME ='" + dbName + "'";
        ResultSet resultSet = statement.executeQuery(query);

        if (resultSet.next()) {
            return resultSet.getInt(1) > 0;
        }

        return false;
    }

    private void createObjects(DataSourceSaveDto dataSource) {
        String url = dataSource.getUrl();
        String username = dataSource.getUsername();
        String password = dataSource.getPassword();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement();
             InputStreamReader reader = new InputStreamReader(ddlResource.getInputStream(), StandardCharsets.UTF_8)) {
            String ddlQuery = FileCopyUtils.copyToString(reader);
            statement.execute(ddlQuery);
        } catch (Exception e) {
            throw new RuntimeException("테이블을 생성하는데 오류가 발생했습니다.(원인: " + e.getMessage() + ")");
        }
    }

    private String getDBName(String url) {
        String[] splits = url.split("://");
        String tempUrl = "protocol://" + splits[1];
        URI uri = URI.create(tempUrl);
        String path = uri.getPath();

        return path.replace("/", "");
    }

    private String getHostUrl(String url) {
        String[] splits = url.split("://");
        String tempUrl = "protocol://" + splits[1];
        URI uri = URI.create(tempUrl);

        return splits[0] + "://" + uri.getHost() + ":" + uri.getPort();
    }
}
