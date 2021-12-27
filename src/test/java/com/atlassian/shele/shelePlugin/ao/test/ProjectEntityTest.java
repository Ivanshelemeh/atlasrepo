
package com.atlassian.shele.shelePlugin.ao.test;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.activeobjects.test.TestActiveObjects;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.shele.shelePlugin.ao.ProjectEntity;
import lombok.SneakyThrows;
import net.java.ao.EntityManager;
import net.java.ao.Query;
import net.java.ao.test.converters.NameConverters;
import net.java.ao.test.jdbc.Hsql;
import net.java.ao.test.jdbc.Jdbc;
import net.java.ao.test.junit.ActiveObjectsJUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(ActiveObjectsJUnitRunner.class)
@Jdbc(Hsql.class)
@NameConverters
public class ProjectEntityTest {

    @ComponentImport
    private ActiveObjects objects;
    private EntityManager entityManager;

    @Before
    public void setUp() throws SQLException {
        assertNotNull(entityManager);
        objects = new TestActiveObjects(entityManager);
        entityManager.migrate(ProjectEntity.class);
    }

    @After
    public void tearDown(){
        entityManager.flushAll();
    }

    @SneakyThrows
    @Test
    public void projectEntityTest() {
        ProjectEntity projectEntity = objects.create(ProjectEntity.class);
        projectEntity.setProject("Pro");
        projectEntity.setEventTypeId("22");
        projectEntity.save();
        assertEquals(1, objects.find(ProjectEntity.class).length);

    }

    @Test
    public void isEmptyProjectEntitiesTest() throws Exception {
        ProjectEntity[] projectEntities = objects.find(ProjectEntity.class, Query.select());
        this.objects.delete(projectEntities);
        assertEquals(0, objects.find(ProjectEntity.class).length);
    }
}
