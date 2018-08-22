package com.cookie.tool;

/**
 * @author cxq
 * @date 2018/8/22 14:27
 */
public abstract class LoggerDBPool implements IDBPool {

    private LoggerDBPool.IDBExceptionLogger exceptionLogger = null;

    public LoggerDBPool() {
    }

    public void setExceptionLogger(LoggerDBPool.IDBExceptionLogger exceptionLogger) {
        this.exceptionLogger = exceptionLogger;
    }

    public void exceptionCallback(Exception e, String position, String sql, String params) {
        if (this.exceptionLogger != null) {
            this.exceptionLogger.log(e, position, sql, params);
        }

    }

    public abstract static class ExceptionLogger implements LoggerDBPool.IDBExceptionLogger {
        public ExceptionLogger() {
        }
    }

    public interface IDBExceptionLogger {
        void log(Exception var1, String var2, String var3, String var4);
    }
}
