package com.atlassian.shele.shelePlugin.liseners;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.event.api.EventListener;
import com.atlassian.event.api.EventPublisher;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.event.issue.IssueEvent;
import com.atlassian.jira.issue.comments.Comment;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.sal.api.transaction.TransactionCallback;
import com.atlassian.shele.shelePlugin.ao.IssueEntity;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

@Component
public class IssueEventLisener  implements InitializingBean, DisposableBean {

    private final Logger logger = Logger.getLogger(this.getClass());
    //@JiraImport

    private final EventPublisher publisher;

    private final ActiveObjects activeObjects;


    @Inject
    public IssueEventLisener(@ComponentImport EventPublisher publisher, @ComponentImport ActiveObjects activeObjects) {
        this.publisher = publisher;
        this.activeObjects = activeObjects;
    }
    @EventListener
    public void onIssueEvent( IssueEvent event)throws Exception{

        activeObjects.executeInTransaction(new TransactionCallback<IssueEntity>() {
            @Override
            public IssueEntity doInTransaction() {
                IssueEntity issueEntity = activeObjects.create(IssueEntity.class);
                issueEntity.setCreatedTime(event.getTime());
                issueEntity.setIssueId(Math.toIntExact(event.getIssue().getId()));
                issueEntity.setField(event.getIssue().getIssueType().getName());
                issueEntity.setProjectId(Math.toIntExact(event.getProject().getId()));
                issueEntity.setAuthorIssue(String.valueOf(event.getIssue().getCreator().toString()));
                List<Comment> commentList= ComponentAccessor.getCommentManager().getComments(event.getIssue());
                //  commentList.remove(event.getComment().getBody());
                Comment pr = commentList.get(commentList.size()-2);
                issueEntity.setPrevField(pr.getBody());
                issueEntity.setNewField(ComponentAccessor.getCommentManager().getLastComment(event.getIssue()).getBody());
                issueEntity.setWorkFlow(String.valueOf(ComponentAccessor.getWorkflowManager().getWorkflow(event.getIssue()).getDisplayName()));
                issueEntity.save();
                return issueEntity;
        }

        });
    }

   @Override
    public void destroy() throws Exception {
        this.publisher.unregister(this);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.publisher.register(this);
    }
}
