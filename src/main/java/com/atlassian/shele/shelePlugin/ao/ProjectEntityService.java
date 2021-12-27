package com.atlassian.shele.shelePlugin.ao;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import net.java.ao.Query;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;
import static java.util.stream.Collectors.toList;

@Service
public class ProjectEntityService {

    private final ActiveObjects activeObjects;
    private final ProjectMapper mapper;

    public ProjectEntityService(@ComponentImport ActiveObjects activeObjects, ProjectMapper mapper) {
        this.activeObjects = checkNotNull(activeObjects);
        this.mapper = mapper;
    }

    public List<ProjectDTO> saveEntity(ProjectDTO dto) {
        List<String> projectKeys = dto.getProject();
        List<Long> eventTypeIds = dto.getEventTypeIds();
        String convertString = String.valueOf(eventTypeIds);
        List<ProjectEntity> projectEntityList = projectKeys.stream().map(s -> {
            ProjectEntity projectEntity = activeObjects.create(ProjectEntity.class);
            projectEntity.setProject(s);
            projectEntity.setEventTypeId(convertString);
            projectEntity.save();
            return projectEntity;
        }).collect(toList());

        return mapper.toListDTO(projectEntityList);

    }

    public void deleteEntities() {
        ProjectEntity[] projectEntities = activeObjects.find(ProjectEntity.class,
                Query.select());
        this.activeObjects.delete(projectEntities);
    }
}
