package ru.graduation_project_topjava.util.validation;

import ru.graduation_project_topjava.HasId;
import ru.graduation_project_topjava.model.AbstractBaseEntity;
import ru.graduation_project_topjava.util.exception.NotFoundException;

public class ValidationUtil {

    private ValidationUtil() {
    }

    public static <T> T checkNotFoundWithId(T object, long id) {
        checkNotFoundWithId(object != null, id);
        return object;
    }

    public static void checkNotFoundWithId(boolean found, long id) {
        checkNotFound(found, "id=" + id);
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }

    public static void checkNew(HasId entity) {
        if (!entity.itsNew()) {
            throw new IllegalArgumentException(entity + " must be new (id=null)");
        }
    }

    public static void checkHaveId(AbstractBaseEntity entity) {
        if (entity.itsNew()) {
            throw new IllegalArgumentException(entity + " haven't id");
        }
    }

    public static void assureIdConsistent(AbstractBaseEntity entity, long id) {
//      conservative when you reply, but accept liberally (http://stackoverflow.com/a/32728226/548473)
        if (entity.itsNew()) {
            entity.setId(id);
        } else if (entity.getId() != id) {
            throw new IllegalArgumentException(entity + " must be with id=" + id);
        }
    }

}
