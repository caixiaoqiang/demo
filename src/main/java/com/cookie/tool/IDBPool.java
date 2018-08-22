package com.cookie.tool;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author cxq
 * @date 2018/8/22 14:26
 */
public interface IDBPool {

    Connection getConnection() throws SQLException;

    void close() throws SQLException;

    void exceptionCallback(Exception var1, String var2, String var3, String var4);
}
