package com.atlassian.shele.shelePlugin.ao;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import net.java.ao.Query;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

@Service
public class ProjectEntityService {
    private final ActiveObjects activeObjects;



    public ProjectEntityService(@ComponentImport ActiveObjects activeObjects) {
        this.activeObjects = checkNotNull(activeObjects);

    }

    public ProjectEntity saveEntity(ProjectDTO dto) {
        List<String> list = dto.getProject();
        List<Long> longs = dto.getEventTypeIds();
        ProjectEntity entity = activeObjects.create(ProjectEntity.class);
        for (String s : list) {
            entity.setProject(s);
            entity.setEventTypeId(longs.toString());
            entity.save();

        }
        return entity;

    }

    public void deleteEntities() {
        ProjectEntity[] projectEntities = activeObjects.find(ProjectEntity.class,
                Query.select());
        this.activeObjects.delete(projectEntities);
    }
}
