package com.atlassian.shele.shelePlugin.liseners;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.event.api.EventListener;
import com.atlassian.event.api.EventPublisher;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.event.issue.IssueEvent;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.shele.shelePlugin.ao.IssueEntity;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.ofbiz.core.entity.GenericValue;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;




@Component
public class IssueEventLisener  {

    private final Logger logger = Logger.getLogger(this.getClass());
    //@JiraImport

    private final EventPublisher publisher;

    private final ActiveObjects activeObjects;
    private ObjectMapper objectMapper =new ObjectMapper();


    @Inject
    public IssueEventLisener(@ComponentImport EventPublisher publisher, @ComponentImport ActiveObjects activeObjects) {
        this.publisher = publisher;
        this.activeObjects = activeObjects;
    }
    @EventListener
    public void onIssueEvent( IssueEvent event)throws Exception{

                IssueEntity issueEntity = activeObjects.create(IssueEntity.class);
                issueEntity.setAuthorIssue(event.getIssue().getCreator().getName());
                issueEntity.setCreatedTime(event.getTime());
                issueEntity.setIssueId(Math.toIntExact(event.getIssue().getId()));
                issueEntity.setProjectId(Math.toIntExact(event.getProject().getId()));
                List<GenericValue> list = event.getChangeLog().getRelated("ChildChangeItem");
                   for (GenericValue value :list) {
                       issueEntity.setNewField(String.valueOf(value.get("newstring")));
                       issueEntity.setPrevField(String.valueOf(value.get("oldstring")));
                       issueEntity.setField(String.valueOf(value.get("field")));
                   }
                issueEntity.setWorkFlow(String.valueOf(ComponentAccessor.getWorkflowManager().getWorkflow(event.getIssue()).getDisplayName()));
                issueEntity.setEvent(event.getEventTypeId());
                issueEntity.save();




    }


}
