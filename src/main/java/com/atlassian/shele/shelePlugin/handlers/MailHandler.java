package com.atlassian.shele.shelePlugin.handlers;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.CustomFieldManager;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.MutableIssue;
import com.atlassian.jira.issue.attachment.CreateAttachmentParamsBean;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.issue.fields.FieldException;
import com.atlassian.jira.issue.history.ChangeItemBean;
import com.atlassian.jira.project.Project;
import com.atlassian.jira.service.util.handler.MessageHandler;
import com.atlassian.jira.service.util.handler.MessageHandlerContext;
import com.atlassian.jira.service.util.handler.MessageHandlerErrorCollector;
import com.atlassian.jira.service.util.handler.MessageUserProcessor;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.mail.MailUtils;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.google.common.io.Files;
import lombok.SneakyThrows;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.File;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.atlassian.shele.shelePlugin.utilit.Utilities.*;

/**
 * Basic mail handler
 *
 * @author shele
 */
public class MailHandler implements MessageHandler {

    private final CustomFieldManager customFieldManager;
    private final MessageUserProcessor processor;
    private String messageTitle;
    public Long projectId;
    public String userKey;
    public Long CFieldId;

    public MailHandler(@ComponentImport CustomFieldManager customFieldManager,
                       @ComponentImport MessageUserProcessor processor) {
        this.customFieldManager = customFieldManager;
        this.processor = processor;
    }

    /**
     * This method calls before handler starts to retrieve incoming messages
     *
     * @param params       comes from config ui handler such as project id, app user , custom  field
     * @param messageerror messageerror throw if config not valid
     */
    @Override
    public void init(Map<String, String> params, MessageHandlerErrorCollector messageerror) {
        if (params.containsKey(PROJECT_ID)) {
            projectId = Long.valueOf(params.get(PROJECT_ID));
        }
        if (params.containsKey(USER_KEY)) {
            userKey = params.get(USER_KEY);
        }
        if (params.containsKey(CUSTOM_FIELD_ID)) {
            CFieldId = Long.valueOf(params.get(CUSTOM_FIELD_ID));
        }
    }

    /**
     * This method created an attachments from incoming message
     *
     * @param message incoming message with attachments located in body of message
     * @param issue   attachments would be added to item
     * @param user    creator of message
     */
    @SneakyThrows
    private void makeAttachment(Message message, Issue issue, ApplicationUser user) {
        MailUtils.Attachment[] attachments = MailUtils.getAttachments(message);
        for (MailUtils.Attachment attachment : attachments) {
            File file = File.createTempFile(attachment.getFilename(), message.getDisposition());
            Files.write(attachment.getContents(), file);
            ChangeItemBean changeItemBean = ComponentAccessor.getAttachmentManager().createAttachment(
                    new CreateAttachmentParamsBean.Builder(
                            file, attachment.getFilename(), attachment.getContentType(), user, issue
                    ).build());
            if (changeItemBean == null) {
                throw new FieldException("attachment is empty");
            }
        }
    }

    /**
     * This method handlers coming messages. If message subject is empty
     * return true.If message subject equals key of current issue via checks regex matcher.
     * Then attachments of message would be added to issue and body of message added to a text custom field of
     * item issue.
     * Else if message subject will not verify a regex matcher , then should be created a new issue
     * which would be filled of parameters contains  incoming message
     *
     * @param msg     incoming message
     * @param context for creation new issue , or new comment
     * @return true if message have been read from incoming message queue
     * @throws MessagingException
     */
    @SneakyThrows
    @Override
    public boolean handleMessage(Message msg, MessageHandlerContext context)
            throws MessagingException {

        messageTitle = msg.getSubject();
        if (messageTitle == null) {
            return true;
        }
        Pattern pattern = Pattern.compile(REGEX_EXP);
        Matcher matcher = pattern.matcher(messageTitle);
        if (matcher.find()) {
            String matchKey = matcher.group(0);
            MutableIssue updatedIssue = ComponentAccessor.getIssueManager().getIssueObject(matchKey);
            CustomField CFTextField = customFieldManager.getCustomFieldObject(CFieldId);
            updatedIssue.setCustomFieldValue(CFTextField, MailUtils.getBody(msg));
            makeAttachment(msg, updatedIssue, processor.getAuthorFromSender(msg));
        } else {
            ApplicationUser sender = processor.getAuthorFromSender(msg);
            if (sender == null) {
                sender = ComponentAccessor.getUserManager().getUserByKey(userKey);
            }
            MutableIssue newIssue = ComponentAccessor.getIssueFactory().getIssue();
            newIssue.setSummary(messageTitle);
            CustomField CF = customFieldManager.getCustomFieldObject(CFieldId);
            newIssue.setCustomFieldValue(CF, MailUtils.getBody(msg));
            newIssue.setProjectId(projectId);
            Project project = ComponentAccessor.getProjectManager().getProjectObj(projectId);
            newIssue.setIssueType(Objects.requireNonNull(project).getIssueTypes().stream().findFirst().orElse(null));
            newIssue.setPriorityId(PRIORITY);
            newIssue.setReporter(sender);
            Issue Issue = context.createIssue(sender, newIssue);
            makeAttachment(msg, Issue, sender);
            return true;
        }
        return true;
    }
}



