package com.cookie.dao;


import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cxq
 * @date 2018/8/22 15:39
 */
public class DBHelper {

    private static final Logger logger = Logger.getLogger(DBHelper.class);

    private DBHelper() {
    }

    public static int excute(Connection conn , String sql ) throws SQLException {
        return  excute(conn,sql,null);
    }

    public static int queryForInt(Connection conn , String sql ) throws SQLException {
        return  queryForInt(conn,sql,null);
    }

    public static String queryForString(Connection conn , String sql ) throws SQLException {
        return  queryForString(conn,sql,null);
    }

    public static Long queryForLong(Connection conn , String sql ) throws SQLException {
        return  queryForLong(conn,sql,null);
    }
    public static Map<String,String> queryForMap(Connection conn , String sql ) throws SQLException {
        List<Map<String,String>> datas =   query(conn,sql,null);
        return  datas.size() > 0 ? datas.get(0) : null ;
    }

    public static Map<String,String> queryForMap(Connection conn , String sql , String [] params ) throws SQLException {
        List<Map<String,String>> datas =   query(conn,sql,params);
        return  datas.size() > 0 ? datas.get(0) : null ;
    }

    public static List<Map<String,String>> query(Connection conn , String sql  ) throws SQLException {
        return  query(conn,sql,null);
    }

    public static int excute(Connection conn , String sql , String [] params ) throws SQLException {
        logger.info("sql = "+sql);
        PreparedStatement ps = conn.prepareStatement(sql);
        if (params!= null && params.length > 0 ){
            for (int i = 0 ; i < params.length ; i++ ){
                ps.setString(i+1,params[i]);
            }
        }
        return  ps.executeUpdate();

    }

    public static int queryForInt(Connection conn , String sql , String [] params ) throws SQLException {
        logger.info("sql = "+sql);
        PreparedStatement ps  = null ;
        ResultSet set = null ;
        try {

            ps = conn.prepareStatement(sql);
            if (params!= null && params.length > 0 ){
                for (int i = 0 ; i < params.length ; i++ ){
                    ps.setString(i+1,params[i]);
                }
            }
            set = ps.executeQuery();
            while (set.next()){
                return set.getInt(1);
            }
        }finally {
            close(ps,set);
        }
        return  -1 ;

    }

    public static String queryForString(Connection conn , String sql , String [] params ) throws SQLException {
        logger.info("sql = "+sql);
        PreparedStatement ps  = null ;
        ResultSet set = null ;
        try {

            ps = conn.prepareStatement(sql);
            if (params!= null && params.length > 0 ){
                for (int i = 0 ; i < params.length ; i++ ){
                    ps.setString(i+1,params[i]);
                }
            }
            set = ps.executeQuery();
            while (set.next()){
                return set.getString(1);
            }
        }finally {
            close(ps,set);
        }
        return  null ;

    }

    public static Long queryForLong(Connection conn , String sql , String [] params ) throws SQLException {
        logger.info("sql = "+sql);
        PreparedStatement ps  = null ;
        ResultSet set = null ;
        try {

            ps = conn.prepareStatement(sql);
            if (params!= null && params.length > 0 ){
                for (int i = 0 ; i < params.length ; i++ ){
                    ps.setString(i+1,params[i]);
                }
            }
            set = ps.executeQuery();
            while (set.next()){
                return set.getLong(1);
            }
        }finally {
            close(ps,set);
        }
        return  null ;

    }


    public static List<Map<String,String>> query(Connection conn , String sql , String [] params ) throws SQLException {
        logger.info("sql = "+sql);
        List<Map<String,String>> datas = new ArrayList<>();
        PreparedStatement ps  = null ;
        ResultSet set = null ;
        try {

            ps = conn.prepareStatement(sql);
            if (params!= null && params.length > 0 ){
                for (int i = 0 ; i < params.length ; i++ ){
                    ps.setString(i+1,params[i]);
                }
            }
            set = ps.executeQuery();
            ResultSetMetaData metaData = set.getMetaData();
            int cloumnCount = metaData.getColumnCount();
            while (set.next()){
                Map<String,String> map = new HashMap<>();
                for (int i = 1 ; i <= cloumnCount+1 ; i++ ){
                    if (StringUtils.isEmpty(set.getString(i))){
                        map.put(metaData.getColumnLabel(i),set.getString(i));
                    }
                }
                datas.add(map);
            }
        }finally {
            close(ps,set);
        }
        return  datas ;
    }

    public static ResultSet getResultSet(Connection conn , String sql , String [] params ) throws SQLException {
        logger.info("sql = "+sql);
        PreparedStatement ps  = null ;
        ResultSet set = null ;
        try {

            ps = conn.prepareStatement(sql);
            if (params!= null && params.length > 0 ){
                for (int i = 0 ; i < params.length ; i++ ){
                    ps.setString(i+1,params[i]);
                }
            }
            set =   ps.executeQuery();
        }finally {
            ps.close();
        }

        return set ;
    }


    public static void close(PreparedStatement ps , ResultSet set ) throws SQLException {
        if (ps != null ){
            ps.close();
        }
        if (set != null ){
            set.close();
        }
    }



}
