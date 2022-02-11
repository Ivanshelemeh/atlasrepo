package com.atlassian.shele.shelePlugin.rest;

import com.atlassian.jira.issue.issuetype.IssueType;
import com.atlassian.jira.project.Project;
import com.atlassian.jira.project.ProjectManager;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("/issue")
@Service
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProjectIssueTypeService {

    private final ProjectManager projectManager;


    @Autowired
    public ProjectIssueTypeService(@ComponentImport ProjectManager projectManager) {
        this.projectManager = projectManager;
    }

    @GET
    @Path("/key")
    public List<String> getProjectKey(@QueryParam("projectKey") String projectKey) {
        if (projectKey == null ) {
            return null;
        }
        Project project = projectManager.getProjectObjByKey(projectKey);
        List<String> issueTypesNames = project.getIssueTypes().stream().map(IssueType::getName).collect(Collectors.toList());
        return issueTypesNames;
    }
}

