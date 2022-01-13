package com.atlassian.shele.shelePlugin.utilit;

import org.codehaus.jackson.map.ObjectMapper;

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
}
