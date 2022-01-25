package com.atlassian.shele.shelePlugin.webwork;

import com.atlassian.configurable.ObjectConfigurationException;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.issue.issuetype.IssueType;
import com.atlassian.jira.plugins.mail.webwork.AbstractEditHandlerDetailsWebAction;
import com.atlassian.jira.project.Project;
import com.atlassian.jira.service.JiraServiceContainer;
import com.atlassian.jira.service.services.file.AbstractMessageHandlingService;
import com.atlassian.jira.service.util.ServiceUtils;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.plugin.PluginAccessor;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.google.common.collect.ImmutableMap;
import lombok.SneakyThrows;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.atlassian.shele.shelePlugin.utilit.Utilities.*;

/**
 * Webwork  creates configuration of mail handler. It gives to choose
 * project , app user , and custom field for handler
 *
 * @author shele
 */
public class MailConfigDetailsWebAction extends AbstractEditHandlerDetailsWebAction {


    private Long projectId;
    private String userName;


    public void setIssueTypeId(Long issueTypeId) {
        this.issueTypeId= issueTypeId;
    }

    public Long getIssueTypeId() {
        return issueTypeId;
    }

    private Long issueTypeId;

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    private List<Project> projects;

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getCustomFieldId() {
        return customFieldId;
    }

    public void setCustomFieldId(Long customFieldId) {
        this.customFieldId = customFieldId;
    }

    private Long customFieldId;

    @Inject
    public MailConfigDetailsWebAction(@ComponentImport PluginAccessor pluginAccessor) {
        super(pluginAccessor);
    }

    /**
     * In this method map params must be filled our custom properties from our custom mail handler
     *
     * @param jiraServiceContainer create a map from properties located in mail handler
     *                             through getHandlerParams() method
     * @throws ObjectConfigurationException
     */
    @Override
    protected void copyServiceSettings(JiraServiceContainer jiraServiceContainer) throws ObjectConfigurationException {
        final Map<String, String> params = ServiceUtils.getParameterMap(jiraServiceContainer.
                getProperty(AbstractMessageHandlingService.KEY_HANDLER_PARAMS));
        if (params.get(USER_KEY) != null) {
            userName = params.get(USER_KEY);
        }
        if (params.get(CUSTOM_FIELD_ID) != null) {
            customFieldId= Long.valueOf(params.get(CUSTOM_FIELD_ID));
        }
        if (params.get(ISSUE_TYPE)!= null){
            issueTypeId = Long.valueOf(params.get(ISSUE_TYPE));
        }
        if (params.get(PROJECT_ID)!= null){
            projectId = Long.valueOf(params.get(PROJECT_ID));
        }
    }


    /**
     * Put meanings from ui to map
     *
     * @return map params in which K -it is a id from selector ui
     * V - it is a variable in current config
     */
    @Override
    protected  Map<String, String> getHandlerParams(){
        return ImmutableMap.<String, String>builder()
                .put(USER_KEY, userName)
                .put(CUSTOM_FIELD_ID, customFieldId.toString())
                .put(ISSUE_TYPE,issueTypeId.toString())
                .put(PROJECT_ID, projectId.toString())
                .build();
    }

    /**
     * This method helps to prepare projects, custom fields  via filter to take just text custom field,
     * and app users
     *
     * @return page for choosing config mail handler, or error page
     * @throws Exception
     */
    @Override
    public String doDefault() throws Exception {
        String result = super.doDefault();
        List<Project> projects = ComponentAccessor.getProjectManager().getProjects();
        List<CustomField> customFields = ComponentAccessor.getCustomFieldManager().getCustomFieldObjects()
                .stream().filter(cf -> cf.getCustomFieldType().getKey()
                        .equals(FILTER_CF))
                .collect(Collectors.toList());
        Collection<ApplicationUser> applicationUsers = ComponentAccessor.getUserManager().getUsers();
        Collection<IssueType> issueTypes = ComponentAccessor.getIssueTypeSchemeManager().getIssueTypesForProject(projects.get(0));
        if (!projects.isEmpty() && !customFields.isEmpty() && !applicationUsers.isEmpty()) {
            this.getServletContext().setAttribute("type", issueTypes);
            this.getServletContext().setAttribute(PROJECTS, projects);
            this.getServletContext().setAttribute(CUSTOM_FIELDS, customFields);
            this.getServletContext().setAttribute(APP_USERS, applicationUsers);
            return result;
        }
        return ERROR_PAGE;
    }

    @SneakyThrows
    @Override
    protected void doValidation() {
        if (projectId == null  || userName == null){
            super.addErrorMessage("Invalid parameters");
        }
        super.doValidation();
    }
}
