
package com.atlassian.shele.shelePlugin.ao.test;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.activeobjects.test.TestActiveObjects;
import com.atlassian.shele.shelePlugin.ao.IssueDTO;
import com.atlassian.shele.shelePlugin.ao.IssueEntity;
import com.atlassian.shele.shelePlugin.ao.MapIssueMapper;
import net.java.ao.EntityManager;
import net.java.ao.test.converters.NameConverters;
import net.java.ao.test.jdbc.Hsql;
import net.java.ao.test.jdbc.Jdbc;
import net.java.ao.test.junit.ActiveObjectsJUnitRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;

import java.sql.SQLException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Issue entity unit test
 */

@RunWith(ActiveObjectsJUnitRunner.class)
@Jdbc(Hsql.class)
@NameConverters
public class IssueTest {

    private ActiveObjects ao;
    private EntityManager entityManager;
    private final MapIssueMapper mapIssueMapper = Mappers.getMapper(MapIssueMapper.class);

    @Before
    public void setUp() throws SQLException {
        assertNotNull(entityManager);
        ao = new TestActiveObjects(entityManager);
        entityManager.migrate(IssueEntity.class);
    }

    @Test
    public void testIssue() throws Exception {
        assertEquals(0, ao.find(IssueEntity.class).length);
    }

    @Test
    public void updateIssueTestCase() throws Exception {
        IssueEntity issueEntity = ao.create(IssueEntity.class);
        issueEntity.setIssueId(1);
        issueEntity.setAuthorIssue("Phil Wallmart");
        issueEntity.save();
        IssueDTO dto = mapIssueMapper.toIssueDTO(issueEntity);
        assertEquals(dto.getIssueId(), issueEntity.getIssueId());
        assertEquals(1, ao.find(IssueEntity.class).length);
    }
}