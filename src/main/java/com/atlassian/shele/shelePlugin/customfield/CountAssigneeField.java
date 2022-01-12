package com.atlassian.shele.shelePlugin.customfield;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.customfields.impl.GenericTextCFType;
import com.atlassian.jira.issue.customfields.manager.GenericConfigManager;
import com.atlassian.jira.issue.customfields.persistence.CustomFieldValuePersister;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.issue.fields.layout.field.FieldLayoutItem;
import com.atlassian.jira.issue.history.ChangeItemBean;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.atlassian.shele.shelePlugin.utilit.Utilities.CUST_FIELD;

/**
 * This custom field displays count of changing assignee
 * in current issue
 */
public class CountAssigneeField extends GenericTextCFType {

    public CountAssigneeField(@ComponentImport CustomFieldValuePersister customFieldValuePersister,
                              @ComponentImport GenericConfigManager genericConfigManager) {
        super(customFieldValuePersister, genericConfigManager);
    }

    /**
     * Prepare template velocity for showing count of assignee
     * just for issue reporter
     *
     * @param issue           current issue on project
     * @param field           custom field object
     * @param fieldLayoutItem for rendering field
     * @return params template velocity
     */
    @Nonnull
    @Override
    public Map<String, Object> getVelocityParameters(final Issue issue, final CustomField field,
                                                     final FieldLayoutItem fieldLayoutItem) {
        Map<String, Object> params = new HashMap<>();
        if (issue == null || issue.getReporter() == null) {
            return params;
        }
        String reporterKey = issue.getReporter().getKey();
        params.put("reporterKey", reporterKey);
        return params;
    }

    /**
     * Calculate how many times , changed assignee
     * in issue and show item on screen
     *
     * @param field custom field  object
     * @param issue current issue on project
     * @return assignee count
     */
    @Override
    public String getValueFromIssue(final CustomField field, final Issue issue) {
        List<ChangeItemBean> itemBeans = ComponentAccessor.getChangeHistoryManager()
                .getChangeItemsForField(issue, CUST_FIELD);
        int lengthAssignee = itemBeans.size();
        String assignee = String.valueOf(lengthAssignee);
        return assignee;
    }
}