package com.atlassian.shele.shelePlugin.ao;

import net.java.ao.Entity;
import net.java.ao.schema.StringLength;
import net.java.ao.schema.Table;

import java.util.Date;

@Table("ISSUE_MUTATOR")
public interface IssueEntity extends Entity {


    String getAuthorIssue();
    void setAuthorIssue(String authorIssue);

    Integer getProjectId();
    void setProjectId(Integer projectId);


    Integer getIssueId();
    void  setIssueId(Integer issueId);

    String getField();
    void setField(String field);


    Date getCreatedTime();
    void setCreatedTime(Date date);


  String  getNewField();
    void  setNewField(String newField);


    String getPrevField();
   void setPrevField(String prevField);


    @StringLength(StringLength.UNLIMITED)
    String getWorkFlow();
    void setWorkFlow(String workFlow );

    Long getEvent();
    void setEvent(Long event);

}
