package ru.graduation_project_topjava.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@MappedSuperclass
public abstract class AbstractBaseNamedEntity extends AbstractBaseEntity {

    public static final LocalDate MIN = LocalDate.of(1,1,1);

    @NotBlank (message = "Name is mandatory")
    @Size(min = 2, max = 128)
    @Column(name = "name", nullable = false)
    protected String name;

    public AbstractBaseNamedEntity() {
    }

    public AbstractBaseNamedEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
