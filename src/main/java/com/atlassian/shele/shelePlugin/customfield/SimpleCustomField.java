package com.atlassian.shele.shelePlugin.customfield;

import com.atlassian.jira.issue.customfields.impl.GenericTextCFType;
import com.atlassian.jira.issue.customfields.manager.GenericConfigManager;
import com.atlassian.jira.issue.customfields.persistence.CustomFieldValuePersister;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;

/**
 * SimpleCustomField module for examining how create a customfield
 * which should be shown only admin.
 *
 */
public class SimpleCustomField extends GenericTextCFType {

    public SimpleCustomField(@ComponentImport CustomFieldValuePersister customFieldValuePersister,
                             @ComponentImport GenericConfigManager genericConfigManager) {
        super(customFieldValuePersister, genericConfigManager);
    }
}

