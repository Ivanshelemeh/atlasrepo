package com.atlassian.shele.shelePlugin.utilit;

import org.codehaus.jackson.map.ObjectMapper;

import java.util.regex.Pattern;

public final class Utilities {
    public final static String SELECT_LOAD = "select";
    public final static String SELECT_ERROR = "error_select";
    public final static String SUCCESS_LOAD = "success";
    public final static String ERROR_LOAD = "error";
    public final static String PROJECT_NAME = "prod";
    public final static String EVENT = "event";
    public final static String ENT = "entity";
    public final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    public final static String CUST_FIELD = "assignee";
    public final static String FIELD_NAME = "mail text";
    public final static String REGEX_EXP = "((?<!([A-Z0-9]{1,10})-?)[A-Z0-9]+-\\d+)";
    public final static String CONFIG_HANDLER = "input";
    public final static String FILTER_CF = "com.atlassian.jira.plugin.system.customfieldtypes:textarea";
    public final static String PROJECT_ID = "projectId";
    public final static String USER_KEY = "userName";
    public final static String CUSTOM_FIELD_ID = "customFieldId";
    public final static String PRIORITY = "High";
    public final static String ERROR_PAGE = "securitybreach";
    public final static String PROJECTS = "projects";
    public final static String CUSTOM_FIELDS = "custom_fields";
    public final static String APP_USERS = "users";
    public final static Pattern PATTERN = Pattern.compile(REGEX_EXP);
    public final static String ISSUE_TYPE = "issueTypeId";
    public final static String PROJECT_KEY = "project";
    public final static String ISSUE_TYPE_KEY="issuetype";
}
