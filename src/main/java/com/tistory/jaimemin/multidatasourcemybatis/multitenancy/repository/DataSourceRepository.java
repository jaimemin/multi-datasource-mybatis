package com.tistory.jaimemin.multidatasourcemybatis.multitenancy.repository;

import com.tistory.jaimemin.multidatasourcemybatis.multitenancy.dto.DataSourceDto;
import com.tistory.jaimemin.multidatasourcemybatis.multitenancy.dto.DataSourceSaveDto;
import com.tistory.jaimemin.multidatasourcemybatis.multitenancy.mapper.DataSourceMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DataSourceRepository {

    private final DataSourceMapper mapper;

    public DataSourceRepository(@Qualifier("masterSqlSession")SqlSession sqlSession) {
        mapper = sqlSession.getMapper(DataSourceMapper.class);
    }

    public List<DataSourceDto> getAllDataSources() {
        return mapper.getAllDataSources();
    }

    public DataSourceDto findByTenantId(String tenantId) {
        return mapper.findByTenantId(tenantId);
    }

    public void save(DataSourceSaveDto newDataSource) {
        mapper.save(newDataSource);
    }

    public void delete(String tenantId) {
        mapper.delete(tenantId);
    }
}
