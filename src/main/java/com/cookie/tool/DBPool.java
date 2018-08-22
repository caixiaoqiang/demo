package com.cookie.tool;

import com.cookie.CommonConfigBase;
import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author cxq
 * @date 2018/8/22 14:27
 */
public class DBPool extends  LoggerDBPool implements  IDBPool {
    private final static Logger logger = LoggerFactory.getLogger(DBPool.class);

    private String connectURI;
    private String username;
    private String password;
    private String driver;
    private int maxActive = 20;
    private int maxIdle = 2;
    private int maxWait = 10000;
    private DataSource dataSource = null;

    public DBPool() {
    }

    public DataSource getDataSource() {
        return this.dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void init(String driver, String connectURI, String username, String password) {
        logger.info("driver = "+driver);
        logger.info("connectURI = "+connectURI);
        logger.info("username = "+username);
        logger.info("password = "+password);
        logger.info("dataSource = "+dataSource);
        this.driver = driver;
        this.connectURI = connectURI;
        this.username = username;
        this.password = password;
        this.dataSource = this.setupDataSource();
    }

    public void init(String driver, String connectURI, String username, String password, int maxActive, int maxIdle) {
        this.maxActive = maxActive;
        this.maxIdle = maxIdle;
        this.init(driver, connectURI, username, password);
    }

    public void init(String driver, String connectURI, String username, String password, int maxActive, int maxIdle, int maxWait) {
        this.maxWait = maxWait;
        this.init(driver, connectURI, username, password, maxActive, maxIdle);
    }

    private DataSource setupDataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(this.driver);
        ds.setUsername(this.username);
        ds.setPassword(this.password);
        ds.setUrl(this.connectURI);
        ds.setValidationQuery("select 1 from dual");
        ds.setRemoveAbandoned(true);
        ds.setTestWhileIdle(true);
        ds.setTestOnBorrow(true);
        ds.setTestOnReturn(false);
        ds.setMaxActive(this.maxActive);
        ds.setMaxIdle(this.maxIdle);
        ds.setMaxWait((long)this.maxWait);
        return ds;
    }

    public Connection getConnection() throws SQLException {
        return this.dataSource.getConnection();
    }

    public void close() throws SQLException {
        DriverManager.deregisterDriver(DriverManager.getDriver(this.connectURI));
    }

    public String getConnectionInfo() {
        BasicDataSource ds = (BasicDataSource)this.dataSource;
        return "[" + ds.getNumActive() + "," + ds.getNumIdle() + "]";
    }
}
