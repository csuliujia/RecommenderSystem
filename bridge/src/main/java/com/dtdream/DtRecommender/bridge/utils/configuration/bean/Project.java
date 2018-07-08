package com.dtdream.DtRecommender.bridge.utils.configuration.bean;

import java.util.ArrayList;

/**
 * A project object is corresponding to a project in MaxCompute. A project can have multiple tasks.
 */
public class Project {
    private String projectName;
    private ArrayList<Task> tasks = new ArrayList<Task>();

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void addTasks(Task newTask) {
        tasks.add(newTask);
    }
}
