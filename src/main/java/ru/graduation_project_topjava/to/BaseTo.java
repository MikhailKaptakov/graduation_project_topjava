package ru.graduation_project_topjava.to;

import ru.graduation_project_topjava.HasId;

public abstract class BaseTo implements HasId {
    protected Long id;

    public BaseTo() {
    }

    public BaseTo(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
