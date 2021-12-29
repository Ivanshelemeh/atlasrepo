package com.atlassian.shele.shelePlugin.integration.test;
/**
import com.atlassian.jira.functest.framework.BaseJiraFuncTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SimpleExampleTest extends BaseJiraFuncTest {

    @Before
    public void setUp() {
        backdoor.project().addProject("testproject", "KP", "admin");
        backdoor.issues().createIssue("KP", "Test Issue");
    }

    @Test
    public void goIssueNavigatorTest() {
        navigation.login("admin");
        navigation.issueNavigator().createSearch("project= KP");
        tester.assertTextPresent("KP-1");

    }
    @After
    public void cleanUp(){
        backdoor.project().deleteProject("KP");
    }


}
*/
