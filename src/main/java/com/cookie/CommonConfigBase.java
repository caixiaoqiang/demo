package com.cookie;

import com.cookie.tool.DBPool;
import com.cookie.tool.IDBPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import com.alibaba.druid.pool.DruidDataSource;

import javax.sql.DataSource;

/**
 * @author cxq
 * @date 2018/8/22 14:31
 */
public class CommonConfigBase {

    private final static Logger logger = LoggerFactory.getLogger(CommonConfigBase.class);

    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        logger.info("-------------------- primaryDataSource init ---------------------");
        return DataSourceBuilder.create().type(DruidDataSource.class).build();
    }

    @Bean
    @Autowired
    public IDBPool dbPool(DataSource dataSource) {
        logger.info("-------------------- dataSource init ---------------------");
        DBPool pool = new DBPool();
        pool.setDataSource(dataSource);

        return pool;
    }


}
