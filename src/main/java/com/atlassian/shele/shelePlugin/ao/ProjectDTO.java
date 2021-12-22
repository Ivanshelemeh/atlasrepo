package com.atlassian.shele.shelePlugin.ao;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ProjectDTO {
    private List<String> project;
    private List<Long> eventTypeIds;
}
