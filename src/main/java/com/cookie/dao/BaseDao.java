package com.cookie.dao;

import com.cookie.tool.IDBPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author cxq
 * @date 2018/8/22 15:39
 */
public class BaseDao extends PrintStackDao {
    private static final Logger logger = LoggerFactory.getLogger(BaseDao.class);

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

    /**
     * 执行一条sql
     * @param sql
     * @return  返回影响的行数
     */
    public int execute(String sql) {
        return this.execute(sql, (String[])null);
    }

    /**
     * 执行一条sql
     * @param sql
     * @param params
     * @return  返回影响的行数
     */
    public int execute(String sql, String[] params) {
        Connection conn = null;

        try {
            conn = this.pool.getConnection();
            int var4 = DBHelper.excute(conn, sql, params);
            return var4;
        } catch (SQLException var14) {
            logger.error("execute错误:" + var14.getLocalizedMessage());
            this.printCallStack(var14, sql, params);
        } finally {
            close(conn);
        }

        return -1;
    }

    /**
     * 事务执行一条sql
     * @param conn
     * @param sql
     * @param params
     * @return  返回影响行数
     * @throws SQLException
     */
    public int execute(Connection conn , String sql, String[] params) throws SQLException {
        int var4 = DBHelper.excute(conn, sql, params);
        return var4;

    }

    /**
     * 执行一条sql
     * @param sql
     * @return  如果没有，返回-1
     */
    public int rawQueryForInt(String sql ){
        return  rawQueryForInt(sql,null);
    }

    /**
     * 执行一条sql
     * @param sql
     * @param params
     * @return 如果没有，返回-1
     */
    public int rawQueryForInt(String sql , String [] params ){
        Connection conn = null ;
        try {
            conn = this.pool.getConnection();
            return DBHelper.queryForInt(conn,sql,params);
        } catch (SQLException e) {
            logger.error("queryForInt 错误 : "+e.getLocalizedMessage());
            this.printCallStack(e, sql, params);
        }finally {
            close(conn);
        }
        return  -1 ;
    }

    /**
     * 执行一条sql
     * @param sql
     * @return 如果没有，返回-1L
     */
    public long rawQueryForLong(String sql ){
        return  rawQueryForLong(sql,null);
    }

    /**
     * 执行一条sql
     * @param sql
     * @param params
     * @return  如果没有，返回-1L
     */
    public long rawQueryForLong(String sql , String [] params ){
        Connection conn = null ;
        try {
            conn = this.pool.getConnection();
            return DBHelper.queryForLong(conn,sql,params);
        } catch (SQLException e) {
            logger.error("queryForLong 错误 : "+e.getLocalizedMessage());
            this.printCallStack(e, sql, params);
        }finally {
            close(conn);
        }
        return  -1L ;
    }

    /**
     * 执行一条sql
     * @param sql
     * @return  如果没有，返回null
     */
    public String rawQueryForString(String sql ){
        return  rawQueryForString(sql,null);
    }

    /**
     * 执行一条sql
     * @param sql
     * @param params
     * @return  如果没有，返回null
     */
    public String rawQueryForString(String sql , String [] params ){
        Connection conn = null ;
        try {
            conn = this.pool.getConnection();
            return DBHelper.queryForString(conn,sql,params);
        } catch (SQLException e) {
            logger.error("queryForString 错误 : "+e.getLocalizedMessage());
            this.printCallStack(e, sql, params);
        }finally {
            close(conn);
        }
        return  null ;
    }

    /**
     * 执行一条sql
     * @param sql
     * @return  如果没有，返回null
     */
    public Map<String,String> rawQueryForMap(String sql ){
        return rawQueryForMap(sql,null);
    }

    /**
     * 执行一条sql
     * @param sql
     * @param params
     * @return  如果没有，返回null
     */
    public Map<String,String> rawQueryForMap(String sql , String [] params ){
        Connection conn = null ;
        try {
            conn = this.pool.getConnection();
            return DBHelper.queryForMap(conn,sql,params);
        } catch (SQLException e) {
            logger.error("rawQueryForMap 错误 : "+e.getLocalizedMessage());
            this.printCallStack(e, sql, params);
        }finally {
            close(conn);
        }
        return  null ;
    }

    /**
     * 执行一条sql
     * @param sql
     * @return  如果没有，返回null
     */
    public List<Map<String,String>> rawQuery(String sql ){
        return rawQuery(sql,null);
    }

    /**
     * 执行一条sql
     * @param sql
     * @param params
     * @return  如果没有，返回null
     */
    public List<Map<String,String>> rawQuery(String sql , String [] params ){
        Connection conn = null ;
        try {
            conn = this.pool.getConnection();
            return DBHelper.query(conn,sql,params);
        } catch (SQLException e) {
            logger.error("rawQuery 错误 : "+e.getLocalizedMessage());
            this.printCallStack(e, sql, params);
        }finally {
            close(conn);
        }
        return  null ;
    }


    public void close(Connection conn){
        if (conn != null) {
            try {
                conn.close();
                conn = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
