package com.atlassian.shele.shelePlugin.webwork;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.web.action.JiraWebActionSupport;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.shele.shelePlugin.ao.IssueDTO;
import com.atlassian.shele.shelePlugin.ao.IssueService;

import java.util.List;

import static com.atlassian.shele.shelePlugin.utilit.Utilities.*;

public class MywebWorkAction extends JiraWebActionSupport {

    private final JiraAuthenticationContext context;
    private final IssueService issueService;
    private String selectedItem;

    public MywebWorkAction(@ComponentImport JiraAuthenticationContext context, IssueService issueService) {
        this.context = context;
        this.issueService = issueService;
    }

    @Override
    public String execute() throws Exception {
        ApplicationUser leadObject = ComponentAccessor.getProjectManager().getProjectObjByKeyIgnoreCase(selectedItem)
                .getProjectLead();
        if (leadObject != null && leadObject.getKey().equals(context.getLoggedInUser().getKey())) {
            List<IssueDTO> entityDTOList = issueService.getDTO();
            this.getServletContext().setAttribute(ENT, entityDTOList);
            return SUCCESS_LOAD;
        } else {
            return ERROR_LOAD;
        }
    }

    public String getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(String selectedItem) {
        this.selectedItem = selectedItem;
    }
}
