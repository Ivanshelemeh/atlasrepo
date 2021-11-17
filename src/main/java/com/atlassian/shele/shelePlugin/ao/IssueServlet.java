package com.atlassian.shele.shelePlugin.ao;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.sal.api.transaction.TransactionCallback;
import org.springframework.context.annotation.ComponentScan;

import javax.inject.Inject;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

@ComponentScan
public class IssueServlet extends HttpServlet {


    private final ActiveObjects ao;

    @Inject
    public IssueServlet(ActiveObjects ao) {
        this.ao = checkNotNull(ao);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        IssueEntity issueEntity = ao.create(IssueEntity.class);
        issueEntity.setIssueId(23);
        issueEntity.setAuthorIssue("Fill");
        issueEntity.save();
     //   response.getWriter().write(issueEntity.getField());
  //      response.getWriter().close();

        }



    }


