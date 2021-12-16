package com.atlassian.shele.shelePlugin.rest;


import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.shele.shelePlugin.ao.IssueDTOPersistLayer;
import com.atlassian.shele.shelePlugin.ao.ProjectDTO;
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
import java.util.List;

@Path("/api")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Component
@Slf4j
public class RestIssueService {

    private final IssueDTOPersistLayer persistLayer;

    @Autowired
    public  RestIssueService(IssueDTOPersistLayer persistLayer) {
        this.persistLayer=persistLayer;
    }

        @POST
        @Path("/issue")
        public Response.Status submitProject(ProjectDTO dto){
            ProjectEntity projectEntity = persistLayer.getEntity();
            persistLayer.deleteRaws();
            List<String> list = dto.getProject();
            List<Long> longs = dto.getEventTypeIds();
                for(String s : list){
                    ProjectEntity project =persistLayer.getEntity();
                    project.setProject(s);
                    project.setEventTypeId(longs.toString());
                   project.setIssueStatus(ComponentAccessor.getWorkflowManager().getDefaultWorkflow().getLinkedStatusObjects().get(0).getSimpleStatus().getName());
                   project.save();
                }
           return Response.Status.CREATED;
        }
    }



