package com.atlassian.shele.shelePlugin.rest;


import com.atlassian.shele.shelePlugin.ao.ProjectDTO;
import com.atlassian.shele.shelePlugin.ao.ProjectEntityService;
import lombok.extern.slf4j.Slf4j;
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

    private final ProjectEntityService persistLayer;

    public RestIssueService(ProjectEntityService persistLayer) {
        this.persistLayer = persistLayer;
    }

    @POST
    @Path("/issue")
    public Response submitProject(ProjectDTO dto) {
        persistLayer.deleteEntities();
        List<ProjectDTO> projectDTOS=persistLayer.saveEntity(dto);
        return Response.ok().build();

    }
}




