/*
 * Copyright (c) 2003, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.util.Enumeration;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.LoggingMXBean;

/**
 * Logging is the implementation class of LoggingMXBean.
 *
 * The <tt>LoggingMXBean</tt> interface provides a standard
 * method for management access to the individual
 * {@code Logger} objects available at runtime.
 *
 * @author Ron Mann
 * @author Mandy Chung
 * @since 1.5
 *
 * @see javax.management
 * @see java.util.logging.Logger
 * @see LogManager
 */
class Logging implements LoggingMXBean {

    private static LogManager logManager = LogManager.getLogManager();

    /** Constructor of Logging which is the implementation class
     *  of LoggingMXBean.
     */
    Logging() {
    }

    public List<String> getLoggerNames() {
        Enumeration<String> loggers = logManager.getLoggerNames();
        ArrayList<String> array = new ArrayList<>();

        for (; loggers.hasMoreElements();) {
            array.add(loggers.nextElement());
        }
        return array;
    }

    private static String EMPTY_STRING = "";
    public String getLoggerLevel(String loggerName) {
        java.util.logging.Logger l = logManager.getLogger(loggerName);
        if (l == null) {
            return null;
        }

        java.util.logging.Level level = l.getLevel();
        if (level == null) {
            return EMPTY_STRING;
        } else {
            return level.getLevelName();
        }
    }

    public void setLoggerLevel(String loggerName, String levelName) {
        if (loggerName == null) {
            throw new NullPointerException("loggerName is null");
        }

        java.util.logging.Logger logger = logManager.getLogger(loggerName);
        if (logger == null) {
            throw new IllegalArgumentException("Logger " + loggerName +
                "does not exist");
        }

        java.util.logging.Level level = null;
        if (levelName != null) {
            // parse will throw IAE if logLevel is invalid
            level = Level.findLevel(levelName);
            if (level == null) {
                throw new IllegalArgumentException("Unknown level \"" + levelName + "\"");
            }
        }

        logger.setLevel(level);
    }

    public String getParentLoggerName( String loggerName ) {
        java.util.logging.Logger l = logManager.getLogger( loggerName );
        if (l == null) {
            return null;
        }

        Logger p = l.getParent();
        if (p == null) {
            // root logger
            return EMPTY_STRING;
        } else {
            return p.getName();
        }
    }
}
