package com.atlassian.shele.shelePlugin.webwork;

import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.web.action.JiraWebActionSupport;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.sal.api.user.UserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class MywebWorkAction extends JiraWebActionSupport {
    private final static String SUCCECC_LOAD= "success";
    private final static String ERROR_LOAD="error";
    private static final Logger logger= LoggerFactory.getLogger(MywebWorkAction.class);
    private final UserManager userManager;
    private final JiraAuthenticationContext context;


    @Inject
    public MywebWorkAction(@ComponentImport UserManager userManager, @ComponentImport JiraAuthenticationContext context) {
        this.context=context;
        this.userManager=userManager;
    }

    @Override
    public String execute() throws Exception{
        //  VelocityEngine engine = new VelocityEngine();
        //  engine.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS,"org.apache.velocity.runtime.log.Log4JLogChute");
        //engine.setProperty("runtime.log.logsystem.log4j.logger","com.mindtree.igg.website.email.TemplateMergeUtilVelocityImpl");
        //  engine.init();
        //  Template template= engine.getTemplate(SUCCECC_LOAD);
        //  VelocityContext context = new VelocityContext();
        // context.put(SUCCECC_LOAD,"succecc");
        //   StringWriter writer = new StringWriter();
        //   template.merge(context,writer);

        //}
        //} else {
        //prop.getBaseUrl(SUCCECC_LOAD);
        //  return SUCCECC_LOAD;


        if (context.getLoggedInUser() != null && userManager.isAdmin(context.getLoggedInUser().getKey())){
            return SUCCECC_LOAD;
        }
        return ERROR_LOAD;
    }

}
