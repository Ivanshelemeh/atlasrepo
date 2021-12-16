package com.atlassian.shele.shelePlugin.ao;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);
    ProjectDTO toDTO(ProjectEntity projectEntity);
  default   List<String> map(String value){
      return Collections.singletonList(value);
  }
}
