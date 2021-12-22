package com.atlassian.shele.shelePlugin.ao;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import net.java.ao.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

@Service
public class ProjectEntityService {
    private final ActiveObjects activeObjects;
    private final ProjectMapper mapper;

    public ProjectEntityService(@ComponentImport ActiveObjects activeObjects, ProjectMapper mapper) {
        this.activeObjects = checkNotNull(activeObjects);
        this.mapper = mapper;
    }

    public List<ProjectDTO> saveEntity(ProjectDTO dto) {
        List<ProjectEntity> projectEntityList = new ArrayList<>();
        List<String> stringList = dto.getProject();
        List<Long> longList = dto.getEventTypeIds();
        String convertString = String.valueOf(longList);
        stringList.forEach(s -> {
            ProjectEntity entity = activeObjects.create(ProjectEntity.class);
            entity.setProject(s);
            entity.setEventTypeId(convertString);
            entity.save();
            projectEntityList.add(entity);
        });

        return mapper.toListDTO(projectEntityList);

    }

    public void deleteEntities() {
        ProjectEntity[] projectEntities = activeObjects.find(ProjectEntity.class,
                Query.select());
        this.activeObjects.delete(projectEntities);
    }
}
