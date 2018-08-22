package com.cookie.dao;

import org.apache.log4j.Logger;

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

    public static int execute(Connection conn, String sql) throws SQLException {
        return execute(conn, sql, (String[])null);
    }

    public static int execute(Connection conn, String sql, String[] params) throws SQLException {
        PreparedStatement stat = null;
        logger.debug("execute,sql=" + sql);
        stat = conn.prepareStatement(sql);
        if (params != null) {
            int index = 1;
            String[] var5 = params;
            int var6 = params.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                String param = var5[var7];
                stat.setString(index++, param);
            }
        }

        return stat.executeUpdate();
    }

    public static int queryForInt(Connection conn, String sql) throws SQLException {
        return queryForInt(conn, sql, (String[])null);
    }

    public static int queryForInt(Connection conn, String sql, String[] params) throws SQLException {
        ResultSet rs = null;
        PreparedStatement stat = null;

        try {
            logger.debug("query,sql=" + sql);
            stat = conn.prepareStatement(sql);
            int index;
            if (params != null) {
                index = 1;
                String[] var6 = params;
                int var7 = params.length;

                for(int var8 = 0; var8 < var7; ++var8) {
                    String param = var6[var8];
                    stat.setString(index++, param);
                }
            }

            rs = stat.executeQuery();
            if (rs.next()) {
                index = rs.getInt(1);
                return index;
            }
        } finally {
            if (stat != null) {
                stat.close();
            }

            if (rs != null) {
                rs.close();
            }

        }

        return -1;
    }

    public static String queryForString(Connection conn, String sql, String[] params) throws SQLException {
        ResultSet rs = null;
        PreparedStatement stat = null;

        try {
            logger.debug("query,sql=" + sql);
            stat = conn.prepareStatement(sql);
            if (params != null) {
                int index = 1;
                String[] var6 = params;
                int var7 = params.length;

                for(int var8 = 0; var8 < var7; ++var8) {
                    String param = var6[var8];
                    stat.setString(index++, param);
                }
            }

            rs = stat.executeQuery();
            if (rs.next()) {
                String var13 = rs.getString(1);
                return var13;
            }
        } finally {
            if (stat != null) {
                stat.close();
            }

            if (rs != null) {
                rs.close();
            }

        }

        return null;
    }

    public static long queryForLong(Connection conn, String sql, String[] params) throws SQLException {
        ResultSet rs = null;
        PreparedStatement stat = null;

        try {
            logger.debug("query,sql=" + sql);
            stat = conn.prepareStatement(sql);
            if (params != null) {
                int index = 1;
                String[] var6 = params;
                int var7 = params.length;

                for(int var8 = 0; var8 < var7; ++var8) {
                    String param = var6[var8];
                    stat.setString(index++, param);
                }
            }

            rs = stat.executeQuery();
            if (rs.next()) {
                long var13 = rs.getLong(1);
                return var13;
            }
        } finally {
            if (stat != null) {
                stat.close();
            }

            if (rs != null) {
                rs.close();
            }

        }

        return -1L;
    }

    public static Map<String, String> queryForMap(Connection conn, String sql) throws SQLException {
        return queryForMap(conn, sql, (String[])null);
    }

    public static Map<String, String> queryForMap(Connection conn, String sql, String[] params) throws SQLException {
        List<Map<String, String>> list = query(conn, sql, params);
        return list.size() > 0 ? (Map)list.get(0) : null;
    }

    public static List<Map<String, String>> query(Connection conn, String sql) throws SQLException {
        return query(conn, sql, (String[])null);
    }

    public static List<Map<String, String>> query(Connection conn, String sql, String[] params) throws SQLException {
        List<Map<String, String>> datas = new ArrayList();
        ResultSet rs = null;
        PreparedStatement stat = null;

        try {
            logger.debug("query,sql=" + sql);
            stat = conn.prepareStatement(sql);
            int i;
            if (params != null) {
                int index = 1;
                String[] var7 = params;
                int var8 = params.length;

                for(i = 0; i < var8; ++i) {
                    String param = var7[i];
                    stat.setString(index++, param);
                }
            }

            rs = stat.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while(rs.next()) {
                Map<String, String> data = new HashMap();

                for(i = 1; i <= columnCount; ++i) {
                    if (rs.getString(i) != null) {
                        data.put(metaData.getColumnLabel(i), rs.getString(i));
                    }
                }

                datas.add(data);
            }
        } finally {
            if (stat != null) {
                stat.close();
            }

            if (rs != null) {
                rs.close();
            }

        }

        return datas;
    }

    private static ResultSet getResultSet(Connection conn, String sql, String[] params) throws SQLException {
        PreparedStatement stat = null;

        ResultSet var12;
        try {
            logger.debug("query,sql=" + sql);
            stat = conn.prepareStatement(sql);
            if (params != null) {
                int index = 1;
                String[] var5 = params;
                int var6 = params.length;

                for(int var7 = 0; var7 < var6; ++var7) {
                    String param = var5[var7];
                    stat.setString(index++, param);
                }
            }

            var12 = stat.executeQuery();
        } finally {
            stat.close();
        }

        return var12;
    }
}
