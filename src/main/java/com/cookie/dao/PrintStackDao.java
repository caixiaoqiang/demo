package com.cookie.dao;

import com.cookie.tool.IDBPool;
import org.apache.log4j.Logger;

/**
 * @author cxq
 * @date 2018/8/22 15:38
 */
public abstract  class PrintStackDao {

    private static final Logger logger = Logger.getLogger(PrintStackDao.class);
    protected IDBPool pool = null;

    public PrintStackDao() {
    }

    protected void printCallStack(Exception e, String sql, String[] params) {
        StackTraceElement[] stackElements = e.getStackTrace();
        StringBuffer sb = new StringBuffer();
        if (stackElements != null) {
            for(int i = 0; i < stackElements.length; ++i) {
                String className = stackElements[i].getClassName();
                if (className.contains("com.fantasi")) {
                    sb.append(stackElements[i].getClassName() + "\t");
                    sb.append(stackElements[i].getLineNumber() + "\t");
                    sb.append(stackElements[i].getMethodName() + "\n");
                }
            }

            logger.error(sb.toString());
        }

        StringBuffer sb2 = new StringBuffer();
        if (params != null) {
            for(int i = 0; i < params.length; ++i) {
                sb2.append(params[i]);
            }
        }

        this.pool.exceptionCallback(e, sb.toString(), sql, sb2.toString());
    }
}
