package com.atlassian.shele.shelePlugin.ao.test;

import com.atlassian.shele.shelePlugin.ao.ProjectDTO;
import com.atlassian.shele.shelePlugin.ao.ProjectEntityService;
import com.atlassian.shele.shelePlugin.rest.RestIssueService;
import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Unit test via mock of ProjectEntityService
 */
@RunWith(MockitoJUnitRunner.class)
public class RestIssueTest {

    private ProjectEntityService projectEntityService = Mockito.mock(ProjectEntityService.class);

    @InjectMocks
    private RestIssueService restIssueService = new RestIssueService(projectEntityService);

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void should_be_Equals_DTO_Entity() {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setProject(ImmutableList.of("project", "new wall"));
        projectDTO.setEventTypeIds(ImmutableList.of(1L, 2L, 3L));
        List<String> projectDTOList = projectDTO.getProject();
        assertEquals(projectDTOList, projectDTO.getProject());
    }

    @Test
    public void verify_ProjectDTO_test() {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setProject(ImmutableList.of("some project"));
        projectDTO.setEventTypeIds(ImmutableList.of(2L, 4L));
        List<ProjectDTO> projectDTOCollection = projectEntityService.saveEntity(projectDTO);
        Mockito.when(projectEntityService.saveEntity(projectDTO)).thenReturn((projectDTOCollection));
        Mockito.verify(projectEntityService, Mockito.times(1)).saveEntity(Mockito.anyObject());
    }
}
