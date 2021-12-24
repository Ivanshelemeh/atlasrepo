
package com.atlassian.shele.shelePlugin.ao.test;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.activeobjects.test.TestActiveObjects;
import com.atlassian.shele.shelePlugin.ao.ProjectEntity;
import lombok.SneakyThrows;
import net.java.ao.EntityManager;
import net.java.ao.Query;
import net.java.ao.test.converters.NameConverters;
import net.java.ao.test.jdbc.Hsql;
import net.java.ao.test.jdbc.Jdbc;
import net.java.ao.test.junit.ActiveObjectsJUnitRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

@RunWith(ActiveObjectsJUnitRunner.class)
@Jdbc(Hsql.class)
@NameConverters
public class ProjectEntityTest {

    private ActiveObjects objects;
    private EntityManager manager;

    @Before
    public void setUp() throws Exception {
        assertNotNull(manager);
        objects = new TestActiveObjects(manager);
        manager.migrate(ProjectEntity.class);
    }

    @SneakyThrows
    @Test
    public void projectEntityTest() {
        ProjectEntity projectEntity = objects.create(ProjectEntity.class);
        projectEntity.setProject("Pro");
        projectEntity.setEventTypeId("22");
        projectEntity.save();
        assertEquals(1, manager.find(ProjectEntity.class).length);
    }

    @Test
    public void isEmptyProjectEntitiesTest() throws Exception {
        ProjectEntity[] projectEntities = objects.find(ProjectEntity.class, Query.select());
        this.objects.delete(projectEntities);
        assertEquals(0, manager.find(ProjectEntity.class).length);
    }
}
