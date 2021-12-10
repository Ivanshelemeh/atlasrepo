package com.atlassian.shele.shelePlugin.webwork;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.web.action.JiraWebActionSupport;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.sal.api.user.UserManager;
import com.atlassian.shele.shelePlugin.ao.IssueDTO;
import com.atlassian.shele.shelePlugin.ao.IssueDTOPersistLayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MywebWorkAction extends JiraWebActionSupport {
    private final static String SUCCESS_LOAD= "success";
    private final static String ERROR_LOAD="error";
    private static final Logger logger= LoggerFactory.getLogger(MywebWorkAction.class);
    private final UserManager userManager;
    private final JiraAuthenticationContext context;
    private final ActiveObjects objects;
    private final IssueDTOPersistLayer dtoPersistLayer;

    @Autowired
    public MywebWorkAction(@ComponentImport UserManager userManager, @ComponentImport JiraAuthenticationContext context,@ComponentImport ActiveObjects objects,
                            IssueDTOPersistLayer dtoPersistLayer) {
        this.context=context;
        this.userManager=userManager;
        this.objects = objects;
        this.dtoPersistLayer=dtoPersistLayer;
    }

    @Override
    public String execute() throws Exception{
        ApplicationUser leadObject = ComponentAccessor.getProjectManager().getProjectObjByKeyIgnoreCase("KU").getProjectLead();
        if (leadObject.getKey().equals(context.getLoggedInUser().getKey())) {
            List<IssueDTO> entity= dtoPersistLayer.getDTO();
            this.getServletContext().setAttribute("entity", entity);
            return SUCCESS_LOAD;
        } else {
            return ERROR_LOAD;
        }
    }

}
