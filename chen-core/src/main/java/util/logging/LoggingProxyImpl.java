/*
 * Copyright (c) 2009, 2013, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package util.logging;

import sun.util.logging.LoggingProxy;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Implementation of LoggingProxy when java.util.logging classes exist.
 */
class LoggingProxyImpl implements LoggingProxy {
    static final LoggingProxy INSTANCE = new LoggingProxyImpl();

    private LoggingProxyImpl() { }

    @Override
    public Object getLogger(String name) {
        // always create a platform logger with the resource bundle name
        return java.util.logging.Logger.getPlatformLogger(name);
    }

    @Override
    public Object getLevel(Object logger) {
        return ((java.util.logging.Logger) logger).getLevel();
    }

    @Override
    public void setLevel(Object logger, Object newLevel) {
        ((java.util.logging.Logger) logger).setLevel((java.util.logging.Level) newLevel);
    }

    @Override
    public boolean isLoggable(Object logger, Object level) {
        return ((java.util.logging.Logger) logger).isLoggable((java.util.logging.Level) level);
    }

    @Override
    public void log(Object logger, Object level, String msg) {
        ((java.util.logging.Logger) logger).log((java.util.logging.Level) level, msg);
    }

    @Override
    public void log(Object logger, Object level, String msg, Throwable t) {
        ((java.util.logging.Logger) logger).log((java.util.logging.Level) level, msg, t);
    }

    @Override
    public void log(Object logger, Object level, String msg, Object... params) {
        ((Logger) logger).log((java.util.logging.Level) level, msg, params);
    }

    @Override
    public java.util.List<String> getLoggerNames() {
        return java.util.logging.LogManager.getLoggingMXBean().getLoggerNames();
    }

    @Override
    public String getLoggerLevel(String loggerName) {
        return java.util.logging.LogManager.getLoggingMXBean().getLoggerLevel(loggerName);
    }

    @Override
    public void setLoggerLevel(String loggerName, String levelName) {
        java.util.logging.LogManager.getLoggingMXBean().setLoggerLevel(loggerName, levelName);
    }

    @Override
    public String getParentLoggerName(String loggerName) {
        return java.util.logging.LogManager.getLoggingMXBean().getParentLoggerName(loggerName);
    }

    @Override
    public Object parseLevel(String levelName) {
        java.util.logging.Level level = java.util.logging.Level.findLevel(levelName);
        if (level == null) {
            throw new IllegalArgumentException("Unknown level \"" + levelName + "\"");
        }
        return level;
    }

    @Override
    public String getLevelName(Object level) {
        return ((java.util.logging.Level) level).getLevelName();
    }

    @Override
    public int getLevelValue(Object level) {
        return ((Level) level).intValue();
    }

    @Override
    public String getProperty(String key) {
        return LogManager.getLogManager().getProperty(key);
    }
}
