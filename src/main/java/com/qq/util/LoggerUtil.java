package com.qq.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qq.Main;

public class LoggerUtil
{
    private static final Logger theLogger = LoggerFactory.getLogger( Main.class );

    public static final Logger getLogger()
    {
        return theLogger;
    }
}
