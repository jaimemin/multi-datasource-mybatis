package com.tistory.jaimemin.multidatasourcemybatis.multitenancy.mapper;

import com.tistory.jaimemin.multidatasourcemybatis.multitenancy.dto.DataSourceDto;
import com.tistory.jaimemin.multidatasourcemybatis.multitenancy.dto.DataSourceSaveDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DataSourceMapper {

    List<DataSourceDto> getAllDataSources();

    DataSourceDto findByTenantId(String tenantId);

    void save(DataSourceSaveDto newDataSource);

    void delete(String tenantId);
}
