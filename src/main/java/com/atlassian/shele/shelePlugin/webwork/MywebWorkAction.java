package com.atlassian.shele.shelePlugin.webwork;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.web.action.JiraWebActionSupport;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.sal.api.user.UserManager;
import com.atlassian.shele.shelePlugin.ao.IssueDTO;
import com.atlassian.shele.shelePlugin.ao.IssueEntity;
import com.atlassian.shele.shelePlugin.ao.MapIssueMapper;
import com.atlassian.velocity.VelocityManager;
import net.java.ao.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MywebWorkAction extends JiraWebActionSupport {
    private final static String SUCCECC_LOAD= "success";
    private final static String ERROR_LOAD="error";
    private static final Logger logger= LoggerFactory.getLogger(MywebWorkAction.class);
    private final UserManager userManager;
    private final JiraAuthenticationContext context;
    private final ActiveObjects objects;
    private final VelocityManager velocityManager;
    private final MapIssueMapper mapper;

    @Autowired
    public MywebWorkAction(@ComponentImport UserManager userManager, @ComponentImport JiraAuthenticationContext context,@ComponentImport ActiveObjects objects,
                           @ComponentImport VelocityManager velocityManager, MapIssueMapper mapper) {
        this.context=context;
        this.userManager=userManager;
        this.objects = objects;
        this.velocityManager = velocityManager;
        this.mapper =mapper;
    }

    @Override
    public String execute() throws Exception{
        ApplicationUser leadObject = ComponentAccessor.getProjectManager().getProjectObjByKeyIgnoreCase("KUDO").getProjectLead();
        if (leadObject.getKey().equals(context.getLoggedInUser().getKey())) {

            IssueEntity [] ent= this.objects.find(IssueEntity.class,Query.select().limit(1));
            List<IssueDTO> entity= Arrays.stream(ent).map(mapper::toIssueDTO).collect(Collectors.toList());
            this.getServletContext().setAttribute("entity", entity);
            return SUCCECC_LOAD;
        } else {
            return ERROR_LOAD;
        }
    }

}
