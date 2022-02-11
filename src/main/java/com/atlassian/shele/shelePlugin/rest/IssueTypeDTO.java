package com.atlassian.shele.shelePlugin.rest;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class IssueTypeDTO {

    private List<Result> results;
}
