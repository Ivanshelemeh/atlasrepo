package com.atlassian.shele.shelePlugin.webwork;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.web.action.JiraWebActionSupport;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.sal.api.user.UserManager;
import com.atlassian.shele.shelePlugin.ao.IssueEntity;
import com.atlassian.velocity.VelocityManager;
import net.java.ao.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

public class MywebWorkAction extends JiraWebActionSupport {
    private final static String SUCCECC_LOAD= "success";
    private final static String ERROR_LOAD="error";
    private static final Logger logger= LoggerFactory.getLogger(MywebWorkAction.class);
    private final UserManager userManager;
    private final JiraAuthenticationContext context;
    private final ActiveObjects objects;
    private final VelocityManager velocityManager;

    @Inject
    public MywebWorkAction(@ComponentImport UserManager userManager, @ComponentImport JiraAuthenticationContext context,@ComponentImport ActiveObjects objects,
                           @ComponentImport VelocityManager velocityManager) {
        this.context=context;
        this.userManager=userManager;
        this.objects = objects;
        this.velocityManager = velocityManager;
    }

    @Override
    public String execute() throws Exception{
        //  String user= String.valueOf(ComponentAccessor.getProjectManager().getProjectObjByKey("VAL").getProjectLead());
        //  if (user== context1.getLoggedInUser().getKey()){
        IssueEntity[] entities = this.objects.find(IssueEntity.class, Query.select().limit(10));
        Map<String,Object> stringObjectMap = new HashMap<String,Object>();
        stringObjectMap.put("entity", entities);
        String content= this.velocityManager.getEncodedBody("/template.webwork/","success.vm", "UTF-8",stringObjectMap);
        return SUCCECC_LOAD;
    }


}
