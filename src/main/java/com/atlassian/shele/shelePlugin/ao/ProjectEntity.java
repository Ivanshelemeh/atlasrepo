package com.atlassian.shele.shelePlugin.ao;

import net.java.ao.Entity;
import net.java.ao.schema.StringLength;
import net.java.ao.schema.Table;

@Table("PROJECT_ENTITY")
public interface ProjectEntity extends Entity {

    @StringLength(StringLength.UNLIMITED)
    String getProject();

    void setProject(String project);

    String getEventTypeId();

    void setEventTypeId(String eventTypeId);
}
