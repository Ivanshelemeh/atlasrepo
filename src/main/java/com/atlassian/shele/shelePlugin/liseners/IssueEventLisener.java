package com.atlassian.shele.shelePlugin.liseners;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.event.api.EventListener;
import com.atlassian.event.api.EventPublisher;
import com.atlassian.jira.event.issue.IssueEvent;
import com.atlassian.jira.issue.changehistory.ChangeHistoryManager;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.sal.api.transaction.TransactionCallback;
import com.atlassian.shele.shelePlugin.ao.IssueEntity;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Date;

@Component
public class IssueEventLisener  implements InitializingBean, DisposableBean {

    private final Logger logger = Logger.getLogger(this.getClass());
    //@JiraImport

    private final EventPublisher publisher;

    private final ActiveObjects activeObjects;

    private final ChangeHistoryManager historyManager;

    @Inject
    public IssueEventLisener(@ComponentImport EventPublisher publisher, @ComponentImport ActiveObjects activeObjects,
                             @ComponentImport ChangeHistoryManager historyManager) {
        this.publisher = publisher;
        this.activeObjects = activeObjects;
       this.historyManager = historyManager;
    }
    @EventListener
    public void onIssueEvent( IssueEvent event)throws Exception{
        this.logger.error(event.toString());
     //   Issue newEvent = (Issue) historyManager.getChangeHistories(event.getIssue())
       //                 .stream()
         //                       .findAny()
           //                             .orElse(historyManager.getChangeHistoryById(event.getEventTypeId()));
        activeObjects.executeInTransaction(new TransactionCallback<IssueEntity>() {

            @Override
            public IssueEntity doInTransaction() {
                IssueEntity entity = activeObjects.create(IssueEntity.class);//new DBParam("IssueID", Integer.class),
                    //new DBParam("CreateTime", Date.class), new DBParam("Field", String.class), new
                           // DBParam("AuthorIssue", String.class));
            entity.setCreatedTime(event.getTime());
            entity.setIssueId(Math.toIntExact(event.getIssue().getId()));
            entity.setField(String.valueOf(event.getChangeLog()));
            entity.setAuthorIssue(String.valueOf(event.getUser()));
                entity.setNewField(String.valueOf(historyManager.getAllChangeItems(event.getIssue())));
                String[] arrString= (historyManager.getChangeHistoriesSince(event.getIssue(), event.getTime())
                        .stream().toArray(String[]::new));
                //  String newEvent = String.join(",",arrString);
                entity.setPrevField(String.valueOf(arrString));
          //  entity.setNewField(historyManager.getChangeHistories(event.getIssue()));
          //  entity.setPrevField(historyManager.getChangeHistoriesSince(event.getIssue(),new Date(String.valueOf(event.getTime()))));
            entity.save();
            //  publisher.register(event);
            return entity;
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
