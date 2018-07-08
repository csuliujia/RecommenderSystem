package com.dtdream.DtRecommender.bridge.utils.configuration;

import com.dtdream.DtRecommender.bridge.utils.configuration.bean.Project;
import com.dtdream.DtRecommender.bridge.utils.configuration.bean.Task;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * define the rule to parse a xml file into Project object.
 */
public class SAXHandler extends DefaultHandler {

    private ArrayList<Project> projects = new ArrayList<Project>();
    private String currentContent;
    private Project currentProject;
    private Task currentTask;

    @Override
    public void startDocument() {
    }

    @Override
    public void characters(char[] arg0, int arg1, int arg2)
            throws SAXException {
        currentContent = new String(arg0, arg1, arg2);
    }

    @Override
    public void startElement(String arg0, String arg1, String arg2, Attributes arg3)
            throws SAXException {
        if (arg2.equals("project")) {
            currentProject = new Project();
            projects.add(currentProject);
        } else if (arg2.equals("task")) {
            currentTask = new Task();
            currentProject.addTasks(currentTask);
        }
    }

    @Override
    public void endElement(String arg0, String arg1, String arg2)
            throws SAXException {
        if (arg2.equals("projectName")) currentProject.setProjectName(currentContent);
        else if (arg2.equals("topic")) currentTask.setTopic(currentContent);
        else if (arg2.equals("tag")) currentTask.setTag(currentContent);
        else if (arg2.equals("table")) currentTask.setTable(currentContent);
        else if (arg2.equals("recordNum")) currentTask.setRecordNum(Long.parseLong(currentContent.trim()));
        else if (arg2.equals("commitCycle")) currentTask.setCommitCycle(Long.parseLong(currentContent.trim()));
        else if (arg2.equals("ConsumerId") ||
                arg2.equals("AccessKey") ||
                arg2.equals("SecretKey") ||
                arg2.equals("ConsumeThreadNums")) currentTask.setMqConsumerProperties(arg2, currentContent);
    }

    public ArrayList<Project> getProjects() {
        return projects;
    }
}
