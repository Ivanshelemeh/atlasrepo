package com.atlassian.shele.shelePlugin.handlers;

import com.atlassian.jira.plugins.mail.HandlerDetailsValidator;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.user.util.UserManager;
import com.atlassian.jira.user.util.UserUtil;

public class MailHandlerDetailsValidator extends HandlerDetailsValidator {

    public MailHandlerDetailsValidator(UserManager userManager, JiraAuthenticationContext authenticationContext, UserUtil userUtil) {
        super(userManager, authenticationContext, userUtil);
    }
}
