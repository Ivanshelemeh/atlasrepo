package com.atlassian.shele.shelePlugin.customfield;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.changehistory.ChangeHistoryManager;
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

    private final ChangeHistoryManager manager;

    public SecondCustomField(@ComponentImport CustomFieldValuePersister customFieldValuePersister,
                             @ComponentImport GenericConfigManager genericConfigManager,
                             @ComponentImport ChangeHistoryManager manager) {
        super(customFieldValuePersister, genericConfigManager);
        this.manager = manager;
    }

    @Nonnull
    @Override
    public Map<String, Object> getVelocityParameters(Issue issue, CustomField field,
                                                     FieldLayoutItem fieldLayoutItem) {
        Map<String, Object> params = new HashMap<>();
        String report = issue.getReporter().getKey();
        params.put("report", report);
        return params;
    }

    @Override
    public String getValueFromIssue(CustomField field, Issue issue) {
        int countAssignee = 0;
        List<ChangeItemBean> itemBeans = ComponentAccessor.getChangeHistoryManager()
                .getChangeItemsForField(issue, CUST_FIELD);
        int lengthAssignee = itemBeans.size();
        countAssignee = lengthAssignee;
        String assignee = String.valueOf(countAssignee);
        return assignee;
    }
}
