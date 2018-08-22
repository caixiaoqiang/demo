package com.cookie.dao;

import com.cookie.tool.IDBPool;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author cxq
 * @date 2018/8/22 15:39
 */
public class BaseDao extends PrintStackDao {
    private static final Logger logger = Logger.getLogger(BaseDao.class);

    public BaseDao() {
    }

    public BaseDao(IDBPool pool) {
        this.pool = pool;
    }

    public void setDBPool(IDBPool pool) {
        this.pool = pool;
    }

    public IDBPool getDBPool() {
        return this.pool;
    }

    public int execute(String sql) {
        return this.execute(sql, (String[])null);
    }

    public int execute(String sql, String[] params) {
        Connection conn = null;

        try {
            conn = this.pool.getConnection();
            int var4 = DBHelper.execute(conn, sql, params);
            return var4;
        } catch (SQLException var14) {
            logger.error("execute错误:" + var14.getLocalizedMessage());
            this.printCallStack(var14, sql, params);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                    conn = null;
                }
            } catch (SQLException var13) {
                ;
            }

        }

        return -1;
    }
}
