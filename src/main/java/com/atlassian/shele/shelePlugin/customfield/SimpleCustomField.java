package com.atlassian.shele.shelePlugin.customfield;

import com.atlassian.jira.issue.customfields.impl.GenericTextCFType;
import com.atlassian.jira.issue.customfields.manager.GenericConfigManager;
import com.atlassian.jira.issue.customfields.persistence.CustomFieldValuePersister;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;

/**
 * SimpleCustomField module access for change item only
 * amin
 */
//TODO create customfield with count of assignee into it
public class SimpleCustomField extends GenericTextCFType {

    private final JiraAuthenticationContext authenticationContext;

    public SimpleCustomField(@ComponentImport CustomFieldValuePersister customFieldValuePersister,
                             @ComponentImport GenericConfigManager genericConfigManager,
                             @ComponentImport JiraAuthenticationContext authenticationContext) {
        super(customFieldValuePersister, genericConfigManager);
        this.authenticationContext = authenticationContext;
    }
}

