package com.atlassian.shele.shelePlugin.webwork;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.config.properties.APKeys;
import com.atlassian.jira.event.type.EventType;
import com.atlassian.jira.project.Project;
import com.atlassian.jira.project.ProjectManager;
import com.atlassian.jira.security.xsrf.RequiresXsrfCheck;
import com.atlassian.jira.web.action.JiraWebActionSupport;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

import static com.atlassian.shele.shelePlugin.utilit.Utilities.*;


public class SelectAction extends JiraWebActionSupport {
    private static final Logger logger = LoggerFactory.getLogger(SelectAction.class);
    private final ProjectManager projectManager;

    @Inject
    public SelectAction(@ComponentImport ProjectManager projectManager) {
        this.projectManager = projectManager;

    }

    @Override
    @RequiresXsrfCheck
    public String execute() throws Exception {
        List<Project> pro = projectManager.getProjects();
        List<EventType> list = ComponentAccessor.getEventTypeManager().getEventTypes().stream().collect(Collectors.toList());
        String val = ComponentAccessor.getApplicationProperties().getString(APKeys.JIRA_BASEURL);
        if (!pro.isEmpty() && !list.isEmpty()) {
            this.getServletContext().setAttribute(PROJECT_NAME, pro);
            this.getServletContext().setAttribute(EVENT, list);
            return SELECT_LOAD;
        } else {
            return SELECT_ERROR;
        }
    }

}

