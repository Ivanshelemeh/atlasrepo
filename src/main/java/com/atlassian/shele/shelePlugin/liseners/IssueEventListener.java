package com.atlassian.shele.shelePlugin.liseners;

import com.atlassian.event.api.EventListener;
import com.atlassian.jira.event.issue.IssueEvent;
import com.atlassian.shele.shelePlugin.ao.IssueService;
import com.atlassian.shele.shelePlugin.ao.ProjectMapper;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

@Component
public class IssueEventListener {

    private final Logger logger = Logger.getLogger(this.getClass());
    private final IssueService service;
    private final ProjectMapper projectMapper;

    @Inject
    public IssueEventListener(IssueService service, ProjectMapper projectMapper) {
        this.service = service;
        this.projectMapper = projectMapper;
    }

    @EventListener
    public void onIssueEvent(IssueEvent event) throws Exception {
        String valueString = service.getEventIdsAsString(event);
        if (valueString == null) {
            return;
        }

        List<Long> checkIds = projectMapper.mapToLong(valueString);
        if (checkIds.contains(event.getEventTypeId())) {
            service.persistIssueEntity(event);
        }
    }
}
