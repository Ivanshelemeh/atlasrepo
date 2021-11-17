package com.atlassian.shele.shelePlugin.rest;


import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.jira.issue.Issue;
import com.atlassian.sal.api.transaction.TransactionCallback;
import com.atlassian.shele.shelePlugin.ao.IssueEntity;
import net.java.ao.EntityManager;
import net.java.ao.RawEntity;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.beans.PropertyChangeListener;
import java.time.LocalDateTime;
import java.util.Locale;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

@Path("/api")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RestIssueService {

    private  ActiveObjects activeObjects;

    public  RestIssueService(ActiveObjects activeObjects) {
        this.activeObjects = checkNotNull(activeObjects);
    }
        @GET
        @Path("/getIssue")
        public Response createIssue(){
            IssueEntity is = activeObjects.create(IssueEntity.class);
            is.setIssueId(2);
            is.setAuthorIssue("Jessy");
            is.save();
           return Response.ok().build();
        }
 

        
        




    }



