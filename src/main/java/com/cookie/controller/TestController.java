package com.cookie.controller;

import com.cookie.CommonConfigBase;
import com.cookie.tool.IDBPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author cxq
 * @date 2018/8/17 15:50
 */
@RestController
@RequestMapping("test")
public class TestController {

    private final static Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private DataSource dataSource;

//    @Autowired
//    private IDBPool dbPool ;

    @RequestMapping("/hello")
    public String test() {
        return "Hello World";
    }

    @GetMapping("/db")
    public String getDB(){

        logger.info("--------"+dataSource);
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet var12;
        try {
            conn = dataSource.getConnection();
            stat = conn.prepareStatement("select count(1) from test ");
            var12 = stat.executeQuery();
            logger.info("var12 = "+var12);
            if (var12.next()) {
                long var13 = var12.getLong(1);
               logger.info("========="+var13);
            }
        } catch (SQLException var14) {
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                    conn = null;
                }
            } catch (SQLException var13) {
            }

        }


        return  null ;
    }
}
