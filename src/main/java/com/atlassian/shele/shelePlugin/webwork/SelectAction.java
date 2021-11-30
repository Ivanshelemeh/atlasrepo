package com.atlassian.shele.shelePlugin.webwork;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.jira.project.Project;
import com.atlassian.jira.project.ProjectManager;
import com.atlassian.jira.security.xsrf.RequiresXsrfCheck;
import com.atlassian.jira.web.action.JiraWebActionSupport;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;


public class SelectAction extends JiraWebActionSupport {
    private final static String SELECT_LOAD = "select";
    private static final Logger logger = LoggerFactory.getLogger(SelectAction.class);
    private final ActiveObjects objects;
    private final ProjectManager projectManager;

    @Inject
    public SelectAction(@ComponentImport ActiveObjects objects, @ComponentImport ProjectManager projectManager) {
        this.objects = objects;
        this.projectManager = projectManager;
    }
    //   public String filterProject(){
    //    return projectManager.getProjectObjByName("kudo").getName();
    //   .stream().map(Project::getKey)
    //  .collect(Collectors.joining(":"));
    // }


    @Override
    @RequiresXsrfCheck
    public String execute() throws Exception {
        List<Project> projectList= projectManager.getProjects();
        Object values = projectList.stream().filter(project -> project.getKey()!=null).findFirst().get();

        this.getServletContext().setAttribute("prod",values);
        return SELECT_LOAD;

    }

}

