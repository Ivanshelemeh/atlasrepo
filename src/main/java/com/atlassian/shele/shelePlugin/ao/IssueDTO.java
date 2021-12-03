package com.atlassian.shele.shelePlugin.ao;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class IssueDTO {
    public String authorIssue;
    private Integer projectId;
    private Integer issueId;
    private String field;
    private Date createdTime;
    private String newChange;
    private String prevChange;
    private String workFlow;

}
