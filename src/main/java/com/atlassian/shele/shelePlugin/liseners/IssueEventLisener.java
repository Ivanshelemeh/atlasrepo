package com.atlassian.shele.shelePlugin.liseners;

import com.atlassian.activeobjects.external.ActiveObjects;
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
import java.util.Optional;


@Component
public class IssueEventLisener {

    private final Logger logger = Logger.getLogger(this.getClass());
    //@JiraImport
    private final EventPublisher publisher;
    private final ActiveObjects activeObjects;
    private ObjectMapper objectMapper = new ObjectMapper();
    private final IssueService service;


    @Inject
    public IssueEventLisener(@ComponentImport EventPublisher publisher, @ComponentImport ActiveObjects activeObjects, IssueService service) {
        this.publisher = publisher;
        this.activeObjects = activeObjects;
        this.service = service;
    }

    @EventListener
    public void onIssueEvent(IssueEvent event) throws Exception {
        Optional<String> stringOptional = service.stringOptional(event);
        if (!stringOptional.isPresent()) {
            return ;
        }
            List<Long> checkIds = objectMapper.reader().withType(new TypeReference<ArrayList<Long>>() {

            }).readValue(stringOptional.get());
            if (checkIds.contains(event.getEventTypeId())) {
                service.persistIssueEntity(event);
            }




    }
}









