package com.tistory.jaimemin.multidatasourcemybatis.multitenancy.config;

import com.tistory.jaimemin.multidatasourcemybatis.multitenancy.dto.DataSourceDto;
import com.tistory.jaimemin.multidatasourcemybatis.multitenancy.repository.DataSourceRepository;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DynamicRoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return TenantContextHolder.getTenantId();
    }

    public void init(DataSourceRepository repository) {
        List<DataSourceDto> dataSources = repository.getAllDataSources();
        setDefaultDataSource(dataSources);
        setDataSources(dataSources);
    }

    private void setDefaultDataSource(List<DataSourceDto> dataSources) {
        DataSourceDto defaultDataSource = dataSources.stream()
                .filter(DataSourceDto::isDefault)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("기본 데이터베이스가 존재하지 않습니다."));

        setDefaultTargetDataSource(defaultDataSource.buildDataSource());
    }

    private void setDataSources(List<DataSourceDto> dataSources) {
        Map<Object, Object> dataSourceMap = dataSources.stream()
                .collect(Collectors.toMap(DataSourceDto::getTenantId, DataSourceDto::buildDataSource));

        setTargetDataSources(dataSourceMap);
    }

    public void refresh(DataSourceRepository repository) {
        init(repository);
        afterPropertiesSet();
    }
}
