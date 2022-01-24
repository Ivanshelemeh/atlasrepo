package com.atlassian.shele.shelePlugin.webwork;

import com.atlassian.configurable.ObjectConfigurationException;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.plugins.mail.webwork.AbstractEditHandlerDetailsWebAction;
import com.atlassian.jira.project.Project;
import com.atlassian.jira.service.JiraServiceContainer;
import com.atlassian.jira.service.services.file.AbstractMessageHandlingService;
import com.atlassian.jira.service.util.ServiceUtils;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.plugin.PluginAccessor;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.google.common.collect.ImmutableMap;

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
    private String userKey;
    private Long CFieldId;

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public Long getCFieldId() {
        return CFieldId;
    }

    public void setCFieldId(Long CFieldId) {
        this.CFieldId = CFieldId;
    }

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
        if (params.get(PROJECT_ID) != null) {
            projectId = Long.valueOf(params.get(PROJECT_ID));
        }
        if (params.get(USER_KEY) != null) {
            userKey = params.get(USER_KEY);
        }
        if (params.get(CUSTOM_FIELD_ID) != null) {
            CFieldId = Long.valueOf(params.get(CUSTOM_FIELD_ID));
        }
    }

    /**
     * Put meanings from ui to map
     *
     * @return map params in which K -it is a id from selector ui
     * V - it is a variable in current config
     */
    @Override
    protected Map<String, String> getHandlerParams() {
        return ImmutableMap.<String, String>builder()
                .put(PROJECT_ID, projectId.toString())
                .put(USER_KEY, userKey)
                .put(CUSTOM_FIELD_ID, CFieldId.toString())
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
        if (!projects.isEmpty() && !customFields.isEmpty() && !applicationUsers.isEmpty()) {
            this.getServletContext().setAttribute(PROJECTS, projects);
            this.getServletContext().setAttribute(CUSTOM_FIELDS, customFields);
            this.getServletContext().setAttribute(APP_USERS, applicationUsers);
            return result;
        }
        return ERROR_PAGE;
    }

    /**
     * This is for validation our properties
     */
    @Override
    protected void doValidation() {
        if (configuration == null)
            return;
        super.doValidation();
    }
}
