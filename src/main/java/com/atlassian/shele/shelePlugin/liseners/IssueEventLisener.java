package com.atlassian.shele.shelePlugin.liseners;

import com.atlassian.event.api.EventListener;
import com.atlassian.event.api.EventPublisher;
import com.atlassian.jira.event.issue.IssueEvent;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.shele.shelePlugin.ao.IssueService;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;


@Component
public class IssueEventLisener {

    private final Logger logger = Logger.getLogger(this.getClass());
    private final EventPublisher publisher;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final IssueService service;


    @Inject
    public IssueEventLisener(@ComponentImport EventPublisher publisher, IssueService service) {
        this.publisher = publisher;
        this.service = service;
    }

    @EventListener
    public void onIssueEvent(IssueEvent event) throws Exception {
        String valueString = service.getEventIdsAsString(event);
        if (valueString == null) {
            return;
        }

        List<Long> checkIds = objectMapper.reader().withType(new TypeReference<ArrayList<Long>>() {
        }).readValue(valueString);
        if (checkIds.contains(event.getEventTypeId())) {
            service.persistIssueEntity(event);
        }
    }
}









