package com.tistory.jaimemin.multidatasourcemybatis.multitenancy.service;

import com.tistory.jaimemin.multidatasourcemybatis.multitenancy.config.DynamicRoutingDataSource;
import com.tistory.jaimemin.multidatasourcemybatis.multitenancy.dto.DataSourceDto;
import com.tistory.jaimemin.multidatasourcemybatis.multitenancy.dto.DataSourceSaveDto;
import com.tistory.jaimemin.multidatasourcemybatis.multitenancy.repository.DataSourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DataSourceService {

    private final DBService dbService;

    private final DataSourceRepository repository;

    private final DynamicRoutingDataSource routingDataSource;

    @Value("${spring.datasource.master.driver-class-name}")
    private String driveClassName;

    public List<DataSourceDto> getAllDataSources() {
        return repository.getAllDataSources();
    }

    @Transactional
    public void save(DataSourceSaveDto newDataSource) {
        DataSourceDto dataSource = repository.findByTenantId(newDataSource.getTenantId());

        if (!ObjectUtils.isEmpty(dataSource)) {
            throw new IllegalStateException("해당 테넌트는 이미 등록된 데이터베이스가 있습니다.");
        }

        saveDataSource(newDataSource);
    }

    @Transactional
    public void delete(String tenantId) {
        DataSourceDto dataSource = repository.findByTenantId(tenantId);

        if (ObjectUtils.isEmpty(dataSource)) {
            throw new IllegalArgumentException("등록되지 않은 데이터베이스입니다.");
        }

        if (dataSource.isDefault()) {
            return;
        }

        repository.delete(dataSource.getTenantId());
        routingDataSource.refresh(repository);
    }

    private void saveDataSource(DataSourceSaveDto newDataSource) {
        newDataSource.setDriverClassName(newDataSource.getDriverClassName());
        newDataSource.addDefaultOption();

        if (isNotValid(newDataSource)) {
            throw new IllegalArgumentException("DB URL 혹은 계정 정보를 확인해주세요.");
        }

        newDataSource.setDriverClassName(driveClassName);
        dbService.generate(newDataSource);
        repository.save(newDataSource);
        routingDataSource.refresh(repository);
    }

    private boolean isNotValid(DataSourceSaveDto newDataSource) {
        return !dbService.isValid(newDataSource.getUrl(), newDataSource.getUsername(), newDataSource.getPassword());
    }
}
