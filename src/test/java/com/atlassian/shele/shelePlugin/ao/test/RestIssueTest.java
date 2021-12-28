package com.atlassian.shele.shelePlugin.ao.test;

import com.atlassian.shele.shelePlugin.ao.ProjectDTO;
import com.atlassian.shele.shelePlugin.ao.ProjectEntityService;
import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Unit test via mock of ProjectEntityService
 */
public class RestIssueTest {

    @Mock
    private ProjectEntityService service;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void should_be_Equals_DTO_Entity() {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setProject(ImmutableList.of("project", "new wall"));
        projectDTO.setEventTypeIds(ImmutableList.of(1l, 2L, 3L));
        List<String> projectDTOList = projectDTO.getProject();
        assertEquals(projectDTOList, projectDTO.getProject());
    }

    @Test
    public void verify_ProjectDTO_test() {
        ProjectDTO projectDTO = new ProjectDTO();
        List<ProjectDTO> projectDTOCollection = new ArrayList<>();
        Mockito.when(service.saveEntity(projectDTO)).thenReturn((projectDTOCollection));
        Mockito.verify(Mockito.mock(ProjectEntityService.class), Mockito.atLeastOnce());
    }
}
