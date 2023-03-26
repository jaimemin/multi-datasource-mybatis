# multi-datasource-mybatis
---
서버를 기동하기 전 아래 내용이 선제적으로 적용되어 있어야 합니다.

MariaDB > multi_tenant_master 데이터베이스 생성 및 data_source_config 테이블 생성

```
-- DROP TABLE
DROP TABLE IF EXISTS data_source_config;

-- CREATE TABLE
CREATE TABLE data_source_config
(
    TENANT_ID             VARCHAR(100) NOT NULL COMMENT '테넌트 ID',
    DRIVER_CLASS_NAME     VARCHAR(100) NOT NULL COMMENT 'DB 드라이버 클래스명',
    URL                   VARCHAR(500) NOT NULL COMMENT 'DB URL',
    USERNAME              VARCHAR(100) NOT NULL COMMENT 'DB 사용자ID',
    PASSWORD              VARCHAR(100) NOT NULL COMMENT 'DB 비밀번호',
    IS_DEFAULT            VARCHAR(1)   NOT NULL DEFAULT 'N' COMMENT '기본 설정 여부',
    PRIMARY KEY (TENANT_ID)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT '멀티 테넌트 데이터베이스 정보';
```

MariaDB > multi_tenant_default 데이터베이스 생성 및 product 테이블 생성

```
DROP TABLE IF EXISTS product;

CREATE TABLE product (
    id BIGINT auto_increment NOT NULL,
    product_name varchar(100) NOT NULL,
    quantity INT NOT NULL,
    created_by varchar(100) NOT NULL,
    CONSTRAINT tbl_product_PK PRIMARY KEY (id)
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_general_ci;
```

MariaDB > multi_tenancy_master.data_source_config에 default 테이블 정보 추가

```
INSERT INTO multi_tenant_master.data_source_config
(TENANT_ID
, DRIVER_CLASS_NAME
, URL
, USERNAME
, PASSWORD
, IS_DEFAULT)
VALUES('default'
, 'org.mariadb.jdbc.Driver'
, 'jdbc:mariadb://localhost:3306/multi_tenant_default?allowMultiQueries=true'
, 'root'
, '1234'
, 'Y');
```

프로젝트 관련 상세 설명: https://jaimemin.tistory.com/2270 
