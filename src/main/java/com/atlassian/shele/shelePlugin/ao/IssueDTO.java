package com.atlassian.shele.shelePlugin.ao;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * It is DTO model for
 * IssueEntity entity
 */
@Getter
@Setter
@ToString
public class IssueDTO {
    public String authorIssue;
    private Integer projectId;
    private Integer issueId;
    private String field;
    private Date createdTime;
    private String newField;
    private String prevField;
    private Long event;
}
