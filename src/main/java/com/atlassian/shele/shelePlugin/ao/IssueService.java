package com.atlassian.shele.shelePlugin.ao;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.jira.event.issue.IssueEvent;
import com.atlassian.jira.event.type.EventType;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import lombok.SneakyThrows;
import net.java.ao.Query;
import org.ofbiz.core.entity.GenericValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Issue service requires to access
 * in datastore and retrieves data from it
 */
//TODO May should make this class abstract , then use it as template?
@Service
public class IssueService {

    private final ActiveObjects activeObjects;
    private final MapIssueMapper mapper;

    @Autowired
    public IssueService(@ComponentImport ActiveObjects activeObjects, MapIssueMapper mapper) {
        this.activeObjects = activeObjects;
        this.mapper = mapper;
    }

    @SneakyThrows
    public void persistIssueEntity(IssueEvent event) {
        IssueEntity issueEntity = activeObjects.create(IssueEntity.class);
        issueEntity.setAuthorIssue(event.getIssue().getCreator().getName());
        issueEntity.setCreatedTime(event.getTime());
        issueEntity.setIssueId(Math.toIntExact(event.getIssue().getId()));
        issueEntity.setProjectId(Math.toIntExact(event.getProject().getId()));
        issueEntity.setEvent(event.getEventTypeId());
        if (EventType.ISSUE_COMMENTED_ID.equals(event.getEventTypeId())) {
            issueEntity.setNewField(event.getComment().getBody());
            issueEntity.setField("Comment");
        } else if (EventType.ISSUE_COMMENT_EDITED_ID.equals(event.getEventTypeId())) {
            issueEntity.setNewField(event.getComment().getBody());
            issueEntity.setField("Comment edited");
        } else {
            List<GenericValue> genericValues = event.getChangeLog().getRelated("ChildChangeItem");
            genericValues.forEach(genericValue -> {
                issueEntity.setField(String.valueOf(genericValue.get("field")));
                issueEntity.setNewField(String.valueOf(genericValue.get("newstring")));
                issueEntity.setPrevField(String.valueOf(genericValue.get("oldstring")));
            });
        }
        issueEntity.save();
    }

    public String getEventIdsAsString(IssueEvent eventIssue) {
        return Arrays.stream(activeObjects.find(ProjectEntity.class, Query.select().where("PROJECT=?",
                        eventIssue.getProject().getId()))).map(ProjectEntity::getEventTypeId)
                .findFirst().orElse(null);
    }

    public List<IssueDTO> getDTO() {
        IssueEntity[] entities = this.activeObjects.find(IssueEntity.class, Query.select());
        return Arrays.stream(entities).map(mapper::toIssueDTO).collect(Collectors.toList());
    }

}

