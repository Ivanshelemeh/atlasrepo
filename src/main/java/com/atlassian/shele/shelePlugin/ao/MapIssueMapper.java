package com.atlassian.shele.shelePlugin.ao;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface  MapIssueMapper {
    MapIssueMapper INSTANCE = Mappers.getMapper(MapIssueMapper.class);
    IssueDTO toIssueDTO(IssueEntity entity);
}