package com.atlassian.shele.shelePlugin.ao;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProjectDTO {
    private List<String> project;
    private String issueStatus;
}
