package com.atlassian.shele.shelePlugin.webwork;

import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.web.action.JiraWebActionSupport;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.sal.api.user.UserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class MywebWork extends JiraWebActionSupport {
    private final static String SUCCECC_LOAD= "http://localhost:2990/jira/plugins/servlet/database-console/login.do";
    private final static String ERROR_LOAD="/template.webwork/error.vm";
    private static final Logger logger= LoggerFactory.getLogger(MywebWork.class);
    private final UserManager userManager;
    private final JiraAuthenticationContext context;


    @Inject
    public MywebWork(@ComponentImport UserManager userManager, @ComponentImport JiraAuthenticationContext context) {
        this.context=context;
        this.userManager=userManager;
    }

    @Override
    public String execute() throws Exception{
        if (context.getLoggedInUser() != null && userManager.isAdmin(context.getLoggedInUser().getKey())){
            return SUCCECC_LOAD;
        }
        return ERROR_LOAD;
    }

}
