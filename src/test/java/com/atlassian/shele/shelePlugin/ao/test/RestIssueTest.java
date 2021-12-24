package com.atlassian.shele.shelePlugin.ao.test;

import com.atlassian.shele.shelePlugin.ao.ProjectDTO;
import com.atlassian.shele.shelePlugin.ao.ProjectEntityService;
import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;

/**
 * Unit test on RestIssueService
 */
public class RestIssueTest {

    @Mock
    private ProjectEntityService service;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    //Test have passed but appeared some warnings from mockito
    @Test
    public void shouldPassed() {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setProject(ImmutableList.of("project", "new wall"));
        projectDTO.setEventTypeIds(ImmutableList.of(1l, 2L, 3L));
        List<ProjectDTO> projectDTOList = service.saveEntity(projectDTO);

        given(service.saveEntity(projectDTO)).willReturn(projectDTOList);
        assertNotNull(projectDTOList);
    }
}
