package ru.graduation_project_topjava;

import org.springframework.util.Assert;

public interface HasId {
    Long getId();

    void setId(Long id);

    default boolean itsNew() {
        return getId() == null;
    }

    // doesn't work for hibernate lazy proxy
    default Long id() {
        Assert.notNull(getId(), "Entity must has id");
        return getId();
    }
}
