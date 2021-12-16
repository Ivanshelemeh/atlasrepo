package com.atlassian.shele.shelePlugin.ao;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.event.issue.IssueEvent;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import lombok.SneakyThrows;
import net.java.ao.Query;
import org.ofbiz.core.entity.GenericValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class IssueService {

    private final ActiveObjects activeObjects;
    private final MapIssueMapper mapper;

    @Autowired
    public IssueService(@ComponentImport ActiveObjects activeObjects,MapIssueMapper mapper) {
        this.activeObjects = activeObjects;
        this.mapper= mapper;
    }
    @SneakyThrows
    public void persistIssueEntity(IssueEvent event) {
        IssueEntity issueEntity = activeObjects.create(IssueEntity.class);
        issueEntity.setAuthorIssue(event.getIssue().getCreator().getName());
        issueEntity.setCreatedTime(event.getTime());
        issueEntity.setIssueId(Math.toIntExact(event.getIssue().getId()));
        issueEntity.setProjectId(Math.toIntExact(event.getProject().getId()));
        List<GenericValue> list = event.getChangeLog().getRelated("ChildChangeItem");
        for (GenericValue value : list) {
            issueEntity.setNewField(String.valueOf(value.get("newstring")));
            issueEntity.setPrevField(String.valueOf(value.get("oldstring")));
            issueEntity.setField(String.valueOf(value.get("field")));
        }
        issueEntity.setWorkFlow(String.valueOf(ComponentAccessor.getWorkflowManager().getWorkflow(event.getIssue()).getDisplayName()));
        issueEntity.setEvent(event.getEventTypeId());
            issueEntity.save();
    }


    public Optional<String> stringOptional(IssueEvent eventIssue){
        Optional<String> stringOptional = Arrays.stream(activeObjects.find(ProjectEntity.class, Query.select().where("PROJECT=?", eventIssue.getProject().getId()))).map(ProjectEntity::getEventTypeId)
                .findFirst();
        if (stringOptional.isPresent()){
            return stringOptional;
        }
        return null;

    }
    public List<IssueDTO> getDTO(){
        IssueEntity[] entities=this.activeObjects.find(IssueEntity.class, Query.select());
        return Arrays.stream(entities).map(mapper::toIssueDTO).collect(Collectors.toList());
    }
    public void persistCommentsValue(IssueEvent ev){
        IssueEntity issueEntity =activeObjects.create(IssueEntity.class);
        issueEntity.setNewField(ev.getComment().getBody());
        issueEntity.setField("Comment value");
        issueEntity.setIssueId(Math.toIntExact(ev.getIssue().getId()));
        issueEntity.setProjectId(Math.toIntExact(ev.getProject().getId()));
        issueEntity.setAuthorIssue(ev.getIssue().getCreator().getName());
        issueEntity.setCreatedTime(ev.getTime());
        issueEntity.setEvent(ev.getEventTypeId());
        issueEntity.setWorkFlow(ComponentAccessor.getWorkflowManager().getWorkflow(ev
                .getIssue()).getDescription());
        issueEntity.save();
    }
}
