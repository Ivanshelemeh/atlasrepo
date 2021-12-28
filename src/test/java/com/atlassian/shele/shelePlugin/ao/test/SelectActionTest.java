package com.atlassian.shele.shelePlugin.ao.test;

import com.atlassian.sal.api.project.ProjectManager;
import com.atlassian.shele.shelePlugin.ao.ProjectDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SelectActionTest {

    final ProjectManager projectManager = Mockito.mock(ProjectManager.class);

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void mockProject_managerTest() {
        Collection<String> mockCollection = projectManager.getAllProjectKeys();
        Mockito.when(projectManager.getAllProjectKeys()).thenReturn(mockCollection);
        assertNotNull(mockCollection);
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setProject((List<String>) mockCollection);
        assertEquals(mockCollection, projectDTO.getProject());
    }
}

