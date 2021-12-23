package com.atlassian.shele.shelePlugin.ao;

import lombok.SneakyThrows;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Mapping(target = "eventTypeIds", source = "eventTypeId", qualifiedByName = "myName")
    ProjectDTO toDTO(ProjectEntity projectEntity);

    List<ProjectDTO> toListDTO(List<ProjectEntity> list);

    default List<String> map(String value) {
        return Collections.singletonList(value);
    }

    @Named("myName")
    @SneakyThrows
    default List<Long> mapToLong(String value) {
        return OBJECT_MAPPER.reader().withType(new TypeReference<ArrayList<Long>>() {
        }).readValue(value);
    }
}
