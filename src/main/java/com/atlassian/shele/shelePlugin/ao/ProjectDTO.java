package com.atlassian.shele.shelePlugin.ao;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProjectDTO {
    private String project;
    private String issueStatus;
}
