package com.dtdream.DtRecommender.common.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by handou on 9/21/16.
 */
public class AppContextParser {

    public static Object getAppicationConext(String module, String xmlFile, String bean) {
        String filePath = "file:" + LogHelper.getAbsolutePath(module, xmlFile);
        ApplicationContext context = new ClassPathXmlApplicationContext(filePath);

        return context.getBean(bean);
    }
}
