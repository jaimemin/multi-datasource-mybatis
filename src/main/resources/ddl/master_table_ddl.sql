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