package com.atlassian.shele.shelePlugin.ao;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapIssueMapper {

    IssueDTO toIssueDTO(IssueEntity entity);

}