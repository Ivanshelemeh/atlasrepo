package com.atlassian.shele.shelePlugin.ao;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.event.type.EventType;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import net.java.ao.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

@Component
public class IssueDTOPersistLayer {
    private final ActiveObjects activeObjects;
    private final MapIssueMapper mapper;

    @Autowired
    public IssueDTOPersistLayer(@ComponentImport ActiveObjects activeObjects, MapIssueMapper mapper) {
        this.activeObjects = checkNotNull(activeObjects);
        this.mapper= mapper;
    }
    public List<IssueDTO> getDTO(){
        IssueEntity[] entities=this.activeObjects.find(IssueEntity.class, Query.select());
        return Arrays.stream(entities).map(mapper::toIssueDTO).collect(Collectors.toList());
    }
    public ProjectEntity getEntity(){
        ProjectEntity entity= activeObjects.create(ProjectEntity.class);
        entity.setProject(String.valueOf(ComponentAccessor.getProjectManager().getProjects()));
        entity.setEventTypeId(ComponentAccessor.getEventTypeManager().getEventTypes().stream().map(EventType::getId).toString());
        entity.setIssueStatus(ComponentAccessor.getWorkflowManager().getDefaultWorkflow().toString());
        entity.save();
        return entity;
    }
    public void deleteRaws(){
        ProjectEntity [] projectEntities= activeObjects.find(ProjectEntity.class,Query.select("ID,PROJECT, ISSUE_STATUS,EVENT_TYPE_ID"));
        this.activeObjects.delete(projectEntities);
    }
}
