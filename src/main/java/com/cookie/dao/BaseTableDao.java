package com.cookie.dao;

import com.cookie.sql.SqlParam;
import com.cookie.tool.IDBPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author cxq
 * @date 2018/8/24 9:49
 */
public class BaseTableDao extends BaseDao {

    private static final Logger logger = LoggerFactory.getLogger(BaseTableDao.class);

    protected  String [] primartKeys ;

    public BaseTableDao (IDBPool pool ){
        super(pool);
        this.primartKeys = new String[]{"id"};
    }
    public BaseTableDao (IDBPool pool  , String [] primartKeys ){
        super(pool);
        this.primartKeys = primartKeys ;
    }

    public String[] getPrimartKeys() {
        return primartKeys;
    }

    public void setPrimartKeys(String[] primartKeys) {
        this.primartKeys = primartKeys;
    }


    public int insertTable(String table , Map<String,String> data ){
        Connection conn = null ;
        SqlParam sqlParam = null ;
        if (data == null || data.isEmpty()){
            return  0 ;
        }
        try {
            conn = pool.getConnection();
            sqlParam = SqlParam.getInsertSqlParm(table, data);
            return  DBHelper.excute(conn,sqlParam.getSql(),sqlParam.getParams());
        } catch (SQLException e) {
            logger.error(" insertTable failed"+e.getLocalizedMessage());
            if (sqlParam == null ){
                this.printCallStack(e,"insertTable",null);
            }else {
                this.printCallStack(e,sqlParam.getSql(),sqlParam.getParams());
            }
        }finally {
            if (conn != null ){
                try {
                    conn.close();
                    conn = null ;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return  -1 ;
    }

    public int insertTable(Connection conn , String table , Map<String,String> data) throws SQLException {
        if (data == null || data.isEmpty()){
            return  0 ;
        }
        SqlParam sqlParam = SqlParam.getInsertSqlParm(table,data);
        return  DBHelper.excute(conn,sqlParam.getSql(),sqlParam.getParams());
    }


    public int insertTable(String table , List<Map<String,String>> datas ){
        Connection conn = null ;
        SqlParam sqlParam = null ;
        if (datas == null || datas.size() == 0 ){
            return  0 ;
        }
        try {
            conn = pool.getConnection();
            conn.setAutoCommit(false);
            int result = 0 ;
            for (Map<String,String> data : datas ){
                sqlParam = SqlParam.getInsertSqlParm(table, data);
                result +=  DBHelper.excute(conn,sqlParam.getSql(),sqlParam.getParams());
            }
            conn.commit();
            return  result ;
        } catch (SQLException e) {
            logger.error(" insertTable failed"+e.getLocalizedMessage());
            if (sqlParam == null ){
                this.printCallStack(e,"insertTable",null);
            }else {
                this.printCallStack(e,sqlParam.getSql(),sqlParam.getParams());
            }
        }finally {
            if (conn != null ){
                try {
                    conn.close();
                    conn = null ;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return  -1 ;
    }

    public int insertTable(Connection conn , String table , List<Map<String,String>> datas ) throws SQLException {
        if (datas == null || datas.size() == 0 ){
            return  0 ;
        }
        int result = 0 ;
        for (Map<String,String> data : datas ){
            result += this.insertTable(conn,table,data);
        }
        return  result ;

    }

    public int insertTableAndReturn(String table , Map<String,String> data ){
        Connection conn = null ;
        SqlParam sqlParam = null ;
        if (data == null || data.isEmpty()){
            return  0 ;
        }
        try {
            conn = pool.getConnection();
            conn.setAutoCommit(false);
            sqlParam = SqlParam.getInsertSqlParm(table, data);
            DBHelper.excute(conn,sqlParam.getSql(),sqlParam.getParams());

            Map<String,String> map = DBHelper.queryForMap(conn,"SELECT LAST_INSERT_ID() as id;");
            conn.commit();
            return Integer.parseInt(map.get("id"));
        } catch (SQLException e) {
            logger.error(" insertTableAndReturn failed"+e.getLocalizedMessage());
            if (sqlParam == null ){
                this.printCallStack(e,"insertTableAndReturn",null);
            }else {
                this.printCallStack(e,sqlParam.getSql(),sqlParam.getParams());
            }
        }finally {
            if (conn != null ){
                try {
                    conn.close();
                    conn = null ;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return  -1 ;
    }

    public int insertTableAndReturn(Connection conn , String table , Map<String,String> data) throws SQLException {
        if (data == null || data.isEmpty()){
            return  0 ;
        }
        SqlParam sqlParam = SqlParam.getInsertSqlParm(table,data);
        DBHelper.excute(conn,sqlParam.getSql(),sqlParam.getParams());

        Map<String,String> map = DBHelper.queryForMap(conn,"SELECT LAST_INSERT_ID() as id;");
        return Integer.parseInt(map.get("id"));


    }

    public int updateTable(String table , Map<String,String> data ) {
        if (data == null || data.isEmpty()){
            return  0 ;
        }

        String whereCluse = "";
        String [] whereParams = new String[primartKeys.length];
        for (int i = 0 ; i < primartKeys.length ; i++ ){
            whereCluse += primartKeys[i];
            if (i < primartKeys.length -1 ){
                whereCluse += " and ";
            }
            String val = data.get(primartKeys[i]);
            whereParams[i] = val ;

            data.remove(val);
        }
        return  updateTable(table,whereCluse,whereParams,data);

    }

    public int updateTable( String table , String whereClause , String [] whereParams ,  Map<String,String> data)  {
        if (data == null || data.isEmpty()){
            return  0 ;
        }

        Connection conn = null ;

        try {
            conn = pool.getConnection();
            return  updateTable(conn,table,whereClause,whereParams,data);
        } catch (SQLException e) {
            logger.error(" updateTable failed : "+e.getLocalizedMessage());
            this.printCallStack(e,"updateTable",null);
        }finally {
            if (conn != null ){
                try {
                    conn.close();
                    conn = null ;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return  -1 ;

    }

    public int updateTable(String table , List<Map<String,String>> datas ){
        Connection conn = null ;
        try {
            conn = pool.getConnection();
            int index = 0 ;
            for (Map<String,String> data : datas ){
                index += updateTable(conn,table,data);
            }
            return  index ;
        } catch (SQLException e) {
            logger.error(" updateTable failed : "+e.getLocalizedMessage());
            printCallStack(e,"updateTable",null);
        }finally {
            if (conn != null ){
                try {
                    conn.close();
                    conn = null ;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return  0 ;

    }
    
    
    public int updateTable(Connection conn , String table , Map<String,String> data ) throws SQLException {
        if (data == null || data.isEmpty()){
            return  0 ;
        }

        String whereCluse = "";
        String [] whereParams = new String[primartKeys.length];
        for (int i = 0 ; i < primartKeys.length ; i++ ){
            whereCluse += primartKeys[i];
            if (i < primartKeys.length -1 ){
                whereCluse += " and ";
            }
            String val = data.get(primartKeys[i]);
            whereParams[i] = val ;
            
            data.remove(val);
        }
        
        return  updateTable(conn,table,whereCluse,whereParams,data);
    }

    public int updateTable(Connection conn , String table , String whereClause , String [] whereParams ,  Map<String,String> data) throws SQLException {
        if (data == null || data.isEmpty()){
            return  0 ;
        }
        if (StringUtils.isEmpty(whereClause)){
            whereClause = " where " + whereClause ;
        }

        String [] params = new String[whereParams.length + data.keySet().size()] ;
        int index = 0 ;
        String setStr = " set " ;
        Iterator<Map.Entry<String, String>> it = data.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<String,String> map = it.next();
            setStr += map.getKey() + " = ?,";
            params[index++] = map.getValue();
        }
        setStr.substring(0,data.keySet().size()-1);

        for (String str : whereParams){
            params[index++] = str ;
        }

        String sql = "update "+table + setStr + whereClause ;
        return  DBHelper.excute(conn,sql,params);

    }

    public int updateTable(Connection conn , String table , List<Map<String,String>> datas ) throws SQLException {
        int index = 0 ;
        for (Map<String,String> data : datas ){
            index += updateTable(conn,table,data);
        }
        return  index ;


    }



}
