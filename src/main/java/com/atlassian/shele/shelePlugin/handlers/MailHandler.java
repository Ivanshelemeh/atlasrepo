package com.atlassian.shele.shelePlugin.handlers;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.CustomFieldManager;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.MutableIssue;
import com.atlassian.jira.issue.attachment.CreateAttachmentParamsBean;
import com.atlassian.jira.issue.fields.CustomField;
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

import static com.atlassian.shele.shelePlugin.utilit.Utilities.REGEX_EXP;

public class MailHandler implements MessageHandler {

    private final CustomFieldManager customFieldManager;
    private final MessageUserProcessor processor;

    public MailHandler(@ComponentImport CustomFieldManager customFieldManager, @ComponentImport MessageUserProcessor processor) {
        this.customFieldManager = customFieldManager;
        this.processor = processor;
    }

    @Override
    public void init(Map<String, String> params, MessageHandlerErrorCollector messageerror) {

    }

    @SneakyThrows
    @Override
    public boolean handleMessage(Message msg, MessageHandlerContext context)
            throws MessagingException {

        String inputParameter = msg.getSubject();
        Pattern pattern = Pattern.compile(REGEX_EXP);
        Matcher matcher = pattern.matcher(inputParameter);
        if (matcher.find()) {
            String matchKey = matcher.group(inputParameter);
            MutableIssue upDatedIssue = ComponentAccessor.getIssueManager().getIssueObject(matchKey);
            CustomField CFTextField = customFieldManager.getCustomFieldObject(10500L);
            upDatedIssue.setCustomFieldValue(CFTextField, MailUtils.getBody(msg));
            MailUtils.Attachment[] attachments = MailUtils.getAttachments(msg);
            for (MailUtils.Attachment attachment : attachments) {
                File outFile = File.createTempFile(attachment.getFilename(), attachment.getContentType());
                Files.write(attachment.getContents(), outFile);
                ComponentAccessor.getAttachmentManager().createAttachment(new CreateAttachmentParamsBean.Builder(
                        outFile, attachment.getFilename(),
                        attachment.getContentType(), processor.getAuthorFromSender(msg),
                        upDatedIssue
                ).build());
            }

        } else {
            ApplicationUser sender = processor.getAuthorFromSender(msg);
            if (sender == null) {
                sender = ComponentAccessor.getUserManager().getUserByKey("admin");
            }
            MutableIssue mutableIssue = ComponentAccessor.getIssueFactory().getIssue();

            mutableIssue.setSummary(msg.getSubject());
            CustomField cfMailText = customFieldManager.getCustomFieldObject(10500L);
            mutableIssue.setCustomFieldValue(cfMailText, MailUtils.getBody(msg));
            mutableIssue.setProjectId(10000L);
            mutableIssue.setIssueType(Objects.requireNonNull(ComponentAccessor.getProjectManager()
                    .getProjectObj(10000L)).getIssueTypes().stream().findFirst().orElse(null));
            mutableIssue.setPriorityId("High");
            Issue newIssue = context.createIssue(sender, mutableIssue);

            //TODO refactor method from if statment
            MailUtils.Attachment[] attachments = MailUtils.getAttachments(msg);
            for (MailUtils.Attachment attachment : attachments) {
                File file = File.createTempFile("file", "jpg");
                Files.write(attachment.getContents(), file);
                ComponentAccessor.getAttachmentManager().createAttachment(new CreateAttachmentParamsBean.Builder(
                        file, attachment.getFilename(), attachment.getContentType(), sender, newIssue
                ).build());
            }
            return true;
        }
        return true;
    }
}

