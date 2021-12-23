package com.atlassian.shele.shelePlugin.webwork;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.event.type.EventType;
import com.atlassian.jira.project.Project;
import com.atlassian.jira.project.ProjectManager;
import com.atlassian.jira.security.xsrf.RequiresXsrfCheck;
import com.atlassian.jira.web.action.JiraWebActionSupport;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;

import static com.atlassian.shele.shelePlugin.utilit.Utilities.*;

public class SelectAction extends JiraWebActionSupport {

    private final ProjectManager projectManager;

    @Inject
    public SelectAction(@ComponentImport ProjectManager projectManager) {
        this.projectManager = projectManager;
    }

    @Override
    @RequiresXsrfCheck
    public String execute() throws Exception {
        List<Project> projectList = projectManager.getProjects();
        Collection<EventType> eventTypeList = ComponentAccessor.getEventTypeManager().getEventTypes();
        if (!projectList.isEmpty() && !eventTypeList.isEmpty()) {
            this.getServletContext().setAttribute(PROJECT_NAME, projectList);
            this.getServletContext().setAttribute(EVENT, eventTypeList);
            return SELECT_LOAD;
        } else {
            return SELECT_ERROR;
        }
    }
}
