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

public class SecondCustomField extends GenericTextCFType {

    public SecondCustomField(@ComponentImport CustomFieldValuePersister customFieldValuePersister,
                             @ComponentImport GenericConfigManager genericConfigManager) {
        super(customFieldValuePersister, genericConfigManager);
    }

    @Nonnull
    @Override
    public Map<String, Object> getVelocityParameters(final Issue issue, final CustomField field,
                                                     final FieldLayoutItem fieldLayoutItem) {
        Map<String, Object> params = new HashMap<>();
        String reportKey = issue.getReporter().getKey();
        params.put("reportKey", reportKey);
        return params;
    }

    @Override
    public String getValueFromIssue(final CustomField field, final Issue issue) {
        List<ChangeItemBean> itemBeans = ComponentAccessor.getChangeHistoryManager()
                .getChangeItemsForField(issue, CUST_FIELD);
        int lengthAssignee = itemBeans.size();
        String assignee = String.valueOf(lengthAssignee);
        return assignee;
    }
}
