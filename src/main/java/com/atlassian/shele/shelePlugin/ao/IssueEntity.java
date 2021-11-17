package com.atlassian.shele.shelePlugin.ao;

import com.atlassian.jira.issue.Issue;
import net.java.ao.*;
import net.java.ao.schema.*;

import java.util.Collection;
import java.util.Date;

@Table("ISSUE_MUTATOR")
public interface IssueEntity extends Entity {


    String getAuthorIssue();
    void setAuthorIssue(String authorIssue);

    Integer getProjectId();
    void setProjectId(Integer projectId);


    Integer getIssueId();

    void  setIssueId(Integer issueId);

    @Default("default")
    Issue getField();
    void setField(String field);


    Date getCreatedTime();
    void setCreatedTime(Date date);


  String  getNewField();
    void  setNewField(String newField);


    String prevField();
   void setPrevField(String prevField);

}
