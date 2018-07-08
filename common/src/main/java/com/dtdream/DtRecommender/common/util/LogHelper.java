package com.dtdream.DtRecommender.common.util;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by handou on 8/20/16.
 */
public class LogHelper {

    /* 设置logback的存放路径， 统一存放在如下地方： 模块/config/logback.xml */
    public static void configLogPath(String module) {
        String path = getAbsolutePath(module, "logback.xml");

        System.out.println("log path:   " + path);

        File logback = new File(path);

        if (logback.exists()) {
            LoggerContext lc = (LoggerContext)LoggerFactory.getILoggerFactory();
            JoranConfigurator configurator = new JoranConfigurator();
            configurator.setContext(lc);
            lc.reset();

            try {
                configurator.doConfigure(logback);
            } catch (JoranException e) {
                e.printStackTrace(System.err);

                System.exit(-1);
            }
        } else {
            System.err.println("Failed to config logback.xml.");
        }
    }

    public static String getAbsolutePath(String module, String fileName) {
        String currDir = System.getProperty("user.dir");

        /* 解决窗口模式和命令运行模式 不一致的问题 */
        if (!currDir.contains(module)) {
            currDir += File.separator + module;
        }

        return  currDir + File.separator + "conf" + File.separator + fileName;
    }
}