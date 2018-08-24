package com.cookie.sql;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author cxq
 * @date 2018/8/24 10:02
 */
public class SqlParam {

    private String sql;

    private String[] params;

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String[] getParams() {
        return params;
    }

    public void setParams(String[] params) {
        this.params = params;
    }

    public SqlParam(String sql, String[] params) {
        this.sql = sql;
        this.params = params;
    }

    public static SqlParam getInsertSqlParm(String table , Map<String,String> data ){
        if (data != null && !data.isEmpty()){
            String valueStr = "";
            String columnStr = "";
            String [] params = new String[data.keySet().size()];
            Iterator<Map.Entry<String, String>> it = data.entrySet().iterator();
            int index = 0 ;
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                columnStr += "'"+entry.getKey()+"'," ;
                valueStr += "?,";
                params[index++] = entry.getValue();
            }
            columnStr = columnStr.substring(0,columnStr.length()-1);
            valueStr = valueStr.substring(0,valueStr.length()-1);

            String sql = "insert into "+table+"  ("+columnStr+") values ("+valueStr+")";

            return  new SqlParam(sql,params);


        }
        return  null ;

    }

    public static SqlParam getUpdateSqlParm(String table ,  Map<String,String> vals ,  Map<String,String> filters ){

        if (vals != null && !vals.isEmpty()){
            String valueStr = "";
            String filterStr = "";
            String [] params = new String[vals.keySet().size()];
            Iterator<Map.Entry<String, String>> val = vals.entrySet().iterator();
            int valIndenx = 0 ;
            while (val.hasNext()) {
                Map.Entry<String, String> entry = val.next();
                valueStr += entry.getKey() +" = ? ,";
                params[valIndenx++] = entry.getValue();
            }
            valueStr = valueStr.substring(0,valueStr.length()-1);

            if (filters != null && !filters.isEmpty()){
                Iterator<Map.Entry<String, String>> filter = vals.entrySet().iterator();
                int index = 0 ;
                while (filter.hasNext()) {
                    Map.Entry<String, String> entry = filter.next();
                    valueStr += entry.getKey() +" = ? ,";
                    params[index++] = entry.getValue();
                }
            }
            String sql = "update "+table+" set "+valueStr+" where d = ? ";

            return  new SqlParam(sql,params);


        }
        return  null ;

    }


}
