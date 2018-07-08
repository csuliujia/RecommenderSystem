package com.dtdream.DtRecommender.bridge.utils.configuration;

import com.dtdream.DtRecommender.bridge.utils.configuration.bean.Project;
import com.dtdream.DtRecommender.common.util.LogHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ConfigParser {
    private static final Logger logger = LoggerFactory.getLogger(ConfigParser.class);
    private static ConfigParser instance;

    public static ConfigParser getInstance() {
        if (instance != null) return instance;
        instance = new ConfigParser();
        return instance;
    }

    private ArrayList<Project> projects;

    private ConfigParser() {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = factory.newSAXParser();
            SAXHandler handler = new SAXHandler();
            InputStream in = new FileInputStream(LogHelper.getAbsolutePath("bridge", "table_config.xml"));
            saxParser.parse(in, handler);
            projects = handler.getProjects();
        } catch (ParserConfigurationException e) {
            logger.error("", e);
        } catch (SAXException e) {
            logger.error("", e);
        } catch (IOException e) {
            logger.error("", e);
        }
    }

    public ArrayList<Project> getProjects() {
        return projects;
    }
}
