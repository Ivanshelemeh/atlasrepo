package com.atlassian.shele.shelePlugin.utilit;

public final class Utilities {
    private final static String SELECT_LOAD = "select";
    private final static String SELECT_ERROR = "error1";
    private final static String SUCCESS_LOAD= "success";
    private final static String ERROR_LOAD="error";


    public static String getSelectLoad(){
        return SELECT_LOAD;
    }
    public static String getSelectError(){
        return SELECT_ERROR;
    }
    public static String getSuccessLoad(){
        return SUCCESS_LOAD;
    }
    public static String getErrorLoad(){
        return ERROR_LOAD;
    }

}
