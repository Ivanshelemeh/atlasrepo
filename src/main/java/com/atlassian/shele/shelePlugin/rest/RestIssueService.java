package com.atlassian.shele.shelePlugin.rest;


import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.jira.project.ProjectManager;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.shele.shelePlugin.ao.IssueDTO;
import com.atlassian.shele.shelePlugin.ao.IssueDTOPersistLayer;
import com.atlassian.shele.shelePlugin.ao.ProjectEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

@Path("/api")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Component
@Slf4j
public class RestIssueService {

    private  ActiveObjects activeObjects;
    private ProjectManager projectManager;
    private IssueDTOPersistLayer persistLayer;

    @Autowired
    public  RestIssueService(@ComponentImport ActiveObjects activeObjects ,@ComponentImport ProjectManager projectManager,IssueDTOPersistLayer persistLayer) {
        this.activeObjects = checkNotNull(activeObjects);
        this.projectManager =projectManager;
        this.persistLayer=persistLayer;
    }

        @POST
        @Path("/issue")
        public Response.Status submitProject(){
            ProjectEntity projectEntity =activeObjects.create(ProjectEntity.class);
            projectEntity.setProject(String.valueOf(projectManager.getProjects()));
            Integer status=persistLayer.getDTO().stream().map(IssueDTO::getIssueId).findAny().get();
            projectEntity.setIssueStatus(String.valueOf(status));
            projectEntity.save();
            log.info("responce"+ projectEntity);
           return Response.Status.CREATED;
        }
    }



